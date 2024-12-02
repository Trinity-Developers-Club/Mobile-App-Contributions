package com.example.pagerv.paging

import androidx.paging.PagingSource
import com.example.pagerv.utils.InstantExecutorExtension
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class DataPagingSourceTest {

    @MockK
    lateinit var api: ApiRepository

    private lateinit var dataPagingSource: DataPagingSource

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
        dataPagingSource = DataPagingSource(api, 2)
    }

    @Test
    fun `data paging source load - failure - http error`() = runTest {
        val error = RuntimeException("404", Throwable())
        coEvery {
            api.getData(0, 2)
        } throws error

        val expectedResult = PagingSource.LoadResult.Error<Int, DataItem>(error)
        assertEquals(
            expectedResult, dataPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `data paging source refresh - success`() = runTest {
        val items = listOf(
            DataItem("one", "", "", ""),
            DataItem("two", "", "", "")
        )
        coEvery {
            api.getData(0, 2)
        } returns items

        val expectedResult = PagingSource.LoadResult.Page(
            data = items,
            prevKey = null,
            nextKey = 1
        )
        assertEquals(
            expectedResult, dataPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `data paging source append - success`() = runTest {

        val nextItems = listOf(
            DataItem("three", "", "", ""),
            DataItem("four", "", "", "")
        )
        coEvery {
            api.getData(any(), any())
        } returns nextItems

        val expectedResult = PagingSource.LoadResult.Page(
            data = nextItems,
            prevKey = 0,
            nextKey = 2
        )
        assertEquals(
            expectedResult, dataPagingSource.load(
                PagingSource.LoadParams.Append(
                    key = 1,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `data paging source prepend - success`() = runTest {
        val items = listOf(
            DataItem("three", "", "", ""),
            DataItem("four", "", "", "")
        )
        coEvery {
            api.getData(any(), any())
        } returns items

        val expectedResult = PagingSource.LoadResult.Page(
            data = items,
            prevKey = null,
            nextKey = 1
        )
        assertEquals(
            expectedResult, dataPagingSource.load(
                PagingSource.LoadParams.Prepend(
                    key = 0,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        )
    }
}