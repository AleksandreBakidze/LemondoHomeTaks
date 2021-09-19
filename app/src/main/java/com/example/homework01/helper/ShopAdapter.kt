package com.example.homework01.helper

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework01.R
import com.example.homework01.fragments.HomeFragment
import com.example.homework01.models.Shop
import com.example.homework01.models.ShopData
import com.example.homework01.models.WorkingHour
import com.squareup.picasso.Picasso
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

class ShopAdapter (private val shopList: List<Shop>) :RecyclerView.Adapter<ShopAdapter.ShopViewHolder>(){

    private val workingHour: MutableList<WorkingHour> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shop_row_layout, parent, false)
        return ShopViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val currentPosition = shopList[position]

        //Get and Send Name and orderNo for list
        holder.brandName.text = currentPosition.name
        holder.orderNumber.text = currentPosition.orderNo.toString()

        //get and send image
        Picasso.get().load(currentPosition.backgroundUrl).into(holder.backgroundImage)
        Picasso.get().load(currentPosition.logoUrl).into(holder.iconImage)

//TODO: Convert miliseconds to time
//        fun formatToDigitalClock(miliSeconds: Long): String {
//            val hours = TimeUnit.MILLISECONDS.toHours(miliSeconds).toInt() % 24
//            val minutes = TimeUnit.MILLISECONDS.toMinutes(miliSeconds).toInt() % 60
//            val seconds = TimeUnit.MILLISECONDS.toSeconds(miliSeconds).toInt() % 60
//            return when {
//                hours > 0 -> String.format("%d:%02d:%02d", hours, minutes, seconds)
//                minutes > 0 -> String.format("%02d:%02d", minutes, seconds)
//                seconds > 0 -> String.format("00:%02d", seconds)
//                else -> {
//                    "00:00"
//                }
//            }
//        }


        //Get Current Day Int Mon = 1 , Thu = 2 ...
        var currentDate = Calendar.getInstance()
        var currentDay = currentDate[Calendar.DAY_OF_WEEK]
        //To start from monday
        currentDay--

        var currentTime = Calendar.getInstance().timeInMillis

        //AM or PM
        val dayOrNight = currentDate[Calendar.AM_PM]

        // open and close time
        val openTime = currentPosition.workingHours[position].from
//        val openTimeMillisec = formatToDigitalClock(currentTime.toLong())
        val closeTime = currentPosition.workingHours[position].to

        //Working or not
        val isWorking = currentPosition.workingHours[position].working

        //holder.orderNumber.text = currentTime.toString()

        //Checking if shop is open
        if (currentDay == 1){
            holder.moonIcon.isVisible = false
            holder.openingTime.isVisible = false
            holder.planeOrder.isVisible = false
            holder.closedBlur.isVisible = false
        }

        //Log.e("currentTime", "$openTimeMillisec")

        holder.itemView.context

    }

    override fun getItemCount(): Int {
        return shopList.size - 1
    }

    inner class ShopViewHolder (itemView : View) :RecyclerView.ViewHolder(itemView){

        //Find id's From row
        var backgroundImage: ImageView = itemView.findViewById(R.id.main_img_iv)
        var iconImage: ImageView = itemView.findViewById(R.id.brand_img_iv)
        var brandName: TextView = itemView.findViewById(R.id.brand_name_tv)
        var orderNumber: TextView = itemView.findViewById(R.id.order_number_tv)

        //closed view
        var moonIcon: ImageView = itemView.findViewById(R.id.moon_icon_iv)
        var openingTime: TextView = itemView.findViewById(R.id.opening_time_tv)
        var planeOrder: Button = itemView.findViewById(R.id.plane_order_btn)
        var closedBlur: View = itemView.findViewById(R.id.closed_blur_v)

    }
}