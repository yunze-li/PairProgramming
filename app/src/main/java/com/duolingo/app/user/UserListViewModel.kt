package com.duolingo.app.user

import android.util.Log
import com.duolingo.app.base.BaseViewModel
import com.duolingo.domain.repository.UserRepository
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UserListViewModel
@Inject constructor(
//    private val userRepository: UserRepository,
): BaseViewModel() {


    fun configure() = configureOnce {
        Log.i("YunzeDebug", "view model is configured")
    }
}