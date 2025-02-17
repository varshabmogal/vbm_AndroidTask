package com.appsqr_task.KotlinTask.KAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appsqr_task.KotlinTask.KPojo.KHotelBean
import com.appsqr_task.R
import com.appsqr_task.pojo.HotelBean

class KFlightAdapter(
    private val context: Context,
    private val hotelList: List<KHotelBean>
) : RecyclerView.Adapter<KFlightAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_flight, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = hotelList[position]
        holder.tvFlightName.text = model.flightTitle
        holder.tvRowFStartTime.text = model.tm1
        holder.tvRowFReachTime.text = model.tm2
        holder.tvRowFArrivalTime.text = model.time
        holder.tvRowActivityPrice.text = model.hotelFees
        holder.tvRowFDate.text = model.inDate
        holder.tvRowFReachDate.text = model.outTime
    }

    override fun getItemCount(): Int = hotelList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFlightName: TextView = itemView.findViewById(R.id.tvFlightName)
        val tvRowFStartTime: TextView = itemView.findViewById(R.id.tvRowFStartTime)
        val tvRowFArrivalTime: TextView = itemView.findViewById(R.id.tvRowFArrivalTime)
        val tvRowFReachTime: TextView = itemView.findViewById(R.id.tvRowFReachTime)
        val tvRowActivityPrice: TextView = itemView.findViewById(R.id.tvRowActivityPrice)
        val tvRowFDate: TextView = itemView.findViewById(R.id.tvRowFDate)
        val tvRowFReachDate: TextView = itemView.findViewById(R.id.tvRowFReachDate)
    }
}
