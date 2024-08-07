package com.exam.myapp.di.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.UnknownHostException

class NetworkConnectionInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            chain.proceed(chain.request())
        } catch (e: UnknownHostException) {
            throw NoInternetException("No internet connection", e)
        } catch (e: IOException) {
            throw NoInternetException("Network error", e)
        }
    }
}

class NoInternetException(message: String, cause: Throwable) : IOException(message, cause)