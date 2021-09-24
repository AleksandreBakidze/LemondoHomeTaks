package com.example.homework01.helper

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.homework01.R
import com.example.homework01.models.Shop
import com.squareup.picasso.Picasso
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class ShopAdapter(private val shopList: List<Shop>) :
    RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

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

        //milliseconds converter
        fun convertDate(date: String): Long {
            val formatter2 = DateTimeFormatter.ofPattern("EEEE MMM dd yyyy HH:mm:ss")
            val localDateTime = LocalDateTime.parse(date, formatter2)
            return localDateTime.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()
        }

        //Get Current Day Int Mon = 1 , Thu = 2 ...
        var currentDate = Calendar.getInstance()
        var currentDay = currentDate[Calendar.DAY_OF_WEEK]
        //get current day index
        currentDay--

        //get and convert current date for converterDate
        val openApiDate = LocalDateTime.now() // full current local date
        val formatApiDate =
            DateTimeFormatter.ofPattern("EEEE MMM dd yyyy") //formatter for converter
        val finalFormatter = openApiDate.format(formatApiDate) //format

        //get and convert current data ad time to milliseconds
        val formatApiDataTime =
            DateTimeFormatter.ofPattern("EEEE MMM dd yyyy HH:mm:ss") // format and get data and time
        var finalFormatterDataTime =
            openApiDate.format(formatApiDataTime) // get current data and time
        val convertFinalApiDate = convertDate(finalFormatterDataTime) // convert it to milliseconds

        //api date open
        val openApiTime = currentPosition.workingHours[currentDay].from //open time from api
        val finalApiDateOpen = "$finalFormatter $openApiTime" // final group date and time
        val convertFinalApiDateOpen = convertDate(finalApiDateOpen) // convert to milliseconds

        //api date close
        val closeApiTime = currentPosition.workingHours[currentDay].to //close time from api
        val finalApiDateClose = "$finalFormatter $closeApiTime" // final group date and time
        val convertFinalApiDateClose = convertDate(finalApiDateClose) // convert to milliseconds

        //Range start to end
        val range = convertFinalApiDateOpen..convertFinalApiDateClose

        //AM or PM
        var dayOrNight = currentDate[Calendar.AM_PM]
        var dayTime = Calendar.AM //0
        var nightTime = Calendar.PM //1

        //Working or not
        var isWorking = currentPosition.workingHours[currentDay].working

        //Checking if shop is open
        if (!isWorking || !range.contains(convertFinalApiDate)) {
            holder.moonIcon.isVisible = true
            holder.openingTime.isVisible = true
            holder.planeOrder.isVisible = true
            holder.closedBlur.isVisible = true

            if (currentDay + 1 == 7){
                currentDay = 0
                currentPosition.workingHours[currentDay]

                if (dayOrNight == nightTime) {
                    holder.openingTime.text = currentPosition.workingHours[currentDay].day + " " + currentPosition.workingHours[currentDay].from
                } else {
                    holder.openingTime.text = currentPosition.workingHours[currentDay + 6].day + " " + currentPosition.workingHours[currentDay + 6].from
                }

            }else{
                if (dayOrNight == nightTime) {
                    holder.openingTime.text = currentPosition.workingHours[currentDay + 1].day + " " + currentPosition.workingHours[currentDay + 1].from
                } else {
                    if (!range.contains(convertFinalApiDate) && dayOrNight == dayTime){
                        holder.openingTime.text = currentPosition.workingHours[currentDay + 1].day + " " + currentPosition.workingHours[currentDay + 1].from
                    }
                    else{
                        holder.openingTime.text = currentPosition.workingHours[currentDay].day + " " + currentPosition.workingHours[currentDay].from
                    }
                }
            }

        } else {
            holder.moonIcon.isVisible = false
            holder.openingTime.isVisible = false
            holder.planeOrder.isVisible = false
            holder.closedBlur.isVisible = false
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
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