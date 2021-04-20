package com.lekalina.nytbestsellers.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.lekalina.nytbestsellers.R
import com.lekalina.nytbestsellers.databinding.ActivityBookDetailBinding
import com.lekalina.nytbestsellers.vm.BookDetailViewModel

class BookDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityBookDetailBinding
    lateinit var model: BookDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title = intent.getStringExtra("title")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_detail)
        model = BookDetailViewModel(title)
        binding.viewModel = model
        binding.lifecycleOwner = this
    }
}