package com.hlandim.customersmeeting.dao

import android.content.Context
import com.google.gson.Gson
import com.hlandim.customersmeeting.model.CustomersData
import io.reactivex.Observable
import java.io.BufferedReader

class CustomerRepository(private val context: Context) {

    fun getCustomers(): Observable<CustomersData> {

        return Observable.create<CustomersData> {
            val inputStream = context.assets.open("customers.txt")
            var content = inputStream.bufferedReader().use(BufferedReader::readText)

            content = "{ \"customers\" : [" + content.replace("}", "},") + "] }"
            val lastCommaIndex = content.lastIndexOf(",")
            content = content.substring(0, lastCommaIndex) + content.substring(lastCommaIndex + 1, content.length)

            val data = Gson().fromJson(content, CustomersData::class.java)

            it.onNext(data)
            it.onComplete()
        }
    }
}