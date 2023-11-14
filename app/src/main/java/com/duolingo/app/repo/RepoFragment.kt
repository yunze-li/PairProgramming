package com.duolingo.app.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.duolingo.app.base.BaseDataFragment
import com.duolingo.app.base.ContentState
import com.duolingo.app.base.LoadingState
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import com.jakewharton.rxbinding4.view.clicks
import com.duolingo.app.databinding.RepoFragmentBinding
import com.duolingo.app.extensions.build
import com.duolingo.app.extensions.getLongArg
import com.duolingo.app.extensions.getStringArg
import com.duolingo.domain.needCleanUp.Repo
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class RepoFragment : BaseDataFragment<RepoFragmentBinding>(), RepoView {

    companion object {
        private const val ARGS_REPO_ID = "args_repo_id"
        private const val ARGS_REPO_NAME = "args_repo_name"
        private const val ARGS_USER_NAME = "args_user_name"

        fun newInstance(repoId: Long, repoName: String, userName: String): RepoFragment =
            RepoFragment().build {
                putLong(ARGS_REPO_ID, repoId)
                putString(ARGS_REPO_NAME, repoName)
                putString(ARGS_USER_NAME, userName)
            }
    }

    @Inject
    lateinit var presenter: RepoPresenter

    // Properties
    private fun getParam() = "yunze"
    private val repoId: Long by lazy { getLongArg(ARGS_REPO_ID) }
    private val repoName: String by lazy { getStringArg(ARGS_REPO_NAME) }
    private val userName: String by lazy { getStringArg(ARGS_USER_NAME) }

    // View Binding
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> RepoFragmentBinding =
        RepoFragmentBinding::inflate

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

    //region INTENTS
    override fun intentLoadData(): Observable<String> =
        Observable.just(getParam())

    override fun intentRefreshData(): Observable<String> =
        binding.swipeRefreshLayout.refreshes().map { getParam() }

    override fun intentErrorRetry(): Observable<String> =
        btnErrorRetry?.clicks()?.map { getParam() } ?: Observable.never()

    override fun intentActionLink(): Observable<Unit> =
        (activity as RepoActivity).intentActionLink
    //endregion

    //region RENDER
    override fun render(viewModel: RepoViewModel) {

        showLoading(viewModel.loadingState == LoadingState.LOADING)
        showRefreshingLoading(binding.swipeRefreshLayout, false)
        showRetryLoading(viewModel.loadingState == LoadingState.RETRY)
        showContent(binding.content, viewModel.contentState == ContentState.CONTENT)
        showError(viewModel.contentState == ContentState.ERROR)

        renderData(viewModel.data)
        renderError(viewModel.errorMessage)
        renderSnack(viewModel.snackMessage)
    }

    private fun renderData(repo: Repo?) {
        repo?.also {
            binding.textRepoName.text = it.name
            binding.textRepoDescription.text = it.description
        }
    }
    //endregion
}
