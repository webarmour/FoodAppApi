package ru.webarmour.foodapp.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.webarmour.foodapp.databinding.PopularItemBinding
import ru.webarmour.foodapp.domain.model.MealByCategory

class PopularMealAdapter: ListAdapter<MealByCategory,PopularMealAdapter.MealViewHolder>(MealDiffUtil()) {
    lateinit var onItemClick:((MealByCategory)->Unit)
    var onLongItemClick:((MealByCategory)->Unit)? = null

 class MealViewHolder(
     val binding: PopularItemBinding
 ): RecyclerView.ViewHolder(binding.root){
     fun bind(mealByCategory: MealByCategory) {
         Glide.with(binding.root.context)
             .load(mealByCategory.strMealThumb)
             .into(binding.imgPopularMealItem)
     }

 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val mealItem = getItem(position)
        mealItem?.let {
            holder.bind(it)
        }
        holder.itemView.setOnClickListener {
            Log.d("MEAL_TAG", "Click register $mealItem")
            onItemClick(mealItem)
        }
        holder.itemView.setOnLongClickListener {
            onLongItemClick?.let { it1 -> it1(mealItem) }
            Log.d("MEAL_TAG", "Click register $mealItem")
            true
        }
    }

    class MealDiffUtil: DiffUtil.ItemCallback<MealByCategory>(){
        override fun areItemsTheSame(oldItem: MealByCategory, newItem: MealByCategory): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealByCategory, newItem: MealByCategory): Boolean {
           return oldItem == newItem
        }

    }

}