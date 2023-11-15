package com.duolingo.app.courselist

import android.os.Bundle
import com.duolingo.app.databinding.CourseListFragmentBinding
import com.duolingo.app.mvvm.MvvmFragment
import javax.inject.Inject

class CourseListFragment : MvvmFragment<CourseListFragmentBinding>(CourseListFragmentBinding::inflate) {

    @Inject
    lateinit var presenter: CourseListViewModel

    private val courseAdapter = CourseAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
    }

    override fun onViewCreated(binding: CourseListFragmentBinding, savedInstanceState: Bundle?) {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = courseAdapter

        whileStarted(presenter.courseFlowable) {
            courseAdapter.setData(it)
            binding.recyclerView.scrollToPosition(0)
        }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.recyclerView.setHasFixedSize(true)
//        binding.recyclerView.adapter = courseAdapter
//
//    }

//    override fun onResume() {
//        super.onResume()
//        presenter.attach(this)
//    }
//
//    override fun onPause() {
//        super.onPause()
//        presenter.detach()
//    }

//    override fun intentLoadData(): Observable<String> =
//        Observable.just(getParam())
//
//    override fun intentRefreshData(): Observable<String> =
//        binding.swipeRefreshLayout.refreshes().map { getParam() }
//
//    override fun intentErrorRetry(): Observable<String> =
//        btnErrorRetry?.clicks()?.map { getParam() } ?: Observable.never()
//
//    override fun openCourse(): Observable<Pair<Course, String>> =
//        courseAdapter.repoClickIntent.map { it to getParam() }

//    override fun render(courseListData: CourseListData) {
//
//        showLoading(courseListData.loadingState == LoadingState.LOADING)
//        showRefreshingLoading(binding.swipeRefreshLayout, false)
//        showRetryLoading(courseListData.loadingState == LoadingState.RETRY)
//        showContent(binding.content, courseListData.contentState == ContentState.CONTENT)
//        showError(courseListData.contentState == ContentState.ERROR)
//
//        renderData(courseListData.data)
//        renderError(courseListData.errorMessage)
//        renderSnack(courseListData.snackMessage)
//    }
//
//    private fun renderData(courseList: List<Course>?) {
//        courseList?.also {
//            courseAdapter.setData(it)
//            binding.recyclerView.scrollToPosition(0)
//        }
//    }

    companion object {

        /** Get new instance of [CourseListFragment] */
        fun newInstance(): CourseListFragment = CourseListFragment()
    }
}
