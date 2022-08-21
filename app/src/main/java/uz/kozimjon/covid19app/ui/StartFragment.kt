package uz.kozimjon.covid19app.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import io.paperdb.Paper
import uz.kozimjon.covid19app.R
import uz.kozimjon.covid19app.adapter.SliderAdapter
import uz.kozimjon.covid19app.databinding.FragmentStartBinding
import uz.kozimjon.covid19app.model.Slider

class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
    private var list = ArrayList<Slider>()
    private var selectedItem = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewPager()

        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position == 0) {
                    binding.tvNext.text = "GET STARTED"
                } else {
                    binding.tvNext.text = "NEXT"
                }

                selectedItem = position
            }
        })

        binding.tvNext.setOnClickListener {
            if (selectedItem == 2) {
                Paper.book().write("saved_data", "OK")

                val action = StartFragmentDirections.actionStartFragmentToNavHome()
                findNavController().navigate(action)
            } else {
                binding.viewpager.currentItem = binding.viewpager.currentItem + 1
            }
        }
    }

    private fun setViewPager() {
        list = ArrayList()
        list.add(Slider("Wear a Mask", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Volutpat felis sit eget euismod et vulputate. Vitae lacus, maecenas odio ac.",
            R.drawable.page1
        ))
        list.add(Slider("Hand Wash & Sanitize", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Volutpat felis sit eget euismod et vulputate. Vitae lacus, maecenas odio ac.",
            R.drawable.page2
        ))
        list.add(Slider("Pyshical Distancing", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Volutpat felis sit eget euismod et vulputate. Vitae lacus, maecenas odio ac.",
            R.drawable.page3
        ))

        val sliderAdapter = SliderAdapter()
        binding.viewpager.adapter = sliderAdapter
        sliderAdapter.setData(list)

        setIndicator()
    }

    private fun setIndicator() {
        binding.indicatorView.apply {
            setSliderWidth(resources.getDimension(R.dimen.dp_10))
            setSliderHeight(resources.getDimension(R.dimen.dp_10))
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.CIRCLE)
            setupWithViewPager(binding.viewpager)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}