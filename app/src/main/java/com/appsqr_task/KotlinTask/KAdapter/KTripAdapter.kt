package com.appsqr_task.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.appsqr_task.KTripActivity
import com.appsqr_task.KotlinTask.KPojo.KPlanedTripBean
import com.appsqr_task.R
import com.appsqr_task.TripActivity
import com.appsqr_task.pojo.PlanedTripBean
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class KTripAdapter(
    private val context: Context,
    private val planedTripList: List<KPlanedTripBean>
) : RecyclerView.Adapter<KTripAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_planed_trip, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = planedTripList[position]

        holder.apply {
            tvRowLocation.text = model.city
            tvRowtripTitle.text = model.title
            tvRowtripDate.text = model.date
            tvRowtripDuration.text = model.duration

            Glide.with(itemView.context)
                .load(model.img)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ivRowtripImage)

            btnRowViewTrip.setOnClickListener {
                val intent = Intent(context, KTripActivity::class.java).apply {
                    putExtra("city", model.city)
                    putExtra("date", model.date)
                    putExtra("img", model.img)
                    putExtra("duration", model.duration)
                    putExtra("title", model.title)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = planedTripList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivRowtripImage: ImageView = itemView.findViewById(R.id.ivRowtripImage)
        val tvRowtripTitle: TextView = itemView.findViewById(R.id.tvRowtripTitle)
        val tvRowtripDate: TextView = itemView.findViewById(R.id.tvRowtripDate)
        val tvRowtripDuration: TextView = itemView.findViewById(R.id.tvRowtripDuration)
        val tvRowLocation: TextView = itemView.findViewById(R.id.tvRowLocation)
        val btnRowViewTrip: AppCompatButton = itemView.findViewById(R.id.btnRowViewTrip)
    }
}
