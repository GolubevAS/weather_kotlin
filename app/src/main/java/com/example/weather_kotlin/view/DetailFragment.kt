package com.example.weather_kotlin.view

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.weather_kotlin.databinding.DetailFragmentBinding
import com.example.weather_kotlin.viewmodel.MainViewModel
import com.example.weather_kotlin.databinding.MainFragmentBinding
import com.example.weather_kotlin.model.Weather
import com.example.weather_kotlin.model.WeatherDTO
import com.example.weather_kotlin.model.WeatherLoader
import com.example.weather_kotlin.viewmodel.AppState
import com.example.weather_kotlin.viewmodel.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import java.lang.NullPointerException

class DetailFragment : Fragment() {

    companion object {
        fun newInstance(bundle: Bundle?) : DetailFragment {
            val fragment =  DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
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

        val weather: Weather? = arguments?.getParcelable("WEATHER_EXTRA")

        weather?.let {

            binding.cityName.text = weather.city.name


            WeatherLoader.load(weather.city, object : WeatherLoader.OnWeatherLoadListener {
                override fun onLoaded(weatherDTO: WeatherDTO) {

                    weatherDTO.main?.let {main ->
                        binding.weatherDescription.text = main.description
                        binding.temperature.text = main.toString()
                        binding.feelsLikeValue.text = main.toString()
                    }
                }

                override fun onFailed(throwable: Throwable) {
                    Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_SHORT).show()
                }

            })


        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}