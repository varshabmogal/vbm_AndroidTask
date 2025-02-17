package com.appsqr_task.KotlinTask.KAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appsqr_task.KotlinTask.KPojo.KHotelBean
import com.appsqr_task.R
import com.appsqr_task.pojo.HotelBean
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class KActivityAdapter(
    private val context: Context,
    private val hotelList: List<KHotelBean>
) : RecyclerView.Adapter<KActivityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_activity, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = hotelList[position]
        holder.tvRowActivity.text = model.activity
        holder.tvRowActivityAddress.text = model.activityAddress
        holder.tvRowActivityDateTime.text = "${model.time} on ${model.inDate}"
        holder.tvRowActivityPrice.text = "${model.activityPrice}"

        Glide.with(context).load(model.img)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.ivRowActivityImg)
    }

    override fun getItemCount(): Int = hotelList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivRowActivityImg: ImageView = itemView.findViewById(R.id.ivRowActivityImg)
        val tvRowActivityDateTime: TextView = itemView.findViewById(R.id.tvRowActivityDateTime)
        val tvRowActivity: TextView = itemView.findViewById(R.id.tvRowActivity)
        val tvRowActivityAddress: TextView = itemView.findViewById(R.id.tvRowActivityAddress)
        val tvRowActivityPrice: TextView = itemView.findViewById(R.id.tvRowActivityPrice)
        val tvRowActivityMap: TextView = itemView.findViewById(R.id.tvRowActivityMap)
    }
}
