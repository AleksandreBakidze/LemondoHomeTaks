package com.example.homework01.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var lemondoRecyclerView: RecyclerView
    //private lateinit var lemondoArrayList: ArrayList<LookingPersonDataClass>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //call recycler
        lemondoRecyclerView = binding.recyclerViewId
        lemondoRecyclerView.layoutManager =LinearLayoutManager(this)
        lemondoRecyclerView.setHasFixedSize(true)

    }
}