package com.lekalina.nytbestsellers.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lekalina.nytbestsellers.R
import com.lekalina.nytbestsellers.api.NetworkState
import com.lekalina.nytbestsellers.data.Book
import com.lekalina.nytbestsellers.databinding.ActivityBooksBinding
import com.lekalina.nytbestsellers.vm.BooksViewModel
import com.lekalina.nytbestsellers.vm.BooksViewModelFactory
import com.lekalina.nytbestsellers.vm.ContentState
import kotlinx.android.synthetic.main.activity_category.list
import kotlinx.android.synthetic.main.activity_category.refresh

class BooksActivity: AppCompatActivity() {

    lateinit var binding: ActivityBooksBinding
    lateinit var model: BooksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val categoryId = intent.getStringExtra("category_id")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_books)
        model = ViewModelProvider(this, BooksViewModelFactory(categoryId)).get(BooksViewModel::class.java)
        binding.viewModel = model
        binding.lifecycleOwner = this
        val context = this

        // setup adapter
        list.layoutManager = LinearLayoutManager(this)
        val adapter = BooksAdapter(object: BooksAdapter.OnItemClickListener {
            override fun onItemClick(view: View, item: Book) {
                val intent = Intent(context, BookDetailActivity::class.java)
                intent.putExtra("title", item.title)
                context.startActivity(intent)
            }
        })
        list.adapter = adapter

        // setup swipe refresh
        refresh.setOnRefreshListener {
            model.getData()
        }

        // setup observers
        model.bookList.observeForever { list ->
            refresh.isRefreshing = false
            adapter.setList(list)
            if (!NetworkState.getInstance().isOnline && list.isNotEmpty()) {
                // let the user know they are viewing offline content
                Toast.makeText(context, getString(R.string.offline_content), Toast.LENGTH_SHORT).show()
            }
            val contentState: ContentState = if (list.isEmpty()) {
                if (NetworkState.getInstance().isOnline) {
                    ContentState.NOT_AVAILABLE
                } else {
                    ContentState.OFFLINE
                }
            } else {
                ContentState.AVAILABLE

            }
            model.toggleContentStates(contentState)
        }
    }

    override fun onResume() {
        super.onResume()
        model.getData()
    }
}