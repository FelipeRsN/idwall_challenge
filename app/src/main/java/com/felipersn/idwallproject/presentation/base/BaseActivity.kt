package com.felipersn.idwallproject.presentation.base

import android.view.MenuItem
import dagger.android.support.DaggerAppCompatActivity

open class BaseActivity : DaggerAppCompatActivity() {


    override fun onBackPressed() {
        backPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            backPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun backPressed() {
        finishAfterTransition()
    }
}