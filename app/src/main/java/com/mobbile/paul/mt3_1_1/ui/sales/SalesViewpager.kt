package com.mobbile.paul.mt3_1_1.ui.sales

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_fragment.SalesFragment
import com.mobbile.paul.mt3_1_1.ui.sales.sales_history.SalesHistoryFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_sales_viewpager.*

class SalesViewpager : DaggerAppCompatActivity(){


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.sales -> {
                val fragment = SalesFragment()
                titles.text = "Sales"
                replaceFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.saleshistory -> {
                val fragment = SalesHistoryFragment()
                titles.text = "Sales History"
                replaceFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun replaceFragment(fragment: Fragment) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.view_pager, fragment)
            fragmentTransaction.commit()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_viewpager)

        backbtn.setOnClickListener {
            onBackPressed()
        }

        val fragment = SalesFragment()
        replaceFragment(fragment)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    companion object{
        var TAG = "SalesViewpager"
    }
}

