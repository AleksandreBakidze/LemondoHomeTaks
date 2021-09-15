package com.example.homework01.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework01.databinding.ActivityMainBinding
import com.example.homework01.helper.ShopAdapter
import com.example.homework01.models.ShopData
import com.example.homework01.services.ServiceBuilder
import com.example.homework01.services.ShopService
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        setupBottomNavigationMenu()
    }

    //For bottom nav
    private fun setupBottomNavigationMenu() {
        binding.bottomNavigation.setupWithNavController(binding.shopNavHostFragment.findNavController())
    }
}