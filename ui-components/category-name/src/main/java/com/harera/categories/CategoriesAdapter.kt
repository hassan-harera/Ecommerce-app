package com.harera.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harera.categories_name.databinding.CardViewCategoriesCategoryBinding
import com.harera.model.model.Category

class CategoriesAdapter(
    private var categories: List<Category> = emptyList(),
    private val onCategoryClicked: (String) -> Unit,
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = CardViewCategoriesCategoryBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryName = categories[position].categoryName

        holder.bind.categoryName.text = categoryName
        holder.bind.root.setOnClickListener {
            onCategoryClicked(categoryName)
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun setCategories(categories: List<Category>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    inner class ViewHolder(val bind: CardViewCategoriesCategoryBinding) :
        RecyclerView.ViewHolder(bind.root)
}