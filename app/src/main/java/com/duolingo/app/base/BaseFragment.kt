package com.duolingo.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.duolingo.app.DuoApplication
import com.duolingo.app.di.components.ActivityComponent
import com.duolingo.app.di.components.ApplicationComponent

/**
 * Base [Fragment] class for every fragment in this application.
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected val appComponent: ApplicationComponent by lazy { (requireActivity().application as DuoApplication).appComponent }

    protected val activityComponent: ActivityComponent by lazy { (activity as BaseActivity<*>).activityComponent }

    private var _binding: VB? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
