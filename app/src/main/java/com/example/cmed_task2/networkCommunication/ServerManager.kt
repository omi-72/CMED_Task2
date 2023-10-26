package com.example.cmed_task2.networkCommunication

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.JsonElement
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

class ServerManager {
    companion object {
        val retrofitClient: Api = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiConstants.BASE_URL)
            .build().create(Api::class.java)
    }

    fun getRequest(
        url: String,
        listener: RequestListener
    ) {
        retrofitClient.getData(url).enqueue(APICallBack(listener = listener))
    }

}

interface Api {
    @GET
    fun getData(
        @Url url: String?
    ): Call<JsonElement?>

}

class APICallBack<T>(
    private val params: Map<String, @JvmSuppressWildcards Any?>? = null,
    private val listener: RequestListener
) : Callback<T> {
    @SuppressLint("LogNotTimber")
    override fun onResponse(call: Call<T>, response: Response<T>) {
        try {
            Log.d(
                this.javaClass.simpleName,
                "url: (method: ${call.request().method()}): ${call.request().url()}"
            )
            Log.d(this.javaClass.simpleName, "headers(): ${call.request().headers()}")
            params?.let { Log.d(this.javaClass.simpleName, "params: $params") }
            Log.d(this.javaClass.simpleName, "onResponse: $response")

            if (response.code() == 200) {
                Log.d(this.javaClass.simpleName, "onSuccess: ${response.body()}")
                listener.onSuccess(response)

            } else {
                response.errorBody()?.let {
                    val errorBody = it.string()
                    Log.e(this.javaClass.simpleName, "onFailure: errorBody: $errorBody")

                    val message = try {
                        val body = JSONObject(errorBody)
                        body.getString("message")
                    } catch (e: Exception) {
                        errorBody
                    }
                    listener.onError(message)
                }
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, "onFailure: (catch): $e")
            listener.onError(e.toString())
        }
    }

    @SuppressLint("LogNotTimber")
    override fun onFailure(call: Call<T>, t: Throwable) {
        if (t is NoConnectivityException) {
            // show No Connectivity message to user or do whatever you want.
            Log.e(this.javaClass.simpleName, "onFailure: NoConnectivityException: $t")

            //StockXApplication.applicationContext().showToast(t.message)
        }
        Log.e(
            this.javaClass.simpleName,
            "url: (method: ${call.request().method()}): ${call.request().url()}"
        )
        Log.e(this.javaClass.simpleName, "onFailure: $t")
        listener.onError(t.toString())
    }
}

@JvmSuppressWildcards
interface RequestListener {
    fun onSuccess(response: @JvmSuppressWildcards Any?)
    fun onError(error: String)
}