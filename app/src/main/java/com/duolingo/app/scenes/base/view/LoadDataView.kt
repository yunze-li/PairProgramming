package com.duolingo.app.scenes.base.view

/**
 * Interface representing a View that will use to load data.
 */
interface LoadDataView<in ViewModel> {

    fun render(viewModel: ViewModel)

}
