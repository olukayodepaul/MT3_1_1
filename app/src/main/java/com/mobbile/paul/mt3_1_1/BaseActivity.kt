package com.mobiletraderv.paul.daggertraining

import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.mobbile.paul.mt3_1_1.R
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment

open class BaseActivity : DaggerAppCompatActivity() {

    lateinit var mProgressBar: ProgressBar

    override fun setContentView(layoutResID: Int) {

        val constraintLayout = layoutInflater.inflate(R.layout.actitvity_base, null) as ConstraintLayout
        val frameLayout = constraintLayout.findViewById<FrameLayout>(R.id.activity_contents)
        mProgressBar = constraintLayout.findViewById(R.id.base_progress_bar)
        layoutInflater.inflate(layoutResID, frameLayout, true)
        super.setContentView(constraintLayout)
        Log.d(TAG, "setContentView: ")
    }

    fun showProgressBar(visible: Boolean) {
        mProgressBar.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    companion object {
        private val TAG = "DaggerExample"
    }
}
