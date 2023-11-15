package com.duolingo.app.sessionlist

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil

class SessionItemElementDiffCallback(
    private val oldList: List<SessionListViewModel.SessionElement>,
    private val newList: List<SessionListViewModel.SessionElement>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].name == newList[newItemPosition].name
                && oldList[oldItemPosition].difficultyValueText == newList[newItemPosition].difficultyValueText
                && oldList[oldItemPosition].isCompleted == newList[newItemPosition].isCompleted

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val (oldName, oldDifficulty, oldIsCompleted, _) = oldList[oldPosition]
        val (newName, newDifficulty, newIsCompleted, _) = newList[newPosition]
        return oldName == newName && oldDifficulty == newDifficulty && oldIsCompleted == newIsCompleted
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? =
        super.getChangePayload(oldPosition, newPosition)

}