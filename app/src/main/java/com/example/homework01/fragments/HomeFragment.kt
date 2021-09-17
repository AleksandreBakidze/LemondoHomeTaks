package com.example.homework01.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework01.databinding.FragmentHomeBinding
import com.example.homework01.helper.Constants
import com.example.homework01.helper.Constants.CLIENT_ID
import com.example.homework01.helper.Constants.CLIENT_SECRET
import com.example.homework01.helper.Constants.GRANT_TYPE
import com.example.homework01.helper.Constants.SCOPE
import com.example.homework01.helper.ShopAdapter
import com.example.homework01.helper.StoreToken
import com.example.homework01.models.AuthToken
import com.example.homework01.models.Shop
import com.example.homework01.models.ShopData
import com.example.homework01.services.AuthInterceptor
import com.example.homework01.services.ShopService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var shopAdapter: ShopAdapter

    lateinit var authInterceptor: AuthInterceptor

    private lateinit var  retrofitBuilder: ShopService
    private lateinit var lemondoArrayList: ArrayList<Shop>
    private lateinit var lemondoRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        lemondoRecyclerView = binding.recyclerViewId
        lemondoRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
       // lemondoRecyclerView.setHasFixedSize(true)

        lemondoArrayList = arrayListOf<Shop>()

        getData()

        return binding.root
    }

    private fun getData() {
        authInterceptor = AuthInterceptor()

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(OkHttpClient().newBuilder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor).build())
            .build()
            .create(ShopService::class.java)

        val tokenData = retrofitBuilder.getToken(GRANT_TYPE, CLIENT_ID, CLIENT_SECRET, SCOPE)
        tokenData.enqueue(object : Callback<AuthToken> {
            override fun onResponse(call: Call<AuthToken>, response: Response<AuthToken>) {
                if (response.isSuccessful){
                    val token = response.body()?.access_token!!
                    StoreToken.saveToken(token)
                    getShop()
                }
            }

            override fun onFailure(call: Call<AuthToken>, t: Throwable) {
                Log.e("error", "${t.message}")
            }
        })
    }

    fun getShop() {
        val retrofitData = retrofitBuilder.getShopList()

        retrofitData.enqueue(object : Callback<ShopData> {
            override fun onResponse(call: Call<ShopData>?, response: Response<ShopData>?) {

                val responseBody = response?.body()?.shops

                shopAdapter = ShopAdapter(responseBody!!)
                shopAdapter.notifyDataSetChanged()
                lemondoRecyclerView.adapter = shopAdapter

            }
            override fun onFailure(call: Call<ShopData>?, t: Throwable) {
                Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
