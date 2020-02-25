package br.eloibrito.com.movie_db.ui

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import br.eloibrito.com.movie_db.R
import br.eloibrito.com.movie_db.data.network.EndPoint
import br.eloibrito.com.movie_db.models.Movies
import br.eloibrito.com.movie_db.utils.CarregaImagemDaUrl
import br.eloibrito.com.movie_db.utils.CheckReadPermission
import kotlinx.android.synthetic.main.layout_list_movie_details.*


class ListMoviesDetailsActivity : AppCompatActivity() {

    companion object {

        val ID_DETAIL = "detail:_id"
        val VIEW_NAME_HEADER_IMAGE = "detail:header:image"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_list_movie_details)


        controles()

    }

    private fun controles() {

        ViewCompat.setTransitionName(
            img,
            VIEW_NAME_HEADER_IMAGE
        )

        val movies = intent.getSerializableExtra(ID_DETAIL) as Movies

        movies?.let {

            var sbHtml = StringBuilder()
            sbHtml.append(
                String.format(
                    "<font color='red'>%s</font> %s", resources.getString(
                        R.string.detalhes_nota
                    ), it.vote_average
                )
            )
            sbHtml.append(
                String.format(
                    "<br/><font color='red'>%s</font> %s", resources.getString(
                        R.string.detalhes_data
                    ), it.release_date
                )
            )
            sbHtml.append(
                String.format(
                    "<br/><font color='red'>%s</font> %s", resources.getString(
                        R.string.detalhes_linguagem_original
                    ), it.original_language
                )
            )
            sbHtml.append(
                String.format(
                    "<br/><font color='red'>%s</font> %s", resources.getString(
                        R.string.detalhes_total_votos
                    ), it.vote_count
                )
            )
            sbHtml.append(
                String.format(
                    "<br/><font color='red'>%s</font> %s", resources.getString(
                        R.string.detalhes_popularidade
                    ), it.popularity
                )
            )

            txt_title.text = String.format("%s", movies.title)
            txt_details.text = Html.fromHtml(sbHtml.toString())
            txt_details_about.text = String.format("%s", it.overview)

            like.setImageResource(if (it.like) R.drawable.ic_like else R.drawable.ic_deslike)

            CarregaImagemDaUrl.carregaImagen(
                this,
                img,
                String.format("%s/%s", EndPoint.image_movie, movies.poster_path)
            )
        }

        img.setOnClickListener { v ->
            CarregaImagemDaUrl.carregaImagen(
                this,
                img,
                String.format("%s/%s", EndPoint.image_movie, movies.backdrop_path)
            )
        }
    }
}