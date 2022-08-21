package uz.kozimjon.covid19app.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import kotlinx.coroutines.launch
import uz.kozimjon.covid19app.R
import uz.kozimjon.covid19app.adapter.ArticleInHomeAdapter
import uz.kozimjon.covid19app.adapter.PreventionAdapter
import uz.kozimjon.covid19app.adapter.SpinnerAdapter
import uz.kozimjon.covid19app.databinding.FragmentHomeBinding
import uz.kozimjon.covid19app.model.ArticleResponse
import uz.kozimjon.covid19app.model.Spinner
import uz.kozimjon.covid19app.utils.ArticleResource
import uz.kozimjon.covid19app.utils.Constants
import uz.kozimjon.covid19app.viewmodel.ArticleViewModel


@AndroidEntryPoint
class HomeFragment : Fragment(), PreventionAdapter.OnPreventionClickListener, ArticleInHomeAdapter.OnArticleInHomeClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val articleViewModel: ArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Paper.init(requireContext())
        val savedData = Paper.book().read<String>("saved_data")

        if (savedData.isNullOrEmpty()) {
            val action = HomeFragmentDirections.actionNavHomeToStartFragment()
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            articleViewModel.getArticles().collect {
                when (it) {
                    is ArticleResource.Success -> {
                        val preventionAdapter = PreventionAdapter()
                        binding.rvPrevention.adapter = preventionAdapter
                        preventionAdapter.setData(this@HomeFragment, it.articleResponse.articles ?: emptyList())

                        val articleInHomeAdapter = ArticleInHomeAdapter()
                        binding.rvArticle.adapter = articleInHomeAdapter
                        binding.rvNews.adapter = articleInHomeAdapter
                        articleInHomeAdapter.setData(this@HomeFragment, it.articleResponse.articles ?: emptyList())

                        screenVisible()
                    }
                    is ArticleResource.Error -> {
                        Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                        screenVisible()
                    }
                    is ArticleResource.Loading -> {
                        progressVisible()
                    }
                }
            }
        }

        setSpinner()

        binding.llCallNow.setOnClickListener {
            if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:" + "123")
                activity!!.startActivity(callIntent)
            } else {
                ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CALL_PHONE), 1)
            }
        }

        binding.llMessage.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_VIEW)
            sendIntent.data = Uri.parse("sms:" + "123")
            activity!!.startActivity(sendIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setSpinner() {
        val list = ArrayList<Spinner>()
        list.add(Spinner(1, "Uzb", R.drawable.uz))
        list.add(Spinner(2, "Ru", R.drawable.ru))
        list.add(Spinner(3, "Eng", R.drawable.eng))

        val spinnerAdapter = SpinnerAdapter(requireContext(), list)
        binding.spinner.adapter = spinnerAdapter
    }

    private fun screenVisible() {
        binding.progressBar.visibility = View.GONE
        binding.nsv.visibility = View.VISIBLE
    }

    private fun progressVisible() {
        binding.progressBar.visibility = View.VISIBLE
        binding.nsv.visibility = View.GONE
    }

    override fun onPreventionClick(article: ArticleResponse.Article) {
        val action = HomeFragmentDirections.actionNavHomeToFragmentDetail(
            article.source?.id,
            article.source?.name,
            article.author,
            article.urlToImage,
            article.description,
            article.content
        )
        findNavController().navigate(action)
    }

    override fun onArticleClick(article: ArticleResponse.Article) {
        val action = HomeFragmentDirections.actionNavHomeToFragmentDetail(
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