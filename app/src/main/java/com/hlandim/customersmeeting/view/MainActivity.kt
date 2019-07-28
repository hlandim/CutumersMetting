package com.hlandim.customersmeeting.view

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.hlandim.customersmeeting.R
import com.hlandim.customersmeeting.databinding.ActivityMainBinding
import com.hlandim.customersmeeting.util.androidThread
import com.hlandim.customersmeeting.util.ioThread
import com.hlandim.customersmeeting.viewmodel.CustomersViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModelInstance: CustomersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        viewModelInstance = CustomersViewModel(application)
        binding.viewModel = viewModelInstance
        binding.lifecycleOwner = this
        this.lifecycle.addObserver(viewModelInstance)
        customers_list.layoutManager = LinearLayoutManager(this)
        customers_list.adapter = CustomersListAdapter(emptyList())

        setSupportActionBar(my_toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_share -> {
            viewModelInstance.generateFile().subscribeOn(ioThread())
                .observeOn(androidThread())
                .subscribe({
                    val sharingIntent = Intent(Intent.ACTION_SEND)
                    sharingIntent.type = "text/plain"
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, it)
                    startActivity(Intent.createChooser(sharingIntent, "Customers JSON"))
                }, {

                })
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }


}
