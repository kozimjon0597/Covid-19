package uz.kozimjon.covid19app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import uz.kozimjon.covid19app.adapter.SavedArticleAdapter
import uz.kozimjon.covid19app.databinding.*
import uz.kozimjon.covid19app.fragments.archive_fragment.ArchivedViewModel
import uz.kozimjon.covid19app.model.SavedArticle
import uz.kozimjon.covid19app.paper_db.Viewed

class RecentlyViewedFragment : Fragment(), SavedArticleAdapter.OnSavedArticleClickListener {
    private var _binding: FragmentRecentlyViewedBinding? = null
    private val binding get() = _binding!!
    private lateinit var savedArticleAdapter: SavedArticleAdapter
//    private val archivedViewModel: ArchivedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentlyViewedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedArticleAdapter = SavedArticleAdapter()
        binding.rvSaved.adapter = savedArticleAdapter
        savedArticleAdapter.setData(Viewed.getViewed(), this@RecentlyViewedFragment)
    }

    override fun onResume() {
        super.onResume()

        savedArticleAdapter.setData(Viewed.getViewed(), this@RecentlyViewedFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSAClick(article: SavedArticle) {
        Toast.makeText(requireContext(), article.name, Toast.LENGTH_SHORT).show()
    }

    override fun onSADeleteClick(article: SavedArticle) {
        Viewed.removeViewed(article)
    }

    override fun onSAAClick(article: SavedArticle) {
//        archivedViewModel.setList()
    }
}