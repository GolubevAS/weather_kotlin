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

   // private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weather = arguments?.getParcelable<Weather>("WEATHER _EXTRA")

//
//        viewModel = ViewModelProvider(this)
//            .get(DetailViewModel::class.java)
//
//        //подписались  на изменения LiveDATA
//        viewModel.getData().observe(viewLifecycleOwner, { state ->
//            render(state as AppState)
//        })
//        //запросили новые даные
//        viewModel.getData()

        binding.cityName.text = weather?.city?.name ?: ""
        binding.temperature.text = weather?.temperature.toString()

    }

//    private fun render(state : AppState) {
//
//        when (state) {
//            is AppState.Success<*> -> {
//               val weather = state.data as Weather
//                binding.loadingContainer.visibility = View.GONE
//                binding.cityName.text = weather.city.name
//                binding.temperature.text = weather.temperature.toString()
//            }
//            is AppState.Error -> {
//                binding.loadingContainer.visibility = View.VISIBLE
//                Snackbar.make(binding.root, state.error.message.toString(), Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Попробовать снова") {
//                        //запрос новых данных
//                        viewModel.getWeather()
//                    }.show()
//            }
//            is AppState.Loading ->
//                binding.loadingContainer.visibility = View.VISIBLE
//
//        }
//
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}