package com.lekalina.nytbestsellers.vm

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide

abstract class BaseViewModel: ViewModel() {

    val showOfflineDinosaur: MutableLiveData<Boolean> = MutableLiveData()
    val showNoContentAvailable: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        showOfflineDinosaur.value = false
        showNoContentAvailable.value = false
        showLoading.value = false
    }

    companion object {
        /**
         * Method for loading urls in XML via databinding
         */
        @BindingAdapter("photoUrl")
        @JvmStatic
        fun loadImage(imageView: ImageView, url: String?) {
            url?.let {
                Glide.with(imageView.context)
                    .load(url)
                    .into(imageView)
            }
        }

        @BindingAdapter("loadThumb")
        @JvmStatic
        fun loadThumbnail(imageView: ImageView, url: String?) {
            url?.let {
                Glide.with(imageView.context)
                    .load(url)
                    .override(100,250) // scaling for faster loading
                    .into(imageView)
            }
        }
    }

    fun toggleContentStates(state: ContentState) {
        when (state) {
            ContentState.OFFLINE -> {
                showOfflineDinosaur.value = true
                showNoContentAvailable.value = false
            }
            ContentState.NOT_AVAILABLE -> {
                showOfflineDinosaur.value = false
                showNoContentAvailable.value = true
            }
            else -> {
                showOfflineDinosaur.value = false
                showNoContentAvailable.value = false
            }
        }
    }
}

enum class ContentState {
    OFFLINE, NOT_AVAILABLE, AVAILABLE
}