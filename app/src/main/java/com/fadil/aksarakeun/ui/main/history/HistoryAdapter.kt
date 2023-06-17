package com.fadil.aksarakeun.ui.main.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fadil.aksarakeun.BuildConfig
import com.fadil.aksarakeun.R
import com.fadil.aksarakeun.base.BaseRecyclerViewAdapter
import com.fadil.aksarakeun.databinding.ItemHistoryBinding
import com.fadil.aksarakeun.models.response.HistoryResponse

class HistoryAdapter : BaseRecyclerViewAdapter<HistoryAdapter.VHolder, HistoryResponse>() {
    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemHistoryBinding.bind(itemView)

        fun onBind(it: HistoryResponse) {
            binding.apply {
                imageHistory.load("${BuildConfig.BASE_API}/${it.image}") {
                    placeholder(R.drawable.ic_image_placeholder)
                    error(R.drawable.ic_image_placeholder)
                }
                textTranslationResult.text = it.result
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false))

    override fun onBindViewHolder(holder: VHolder, item: HistoryResponse, position: Int) {
        holder.onBind(item)
    }

    override fun getItemCount() = items.size
}