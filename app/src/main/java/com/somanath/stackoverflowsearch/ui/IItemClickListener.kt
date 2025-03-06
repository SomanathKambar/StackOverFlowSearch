package com.somanath.stackoverflowsearch.ui

import com.somanath.stackoverflowsearch.data.model.Item

interface IItemClickListener {
    fun onItemClick(item: Item)
}