package com.duolingo.app.courselist

import android.os.Bundle
import com.duolingo.app.databinding.CourseListFragmentBinding
import com.duolingo.app.mvvm.MvvmFragment
import javax.inject.Inject

class CourseListFragment : MvvmFragment<CourseListFragmentBinding>(CourseListFragmentBinding::inflate) {

    @Inject
    lateinit var viewModel: CourseListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
    }

    override fun onViewCreated(binding: CourseListFragmentBinding, savedInstanceState: Bundle?) {
        val courseAdapter = CourseAdapter()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = courseAdapter

        whileStarted(viewModel.courseUiModel) { courseAdapter.setData(it) }
    }

    companion object {

        /** Get new instance of [CourseListFragment] */
        fun newInstance(): CourseListFragment = CourseListFragment()
    }
}
