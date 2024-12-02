package com.example.intuitivecats.breeds.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.intuitivecats.MainActivity
import com.example.intuitivecats.R
import com.example.intuitivecats.databinding.FragmentBreedDetailsBinding


class BreedDetailsFragment : Fragment() {

    private val args: BreedDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentBreedDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedDetailsBinding.inflate(
            layoutInflater
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        binding.apply {
            val catBreed = args.breed
            breed.text = catBreed.name
            country.text = catBreed.origin
            weight.text = " ${catBreed.weight?.metric} ${getString(R.string.kilograms)}"
            lifespan.text = " ${catBreed.life_span}  ${getString(R.string.years)}"
            description.text = catBreed.description

            btnMoreinfo.setOnClickListener {
                sendingToWikipedia()
            }

            Glide.with(binding.root.context)
                .load(catBreed.image?.url)
                .placeholder(R.drawable.ic_baseline_pets_24)
                .error(R.drawable.ic_baseline_pets_24)
                .centerCrop()
                .into(binding.catpic)
        }
    }

    private fun sendingToWikipedia() {
        val url = args.breed.wikipedia_url
        url?.let {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}
