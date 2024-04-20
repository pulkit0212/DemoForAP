package com.ap.demo.network.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getResult(tag: String, call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(tag, body)
            }
            return if (response.errorBody() != null) {
                val e = if (response.errorBody() != null) {
                    JSONObject(response.errorBody()!!.string()).getString("message")
                } else
                    "${response.code()} ${response.message()}"
                error(tag, e)
            } else
                error(tag, " ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(tag, e.message ?: e.toString())
        }
    }

    private fun <T> error(tag: String, msg: String?): Resource<T> {
        return Resource.error(tag, msg)
    }

    fun <T> performOperation(tag: String, networkCall: suspend () -> Resource<T>): LiveData<Resource<T?>> = liveData(Dispatchers.IO) {
            try {
                emit(Resource.loading(tag, data = null))
                val responseStatus = networkCall.invoke()
                if (responseStatus.status == Resource.Status.SUCCESS) {
                    emit(
                        Resource.success(
                            tag = tag,
                            data = responseStatus.data,
                            message = responseStatus.message
                        )
                    )
                } else if (responseStatus.status == Resource.Status.ERROR) {
                    emit(Resource.error(tag, responseStatus.message!!))
                }
            } catch (e: Exception) {
                emit(Resource.error<T>(tag, e.message ?: e.toString()))
            }
        }

    data class Resource<out T>(val status: Status, val data: T?, val message: String?, val tag: String) {
        enum class Status {
            SUCCESS,
            ERROR,
            LOADING
        }
        companion object {
            fun <T> success(tag: String, data: T, message: String? = null) = Resource(Status.SUCCESS, data, null, tag)
            fun <T> error(tag: String, message: String?, data: T? = null) = Resource(Status.ERROR, data, message, tag)
            fun <T> loading(tag: String, data: T? = null) = Resource(Status.LOADING, data, null, tag)
        }
    }

}

