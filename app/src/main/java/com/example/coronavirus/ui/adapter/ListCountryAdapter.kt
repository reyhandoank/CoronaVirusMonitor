package com.example.coronavirus.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coronavirus.R
import com.example.coronavirus.model.CountryCases
import kotlinx.android.synthetic.main.item_country.view.*

class ListCountryAdapter(private var context: Context?)
    : RecyclerView.Adapter<ListCountryAdapter.ViewHolder>() {

    private var casesList = ArrayList<CountryCases>()

    fun setCases(cases: List<CountryCases>) {
        casesList.clear()
        casesList.addAll(cases)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_country, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = casesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.img_recovered?.let {
            context?.let {
                Glide.with(it)
                    .load(R.drawable.ic_mood_black_24dp)
                    .into(holder.itemView.img_recovered)
            }
        }

        holder.itemView.img_infected?.let {
            context?.let {
                Glide.with(it)
                    .load(R.drawable.ic_mood_bad_black_24dp)
                    .into(holder.itemView.img_infected)
            }
        }

        holder.itemView.tv_country.text = casesList[position].countryName
        holder.itemView.tv_total_cases.text = casesList[position].cases
        holder.itemView.tv_recovered.text = casesList[position].recovered
        holder.itemView.tv_infected.text = casesList[position].infected
        holder.itemView.tv_death.text = casesList[position].death
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}