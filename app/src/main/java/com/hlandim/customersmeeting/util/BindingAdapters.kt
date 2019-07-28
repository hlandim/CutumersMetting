package com.hlandim.customersmeeting.util

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.hlandim.customersmeeting.model.Customer
import com.hlandim.customersmeeting.view.CustomersListAdapter

@BindingAdapter("items")
fun setItems(recyclerView: RecyclerView, list: List<Customer>?) {
    list?.let {
        recyclerView.adapter?.let {
            if (it is CustomersListAdapter && list.isNotEmpty()) {
                it.reload(list)
            }
        }
    }

}