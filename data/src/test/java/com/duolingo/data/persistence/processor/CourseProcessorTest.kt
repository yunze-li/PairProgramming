package com.duolingo.data.persistence.processor

import com.duolingo.data.persistence.dao.CourseDao
import com.duolingo.data.persistence.entity.CourseEntity
import com.duolingo.domain.exception.PersistenceException
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class CourseProcessorTest {

    private var dao: CourseDao = mockk()

    private lateinit var processor: CourseProcessor

    @Before
    fun setup() {
        processor = CourseProcessor(dao)
    }

    @Test
    fun insertRepo() {
        val rowID = 1L
        val entity: CourseEntity = mockk()

        every { dao.insert(entity) } returns rowID

        processor.insert(entity).test().assertResult()
    }

    @Test
    fun insertRepoFail() {
        val entity: CourseEntity = mockk()

        every { dao.insert(entity) } returns 0L

        processor.insert(entity).test().assertError(PersistenceException::class.java)
    }
//
//    @Test
//    fun deleteRepo() {
//        val nbEntityDeleted = 1
//        val entity = mock<CourseEntity>()
//
//        whenever(dao.delete(entity)).thenReturn(nbEntityDeleted)
//
//        processor.delete(entity).test()
//            .assertResult()
//    }
//
//    @Test
//    fun deleteRepoFail() {
//        val entity = mock<CourseEntity>()
//
//        whenever(dao.delete(entity)).thenReturn(0)
//
//        processor.delete(entity).test()
//            .assertError(PersistenceException::class.java)
//    }
//
//    @Test
//    fun getRepo() {
//        val id = 1L
//        val entity = mock<CourseEntity>()
//
//        whenever(dao.get(id)).thenReturn(entity)
//
//        processor.get(id).test()
//            .assertValueCount(1)
//            .assertResult(entity)
//    }
//
//    @Test
//    fun getRepoEmpty() {
//        val id = 1L
//
//        whenever(dao.get(id)).thenReturn(null)
//
//        processor.get(id).test()
//            .assertResult()
//    }
//
//    @Test
//    fun getListRepo() {
//        val courseList = mock<List<CourseEntity>>()
//
//        whenever(dao.getAllCourses()).thenReturn(courseList)
//
//        processor.getAllCourses().test()
//            .assertValueCount(1)
//            .assertResult(courseList)
//    }

}