package uz.kozimjon.covid19app.adapter

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.kozimjon.covid19app.fragments.*
import uz.kozimjon.covid19app.fragments.archive_fragment.ArchivedFragment

class NewAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                SavedFragment()
            }
            1 -> {
                ArchivedFragment()
            }
            2 -> {
                RecentlyViewedFragment()
            }
            3 -> {
                HighlightedFragment()
            }
            else -> {
                throw Resources.NotFoundException("Position not found")
            }
        }
    }
}