package com.hlandim.custumersmeeting

import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.res.AssetManager
import com.hlandim.customersmeeting.viewmodel.CustomersViewModel
import com.hlandim.custumersmeeting.util.RxImmediateSchedulerRule
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.io.FileInputStream


class CustomersViewModelTest {

    @Mock
    lateinit var application: Application

    @Mock
    lateinit var assetManager: AssetManager

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        whenever(application.assets).thenReturn(assetManager)
    }

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun `When request the customers, should receive the within 100km, sorted by User ID (ascending)`() {

        val viewModel = CustomersViewModel(application)
        val resource = CustomersViewModelTest::class.java.classLoader!!.getResource("customers_test.txt")
        val inputStream = FileInputStream(resource.path)
        whenever(application.assets.open("customers.txt")).thenReturn(inputStream)

        viewModel.getCustomersData()

        val customers = viewModel.customersData.value
        assertNotNull(customers)
        customers?.let {
            assertEquals(it.customers.size, 2)
            var customer = it.customers[0]
            assertEquals(customer.name, "Ian Kehoe")
            assertEquals(customer.user_id, 4)
            customer = it.customers[1]
            assertEquals(customer.name, "Christina McArdle")
            assertEquals(customer.user_id, 12)
        }

    }

}