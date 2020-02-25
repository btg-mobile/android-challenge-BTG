package br.eloibrito.com.movie_db.ui

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.eloibrito.com.movie_db.R
import br.eloibrito.com.movie_db.ui.adapter.PagerAdapter
import br.eloibrito.com.movie_db.utils.CheckReadPermission
import kotlinx.android.synthetic.main.layout_movie.*

class ListMoviesActivity : AppCompatActivity() {


    var REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124

    lateinit var fragAdapter : PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_movie)

        if (!CheckReadPermission.show(this))
            return

        controles()

    }

    fun controles() {

        toolbar.title = resources.getString(R.string.app_name)
        setSupportActionBar(toolbar)

        fragAdapter = PagerAdapter(supportFragmentManager)
        viewpager.adapter = fragAdapter

        tabs_layout.setupWithViewPager(viewpager)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS ->
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    controles()

        }

    }


}