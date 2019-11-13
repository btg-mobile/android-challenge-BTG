package com.joao.teste.presentation.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.joao.domain.entity.MoviesItem

class BaseAdapter<T>(
    @LayoutRes val view: Int,
    private val searchableList: MutableList<T>,
    val onBind: (view: View, item: T) -> Unit
) : RecyclerView.Adapter<ViewHolder>(), Filterable {
    private var originalList = ArrayList<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(view, parent, false))
    }

    override fun getItemCount() = searchableList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        searchableList.getOrNull(position)?.let {
            onBind(holder.itemView, it)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            private val filterResults = FilterResults()
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                if (originalList.isEmpty())
                    originalList = ArrayList(searchableList)
                searchableList.clear()
                if (constraint.isNullOrBlank()) {
                    searchableList.addAll(originalList)
                } else {
                    val searchResults =
                        originalList.filter { (it as? MoviesItem)?.title?.contains(constraint) == true }
                    searchableList.addAll(searchResults)
                }
                return filterResults.also {
                    it.values = searchableList
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }
        }
    }

}


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)