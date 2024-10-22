package ru.webarmour.foodapp.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.webarmour.foodapp.databinding.CategoryItemBinding
import ru.webarmour.foodapp.domain.model.MealItem

class SearchAdapter: ListAdapter<MealItem,SearchAdapter.CategoryViewHolder>(CategoryDiffUtil()) {
    var onItemClick:((MealItem)->Unit)? = null

 class CategoryViewHolder(
     val binding: CategoryItemBinding
 ): RecyclerView.ViewHolder(binding.root){
     fun bind(item: MealItem) {
         binding.tvCategoryName.text = item.strMeal
         Glide.with(binding.root.context)
             .load(item.strMealThumb)
             .into(binding.imgCategory)
     }

 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val mealItem = getItem(position)
        mealItem?.let {
            holder.bind(it)
        }
        holder.itemView.setOnClickListener {
            Log.d("MEAL_TAG", "Click register $mealItem")
            onItemClick?.let { mealCategory -> mealCategory(mealItem) }
        }
    }

    class CategoryDiffUtil: DiffUtil.ItemCallback<MealItem>(){
        override fun areItemsTheSame(oldItem: MealItem, newItem: MealItem): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealItem, newItem: MealItem): Boolean {
           return oldItem == newItem
        }

    }

}