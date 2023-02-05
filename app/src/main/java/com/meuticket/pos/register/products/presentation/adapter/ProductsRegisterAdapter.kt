package com.meuticket.pos.register.products.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meuticket.pos.register.products.presentation.ProductsRegisterViewModel
import com.meuticket.pos.shared.data.model.Product
import com.meuticket.pos.ui.components.ViewRegisterCell
import com.meuticket.pos.utils.toFormattedCurrency
import java.math.BigDecimal
import java.util.*

class ProductsRegisterAdapter(val viewModel: ProductsRegisterViewModel): RecyclerView.Adapter<ProductsViewHolder>() {

    var localItems: MutableList<Product>

    init {
        localItems = viewModel.products.toMutableList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(ViewRegisterCell(parent.context).apply {
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
            subtitle = BigDecimal(product.value).toFormattedCurrency(locale = Locale.getDefault())

            setEditClickListener {
                viewModel.editClicked(product)
            }

            setDeleteClickListener {
                viewModel.deleteClicked(product)
            }
        }
    }

    override fun getItemCount(): Int {
        return localItems.size
    }

    fun filter(text: String) {

        localItems = viewModel.products.filter { it.name.contains(text) }.toMutableList()
        notifyDataSetChanged()
    }
}

class ProductsViewHolder(val cellView: ViewRegisterCell): RecyclerView.ViewHolder(cellView)