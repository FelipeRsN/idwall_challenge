package com.felipersn.idwallproject.presentation.ui.mainlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.felipersn.idwallproject.R
import com.felipersn.idwallproject.presentation.base.BaseActivity

class MainListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)
    }

    companion object {

        fun provideIntent(context: Context): Intent {
            return Intent(context, MainListActivity::class.java)
        }
    }
}
