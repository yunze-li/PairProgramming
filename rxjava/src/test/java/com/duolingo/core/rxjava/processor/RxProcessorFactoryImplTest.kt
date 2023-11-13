package com.duolingo.core.rxjava.processor

import com.duolingo.rxjava.processor.BackpressureStrategy
import com.duolingo.rxjava.processor.RxProcessorFactoryImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.processors.BehaviorProcessor
import io.reactivex.rxjava3.processors.PublishProcessor
import org.junit.Assert.assertEquals
import org.junit.Test

class RxProcessorFactoryImplTest {

  private val factory = RxProcessorFactoryImpl()

  @Test
  fun `when behavior processor has no default then it is serialized`() {
    val mockFlowable: Flowable<Int> = mockk()
    val mockSerializedProcessor: BehaviorProcessor<Int> = mockk {
      every { onBackpressureLatest() } returns mockFlowable
    }
    val mockProcessor: BehaviorProcessor<Int> = mockk {
      every { toSerialized() } returns mockSerializedProcessor
    }

    mockkStatic(BehaviorProcessor::class) {
      every { BehaviorProcessor /* splinter ignore */.create<Int>() } returns mockProcessor

      assertEquals(mockFlowable, factory.behavior<Int>().observe())
    }
  }

  @Test
  fun `when behavior processor has no default and observation uses default strategy then latest backpressure strategy is used`() {
    val mockFlowable: Flowable<Int> = mockk()
    val mockProcessor: BehaviorProcessor<Int> = mockk {
      every { toSerialized() } returns this
      every { onBackpressureLatest() } returns mockFlowable
    }

    mockkStatic(BehaviorProcessor::class) {
      every { BehaviorProcessor /* splinter ignore */.create<Int>() } returns mockProcessor

      assertEquals(mockFlowable, factory.behavior<Int>().observe())
    }
  }

  @Test
  fun `when behavior processor has no default and observation uses buffer strategy then buffer backpressure strategy is used`() {
    val mockFlowable: Flowable<Int> = mockk()
    val mockProcessor: BehaviorProcessor<Int> = mockk {
      every { toSerialized() } returns this
      every { onBackpressureBuffer() } returns mockFlowable
    }

    mockkStatic(BehaviorProcessor::class) {
      every { BehaviorProcessor /* splinter ignore */.create<Int>() } returns mockProcessor

      assertEquals(mockFlowable, factory.behavior<Int>().observe(BackpressureStrategy.BUFFER))
    }
  }

  @Test
  fun `when behavior processor has no default and observation uses drop strategy then drop backpressure strategy is used`() {
    val mockFlowable: Flowable<Int> = mockk()
    val mockProcessor: BehaviorProcessor<Int> = mockk {
      every { toSerialized() } returns this
      every { onBackpressureDrop() } returns mockFlowable
    }

    mockkStatic(BehaviorProcessor::class) {
      every { BehaviorProcessor /* splinter ignore */.create<Int>() } returns mockProcessor

      assertEquals(mockFlowable, factory.behavior<Int>().observe(BackpressureStrategy.DROP))
    }
  }

  @Test
  fun `when behavior processor has no default and observation uses latest strategy then latest backpressure strategy is used`() {
    val mockFlowable: Flowable<Int> = mockk()
    val mockProcessor: BehaviorProcessor<Int> = mockk {
      every { toSerialized() } returns this
      every { onBackpressureLatest() } returns mockFlowable
    }

    mockkStatic(BehaviorProcessor::class) {
      every { BehaviorProcessor /* splinter ignore */.create<Int>() } returns mockProcessor

      assertEquals(mockFlowable, factory.behavior<Int>().observe(BackpressureStrategy.LATEST))
    }
  }

  @Test
  fun `when behavior processor has no default then no value is observed`() {
    val processor = factory.behavior<Int>()

    processor.observe(BackpressureStrategy.BUFFER).test().assertNoValues()
    processor.observe(BackpressureStrategy.DROP).test().assertNoValues()
    processor.observe(BackpressureStrategy.LATEST).test().assertNoValues()
  }

  @Test
  fun `when behavior processor has no default and values have been offered before then latest value is observed`() {
    val processor = factory.behavior<Int>()

    UPDATES.forEach { processor.offer(it) }
    processor.offer(LAST)

    processor.observe(BackpressureStrategy.BUFFER).test().assertValue(LAST)
    processor.observe(BackpressureStrategy.DROP).test().assertValue(LAST)
    processor.observe(BackpressureStrategy.LATEST).test().assertValue(LAST)
  }

