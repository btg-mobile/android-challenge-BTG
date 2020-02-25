package br.eloibrito.com.movie_db.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import br.eloibrito.com.movie_db.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.Target

object CarregaImagemDaUrl : AppGlideModule() {
    fun buscar(mContext: Context, url: String, vararg v: View) {

        Glide.with(mContext)
            .asBitmap()
            .load(url)
            .apply(options())
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Bitmap>,
                    isFirstResource: Boolean
                ): Boolean {
                    try {

                        if (v.size >= 2)
                            v[1].visibility = View.GONE

                        if (v[0] is ImageView)
                            (v[0] as ImageView).setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_fechar))


                    } catch (err: Exception) {
                        err.printStackTrace()
                    }

                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    model: Any,
                    target: Target<Bitmap>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    try {
                        if (v.size >= 2)
                            v[1].visibility = View.GONE

                        if (v[0] is ImageView) {
                            (v[0] as ImageView).setImageBitmap(resource)
                            (v[0] as ImageView).scaleType = ImageView.ScaleType.CENTER_CROP
                        }

                    } catch (err: Exception) {
                        err.printStackTrace()
                    }

                    return false
                }
            })
            .submit()
    }

    @SuppressLint("CheckResult")
    fun options(): RequestOptions {
        val mOption = RequestOptions()
        mOption
            .fitCenter()
            .error(R.drawable.ic_fechar)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(800, 600)
        //.diskCacheStrategy(DiskCacheStrategy.ALL)
        return mOption
    }


    @Throws(Exception::class)
    fun retornaImagem(mContext: Context, url: String): Bitmap {
        return Glide.with(mContext)
            .asBitmap()
            .load(url)
            .apply(options())
            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .get()
    }

    fun carregaImagen(context: Context, v: ImageView, url : String) {

        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(context)
            .asBitmap()
            .load(url)
            .apply(RequestOptions()
                .placeholder(circularProgressDrawable)
            ).apply(RequestOptions()
                .error(R.drawable.ic_fechar)
            )
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(object : BitmapImageViewTarget(v) {
                override fun setResource(resource: Bitmap?) { //Play with bitmap
                    super.setResource(resource)
                }
            })

    }


}