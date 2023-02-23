package com.meuticket.pos.cart.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meuticket.pos.cart.presentation.CartViewModel
import com.meuticket.pos.shared.data.model.Product
import com.meuticket.pos.ui.components.ViewRegisterCell
import com.meuticket.pos.utils.toFormattedCurrency
import java.math.BigDecimal
import java.util.*

class CartAdapter(val viewModel: CartViewModel): RecyclerView.Adapter<ProductsViewHolder>() {

    var localItems: MutableList<Product> = viewModel.cart.products.toMutableList()

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
            subtitle = "${product.qtd} x ${BigDecimal(product.value).toFormattedCurrency(locale = Locale.getDefault())}"

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
}

class ProductsViewHolder(val cellView: ViewRegisterCell): RecyclerView.ViewHolder(cellView)