package com.somanath.stackoverflowsearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.somanath.stackoverflowsearch.data.model.Item
import com.somanath.stackoverflowsearch.databinding.ItemSearchResultBinding
import com.somanath.stackoverflowsearch.ui.IItemClickListener

class SearchResultsAdapter(private val iItemClickListener: IItemClickListener) :
    RecyclerView.Adapter<SearchResultsAdapter.QuestionViewHolder>() {
    private val resultItems = mutableListOf<Item>()

    fun updateData(newQuestions: List<Item>) {
        resultItems.clear()
        resultItems.addAll(newQuestions)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val resultItem = resultItems[position]
        holder.binding.apply {
            item = resultItem
            root.setOnClickListener {
                iItemClickListener.onItemClick(resultItem)
            }
        }
    }

    override fun getItemCount() = resultItems.size

    class QuestionViewHolder(val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root)
}
