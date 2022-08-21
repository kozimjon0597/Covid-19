package uz.kozimjon.covid19app.ui

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import uz.kozimjon.covid19app.adapter.NewAdapter
import uz.kozimjon.covid19app.databinding.FragmentNewsBinding
import uz.kozimjon.covid19app.fragments.archive_fragment.ArchivedFragment


class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = NewAdapter(this@NewsFragment)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) {tab, index ->
            tab.text = when(index) {
                0 ->  {"Saved"}
                1 ->  {"Archived"}
                2 ->  {"Recently viewed"}
                3 ->  {"Highlight"}
                else -> {
                    throw Resources.NotFoundException("Position not found")
                }
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}