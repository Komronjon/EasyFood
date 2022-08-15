package com.example.easyfood.viewModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfood.data.pojo.Category
import com.example.easyfood.data.pojo.CategoryList
import com.example.easyfood.data.pojo.MealsByCategoryList
import com.example.easyfood.data.pojo.MealsByCategory
import com.example.easyfood.pojo.Meal
import com.example.easyfood.pojo.MealList
import com.example.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(): ViewModel(){
    private  var randomMealLiveData=MutableLiveData<Meal>()
    private var popularItemsLiviData=MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData=MutableLiveData<List<Category>>()
fun getRandomMeal(){
    RetrofitInstance.api.getRandommeal().enqueue(object: Callback<MealList> {
        override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
            if (response.body()!=null){
                val randomMeal: Meal =response.body()!!.meals[0]
                randomMealLiveData.value=randomMeal
            }else{
                return
            }
        }

        override fun onFailure(call: Call<MealList>, t: Throwable) {
            Log.d("home fragment",t.message.toString())
        }
    })
}
   fun getPopularItems(){
       RetrofitInstance.api.getPopularItems("Seafood").enqueue(object :Callback<MealsByCategoryList>{
           override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
               if(response.body()!=null){
                   popularItemsLiviData.value=response.body()!!.meals
               }
           }

           override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
               Log.d("Homefragment",t.message.toString())
           }
       })
   }
    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
               response.body()?.let { categoryList ->
               categoriesLiveData.postValue(categoryList.categories)
               }

            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeViewModel",t.message.toString())
            }
        })
    }

    fun observeRandomMealLivedata():LiveData<Meal>{
        return randomMealLiveData
    }
    fun  observerPopularitemsLiviData():LiveData<List<MealsByCategory>>{
        return popularItemsLiviData
    }
    fun  observerCategoryLiviData():LiveData<List<Category>>{
        return categoriesLiveData

    }

}