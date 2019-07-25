package com.hlandim.customersmeeting.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.hlandim.customersmeeting.R
import com.hlandim.customersmeeting.model.Customer
import com.hlandim.customersmeeting.model.CustomersData
import java.io.BufferedReader

class MainActivity : AppCompatActivity() {

    private val EARTH_RADIUS = 6371

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputStream = assets.open("customers.txt")
        var content = inputStream.bufferedReader().use(BufferedReader::readText)

        content = "{ \"customers\" : [" + content.replace("}", "},") + "] }"
        val lastCommaIndex = content.lastIndexOf(",")
        content = content.substring(0, lastCommaIndex) + content.substring(lastCommaIndex + 1, content.length)

        val customersData = Gson().fromJson(content, CustomersData::class.java)

        val selectedCustomers = mutableListOf<Customer>()
        customersData.customers.forEach {
            val distanceKm = distanceInKm(53.339428, -6.257664, it.latitude, it.longitude)
            if (distanceKm <= 100) {
                selectedCustomers.add(it)
            }
        }

        selectedCustomers.sortBy { customer -> customer.user_id }

        println(selectedCustomers)

    }

    fun distanceInKm(startLati: Double, startLong: Double, endLati: Double, endLong: Double): Double {

        val diffLati = Math.toRadians(endLati - startLati)
        val diffLong = Math.toRadians(endLong - startLong)

        val radiusStartLati = Math.toRadians(startLati)
        val radiusEndLati = Math.toRadians(endLati)

        // A and C are the 'sides' from the spherical triangle.
        val a = Math.pow(Math.sin(diffLati / 2), 2.0) + Math.pow(
            Math.sin(diffLong / 2),
            2.0
        ) * Math.cos(radiusStartLati) * Math.cos(radiusEndLati)
        val c = 2 * Math.asin(Math.sqrt(a))

        return EARTH_RADIUS * c
    }
}
