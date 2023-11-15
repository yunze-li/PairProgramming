package com.duolingo.app.courselist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.duolingo.app.R
import com.duolingo.app.databinding.CourseItemBinding

class CourseAdapter : RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    private var courseUiModels: MutableList<CourseListViewModel.CourseUiModel> = mutableListOf()

    fun setData(newData: List<CourseListViewModel.CourseUiModel>) {
        val diffResult = DiffUtil.calculateDiff(CourseUiModelDiffCallback(courseUiModels, newData))
        courseUiModels.clear()
        courseUiModels.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(CourseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(courseUiModels[position])

    override fun getItemCount() = courseUiModels.size

    class ViewHolder(private val binding: CourseItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            courseUiModel: CourseListViewModel.CourseUiModel,
        ) = with(itemView) {
            binding.courseName.text = context.getString(R.string.course_display_name_with_languages, courseUiModel.uiLanguageText, courseUiModel.learningLanguageText)
            setOnClickListener { courseUiModel.onCourseClick.invoke() }
        }
    }
}