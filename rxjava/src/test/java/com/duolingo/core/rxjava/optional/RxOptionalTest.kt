package com.duolingo.core.rxjava.optional

import com.duolingo.rxjava.optional.RxOptional
import com.duolingo.rxjava.optional.toRxOptional
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Test

class RxOptionalTest {

  @Test
  fun `when null is wrapped then optional contains null`() {
    assertNull(null.toRxOptional().value)
  }

  @Test
  fun `when value is wrapped then optional contains the value`() {
    val clazz = TestClass("one")
    assertEquals(clazz, clazz.toRxOptional().value)
  }

  @Test
  fun `when comparing two empty optionals then they are equal`() {
    assertEquals(null.toRxOptional(), null.toRxOptional())
  }

  @Test
  fun `when empty optional then optional contains null`() {
    assertNull(RxOptional.empty<TestClass>().value)
  }

  @Test
  fun `when comparing two non-empty optionals then they are equal only if the wrapped values are equal`() {
    val clazz1 = TestClass("one")
    val clazz2 = TestClass("two")
    assertEquals(clazz1.toRxOptional(), clazz1.toRxOptional())
    assertEquals(clazz2.toRxOptional(), clazz2.toRxOptional())
    assertNotEquals(clazz1.toRxOptional(), clazz2.toRxOptional())
  }

  @Test
  fun `when hashing an empty optional then its hash is zero`() {
    assertEquals(0, null.toRxOptional().hashCode())
  }

  @Test
  fun `when hashing a non-empty optional then its hash is equal to that of its wrapped value`() {
    val clazz = TestClass("one")
    assertEquals(clazz.hashCode(), clazz.toRxOptional().hashCode())
  }

  @Test
  fun `when retrieving the string representation of an empty optional then it shows a null value`() {
    assertEquals("RxOptional(value=null)", null.toRxOptional().toString())
  }

  @Test
  fun `when retrieving the string representation of a non-empty optional then it shows a the value`() {
    val clazz = TestClass("one")
    assertEquals("RxOptional(value=TestClass(name=one))", clazz.toRxOptional().toString())
  }

  @Test
  fun `when an empty optional is in a lambda then its destructured value is null`() {
    null.toRxOptional().let { (destructured) -> assertNull(destructured) }
  }

  @Test
  fun `when a non-empty optional is in a lambda then its destructured value is null`() {
    val clazz = TestClass("one")
    clazz.toRxOptional().let { (destructured) -> assertEquals(clazz, destructured) }
  }

  private data class TestClass(val name: String)
}
