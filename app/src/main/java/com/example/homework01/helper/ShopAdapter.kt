package com.example.homework01.helper

import android.icu.text.SimpleDateFormat
import android.icu.util.LocaleData
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

class ShopAdapter(private val shopList: List<Shop>) :
    RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

    private val workingHour: MutableList<WorkingHour> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.shop_row_layout, parent, false)
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

        //yyyy MM dd hh:mm:ss

        fun convertDate(date: String): Long{
            val formatter2 = DateTimeFormatter.ofPattern("EEEE MMM dd yyyy HH:mm:ss")
            val localDateTime = LocalDateTime.parse(date, formatter2)
            return localDateTime.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()
        }

        //Get Current Day Int Mon = 1 , Thu = 2 ...
        var currentDate = Calendar.getInstance()
        var currentDay = currentDate[Calendar.DAY_OF_WEEK]
        //To start from monday
        currentDay--

        //current date
        var currentTime = Calendar.getInstance().time
        var formatter = SimpleDateFormat("EEEE MMM dd yyyy HH:mm:ss")
        var formatedData = formatter.format(currentTime)
        var currentLastDate = convertDate(formatedData)

        //api date open
        val apiTime = currentPosition.workingHours[position].from
        val formatter1 = SimpleDateFormat("EEEE MMM dd yyyy ")
        val apiDate = formatter1.format(currentTime)
        //val apiDataFormatted = SimpleDateFormat("HH:mm:ss")
        val finalOpen = "$apiDate $apiDate"
        var openLastDate = convertDate(finalOpen)

        //api date close
        val apiTime1 = currentPosition.workingHours[position].to
        val formatter2 = SimpleDateFormat("EEEE MMM dd yyyy ")
        val apiDate1 = formatter2.format(currentTime)
        //val apiDataFormatter1 = SimpleDateFormat("HH:mm:ss")
        val finalClose = "$apiDate1 $apiDate1"
        var closeLastDate = convertDate(finalClose)

        //Range start to end
        val range = openLastDate..closeLastDate

        //AM or PM
        val dayOrNight = currentDate[Calendar.AM_PM]

        // open and close time
        val openTime = currentPosition.workingHours[position].from
        val closeTime = currentPosition.workingHours[position].to

        //Working or not
        var isWorking = currentPosition.workingHours[position].working

        for (item in currentPosition.workingHours) {
            //val range = finalOpen..finalClose

            //Checking if shop is open
            if (isWorking) {
                holder.moonIcon.isVisible = false
                holder.openingTime.isVisible = false
                holder.planeOrder.isVisible = false
                holder.closedBlur.isVisible = false
            }
        }

//        var millis: Long = currentTime
//        var hhmmss: String = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
//            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
//            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))

        Log.e("Formatter1", "$closeLastDate")

        holder.itemView.context
    }

    override fun getItemCount(): Int {
        return shopList.size - 1
    }

    inner class ShopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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