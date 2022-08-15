package com.example.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.data.pojo.MealsByCategory
import com.example.easyfood.databinding.PopularItemsBinding

class MostPopularAdapter():RecyclerView.Adapter<MostPopularAdapter.PopolarMealViewHolder>() {

    lateinit var onItemClick:((MealsByCategory)->Unit)
    private var mealsList=ArrayList<MealsByCategory>()

    fun setMeals(mealsByCategoryList:ArrayList<MealsByCategory>){

        this.mealsList=mealsByCategoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopolarMealViewHolder {
        return PopolarMealViewHolder(PopularItemsBinding.
        inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopolarMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMeal)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])

        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
    class PopolarMealViewHolder(var binding:PopularItemsBinding):
        RecyclerView.ViewHolder(binding.root){


    }
}