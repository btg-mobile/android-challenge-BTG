package br.eloibrito.com.movie_db.utils

import android.view.Gravity
import android.view.View
import android.widget.TextView
import br.eloibrito.com.movie_db.R
import com.google.android.material.snackbar.Snackbar
import java.util.HashMap

object Utils {

    /**
     * RETORNA HASH MAP
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun retornaHashJson(type: String, vararg params: Any): HashMap<String, String> {
        val mHash = HashMap<String, String>()

        if (params.size > 0) {
            for (`object` in params) {
                val key = (`object` as String).split(type.toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                if (key.size >= 2)
                    mHash[key[0]] = key[1]

            }
        }
        return mHash
    }

    /**
     * METODO IMPLODE STRING
     *
     * @param array
     * @param separador
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun implodeString(array: IntArray, separador: String): String {

        if (array.isEmpty())
            return ""

        if (array.size < 2)
            return array[0].toString()

        val stringBuffer = StringBuffer()
        for (i in array.indices) {
            if (i != 0)
                stringBuffer.append(separador)
            stringBuffer.append(array[i])
        }

        return stringBuffer.toString()
    }

    /**
     * MENSAGEM SNACK
     */

    @Throws(Exception::class)
    fun mensagemSnack(v: View, mensagem: String, idColor: Int?, icone: Int?) {
        val mSnake = Snackbar.make(v, mensagem, Snackbar.LENGTH_SHORT)
        val vsnaker = mSnake.view
        vsnaker.setBackgroundResource(idColor!!)
        val txtSnaker =
            vsnaker.findViewById<View>(R.id.snackbar_text) as TextView
        txtSnaker.setCompoundDrawablesWithIntrinsicBounds(0, 0, icone!!, 0)
        txtSnaker.gravity = Gravity.CENTER
        txtSnaker.maxLines = 4
        mSnake.duration = 10000
        mSnake.setActionTextColor(v.context.resources.getColor(R.color.branco))
        mSnake.setAction("OK") { mSnake.dismiss() }
        mSnake.show()
    }
}