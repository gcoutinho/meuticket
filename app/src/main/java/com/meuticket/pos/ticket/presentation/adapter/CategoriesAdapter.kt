package com.meuticket.pos.ticket.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.meuticket.pos.R
import com.meuticket.pos.ticket.presentation.ProductListViewModel
import com.meuticket.pos.ui.components.ViewSelector
import com.meuticket.pos.ui.utils.animateClick

class CategoriesAdapter(val viewModel: ProductListViewModel): RecyclerView.Adapter<CategorySelectorViewHolder>() {

    private val localItems = viewModel.products.map { it.category }.distinctBy { it.uid }

    lateinit var previousSelected: ViewSelector

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorySelectorViewHolder {
        return CategorySelectorViewHolder(ViewSelector(parent.context))
    }

    override fun getItemCount(): Int {
        return localItems.size
    }

    override fun onBindViewHolder(holder: CategorySelectorViewHolder, position: Int) {
        holder.view.apply {
            val category = localItems[position]
            label = category.name
            setOnClickListener {

                animateClick()

                isSelected = !isSelected

                if(::previousSelected.isInitialized && previousSelected != this) {
                    previousSelected.isSelected = false
                }

                previousSelected = this

                viewModel.filter(category, isSelected)
            }
            if(position == 0) {
                (setPadding(context.resources.getDimensionPixelSize(R.dimen.padding_medium2),0,0,0))
            }
        }
    }
}

class CategorySelectorViewHolder(val view: ViewSelector): ViewHolder(view)