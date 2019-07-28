package com.hlandim.customersmeeting.model

data class Customer(
    val user_id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    @Transient var distanceToOffice: Double
)

data class CustomersData(var customers: List<Customer>)