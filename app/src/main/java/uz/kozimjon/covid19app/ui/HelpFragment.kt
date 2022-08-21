package uz.kozimjon.covid19app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.kozimjon.covid19app.R
import uz.kozimjon.covid19app.adapter.HelpAdapter
import uz.kozimjon.covid19app.databinding.*
import uz.kozimjon.covid19app.model.Help

class HelpFragment : Fragment() {

    private var _binding: FragmentHelpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHelpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = ArrayList<Help>()
        list.add(Help(1, "Hotline", "Lorem ipsum dolor sit amet, consectetur adipiscing elit ut aliquam", R.drawable.image13))
        list.add(Help(2, "Doctor", "Lorem ipsum dolor sit amet, consectetur adipiscing elit ut aliquam", R.drawable.image10))
        list.add(Help(3, "Hospital", "Lorem ipsum dolor sit amet, consectetur adipiscing elit ut aliquam", R.drawable.image11))
        list.add(Help(4, "Consultation", "Lorem ipsum dolor sit amet, consectetur adipiscing elit ut aliquam", R.drawable.image12))

        val helpAdapter = HelpAdapter()
        binding.rvHelp.adapter = helpAdapter
        helpAdapter.setData(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}