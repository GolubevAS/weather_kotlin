package com.example.weather_kotlin.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weather_kotlin.databinding.DetailFragmentBinding
import com.example.weather_kotlin.viewmodel.MainViewModel
import com.example.weather_kotlin.databinding.MainFragmentBinding
import com.example.weather_kotlin.model.Weather
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weather: Weather? = arguments?.getParcelable("WEATHER _EXTRA")

        weather?.let {

            binding.cityName.text = weather.city.name
            binding.temperature.text = weather.temperature.toString()

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}