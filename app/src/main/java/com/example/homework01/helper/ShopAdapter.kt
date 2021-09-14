package com.example.homework01.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework01.R
import com.example.homework01.models.ShopData
import com.squareup.picasso.Picasso

class ShopAdapter (private val shopList: List<ShopData.Shop>) :RecyclerView.Adapter<ShopAdapter.shopViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): shopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shop_row_layout, parent, false)
        return shopViewHolder(view)
    }

    override fun onBindViewHolder(holder: shopViewHolder, position: Int) {
        return holder.bind(shopList[position])
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    class shopViewHolder (itemView : View) :RecyclerView.ViewHolder(itemView){

        //Find id's
        var backgroundImage = itemView.findViewById<ImageView>(R.id.main_img_iv)
        var iconImage = itemView.findViewById<ImageView>(R.id.brand_img_iv)
        val brandName = itemView.findViewById<TextView>(R.id.brand_name_tv)
        var orderNumber = itemView.findViewById<TextView>(R.id.order_number_tv)

        fun bind(shop: ShopData.Shop) {

            //Set data from database
            brandName.text = shop.name
            orderNumber.text = shop.orderNo.toString()
            Picasso.get().load(shop.backgroundUrl).into(backgroundImage)
            Picasso.get().load(shop.logoUrl).into(iconImage)

        }

    }

}