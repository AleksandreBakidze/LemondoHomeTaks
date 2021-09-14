package com.example.homework01.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework01.databinding.ActivityMainBinding
import com.example.homework01.helper.ShopAdapter
import com.example.homework01.models.ShopData
import com.example.homework01.services.ServiceBuilder
import com.example.homework01.services.ShopService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var lemondoRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //call recycler
//        lemondoRecyclerView = binding.recyclerViewId
//        lemondoRecyclerView.layoutManager = LinearLayoutManager(this)
//        lemondoRecyclerView.setHasFixedSize(true)

//        loadShops()

        //initiate the service
        val destinationService  = ServiceBuilder.buildService(ShopService::class.java)
        val requestCall =destinationService.getShopList()
        //make network call asynchronously
        requestCall.enqueue(object : Callback<List<ShopData.Shop>>{
            override fun onResponse(call: Call<List<ShopData.Shop>>, response: Response<List<ShopData.Shop>>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    val countryList  = response.body()!!
                    Log.d("Response", "countrylist size : ${countryList.size}")
                    binding.recyclerViewId.apply {
                        setHasFixedSize(true)
                        layoutManager = GridLayoutManager(this@MainActivity,2)
                        adapter = ShopAdapter(response.body()!!)
                    }
                }else{
                    Toast.makeText(this@MainActivity, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<ShopData.Shop>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }
        })

    }

//    private fun loadShops(){
//        //initiate the service
//        val destinationService  = ServiceBuilder.buildService(ShopService::class.java)
//        val requestCall =destinationService.getShopList()
//        //make network call asynchronously
//        requestCall.enqueue(object : Callback<List<ShopData.Shop>>{
//            override fun onResponse(call: Call<List<ShopData.Shop>>, response: Response<List<ShopData.Shop>>) {
//                Log.d("Response", "onResponse: ${response.body()}")
//                if (response.isSuccessful){
//                    val countryList  = response.body()!!
//                    Log.d("Response", "countrylist size : ${countryList.size}")
//                    binding.recyclerViewId.apply {
//                        setHasFixedSize(true)
//                        layoutManager = GridLayoutManager(this@MainActivity,2)
//                        adapter = ShopAdapter(response.body()!!)
//                    }
//                }else{
//                    Toast.makeText(this@MainActivity, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
//                }
//            }
//            override fun onFailure(call: Call<List<ShopData.Shop>>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "Something went wrong $t", Toast.LENGTH_SHORT).show()
//            }
//        })



}