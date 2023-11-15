package com.duolingo.app.courselist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.duolingo.app.base.BaseDataFragment
import com.duolingo.app.base.ContentState
import com.duolingo.app.base.LoadingState
import com.duolingo.app.databinding.CourseListFragmentBinding
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import com.jakewharton.rxbinding4.view.clicks
import com.duolingo.domain.model.Course
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class CourseListFragment : BaseDataFragment<CourseListFragmentBinding>(), CourseListView {

    companion object {
        fun newInstance(): CourseListFragment = CourseListFragment()
    }

    @Inject
    lateinit var presenter: CourseListPresenter

    // Properties
    private fun getParam() = "yunze"
    private val courseAdapter = CourseAdapter()

    // View Binding
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> CourseListFragmentBinding =
        CourseListFragmentBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detach()
    }

    private fun initView() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = courseAdapter
    }

    override fun intentLoadData(): Observable<String> =
        Observable.just(getParam())

    override fun intentRefreshData(): Observable<String> =
        binding.swipeRefreshLayout.refreshes().map { getParam() }

    override fun intentErrorRetry(): Observable<String> =
        btnErrorRetry?.clicks()?.map { getParam() } ?: Observable.never()

//    override fun intentFavorite(): Observable<Pair<Int, Repo>> =
//        courseAdapter.repoFavoriteIntent

    override fun openCourse(): Observable<Pair<Course, String>> =
        courseAdapter.repoClickIntent.map { it to getParam() }

    override fun render(viewModel: CourseListData) {

        showLoading(viewModel.loadingState == LoadingState.LOADING)
        showRefreshingLoading(binding.swipeRefreshLayout, false)
        showRetryLoading(viewModel.loadingState == LoadingState.RETRY)
        showContent(binding.content, viewModel.contentState == ContentState.CONTENT)
        showError(viewModel.contentState == ContentState.ERROR)

        renderData(viewModel.data)
//        renderFavoriteRepo(viewModel.favoriteRepo, viewModel.favoriteRepoPosition)
        renderError(viewModel.errorMessage)
        renderSnack(viewModel.snackMessage)
    }

    private fun renderData(courseList: List<Course>?) {
        courseList?.also {
            courseAdapter.setData(it)
            binding.recyclerView.scrollToPosition(0)
        }
    }

//    private fun renderFavoriteRepo(favoriteRepo: Repo?, favoriteRepoPosition: Int?) {
//        if (favoriteRepo != null && favoriteRepoPosition != null) {
//            courseAdapter.setData(favoriteRepoPosition, favoriteRepo)
//        }
//    }
}
