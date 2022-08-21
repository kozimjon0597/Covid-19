package uz.kozimjon.covid19app.fragments.archive_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import uz.kozimjon.covid19app.adapter.ArchivedAdapter
import uz.kozimjon.covid19app.databinding.FragmentArchivedBinding
import uz.kozimjon.covid19app.model.SavedArticle
import uz.kozimjon.covid19app.paper_db.Archived

class ArchivedFragment : Fragment(), ArchivedAdapter.OnArchivedClickListener {
    private var _binding: FragmentArchivedBinding? = null
    private val binding get() = _binding!!
    lateinit var archivedAdapter: ArchivedAdapter
    private lateinit var archivedViewModel: ArchivedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArchivedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        archivedAdapter = ArchivedAdapter()
        binding.rvArchived.adapter = archivedAdapter
        archivedAdapter.setData(Archived.getArchived(), this@ArchivedFragment)

//        archivedViewModel = ViewModelProvider(this)[ArchivedViewModel::class.java]
//
//        archivedViewModel.archivedList.observe(viewLifecycleOwner) {
//            archivedAdapter = ArchivedAdapter()
//            binding.rvArchived.adapter = archivedAdapter
//            archivedAdapter.setData(it, this@ArchivedFragment)
//        }

//        archivedViewModel.setList()
    }

    override fun onResume() {
        super.onResume()

//        archivedViewModel.setList()
        archivedAdapter.setData(Archived.getArchived(), this@ArchivedFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAClick(article: SavedArticle) {
        Toast.makeText(requireContext(), article.name, Toast.LENGTH_SHORT).show()
    }

    override fun onADeleteClick(article: SavedArticle) {
        Archived.removeArchived(article)
    }
}