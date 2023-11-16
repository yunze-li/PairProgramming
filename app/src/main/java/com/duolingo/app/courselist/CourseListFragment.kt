package com.duolingo.app.courselist

import android.os.Bundle
import com.duolingo.app.databinding.FragmentCourseListBinding
import com.duolingo.app.mvvm.MvvmFragment
import javax.inject.Inject

class CourseListFragment : MvvmFragment<FragmentCourseListBinding>(FragmentCourseListBinding::inflate) {

    @Inject
    lateinit var viewModel: CourseListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
    }

    override fun onViewCreated(binding: FragmentCourseListBinding, savedInstanceState: Bundle?) {
        val courseElementAdapter = CourseElementAdapter()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = courseElementAdapter

        whileStarted(viewModel.courseElements) { courseElementAdapter.setCourseElement(it) }
        viewModel.configure()
    }

    companion object {

        /** Get new instance of [CourseListFragment] */
        fun newInstance(): CourseListFragment = CourseListFragment()
    }
}
