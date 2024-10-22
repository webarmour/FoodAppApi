package ru.webarmour.foodapp.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.webarmour.foodapp.databinding.CategoryItemBinding
import ru.webarmour.foodapp.domain.model.CategoryItem

class CategoriesAdapter: ListAdapter<CategoryItem,CategoriesAdapter.CategoryViewHolder>(CategoryDiffUtil()) {
    var onItemClick:((CategoryItem)->Unit)? = null

 class CategoryViewHolder(
     val binding: CategoryItemBinding
 ): RecyclerView.ViewHolder(binding.root){
     fun bind(categories: CategoryItem) {
         binding.tvCategoryName.text = categories.strCategory
         Glide.with(binding.root.context)
             .load(categories.strCategoryThumb)
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

    class CategoryDiffUtil: DiffUtil.ItemCallback<CategoryItem>(){
        override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
           return oldItem == newItem
        }

    }

}