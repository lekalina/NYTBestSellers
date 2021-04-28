package com.lekalina.nytbestsellers.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lekalina.nytbestsellers.data.Book
import com.lekalina.nytbestsellers.databinding.BookListItemBinding
import com.lekalina.nytbestsellers.vm.BookItemViewModel

class BooksAdapter(private var clickListener: OnItemClickListener) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    private var items: List<Book>? = null

    fun setList(items: List<Book>?) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BookListItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(BookItemViewModel(items?.get(position)), clickListener)

    inner class ViewHolder(private val binding: BookListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BookItemViewModel, listener: OnItemClickListener) {
            binding.viewModel = item
            binding.root.setOnClickListener {
                item.book?.let { book ->  listener.onItemClick(binding.root, book) }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, item: Book)
    }
}