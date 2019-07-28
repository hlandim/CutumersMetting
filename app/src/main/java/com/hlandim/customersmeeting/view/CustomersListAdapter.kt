package com.hlandim.customersmeeting.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hlandim.customersmeeting.databinding.CustomersListLineBinding
import com.hlandim.customersmeeting.model.Customer

class CustomersListAdapter(private var customers: List<Customer>) :
    RecyclerView.Adapter<CustomersListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CustomersListLineBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return customers.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(customers[position])
    }

    class ViewHolder(private val customersListLineBinding: CustomersListLineBinding) :
        RecyclerView.ViewHolder(customersListLineBinding.root) {

        fun bind(customer: Customer) {
            customersListLineBinding.customer = customer
        }
    }


    fun reload(list: List<Customer>) {
        this.customers = list
        notifyDataSetChanged()

    }
}