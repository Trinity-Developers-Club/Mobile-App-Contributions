package com.example.pagerv.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher

import org.junit.runner.Description

@ExperimentalCoroutinesApi
class CoroutineTestRule : TestWatcher() {

//    val testDispatcherProvider = object : DispatcherProvider {
//        override val io: CoroutineDispatcher = testDispatcher
//        override val ui: CoroutineDispatcher = testDispatcher
//        override val default: CoroutineDispatcher = testDispatcher
//        override val unconfined: CoroutineDispatcher = testDispatcher
//    }

    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}

//interface DispatcherProvider {
//    val io: CoroutineDispatcher
//    val ui: CoroutineDispatcher
//    val default: CoroutineDispatcher
//    val unconfined: CoroutineDispatcher
//}