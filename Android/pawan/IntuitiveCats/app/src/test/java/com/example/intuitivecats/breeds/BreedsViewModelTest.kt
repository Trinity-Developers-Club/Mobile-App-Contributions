package com.example.intuitivecats.breeds


import com.example.intuitivecats.factory.BreedFactory
import com.example.intuitivecats.network.CatRepository
import com.example.intuitivecats.utils.InstantExecutorExtension
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
internal class BreedsViewModelTest {

    @MockK
    lateinit var catRepository: CatRepository

    private lateinit var breedsViewModel: BreedsViewModel


    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        breedsViewModel = BreedsViewModel(catRepository)
    }

    @Test
    fun `fetch cat breed by id successfully returns a breed`() {
        every {
            catRepository.getBreedById("id")
        } returns BreedFactory.create(adaptability = 23, hypoallergenic = 25)

        val item = breedsViewModel.getBreedById("id")
        Assertions.assertThat(item.adaptability).isEqualTo(23)
        Assertions.assertThat(item.hypoallergenic).isEqualTo(25)
    }
}
