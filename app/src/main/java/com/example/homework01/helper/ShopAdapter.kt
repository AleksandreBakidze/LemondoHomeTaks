package com.example.homework01.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework01.R
import com.example.homework01.models.Shop
import com.example.homework01.models.ShopData

class ShopAdapter (private val shopList: List<Shop>) :RecyclerView.Adapter<ShopAdapter.ShopViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shop_row_layout, parent, false)
        return ShopViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val currentPosition = shopList[position]
        holder.brandName.text = currentPosition.name
        holder.orderNumber.text = currentPosition.orderNo.toString()

        holder.itemView
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    class ShopViewHolder (itemView : View) :RecyclerView.ViewHolder(itemView){

        //Find id's From row
        var backgroundImage: ImageView = itemView.findViewById(R.id.main_img_iv)
        var iconImage: ImageView = itemView.findViewById(R.id.brand_img_iv)
        val brandName: TextView = itemView.findViewById(R.id.brand_name_tv)
        var orderNumber: TextView = itemView.findViewById(R.id.order_number_tv)

    }
}