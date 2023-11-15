package com.duolingo.app.courselist

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil

class CourseUiModelDiffCallback(
    private val oldList: List<CourseListViewModel.CourseUiModel>,
    private val newList: List<CourseListViewModel.CourseUiModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].uiLanguageText == newList[newItemPosition].uiLanguageText
                && oldList[oldItemPosition].learningLanguageText == newList[newItemPosition].learningLanguageText

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val (oldUiLanguage, oldLearningLanguage, _) = oldList[oldPosition]
        val (newUiLanguage, newLearningLanguage, _) = newList[newPosition]
        return oldUiLanguage == newUiLanguage && oldLearningLanguage == newLearningLanguage
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? =
        super.getChangePayload(oldPosition, newPosition)

}