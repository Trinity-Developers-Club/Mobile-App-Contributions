package com.example.intuitivecats.breeds.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intuitivecats.breeds.BreedsViewModel
import com.example.intuitivecats.databinding.FragmentBreedsBinding
import com.example.pagerv.LoadMoreAdapter
import com.example.pagerv.PagerDataAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BreedsFragment : Fragment() {

    private lateinit var binding: FragmentBreedsBinding
    private val viewModel: BreedsViewModel by viewModels()

    @Inject
    lateinit var catsAdapter: PagerDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentBreedsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        catsAdapter.setOnItemClickListener { dataItem ->
            val breed = viewModel.getBreedById(dataItem.id)
            val action =
                BreedsFragmentDirections.actionBreedsFragmentToBreedDetailsFragment(breed)
            Navigation.findNavController(binding.root).navigate(action)
        }

        binding.apply {
            recyclerViewCats.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = catsAdapter.withLoadStateFooter(
                    LoadMoreAdapter {
                        catsAdapter.retry()
                    }
                )
            }
        }

        viewModel.dataList.observe(viewLifecycleOwner) {
            lifecycleScope.launchWhenCreated {
                catsAdapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            catsAdapter.loadStateFlow.collect {
                val state = it.refresh
                binding.prgBarCats.isVisible = state is LoadState.Loading
            }
        }
    }
}
