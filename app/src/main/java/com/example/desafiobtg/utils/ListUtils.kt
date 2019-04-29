package com.example.desafiobtg.utils

object ListUtils {
    fun getUncommonItems(listA : List<String>, listB: List<String>): ArrayList<String> {
        val list = ArrayList(listA)
        list.removeAll(listB)

        val list2 = ArrayList(listB)
        list2.removeAll(listA)

        list2.addAll(listA)

        return list2
    }
}