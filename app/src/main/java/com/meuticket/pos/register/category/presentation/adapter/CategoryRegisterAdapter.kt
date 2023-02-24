package com.meuticket.pos.register.category.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meuticket.pos.register.category.presentation.CategoryRegisterViewModel
import com.meuticket.pos.shared.data.model.Category
import com.meuticket.pos.ui.components.ViewRegisterCell

class CategoryRegisterAdapter(val viewModel: CategoryRegisterViewModel): RecyclerView.Adapter<CategoryViewHolder>() {

    var localItems: MutableList<Category>

    init {
        localItems = viewModel.categories.toMutableList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(ViewRegisterCell(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        })
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = localItems[position]
        holder.cellView.apply {
            title = category.name

            setEditClickListener {
                viewModel.editClicked(category)
            }

            setDeleteClickListener {
                viewModel.deleteClicked(category)
            }
        }
    }

    override fun getItemCount(): Int {
        return localItems.size
    }

    fun filter(text: String) {

        localItems = viewModel.categories.filter { it.name.contains(text) }.toMutableList()
        notifyDataSetChanged()
    }
}

class CategoryViewHolder(val cellView: ViewRegisterCell): RecyclerView.ViewHolder(cellView)