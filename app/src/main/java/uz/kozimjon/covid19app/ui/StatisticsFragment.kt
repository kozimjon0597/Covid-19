package uz.kozimjon.covid19app.ui

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.kozimjon.covid19app.adapter.PagerAdapter
import uz.kozimjon.covid19app.databinding.FragmentStatisticsBinding
import uz.kozimjon.covid19app.utils.Constants
import uz.kozimjon.covid19app.utils.CoronavirusResource
import uz.kozimjon.covid19app.viewmodel.CoronavirusViewModel

@AndroidEntryPoint
class StatisticsFragment : Fragment() {
    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private val coronavirusViewModel: CoronavirusViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = PagerAdapter(this@StatisticsFragment)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) {tab, index ->
            tab.text = when(index) {
                0 ->  {"Uzbekistan"}
                1 ->  {"Global"}
                else -> {
                    throw Resources.NotFoundException("Position not found")
                }
            }
        }.attach()

        lifecycleScope.launch {
            coronavirusViewModel.getData().collect {
                when (it) {
                    is CoronavirusResource.Success -> {
                        binding.tvUpdateDate.text = it.coronavirus.cache?.lastUpdated.toString()
                        screenVisible()
                    }
                    is CoronavirusResource.Error -> {
                        Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                        screenVisible()
                    }
                    is CoronavirusResource.Loading -> {
                        progressVisible()
                    }
                }
            }
        }

        binding.llTotal.setOnClickListener {
            binding.vTotal.visibility = View.VISIBLE
            binding.vToday.visibility = View.INVISIBLE
        }

        binding.llToday.setOnClickListener {
//            binding.vToday.visibility = View.VISIBLE
//            binding.vTotal.visibility = View.INVISIBLE

            Toast.makeText(requireContext(), "No data of Today", Toast.LENGTH_SHORT).show()
        }
    }

    // Functions

    private fun screenVisible() {
        binding.progressBar.visibility = View.GONE
        binding.clContent.visibility = View.VISIBLE
    }

    private fun progressVisible() {
        binding.progressBar.visibility = View.VISIBLE
        binding.clContent.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}