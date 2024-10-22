package ru.webarmour.foodapp.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.webarmour.foodapp.databinding.MealItemBinding
import ru.webarmour.foodapp.domain.model.MealByCategory

class MealsByCategoryAdapter :
    ListAdapter<MealByCategory, MealsByCategoryAdapter.CategoryViewHolder>(CategoryDiffUtil()) {
    var onItemClick: ((MealByCategory) -> Unit)? = null

    class CategoryViewHolder(
        val binding: MealItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(categories: MealByCategory) {
            binding.tvMeal.text = categories.strMeal
            Glide.with(binding.root.context)
                .load(categories.strMealThumb)
                .into(binding.imgMeal)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = MealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    class CategoryDiffUtil : DiffUtil.ItemCallback<MealByCategory>() {
        override fun areItemsTheSame(oldItem: MealByCategory, newItem: MealByCategory): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealByCategory, newItem: MealByCategory): Boolean {
            return oldItem == newItem
        }

    }

}