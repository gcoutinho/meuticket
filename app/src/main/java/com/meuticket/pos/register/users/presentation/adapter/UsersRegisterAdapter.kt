package com.meuticket.pos.register.users.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meuticket.pos.register.users.presentation.UsersRegisterViewModel
import com.meuticket.pos.shared.data.model.User
import com.meuticket.pos.ui.components.ViewRegisterCell

class UsersRegisterAdapter(val viewModel: UsersRegisterViewModel): RecyclerView.Adapter<UsersViewHolder>() {

    var localItems: MutableList<User>

    init {
        localItems = viewModel.users.toMutableList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(ViewRegisterCell(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        })
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = localItems[position]
        holder.cellView.apply {
            title = user.name
            subtitle = "Tipo: ${if(user.admin) "Admin" else "Normal"}"

            setEditClickListener {
                viewModel.editClicked(user)
            }

            setDeleteClickListener {
                viewModel.deleteClicked(user)
            }
        }
    }

    override fun getItemCount(): Int {
        return localItems.size
    }

    fun filter(text: String) {

        localItems = viewModel.users.filter { it.name.contains(text) }.toMutableList()
        notifyDataSetChanged()
    }
}

class UsersViewHolder(val cellView: ViewRegisterCell): RecyclerView.ViewHolder(cellView)