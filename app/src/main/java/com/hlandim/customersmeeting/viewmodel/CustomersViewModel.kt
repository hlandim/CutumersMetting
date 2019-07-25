package com.hlandim.customersmeeting.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.hlandim.customersmeeting.model.CustomersData

class CustomersViewModel(application: Application) : AndroidViewModel(application) {

    val custumers: MutableLiveData<CustomersData> = MutableLiveData()

}