package com.hlandim.customersmeeting.viewmodel

import android.app.Application
import android.arch.lifecycle.*
import android.util.Log
import com.google.gson.Gson
import com.hlandim.customersmeeting.dao.CustomerRepository
import com.hlandim.customersmeeting.model.Customer
import com.hlandim.customersmeeting.model.CustomersData
import com.hlandim.customersmeeting.util.Util
import com.hlandim.customersmeeting.util.androidThread
import com.hlandim.customersmeeting.util.ioThread
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.io.File
import java.io.FileWriter
import java.math.RoundingMode

class CustomersViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    val customersData: MutableLiveData<CustomersData> = MutableLiveData()
    var customersRepository: CustomerRepository = CustomerRepository(application)
    var message: MutableLiveData<String> = MutableLiveData()
    val compositeDisposable = CompositeDisposable()

    companion object {
        private const val MAX_DISTANCE_KM = 100

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun load() {
        if (customersData.value == null) {
            getCustomersData()
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun getCustomersData() {
        val disposable = customersRepository.getCustomers().subscribeOn(ioThread())
            .observeOn(androidThread())
            .subscribe({
                it.customers = filterCustomersByDistance(it.customers, MAX_DISTANCE_KM)
                this.customersData.value = it
            }, {
                message.value = it.message
                Log.e("ERROR", it.printStackTrace().toString())
            })

        compositeDisposable.add(disposable)
    }

    fun generateFile(): Observable<String> {
        return Observable.create<String> {
            val json = Gson().toJson(this.customersData.value)
            it.onNext(json)
            it.onComplete()
        }
    }

    private fun filterCustomersByDistance(customers: List<Customer>, maxDistanceKm: Int): MutableList<Customer> {
        val selectedCustomers = mutableListOf<Customer>()
        customers.forEach {
            val distanceKm = Util.distanceInKm(53.339428, -6.257664, it.latitude, it.longitude)
            if (distanceKm <= maxDistanceKm) {
                it.distanceToOffice = distanceKm.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                selectedCustomers.add(it)
            }
        }
        selectedCustomers.sortBy { it.distanceToOffice }
        return selectedCustomers
    }


}