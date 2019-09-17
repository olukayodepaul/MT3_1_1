package com.mobbile.paul.mt3_1_1.ui.sales

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.ui.sales.commission.SalesCommissionFragment
import com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_fragment.SalesFragment
import com.mobbile.paul.mt3_1_1.ui.sales.sales_history.SalesHistoryFragment
import com.mobbile.paul.mt3_1_1.ui.settings.SettingsActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_sales_viewpager.*

class SalesViewpager : DaggerAppCompatActivity(){


    private val bt = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.sales -> {
                val fragment = SalesFragment()
                titles.text = getString(R.string.menu_sales)
                replaceFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.saleshistory -> {
                val fragment = SalesHistoryFragment()
                titles.text = getString(R.string.menu_history)
                replaceFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_commission -> {
                val fragment = SalesCommissionFragment()
                titles.text = getString(R.string.menu_commission)
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

        settings_btn.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }



        val fragment = SalesFragment()
        replaceFragment(fragment)
        navigations.setOnNavigationItemSelectedListener(bt)
    }

    companion object{
        var TAG = "SalesViewpager"
    }
}

