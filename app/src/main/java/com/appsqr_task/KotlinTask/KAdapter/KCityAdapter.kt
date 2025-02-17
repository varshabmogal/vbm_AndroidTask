package com.appsqr_task.KotlinTask.KAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.appsqr_task.Helper.OnPositionClickListener
import com.appsqr_task.KotlinTask.KPojo.KSelectCityBean
import com.appsqr_task.R
import com.appsqr_task.pojo.SelectCityBean

class KCityAdapter(
    private val context: Context,
    private val cityList: List<KSelectCityBean>,
    private val onPositionClickListener: OnPositionClickListener
) : RecyclerView.Adapter<KCityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_city, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = cityList[position]
        holder.tvRowCity.text = city.city
        holder.tvRowCityAddress.text = city.country

        holder.llRowMainCity.setOnClickListener {
            onPositionClickListener.onPositionClick(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = cityList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRowCityAddress: AppCompatTextView = itemView.findViewById(R.id.tvRowCityAddress)
        val tvRowCity: AppCompatTextView = itemView.findViewById(R.id.tvRowCity)
        val ivRowCountryIMG: AppCompatImageView = itemView.findViewById(R.id.ivRowCountryIMG)
        val llRowMainCity: LinearLayout = itemView.findViewById(R.id.llRowMainCity)
    }
}