  @Test
  fun `when behavior processor has no default and values have been offered after then all values are observed`() {
    val processor = factory.behavior<Int>()

    val buffer = processor.observe(BackpressureStrategy.BUFFER).test()
    val drop = processor.observe(BackpressureStrategy.DROP).test()
    val latest = processor.observe(BackpressureStrategy.LATEST).test()

    UPDATES.forEach { processor.offer(it) }

    buffer.assertValueSequence(UPDATES)
    drop.assertValueSequence(UPDATES)
    latest.assertValueSequence(UPDATES)
  }

  @Test
  fun `when behavior processor has a default then it is serialized`() {
    val mockFlowable: Flowable<Int> = mockk()
    val mockSerializedProcessor: BehaviorProcessor<Int> = mockk {
      every { onBackpressureLatest() } returns mockFlowable
    }
    val mockProcessor: BehaviorProcessor<Int> = mockk {
      every { toSerialized() } returns mockSerializedProcessor
    }

    mockkStatic(BehaviorProcessor::class) {
      every { BehaviorProcessor /* splinter ignore */.createDefault(DEFAULT) } returns mockProcessor

      assertEquals(mockFlowable, factory.behavior(DEFAULT).observe())
    }
  }

  @Test
  fun `when behavior processor has a default and observation uses default strategy then latest backpressure strategy is used`() {
    val mockFlowable: Flowable<Int> = mockk()
    val mockProcessor: BehaviorProcessor<Int> = mockk {
      every { toSerialized() } returns this
      every { onBackpressureLatest() } returns mockFlowable
    }

    mockkStatic(BehaviorProcessor::class) {
      every { BehaviorProcessor /* splinter ignore */.createDefault(DEFAULT) } returns mockProcessor

      assertEquals(mockFlowable, factory.behavior(DEFAULT).observe())
    }
  }

  @Test
  fun `when behavior processor has a default and observation uses buffer strategy then buffer backpressure strategy is used`() {
    val mockFlowable: Flowable<Int> = mockk()
    val mockProcessor: BehaviorProcessor<Int> = mockk {
      every { toSerialized() } returns this
      every { onBackpressureBuffer() } returns mockFlowable
    }

    mockkStatic(BehaviorProcessor::class) {
      every { BehaviorProcessor /* splinter ignore */.createDefault(DEFAULT) } returns mockProcessor

      assertEquals(mockFlowable, factory.behavior(DEFAULT).observe(BackpressureStrategy.BUFFER))
    }
  }

  @Test
  fun `when behavior processor has a default and observation uses drop strategy then drop backpressure strategy is used`() {
    val mockFlowable: Flowable<Int> = mockk()
    val mockProcessor: BehaviorProcessor<Int> = mockk {
      every { toSerialized() } returns this
      every { onBackpressureDrop() } returns mockFlowable
    }

    mockkStatic(BehaviorProcessor::class) {
      every { BehaviorProcessor /* splinter ignore */.createDefault(DEFAULT) } returns mockProcessor

      assertEquals(mockFlowable, factory.behavior(DEFAULT).observe(BackpressureStrategy.DROP))
    }
  }

  @Test
  fun `when behavior processor has a default and observation uses latest strategy then latest backpressure strategy is used`() {
    val mockFlowable: Flowable<Int> = mockk()
    val mockProcessor: BehaviorProcessor<Int> = mockk {
      every { toSerialized() } returns this
      every { onBackpressureLatest() } returns mockFlowable
    }

    mockkStatic(BehaviorProcessor::class) {
      every { BehaviorProcessor /* splinter ignore */.createDefault(DEFAULT) } returns mockProcessor

      assertEquals(mockFlowable, factory.behavior(DEFAULT).observe(BackpressureStrategy.LATEST))
    }
  }

  @Test
  fun `when behavior processor has a default then default value is observed`() {
    val processor = factory.behavior(DEFAULT)

    processor.observe(BackpressureStrategy.BUFFER).test().assertValue(DEFAULT)
    processor.observe(BackpressureStrategy.DROP).test().assertValue(DEFAULT)
    processor.observe(BackpressureStrategy.LATEST).test().assertValue(DEFAULT)
  }

  @Test
  fun `when behavior processor has a default and values have been offered before then latest value is observed`() {
    val processor = factory.behavior(DEFAULT)

    UPDATES.forEach { processor.offer(it) }
    processor.offer(LAST)

    processor.observe(BackpressureStrategy.BUFFER).test().assertValue(LAST)
    processor.observe(BackpressureStrategy.DROP).test().assertValue(LAST)
    processor.observe(BackpressureStrategy.LATEST).test().assertValue(LAST)
  }

  @Test
  fun `when behavior processor has a default and values have been offered after then default and all values are observed`() {
    val processor = factory.behavior(DEFAULT)

    val buffer = processor.observe(BackpressureStrategy.BUFFER).test()
    val drop = processor.observe(BackpressureStrategy.DROP).test()
    val latest = processor.observe(BackpressureStrategy.LATEST).test()

    UPDATES.forEach { processor.offer(it) }

    val all = listOf(DEFAULT) + UPDATES
    buffer.assertValueSequence(all)
    drop.assertValueSequence(all)
    latest.assertValueSequence(all)
  }

