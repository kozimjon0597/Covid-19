package uz.kozimjon.covid19app.adapter

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.kozimjon.covid19app.fragments.StatisticsGlobalFragment
import uz.kozimjon.covid19app.fragments.StatisticsUzbekistanFragment
import uz.kozimjon.covid19app.model.Coronavirus

class PagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {StatisticsUzbekistanFragment()}
            1 -> {StatisticsGlobalFragment()}
            else -> {
                throw Resources.NotFoundException("Position not found")
            }
        }
    }
}