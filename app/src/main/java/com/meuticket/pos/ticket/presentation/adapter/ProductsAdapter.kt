package com.meuticket.pos.ticket.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meuticket.pos.shared.data.model.Category
import com.meuticket.pos.shared.data.model.Product
import com.meuticket.pos.ticket.presentation.ProductListViewModel
import com.meuticket.pos.ui.components.ViewProductCell
import com.meuticket.pos.utils.toFormattedCurrency
import java.math.BigDecimal
import java.util.*

class ProductsAdapter(val viewModel: ProductListViewModel): RecyclerView.Adapter<ProductsViewHolder>() {

    lateinit var previousSelected: ViewProductCell

    var localItems: MutableList<Product>

    init {
        localItems = viewModel.products.toMutableList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(ViewProductCell(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        })
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = localItems[position]
        holder.cellView.apply {
            title = product.name
            image = product.image
            value = BigDecimal(product.value).toFormattedCurrency(locale = Locale.getDefault())

            setOnClickListener {
                isSelected = !isSelected

                if(::previousSelected.isInitialized && previousSelected != this) {
                    previousSelected.isSelected = false
                }

                previousSelected = this

                viewModel.productClicked(product, isSelected)
            }
        }
    }

    override fun getItemCount(): Int {
        return localItems.size
    }

    fun resetSelection() {
        if(::previousSelected.isInitialized)
            previousSelected.isSelected = false
    }

    fun filter(text: String) {

        resetSelection()

        localItems = viewModel.products.filter { it.name.contains(text) }.toMutableList()
        notifyDataSetChanged()
    }

    fun filter(category: Category, isSelected: Boolean) {

        resetSelection()

        localItems =
            if(isSelected)
                viewModel.products.filter { it.category == category }.toMutableList()
            else
                viewModel.products.toMutableList()

        notifyDataSetChanged()
    }
}

class ProductsViewHolder(val cellView: ViewProductCell): RecyclerView.ViewHolder(cellView)