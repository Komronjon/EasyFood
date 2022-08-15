package com.example.easyfood.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.databinding.ActivityMealBinding
import com.example.easyfood.fragments.HomeFragment
import com.example.easyfood.pojo.Meal
import com.example.easyfood.viewModel.MealViewModel


class MealActivity : AppCompatActivity() {
    private lateinit var  mealid:String
    private lateinit var  mealname:String
    private lateinit var  mealThumb:String
    private lateinit var binding:ActivityMealBinding
    private lateinit var youtubeLinc:String
    private lateinit var mealMvvm:MealViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm=ViewModelProviders.of(this)[MealViewModel::class.java]
        getMealInformationFromIntent()
        setInformationInViews()
        loadingCase()
        mealMvvm.getMealDetail(mealid)
        observerMealDetailsLiveData()
        onYouTubeImgClick()
    }

    private fun onYouTubeImgClick() {
        binding.imgYoutube.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLinc))
            startActivity(intent)
        }
    }


    private fun observerMealDetailsLiveData() {
        mealMvvm.observerMealDetailsLiveDate().observe(this,object :Observer<Meal>{
            @SuppressLint("SetTextI18n")
            override fun onChanged(t: Meal?) {
                onResponseCase()
                val meal=t
                binding.tvCategoryInfo.text="Category:${meal!!.strCategory}"
                binding.tvAreaInfo.text="Area:${meal.strArea}"
                binding.tvInstructionsSteps.text=meal.strInstructions

                youtubeLinc=meal.strYoutube
            }

        })
    }

    private fun setInformationInViews() {
       Glide.with(applicationContext)
           .load(mealThumb)
           .into(binding.imgMealDetail)

            binding.collapsingToolbar.title=mealname
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
      binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

    }

    private fun getMealInformationFromIntent() {
        val intent=intent
        mealid=intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealname=intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb=intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }
    private fun loadingCase(){
        binding.progressBar.visibility= View.VISIBLE
       binding.floatbutton.visibility= View.INVISIBLE
       binding.tvInstructions.visibility= View.INVISIBLE
       binding.tvCategoryInfo.visibility= View.INVISIBLE
       binding.tvAreaInfo.visibility= View.INVISIBLE
       binding.imgYoutube.visibility= View.INVISIBLE
    }
    private fun onResponseCase(){
        binding.progressBar.visibility= View.INVISIBLE
        binding.floatbutton.visibility= View.VISIBLE
        binding.tvInstructions.visibility= View.VISIBLE
        binding.tvCategoryInfo.visibility= View.VISIBLE
        binding.tvAreaInfo.visibility= View.VISIBLE
        binding.imgYoutube.visibility= View.VISIBLE
    }

}