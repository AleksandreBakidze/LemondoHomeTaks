package com.example.homework01.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework01.databinding.FragmentHomeBinding
import com.example.homework01.helper.ShopAdapter
import com.example.homework01.models.ShopData
import com.example.homework01.services.ShopService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://moitane-api.lemon.do/"

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var shopAdapter: ShopAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.recyclerViewId.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerViewId.layoutManager = linearLayoutManager

        getData()

        return binding.root
    }

    private fun getData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ShopService::class.java)

        val retrofitData = retrofitBuilder.getShopList()

        retrofitData.enqueue(object : Callback<List<ShopData.Shop>?> {
            override fun onResponse(call: Call<List<ShopData.Shop>?>, response: Response<List<ShopData.Shop>?>) {
                val responseBody = response.body()!!

                shopAdapter = ShopAdapter(activity!!.baseContext, responseBody)
                shopAdapter.notifyDataSetChanged()
                binding.recyclerViewId.adapter = shopAdapter

            }
            override fun onFailure(call: Call<List<ShopData.Shop>?>, t: Throwable) {
                Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}