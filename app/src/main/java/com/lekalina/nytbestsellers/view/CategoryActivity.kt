package com.lekalina.nytbestsellers.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.lekalina.nytbestsellers.NYT
import com.lekalina.nytbestsellers.vm.CategoryViewModel
import com.lekalina.nytbestsellers.R
import com.lekalina.nytbestsellers.data.Category
import com.lekalina.nytbestsellers.databinding.ActivityCategoryBinding
import com.lekalina.nytbestsellers.vm.ContentState

class CategoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryBinding
    lateinit var model: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category)
        model = CategoryViewModel()
        binding.viewModel = model
        binding.lifecycleOwner = this

        // setup adapter
        binding.list.layoutManager = LinearLayoutManager(this)
        val adapter = CategoryAdapter(object: CategoryAdapter.OnItemClickListener {
            override fun onItemClick(view: View, item: Category) {
                val intent = Intent(context, BooksActivity::class.java)
                intent.putExtra("category_id", item.identifier)
                context.startActivity(intent)
            }
        })
        binding.list.adapter = adapter

        // setup swipe refresh
        binding.refresh.setOnRefreshListener {
            model.getData()
        }

        // setup observers
        model.categories.observeForever { list ->
            binding.refresh.isRefreshing = false
            adapter.setList(list)
            if(!NYT.networkState.isOnline && list.isNotEmpty()) {
                // let the user know they are viewing offline content
                Toast.makeText(context, getString(R.string.offline_content), Toast.LENGTH_SHORT).show()
            }
            val contentState: ContentState = if(list.isEmpty()) {
                if(NYT.networkState.isOnline) {
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