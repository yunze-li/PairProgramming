package com.duolingo.app.debug

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.duolingo.app.base.BaseActivity
import com.duolingo.app.databinding.ActivityDebugBinding

class DebugActivity : BaseActivity<ActivityDebugBinding>(){

    override val bindingInflater: (LayoutInflater) -> ActivityDebugBinding =
        ActivityDebugBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, DebugActivity::class.java)
    }

}