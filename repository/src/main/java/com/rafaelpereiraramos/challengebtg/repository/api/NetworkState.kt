package com.rafaelpereiraramos.challengebtg.repository.api


enum class Status {
    RUNNING,
    SUCCESS,
    FAILED,
    NO_CONNECTION
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: Status,
    val msg: String? = null) {
    companion object {
        val LOADED =
            NetworkState(Status.SUCCESS)
        val LOADING =
            NetworkState(Status.RUNNING)
        val NO_CONNECTION =
            NetworkState(Status.NO_CONNECTION)
        fun error(msg: String?) = NetworkState(
            Status.FAILED,
            msg
        )
    }
}