package uz.kozimjon.covid19app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.kozimjon.covid19app.adapter.ArticleAdapter
import uz.kozimjon.covid19app.databinding.FragmentArticleBinding
import uz.kozimjon.covid19app.model.ArticleResponse
import uz.kozimjon.covid19app.utils.ArticleResource
import uz.kozimjon.covid19app.viewmodel.ArticleViewModel

@AndroidEntryPoint
class ArticleFragment : Fragment(), ArticleAdapter.OnArticleClickListener {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private val articleViewModel: ArticleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            articleViewModel.getArticles().collect {
                when (it) {
                    is ArticleResource.Success -> {
                        screenVisible()
                        val adapter = ArticleAdapter()
                        binding.rvArticles.adapter = adapter
                        adapter.setData(this@ArticleFragment, it.articleResponse.articles ?: emptyList())
                    }
                    is ArticleResource.Error -> {
                        screenVisible()
                        Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                    }
                    is ArticleResource.Loading -> {
                        progressVisible()
                    }
                }
            }
        }
    }

    // Functions

    private fun screenVisible() {
        binding.progressBar.visibility = View.GONE
        binding.rvArticles.visibility = View.VISIBLE
    }

    private fun progressVisible() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvArticles.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onArticleClick(article: ArticleResponse.Article) {
        val action = ArticleFragmentDirections.actionNavArticleToFragmentDetail(
            article.source?.id,
            article.source?.name,
            article.author,
            article.urlToImage,
            article.description,
            article.content
        )
        findNavController().navigate(action)
    }
}