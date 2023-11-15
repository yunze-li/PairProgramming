package com.duolingo.app.sessionlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.duolingo.app.databinding.SessionItemBinding

class SessionElementAdapter : RecyclerView.Adapter<SessionElementAdapter.ViewHolder>() {

    private var sessionElements: MutableList<SessionListViewModel.SessionElement> = mutableListOf()

    fun setSessionElement(newData: List<SessionListViewModel.SessionElement>) {
        val diffResult = DiffUtil.calculateDiff(SessionItemElementDiffCallback(sessionElements, newData))
        sessionElements.clear()
        sessionElements.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(SessionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(sessionElements[position])

    override fun getItemCount() = sessionElements.size

    class ViewHolder(private val binding: SessionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            sessionElement: SessionListViewModel.SessionElement,
        ) = with(itemView) {
            binding.sessionName.text = sessionElement.name
            binding.difficultyValue.text = sessionElement.difficultyValueText
            setOnClickListener { sessionElement.onSessionClicked.invoke() }
        }
    }
}