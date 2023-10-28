package cn.body.pressup.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import cn.body.pressup.App
import cn.body.pressup.R
import cn.body.pressup.databinding.ActivityMainBinding
import cn.body.pressup.extensions.dp2px
import cn.body.pressup.extensions.getStatusBarHeight
import cn.body.pressup.extensions.onTabSelectionChanged
import cn.body.pressup.extensions.setMargin
import cn.body.pressup.ui.dashboard.ArticleFragment
import cn.body.pressup.ui.home.HomeFragment
import cn.body.pressup.ui.notifications.AboutFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val fragments = HashMap<Int, Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getInstance().running = true
    }

    override fun buildLayoutBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val top = getStatusBarHeight() + dp2px(24f)
        val se = dp2px(42f)
        binding.tabLayout.setMargin(se, top, se, 0)


        fragments[0] = HomeFragment()
        fragments[1] = ArticleFragment()
        fragments[2] = AboutFragment()

        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
            override fun getItemCount(): Int = fragments.size

            override fun createFragment(position: Int): Fragment {
                return fragments[position] ?: throw RuntimeException("Trying to fetch unknown fragment id $position")
            }
        }
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.getTabAt(position)?.select()
            }
        })

        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.setCurrentItem(0, false)

        setupTabs()
        setupTabColors()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        App.getInstance().running = false
    }

    private fun setupTabs() {
        binding.tabLayout.removeAllTabs()
        val tabLabels = arrayOf("BP", "Insights", "Us")
        tabLabels.forEachIndexed { i, labelId ->
            binding.tabLayout.newTab().setCustomView(R.layout.item_tab).apply {
                customView?.findViewById<TextView>(R.id.tab_item_label)?.setText(labelId)
                binding.tabLayout.addTab(this)
            }
        }

        binding.tabLayout.onTabSelectionChanged(
            tabUnselectedAction = {
                updateBottomTabItemColors(it.customView, false)
            },
            tabSelectedAction = {
                binding.viewPager.setCurrentItem(it.position, false)
                updateBottomTabItemColors(it.customView, true)
            }
        )
    }

    private fun setupTabColors() {
        val activeView = binding.tabLayout.getTabAt(binding.viewPager.currentItem)?.customView
        updateBottomTabItemColors(activeView, true)

        getInactiveTabIndexes(binding.viewPager.currentItem).forEach { index ->
            val inactiveView = binding.tabLayout.getTabAt(index)?.customView
            updateBottomTabItemColors(inactiveView, false)
        }

        binding.tabLayout.getTabAt(binding.viewPager.currentItem)?.select()
    }

    private fun getInactiveTabIndexes(activeIndex: Int) = arrayListOf(0, 1, 2).filter { it != activeIndex }

    private fun updateBottomTabItemColors(view: View?, isActive: Boolean) {
        if (isActive) {
            val color = resources.getColor(R.color.white, theme)
            view?.findViewById<TextView>(R.id.tab_item_label)?.setTextColor(color)
            view?.findViewById<TextView>(R.id.tab_item_label)?.setBackgroundResource(R.drawable.shape_72bcd8_to_289cc6_r12)
        } else {
            val color = resources.getColor(R.color.color_164c6f, theme)
            view?.findViewById<TextView>(R.id.tab_item_label)?.setTextColor(color)
            view?.findViewById<TextView>(R.id.tab_item_label)?.setBackgroundResource(R.drawable.shape_8bbdda_r12)
        }
    }
}