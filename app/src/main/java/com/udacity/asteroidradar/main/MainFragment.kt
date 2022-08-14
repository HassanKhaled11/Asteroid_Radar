package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.adapter.AsteroidRecyclerView
import com.udacity.asteroidradar.api.date_list
import com.udacity.asteroidradar.api.end_date
import com.udacity.asteroidradar.api.start_date
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.loadImage

class MainFragment : Fragment() {

    lateinit var progbar:ProgressBar
    lateinit var viewmodel: MainViewModel

    val myRecyclerView: AsteroidRecyclerView by lazy {
        AsteroidRecyclerView {
            findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)

        viewmodel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewmodel
        var rv_showData = binding.asteroidRecycler
        rv_showData.adapter = myRecyclerView
        progbar = binding.statusLoadingWheel


        viewmodel.getAPIAsteroids()
        progbar.visibility = View.VISIBLE

        viewmodel.AsteroidsListLiveData.observe(requireActivity(), Observer {
            progbar.visibility = View.GONE
            myRecyclerView.setList(it)

        })

        viewmodel.SavedAsteroidsLiveData.observe(requireActivity()) {
             progbar.visibility = View.GONE
            Toast.makeText(requireContext(), "saved", Toast.LENGTH_LONG).show()
            myRecyclerView.setList(it)
            binding.activityMainImageOfTheDay.setImageResource(R.drawable.nebula)
        }


        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.show_all_menu-> {
                start_date = date_list.get(0)
                end_date = date_list.get((Constants.DEFAULT_END_DATE_DAYS))
                viewmodel.getAPIAsteroids()
            }
                R.id.show_rent_menu -> {
                    start_date = date_list.get(0)
                    end_date = date_list.get(0)
                    viewmodel.getAPIAsteroids()

                }
            R.id.show_buy_menu -> {
                   start_date = date_list.get(0)
                   end_date = date_list.get((Constants.DEFAULT_END_DATE_DAYS))
                    viewmodel.getAPIAsteroids()

            }
        }
        return  super.onOptionsItemSelected(item)
    }

}
