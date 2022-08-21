package uz.kozimjon.covid19app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import uz.kozimjon.covid19app.R
import uz.kozimjon.covid19app.databinding.*
import uz.kozimjon.covid19app.model.SavedArticle
import uz.kozimjon.covid19app.paper_db.Favourites
import uz.kozimjon.covid19app.paper_db.Viewed

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id: String? = arguments?.getString("id")
        val name: String? = arguments?.getString("name")
        val author: String? = arguments?.getString("author")
        val image: String? = arguments?.getString("image")
        val desc: String? = arguments?.getString("desc")
        val content: String? = arguments?.getString("content")

        val article = SavedArticle(id, name, author, image, desc, content)


        Glide.with(requireContext()).load(image).centerCrop().placeholder(R.drawable.ic_launcher_background).into(binding.ivImage);
        binding.tvName.text = name
        binding.tvAuthor.text = author
        binding.tvDesc.text = desc
        binding.tvContent.text = content

        if (!Viewed.existViewed(article)) {
            Viewed.addViewed(article)
        }

        if (Favourites.existFavourite(article)) {
            binding.ivBookmark.setImageResource(R.drawable.ic_baseline_bookmark_24)
        } else {
            binding.ivBookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
        }

        binding.ivBookmark.setOnClickListener {
            if (Favourites.existFavourite(article)) {
                binding.ivBookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                Favourites.removeFavourite(article)
                Toast.makeText(requireContext(), "Saralanganlardan olindi", Toast.LENGTH_SHORT).show()

            } else {
                binding.ivBookmark.setImageResource(R.drawable.ic_baseline_bookmark_24)
                Favourites.addFavourite(article)
                Toast.makeText(requireContext(), "Saralanganlarga qo'shildi", Toast.LENGTH_SHORT).show()
            }
        }

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}