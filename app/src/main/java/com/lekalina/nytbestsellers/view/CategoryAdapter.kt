package com.lekalina.nytbestsellers.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lekalina.nytbestsellers.data.Category
import com.lekalina.nytbestsellers.databinding.CategoryItemBinding
import com.lekalina.nytbestsellers.vm.CategoryItemViewModel

class CategoryAdapter(private var clickListener: OnItemClickListener) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var items: List<Category>? = null

    fun setList(items: List<Category>?) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(
        CategoryItemViewModel(items?.get(position)), clickListener)

    inner class ViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryItemViewModel, listener: OnItemClickListener) {
            binding.viewModel = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                item.category?.let { book ->  listener.onItemClick(binding.root, book) }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, item: Category)
    }
}