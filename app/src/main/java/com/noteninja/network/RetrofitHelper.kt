package com.noteninja.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import com.justmovies.Api_base.Api
import com.noteninja.base.AppGlobal
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitHelper {
    companion object {
        // Base URL for the API.
        var base_url ="https://movies.cloudnex.in/public/api/"

        // Retrofit instance.
        private lateinit var retrofit: Retrofit
        // REST client instance.
        private lateinit var REST_CLIENT: Api

        // Gson instance for parsing JSON.
        var gson = GsonBuilder()
            .setLenient()
            .create()

        // Function to get the API client.
        @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
        fun getClient(): Api {
            // Building the Retrofit instance.
            retrofit = Retrofit.Builder().baseUrl(base_url).client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson)).build()

            // Creating the API client.
            REST_CLIENT = retrofit.create(Api::class.java)
            return REST_CLIENT
        }

        // Function to configure OkHttpClient.
        fun getOkHttpClient(): OkHttpClient {
            // Logging interceptor to log the details of network requests and responses.
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            // OkHttpClient builder.
            val builder = OkHttpClient.Builder()
            builder.connectTimeout(5, TimeUnit.MINUTES)
            builder.readTimeout(5, TimeUnit.MINUTES)
            builder.writeTimeout(5, TimeUnit.MINUTES)
            builder.addNetworkInterceptor(httpLoggingInterceptor)
            builder.protocols(listOf(Protocol.HTTP_1_1))

            // Adding an interceptor to add the Authorization header to every request.
            builder.addInterceptor { chain ->
                val request = chain.request()
                val header = request.newBuilder()?.header("Authorization",
                    "Bearer ${AppGlobal.token}")
                val build = header!!.build()
                chain.proceed(build)
            }
            return builder.build()
        }

    }
}