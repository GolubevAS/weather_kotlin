package com.example.weather_kotlin.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weather_kotlin.viewmodel.MainViewModel
import com.example.weather_kotlin.databinding.MainFragmentBinding
import com.example.weather_kotlin.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)
            .get(MainViewModel::class.java)

        //подписались  на изменения LiveDATA
        viewModel.getData().observe(viewLifecycleOwner, { state ->
            render(state)
        })
        //запросили новые даные
        viewModel.getWeather()



    }

    private fun render(state : AppState) {

        when (state) {
            is AppState.Success -> {
                binding.loadingContainer.visibility = View.GONE
                binding.cityName.text = state.weather.city
                binding.temperature.text = state.weather.temperature.toString()
            }
            is AppState.Error -> {
                binding.loadingContainer.visibility = View.VISIBLE
                Snackbar.make(binding.root, state.error.message.toString(), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Попробовать снова") {
                        //запрос новых данных
                        viewModel.getWeather()
                    }.show()
            }
            is AppState.Loading ->
                binding.loadingContainer.visibility = View.VISIBLE

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}