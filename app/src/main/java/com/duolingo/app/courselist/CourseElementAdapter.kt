package com.duolingo.app.courselist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.duolingo.app.R
import com.duolingo.app.databinding.CourseItemBinding

class CourseElementAdapter : RecyclerView.Adapter<CourseElementAdapter.ViewHolder>() {

    private var courseElements: MutableList<CourseListViewModel.CourseElement> = mutableListOf()

    fun setCourseElement(newData: List<CourseListViewModel.CourseElement>) {
        val diffResult = DiffUtil.calculateDiff(CourseElementDiffCallback(courseElements, newData))
        courseElements.clear()
        courseElements.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(CourseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(courseElements[position])

    override fun getItemCount() = courseElements.size

    class ViewHolder(private val binding: CourseItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            courseElement: CourseListViewModel.CourseElement,
        ) = with(itemView) {
            binding.courseName.text = context.getString(R.string.course_display_name_with_languages, courseElement.uiLanguageText, courseElement.learningLanguageText)
            setOnClickListener { courseElement.onCourseClicked.invoke() }
        }
    }
}