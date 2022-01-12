package com.example.weather_kotlin.view

import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import coil.load
import coil.transform.CircleCropTransformation
import com.example.weather_kotlin.R
import com.example.weather_kotlin.databinding.DetailFragmentBinding
import com.example.weather_kotlin.viewmodel.MainViewModel
import com.example.weather_kotlin.databinding.MainFragmentBinding
import com.example.weather_kotlin.model.*
import com.example.weather_kotlin.viewmodel.AppState
import com.example.weather_kotlin.viewmodel.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import java.lang.NullPointerException

class DetailFragment : Fragment() {

    companion object {
        fun newInstance(bundle: Bundle?): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel : DetailViewModel by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
    }

    private val listener = Repository.OnLoadListener {

            RepositoryImpl.getWeatherFromServer()?.let { weather ->
                binding.feelsLikeValue.text = weather.feelsLike.toString()
                binding.temperature.text = weather.temperature.toString()
                binding.weatherDescription.text = weather.description.toString()

                //грузим картинку с помощью coil
                binding.weatherImage.load("https://cdn.pixabay.com/photo/2021/10/26/12/34/christmas-6743572_1280.jpg") {
                    crossfade(true)
                    placeholder(R.drawable.ic_baseline_flag_24)
                    transformations(CircleCropTransformation())
                }

                viewModel.saveHistory(weather)

            } ?: Toast.makeText(context, "Error...", Toast.LENGTH_SHORT).show()
        }




    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RepositoryImpl.addLoadListener(listener)

        val weather: Weather? = arguments?.getParcelable("WEATHER_EXTRA")

        weather?.let {
            binding.cityName.text = weather.city.name
            binding.temperature.text = weather.temperature.toInt().toString()
            binding.feelsLikeValue.text = weather.feelsLike.toInt().toString()

            requireActivity().startService(Intent(requireContext(), MainIntentService::class.java)
                .apply {
                    putExtra("WEATHER_EXTRA", weather)
                })

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        RepositoryImpl.removeLoadListener(listener)
        _binding = null
    }




}