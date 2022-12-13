package com.vickylee.vicky_finaltest.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.vickylee.vicky_finaltest.R
import com.vickylee.vicky_finaltest.databinding.FragmentCountryDetailsBinding
import com.vickylee.vicky_finaltest.db.Country
import com.vickylee.vicky_finaltest.db.CountryRepository
import kotlinx.coroutines.launch

class CountryDetailsFragment : Fragment(R.layout.fragment_country_details), OnMapReadyCallback {

    //region Properties
    val TAG = this.toString()
    private var _binding: FragmentCountryDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: CountryDetailsFragmentArgs by navArgs()

    // Google Map
    private lateinit var mMap: GoogleMap
    private lateinit var locationToShow: LatLng
    private lateinit var capitalLocationToShow: LatLng

    // Room
    private lateinit var countryRepository: CountryRepository

    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        countryRepository = CountryRepository(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (args.country.latlng != null) {
            locationToShow =
                LatLng(args.country.latlng!![0].toDouble(), args.country.latlng!![1].toDouble())
        }

        binding.tvName.setText(args.country.name)

        if (args.country.capital == null || (args.country.capital)!!.isEmpty()) {
            binding.tvCapital.setText("Capital: N/A")
        } else {
            var text = "Capital: "
            for (item in args.country.capital!!) {
                text += item
            }
            binding.tvCapital.setText("$text")
        }

        binding.tvPopulation.setText("Population: ${args.country.population}")


        binding.btnFavorite.setOnClickListener {

            lifecycleScope.launch {
                val countryName = args.country.name
                var capital: String = "N/A"
                val population = args.country.population

                if (args.country.capital != null) {
                    var capitalList: List<String> = args.country.capital!!
                    capital = capitalList[0]
                }

                val favCountry = Country(countryName, capital, population)
                countryRepository.insertFavCountry(favCountry)
            }

            Toast.makeText(context, "Added to favorite", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker
        if (args.country.capitalInfo != null) {
            capitalLocationToShow = LatLng(
                args.country.capitalInfo!![0].toDouble(),
                args.country.capitalInfo!![1].toDouble()
            )
            mMap.addMarker(
                MarkerOptions().position(capitalLocationToShow).title(args.country.capital!![0])
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(capitalLocationToShow, 5.0f))
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationToShow, 5.0f))
        }

        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.isTrafficEnabled = false

        val uiSetting = googleMap.uiSettings
        uiSetting.isZoomControlsEnabled = true
        uiSetting.isCompassEnabled = true
    }

}