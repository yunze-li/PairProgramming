package com.duolingo.app.courselist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.duolingo.app.R
import com.duolingo.app.databinding.CourseItemBinding
import com.duolingo.domain.model.Course
import io.reactivex.rxjava3.subjects.PublishSubject

class CourseAdapter : RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    val repoClickIntent: PublishSubject<Course> = PublishSubject.create()
//    val repoFavoriteIntent: PublishSubject<Pair<Int, Repo>> = PublishSubject.create()

    private var data: MutableList<Course> = mutableListOf()

    fun setData(newData: List<Course>) {
        val diffResult = DiffUtil.calculateDiff(CoursesDiffCallback(data, newData))
        data.clear()
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setData(position: Int, course: Course) {
        data[position] = course
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(CourseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(data[position], repoClickIntent)

    override fun getItemCount() = data.size

    class ViewHolder(private val binding: CourseItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            course: Course,
            courseClickIntent: PublishSubject<Course>,
//            repoFavoriteIntent: PublishSubject<Pair<Int, Repo>>
        ) = with(itemView) {
            binding.courseName.text = context.getString(R.string.course_display_name_with_languages, course.uiLanguage.name, course.learningLanguage.name)

            setOnClickListener { courseClickIntent.onNext(course) }
//            binding.imageFavoriteRepo.setOnClickListener {
//                repoFavoriteIntent.onNext(Pair(absoluteAdapterPosition, repo))
//            }
        }
    }
}