  @Test
  fun `when publish processor is created then it is serialized`() {
    val mockFlowable: Flowable<Int> = mockk()
    val mockSerializedProcessor: PublishProcessor<Int> = mockk {
      every { onBackpressureLatest() } returns mockFlowable
    }
    val mockProcessor: PublishProcessor<Int> = mockk {
      every { toSerialized() } returns mockSerializedProcessor
    }

    mockkStatic(PublishProcessor::class) {
      every { PublishProcessor /* splinter ignore */.create<Int>() } returns mockProcessor

      assertEquals(mockFlowable, factory.publish<Int>().observe())
    }
  }

  @Test
  fun `when publish processor observation uses default strategy then latest backpressure strategy is used`() {
    val mockFlowable: Flowable<Int> = mockk()
    val mockProcessor: PublishProcessor<Int> = mockk {
      every { toSerialized() } returns this
      every { onBackpressureLatest() } returns mockFlowable
    }

    mockkStatic(PublishProcessor::class) {
      every { PublishProcessor /* splinter ignore */.create<Int>() } returns mockProcessor

      assertEquals(mockFlowable, factory.publish<Int>().observe())
    }
  }

  @Test
  fun `when publish processor observation uses buffer strategy then buffer backpressure strategy is used`() {
    val mockFlowable: Flowable<Int> = mockk()
    val mockProcessor: PublishProcessor<Int> = mockk {
      every { toSerialized() } returns this
      every { onBackpressureBuffer() } returns mockFlowable
    }

    mockkStatic(PublishProcessor::class) {
      every { PublishProcessor /* splinter ignore */.create<Int>() } returns mockProcessor

      assertEquals(mockFlowable, factory.publish<Int>().observe(BackpressureStrategy.BUFFER))
    }
  }

  @Test
  fun `when publish processor observation uses drop strategy then drop backpressure strategy is used`() {
    val mockFlowable: Flowable<Int> = mockk()
    val mockProcessor: PublishProcessor<Int> = mockk {
      every { toSerialized() } returns this
      every { onBackpressureDrop() } returns mockFlowable
    }

    mockkStatic(PublishProcessor::class) {
      every { PublishProcessor /* splinter ignore */.create<Int>() } returns mockProcessor

      assertEquals(mockFlowable, factory.publish<Int>().observe(BackpressureStrategy.DROP))
    }
  }

  @Test
  fun `when publish processor observation uses latest strategy then latest backpressure strategy is used`() {
    val mockFlowable: Flowable<Int> = mockk()
    val mockProcessor: PublishProcessor<Int> = mockk {
      every { toSerialized() } returns this
      every { onBackpressureLatest() } returns mockFlowable
    }

    mockkStatic(PublishProcessor::class) {
      every { PublishProcessor /* splinter ignore */.create<Int>() } returns mockProcessor

      assertEquals(mockFlowable, factory.publish<Int>().observe(BackpressureStrategy.LATEST))
    }
  }

  @Test
  fun `when publish processor is observed before values have been offered then no value is observed`() {
    val processor = factory.publish<Int>()

    processor.observe(BackpressureStrategy.BUFFER).test().assertNoValues()
    processor.observe(BackpressureStrategy.DROP).test().assertNoValues()
    processor.observe(BackpressureStrategy.LATEST).test().assertNoValues()
  }

  @Test
  fun `when publish processor is observed after values have been offered then no value is observed`() {
    val processor = factory.publish<Int>()

    UPDATES.forEach { processor.offer(it) }
    processor.offer(LAST)

    processor.observe(BackpressureStrategy.BUFFER).test().assertNoValues()
    processor.observe(BackpressureStrategy.DROP).test().assertNoValues()
    processor.observe(BackpressureStrategy.LATEST).test().assertNoValues()
  }

  @Test
  fun `when publish processor is observed before values have been offered then all values are observed`() {
    val processor = factory.publish<Int>()

    val buffer = processor.observe(BackpressureStrategy.BUFFER).test()
    val drop = processor.observe(BackpressureStrategy.DROP).test()
    val latest = processor.observe(BackpressureStrategy.LATEST).test()

    UPDATES.forEach { processor.offer(it) }

    buffer.assertValueSequence(UPDATES)
    drop.assertValueSequence(UPDATES)
    latest.assertValueSequence(UPDATES)
  }

  companion object {
    private const val DEFAULT = 50
    private const val FIRST = 100
    private const val LAST = 150
    private val UPDATES = FIRST until LAST
  }
}
