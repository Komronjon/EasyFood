package com.example.easyfood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager

import com.example.easyfood.adapter.CategoryMealsAdapter
import com.example.easyfood.databinding.ActivityCategoryMealsBinding
import com.example.easyfood.fragments.HomeFragment
import com.example.easyfood.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding:ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel:CategoryMealsViewModel
    lateinit var categoryMealsAdapter:CategoryMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
     preperRecyclerView()
        categoryMealsViewModel=ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]

         categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
          categoryMealsViewModel.observeMealsLiveData().observe(this, Observer {mealsList->
          binding.tvCategoryCount.text=mealsList.size.toString()
           categoryMealsAdapter.setMealsList(mealsList)
          })

    }

    private fun preperRecyclerView() {
        categoryMealsAdapter=CategoryMealsAdapter()
        binding.mealRecyclerview.apply {
            layoutManager=GridLayoutManager(context,
                2,GridLayoutManager.VERTICAL,false)
     adapter=categoryMealsAdapter
        }
    }
}
