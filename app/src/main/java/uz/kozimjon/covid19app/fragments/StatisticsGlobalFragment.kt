package uz.kozimjon.covid19app.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.kozimjon.covid19app.adapter.CountryAdapter
import uz.kozimjon.covid19app.databinding.FragmentStatitsticsGlobalBinding
import uz.kozimjon.covid19app.model.Coronavirus
import uz.kozimjon.covid19app.utils.CoronavirusResource
import uz.kozimjon.covid19app.viewmodel.CoronavirusViewModel

@AndroidEntryPoint
class StatisticsGlobalFragment() : Fragment() {
    private var _binding: FragmentStatitsticsGlobalBinding? = null
    private val binding get() = _binding!!
    private val coronavirusViewModel: CoronavirusViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatitsticsGlobalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            coronavirusViewModel.getData().collect {
                when (it) {
                    is CoronavirusResource.Success -> {
                        screenVisible()

                        val coronavirus = it.coronavirus

                        binding.tvTotalCasesValue.text = (coronavirus.summaryStats?.global?.confirmed ?: "No data").toString()
                        binding.tvRecoveredValue.text = (coronavirus.summaryStats?.global?.recovered ?: "No data").toString()

                        if (coronavirus.summaryStats?.global?.confirmed != null) {
                            binding.tvTotalCasesRound.text = coronavirus.summaryStats.global.confirmed.toString().substring(0, 3) + " M+"
                        } else {
                            binding.tvTotalCasesRound.text = "No data"
                        }

                        if (coronavirus.summaryStats?.global?.recovered != null) {
                            binding.tvRecoveredRound.text = coronavirus.summaryStats.global.recovered.toString().substring(0, 3) + " M+"
                        } else {
                            binding.tvRecoveredRound.text = "No data"
                        }

                        binding.tvProtsent.text = "72 %"
                        binding.progressBar.progress = 72

                        var index = 0
                        val list = ArrayList<Coronavirus.CountryData>()
                        for (element in coronavirus.rawData!!) {
                            list.add(element)
                            index++
                            if (index ==10 ){
                                break
                            }
                        }
                        val adapter = CountryAdapter()
                        binding.rvCountries.adapter = adapter
                        adapter.setData(list)
                    }
                    is CoronavirusResource.Error -> {
                        screenVisible()
                        Log.d("TAG", it.toString())
                    }
                    is CoronavirusResource.Loading -> {
                        progressVisible()
                    }
                }
            }
        }
    }

    // Functions

    private fun screenVisible() {
        binding.pbLoading.visibility = View.GONE
        binding.nsv.visibility = View.VISIBLE
    }

    private fun progressVisible() {
        binding.pbLoading.visibility = View.VISIBLE
        binding.nsv.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}