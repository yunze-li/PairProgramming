package com.duolingo.app.path

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.duolingo.domain.model.Course

class CoursesDiffCallback(
    private val oldList: List<Course>,
    private val newList: List<Course>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val (_, oldUiLanguage, oldLearningLanguage) = oldList[oldPosition]
        val (_, newUiLanguage, newLearningLanguage) = newList[newPosition]
        return oldUiLanguage == newUiLanguage
                && oldLearningLanguage == newLearningLanguage
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? =
        super.getChangePayload(oldPosition, newPosition)

}