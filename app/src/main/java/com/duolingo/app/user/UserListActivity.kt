package com.duolingo.app.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.duolingo.app.base.BaseActivity
import com.duolingo.app.databinding.ActivityUserListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListActivity: BaseActivity<ActivityUserListBinding>() {

    private val viewModel: UserListViewModel by viewModels()

    // View Binding
    override val bindingInflater: (LayoutInflater) -> ActivityUserListBinding =
        ActivityUserListBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: do something with view model
        viewModel.configure()
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, UserListActivity::class.java)
    }

}