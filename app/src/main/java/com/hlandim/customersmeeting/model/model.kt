package com.hlandim.customersmeeting.model

data class Customer(
    val user_id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double
)

data class CustomersData(val customers: List<Customer>)