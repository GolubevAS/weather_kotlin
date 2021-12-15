package com.example.weather_kotlin.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_kotlin.R
import com.example.weather_kotlin.viewmodel.MainViewModel
import com.example.weather_kotlin.databinding.MainFragmentBinding
import com.example.weather_kotlin.model.Weather
import com.example.weather_kotlin.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MainAdapter()
    private var isRussian = true

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
        //создаем RecyclerView
        binding.mainRecyclerView.adapter = adapter

       adapter.listener =  MainAdapter.OnItemClick { weather ->

               val bundle = Bundle()
               bundle.putParcelable("WEATHER_EXTRA", weather)

               requireActivity().supportFragmentManager.beginTransaction()
                   .replace(R.id.main_container, DetailFragment.newInstance(bundle))
                   .addToBackStack("")
                   .commit()

       }

        //создаем ViewModel
        viewModel = ViewModelProvider(this)
            .get(MainViewModel::class.java)

        //подписались  на изменения LiveDATA
        viewModel.getData().observe(viewLifecycleOwner, { state ->
            render(state)
        })
        //запросили новые даные
        viewModel.getWeatherFromLocalStorageRus()

        binding.mainFAB.setOnClickListener{
            isRussian = !isRussian

            if (isRussian) {
                viewModel.getWeatherFromLocalStorageRus()
                binding.mainFAB.setImageResource(R.drawable.ic_baseline_outlined_flag_24)
            } else {
                viewModel.getWeatherFromLocalStorageWorld()
                binding.mainFAB.setImageResource(R.drawable.ic_baseline_flag_24)
            }
        }

    }

    private fun render(state : AppState) {

        when (state) {
            is AppState.Success<*> -> {
               val weather : List<Weather> = state.data as List<Weather>
                adapter.setWeather(weather)
                binding.loadingContainer.visibility = View.GONE

            }
            is AppState.Error -> {
                binding.loadingContainer.visibility = View.VISIBLE
                Snackbar.make(binding.root, state.error.message.toString(), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Попробовать снова") {
                        //запрос новых данных
                        viewModel.getWeatherFromLocalStorageRus()
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