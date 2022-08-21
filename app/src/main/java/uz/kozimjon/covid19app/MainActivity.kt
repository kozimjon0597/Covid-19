package uz.kozimjon.covid19app

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.kozimjon.covid19app.databinding.ActivityMainBinding
import uz.kozimjon.covid19app.utils.ArticleResource
import uz.kozimjon.covid19app.viewmodel.ArticleViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Paper.init(this)

        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        navView.setupWithNavController(navController)

        binding.contentMain.ivNavMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_home -> {
                    showNav()
                    binding.contentMain.tvTitle.text = "Home"
                }
                R.id.nav_statistics -> {
                    showNav()
                    binding.contentMain.tvTitle.text = "Statistics"
                }
                R.id.nav_article -> {
                    showNav()
                    binding.contentMain.tvTitle.text = "Article"
                }
                R.id.nav_news -> {
                    showNav()
                    binding.contentMain.tvTitle.text = "News"
                }
                R.id.nav_symptoms -> {
                    showNav()
                    binding.contentMain.tvTitle.text = "Symptoms"
                }
                R.id.nav_help -> {
                    showNav()
                    binding.contentMain.tvTitle.text = "Help"
                }
                else -> {
                    hideNav()
                }

            }
        }
    }

    private fun showNav() {
        binding.navView.visibility = View.VISIBLE
        binding.contentMain.rlHeader.visibility = View.VISIBLE
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

    }

    private fun hideNav() {
        binding.navView.visibility = View.GONE
        binding.contentMain.rlHeader.visibility = View.GONE
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }
}