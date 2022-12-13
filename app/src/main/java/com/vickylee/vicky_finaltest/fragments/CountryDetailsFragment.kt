package com.vickylee.vicky_finaltest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.vickylee.vicky_finaltest.R
import com.vickylee.vicky_finaltest.databinding.FragmentCountryDetailsBinding

class CountryDetailsFragment : Fragment(R.layout.fragment_country_details), OnMapReadyCallback {

    //region Properties
    val TAG = this.toString()
    private var _binding: FragmentCountryDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: CountryDetailsFragmentArgs by navArgs()

    private lateinit var mMap: GoogleMap
    private lateinit var locationToShow: LatLng
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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

        locationToShow = LatLng(43.98884, -79.34)


        binding.tvName.setText(args.country.name)

        if (args.country.capital == null || (args.country.capital)!!.isEmpty()) {
            binding.tvCapital.setText("Capital: N/A")
        } else {
            var text = "Capital: "
            for(item in args.country.capital!!) {
                text += item
            }
            binding.tvCapital.setText("$text")
        }

        binding.tvPopulation.setText("Population: ${args.country.population}")

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        //val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(locationToShow).title("You are here"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationToShow, 5.0f))

        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.isTrafficEnabled = false

        val uiSetting = googleMap.uiSettings
        uiSetting.isZoomControlsEnabled = true
        uiSetting.isCompassEnabled = true
    }

}