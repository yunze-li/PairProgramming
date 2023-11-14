package com.duolingo.app.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.duolingo.app.base.BaseDataFragment
import com.duolingo.app.base.ContentState
import com.duolingo.app.base.LoadingState
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import com.jakewharton.rxbinding4.view.clicks
import com.duolingo.app.databinding.SessionFragmentBinding
import com.duolingo.app.extensions.build
import com.duolingo.app.extensions.getLongArg
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.id.LongId
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class SessionFragment : BaseDataFragment<SessionFragmentBinding>(), SessionView {

    companion object {
        private const val ARGS_COURSE_ID = "args_course_id"
//        private const val ARGS_REPO_NAME = "args_repo_name"
//        private const val ARGS_USER_NAME = "args_user_name"

        fun newInstance(courseId: Long): SessionFragment =
            SessionFragment().build {
                putLong(ARGS_COURSE_ID, courseId)
//                putString(ARGS_REPO_NAME, repoName)
//                putString(ARGS_USER_NAME, userName)
            }
    }

    @Inject lateinit var presenterFactory: SessionPresenter.SessionPresenterFactory

    // Properties
    private fun getParam() = "yunze"
    private val courseId: Long by lazy { getLongArg(ARGS_COURSE_ID) }
    private val presenter:SessionPresenter by lazy {
        presenterFactory.create(LongId(courseId))
    }
//    private val repoName: String by lazy { getStringArg(ARGS_REPO_NAME) }
//    private val userName: String by lazy { getStringArg(ARGS_USER_NAME) }

    // View Binding
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> SessionFragmentBinding =
        SessionFragmentBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detach()
    }

    override fun intentRefreshData(): Observable<String> =
        binding.swipeRefreshLayout.refreshes().map { getParam() }

    override fun intentErrorRetry(): Observable<String> =
        btnErrorRetry?.clicks()?.map { getParam() } ?: Observable.never()

    override fun intentActionLink(): Observable<Unit> =
        (activity as SessionActivity).intentActionLink

    override fun render(sessionData: SessionData) {

        showLoading(sessionData.loadingState == LoadingState.LOADING)
        showRefreshingLoading(binding.swipeRefreshLayout, false)
        showRetryLoading(sessionData.loadingState == LoadingState.RETRY)
        showContent(binding.content, sessionData.contentState == ContentState.CONTENT)
        showError(sessionData.contentState == ContentState.ERROR)

        renderData(sessionData.data)
        renderError(sessionData.errorMessage)
        renderSnack(sessionData.snackMessage)
    }

    private fun renderData(course: Course?) {
        course?.also {
            binding.uiLanguageName.text = it.uiLanguage.fullname
            binding.learningLanguageName.text = it.learningLanguage.fullname
        }
    }
    //endregion
}
