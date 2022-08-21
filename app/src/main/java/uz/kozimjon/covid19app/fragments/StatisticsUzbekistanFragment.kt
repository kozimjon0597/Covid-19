package uz.kozimjon.covid19app.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.androidplot.xy.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.kozimjon.covid19app.databinding.FragmentStatitsticsUzbekstanBinding
import uz.kozimjon.covid19app.utils.CoronavirusResource
import uz.kozimjon.covid19app.viewmodel.CoronavirusViewModel
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import kotlin.math.roundToInt

@AndroidEntryPoint
class StatisticsUzbekistanFragment() : Fragment() {
    private var _binding: FragmentStatitsticsUzbekstanBinding? = null
    private val binding get() = _binding!!
    private val coronavirusViewModel: CoronavirusViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatitsticsUzbekstanBinding.inflate(inflater, container, false)
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

                        for (element in coronavirus.rawData!!) {
                            if (element.Country_Region == "Uzbekistan") {
                                if (element.Confirmed.isNullOrEmpty()) {
                                    binding.tvTotalCasesValue.text = "No Data"
                                } else {
                                    binding.tvTotalCasesValue.text = element.Confirmed
                                }

                                if (element.Active.isNullOrEmpty()) {
                                    binding.tvActiveCasesValue.text = "No data"
                                } else {
                                    binding.tvActiveCasesValue.text = element.Active
                                }

                                if (element.Recovered.isNullOrEmpty()) {
                                    binding.tvRecoveredValue.text = "No data"
                                } else {
                                    binding.tvRecoveredValue.text = element.Recovered
                                }

                                if (element.Deaths.isNullOrEmpty()) {
                                    binding.tvDeathValue.text = "No data"
                                } else {
                                    binding.tvDeathValue.text = element.Deaths
                                }
                                break
                            }
                        }
                    }
                    is CoronavirusResource.Error -> {
                        Log.d("TAG", it.toString())
                        screenVisible()
                    }
                    is CoronavirusResource.Loading -> {
                        progressVisible()
                    }
                }
            }
        }

        val domainLabels = arrayOf<Number>(1, 2, 3, 6, 7, 8, 9, 10, 13, 14)

        val series1Number = arrayOf<Number>(1, 4, 8, 16, 32, 26, 29, 10, 13)
        val series2Number = arrayOf<Number>(2, 8, 4, 7, 32, 12, 25, 16, 10)
        val series3Number = arrayOf<Number>(2, 4, 5, 9, 26, 10, 16, 8, 10)

        val series1: XYSeries = SimpleXYSeries(
            listOf(* series1Number),
            SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
            "Active"
        )
        val series2: XYSeries = SimpleXYSeries(
            listOf(* series2Number),
            SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
            "Recovered"
        )
        val series3: XYSeries =
            SimpleXYSeries(listOf(* series3Number), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Death")

        val series1Format = LineAndPointFormatter(Color.BLUE, Color.BLACK, null, null)
        val series2Format = LineAndPointFormatter(Color.GREEN, Color.BLACK, null, null)
        val series3Format = LineAndPointFormatter(Color.RED, Color.BLACK, null, null)

        series1Format.interpolationParams =
            CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal)
        series2Format.interpolationParams =
            CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal)
        series3Format.interpolationParams =
            CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal)

        binding.xyPlot.addSeries(series1, series1Format)
        binding.xyPlot.addSeries(series2, series2Format)
        binding.xyPlot.addSeries(series3, series3Format)

        binding.xyPlot.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format =
            object : Format() {
                override fun format(
                    obj: Any?,
                    toAppendTo: StringBuffer?,
                    pos: FieldPosition?
                ): StringBuffer {
                    val i = (obj as Number).toFloat().roundToInt()
                    return toAppendTo!!.append(domainLabels[i])
                }

                override fun parseObject(source: String?, pos: ParsePosition?): Any? {
                    return null
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