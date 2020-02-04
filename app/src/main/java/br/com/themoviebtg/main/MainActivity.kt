package br.com.themoviebtg.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import br.com.themoviebtg.R
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var tabAdapter: TabAdapter
    private val presenter = MainPresenter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_main)

        this.presenter.setupTabView()

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this))

    }

    override fun setupToolBar() {
//        setSupportActionBar(tb_main_bar)
    }

    override fun getTabContext() = this

    override fun getTabFragmentManager(): FragmentManager = supportFragmentManager


    override fun setupTabView(
        tabAdapter: TabAdapter,
        listener: ViewPager.OnPageChangeListener
    ) {
        this.tabAdapter = tabAdapter
        vp_mainTabs.adapter = tabAdapter
        vp_mainTabs.addOnPageChangeListener(listener)
        tl_tabs_swiper.setupWithViewPager(vp_mainTabs)

        drawItemsTabIcon()
        highLightCurrentItem(0)
    }

    private fun drawItemsTabIcon() {
        (0..tl_tabs_swiper.tabCount).forEach { i ->
            tl_tabs_swiper.getTabAt(i)?.customView = null
            tl_tabs_swiper.getTabAt(i)?.customView = tabAdapter.getTabItemView(i)
        }
    }


    override fun highLightCurrentItem(position: Int) {
        drawItemsTabIcon()
        tl_tabs_swiper.getTabAt(position)?.customView = null
        tl_tabs_swiper.getTabAt(position)?.customView = tabAdapter.getSelectedTabItemView(position)
    }


}