package uz.kozimjon.covid19app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import uz.kozimjon.covid19app.adapter.SavedArticleAdapter
import uz.kozimjon.covid19app.databinding.FragmentSavedBinding
import uz.kozimjon.covid19app.fragments.archive_fragment.ArchivedViewModel
import uz.kozimjon.covid19app.model.SavedArticle
import uz.kozimjon.covid19app.paper_db.Favourites

class SavedFragment : Fragment(), SavedArticleAdapter.OnSavedArticleClickListener {
    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!
    private lateinit var savedArticleAdapter: SavedArticleAdapter
    private val archivedViewModel: ArchivedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedArticleAdapter = SavedArticleAdapter()
        binding.rvSaved.adapter = savedArticleAdapter
        savedArticleAdapter.setData(Favourites.getFavourites(), this@SavedFragment)
    }

    override fun onResume() {
        super.onResume()

        savedArticleAdapter.setData(Favourites.getFavourites(), this@SavedFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSAClick(article: SavedArticle) {
        Toast.makeText(requireContext(), article.name, Toast.LENGTH_SHORT).show()
    }

    override fun onSADeleteClick(article: SavedArticle) {
        Favourites.removeFavourite(article)
    }

    override fun onSAAClick(article: SavedArticle) {
//        archivedViewModel.setList()
    }
}