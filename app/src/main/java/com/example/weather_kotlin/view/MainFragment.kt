package com.example.weather_kotlin.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_kotlin.R
import com.example.weather_kotlin.viewmodel.MainViewModel
import com.example.weather_kotlin.databinding.MainFragmentBinding
import com.example.weather_kotlin.model.Weather
import com.example.weather_kotlin.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.util.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MainAdapter()
    private var isRussian = true

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            val fineLocationGranted = result.getOrDefault(
                Manifest.permission.ACCESS_FINE_LOCATION, false
            )
            val coarseLocationGranted = result.getOrDefault(
                Manifest.permission.ACCESS_COARSE_LOCATION, false
            )

            when {
                fineLocationGranted or coarseLocationGranted -> showLocation()
                ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION
                ) -> {
                    AlertDialog.Builder(requireActivity()).setTitle(" Предоставьте доступ")
                        .setMessage(" Необходимдоступ")
                        .setPositiveButton(" Дать доступ") { _, _ -> requestPermission() }
                        .setNegativeButton(" Не дать доступ") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
                else -> requestPermission()
            }
        }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun requestPermission() {
        permissionResult.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    @SuppressLint("MissingPermission")
    private fun showLocation() {

        requireActivity().startActivity(Intent(requireContext(), MapsActivity::class.java))

//        val locationManager =
//            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
//
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            val providerGps = locationManager.getProvider(LocationManager.GPS_PROVIDER)
//
//            providerGps?.let {
//                locationManager.requestLocationUpdates(
//                    LocationManager.GPS_PROVIDER,
//                    60_000L,
//                    100F,
//                    object : LocationListener {
//                        override fun onLocationChanged(location: Location) {
//                            getAdressbyLocation(location)
//                        }
//
//                        override fun onStatusChanged(
//                            provider: String?,
//                            status: Int,
//                            extras: Bundle?
//                        ) {
//                            //super.onStatusChanged(provider, status, extras)
//                        }
//
//                        override fun onProviderEnabled(provider: String) {
//                            //super.onProviderEnabled(provider)
//                        }
//
//                        override fun onProviderDisabled(provider: String) {
//                            // super.onProviderDisabled(provider)
//                        }
//                    }
//                )
//            } ?:
//                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let { location ->
//                    getAdressbyLocation(location)
//                }
//        } else {
//            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let { location ->
//                getAdressbyLocation(location)
//            }
//
//        }
    }

    fun getAdressbyLocation(location: Location) {

        val geocoder: Geocoder = Geocoder(requireActivity())

        Thread {

            try {
                val adress =    geocoder.getFromLocation(location.latitude, location.longitude, 1)

                requireActivity().runOnUiThread {

                    AlertDialog.Builder(requireContext())
                        .setTitle("Я тут был ${Date().time - location.time} назад")
                        .setMessage(adress[0].getAddressLine(0))
                        .show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //создаем RecyclerView
        binding.mainRecyclerView.adapter = adapter

        adapter.listener = MainAdapter.OnItemClick { weather ->

            val bundle = Bundle().apply {
                putParcelable("WEATHER_EXTRA", weather)
            }

            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .replace(R.id.main_container, DetailFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commit()
            }
        }


        //подписались  на изменения LiveDATA
        viewModel.getData().observe(viewLifecycleOwner, { state ->
            render(state)
        })
        //запросили новые даные
        viewModel.getWeatherFromLocalStorageRus()

        binding.mainFAB.setOnClickListener {
            isRussian = !isRussian

            if (isRussian) {
                viewModel.getWeatherFromLocalStorageRus()
                binding.mainFAB.setImageResource(R.drawable.ic_baseline_outlined_flag_24)
            } else {
                viewModel.getWeatherFromLocalStorageWorld()
                binding.mainFAB.setImageResource(R.drawable.ic_baseline_flag_24)
            }
        }

        binding.historyFAB.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), HistoryActivity::class.java))
        }

        binding.locationFAB.setOnClickListener {
            requestPermission()
        }

    }

    private fun render(state: AppState) {

        when (state) {
            is AppState.Success<*> -> {
                val weather: List<Weather> = state.data as List<Weather>
                adapter.setWeather(weather)
                binding.loadingContainer.hide()

            }
            is AppState.Error -> {
                binding.loadingContainer.show()
                binding.root.showSnackBar(state.error.message.toString(),
                    getString(R.string.try_again),
                    {
                        //Запросили новые данные
                        viewModel.getWeatherFromLocalStorageRus()
                    })
            }
            is AppState.Loading ->
                binding.loadingContainer.show()

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}