package com.ap.demo.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.databinding.ktx.BuildConfig
import com.ap.demo.network.Constants
import com.ap.demo.network.api.ApiService
import com.ap.demo.network.base.AuthorizationInterceptor
import com.ap.demo.network.base.NetworkConnectionInterceptor
import com.ap.demo.network.repository.ApiHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(appContext)

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
           // .addInterceptor(loggingInterceptor)
            .readTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .addInterceptor(NetworkConnectionInterceptor())
            .addInterceptor(AuthorizationInterceptor())
            .build();
    } else {
        OkHttpClient
            .Builder()
            .readTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .addInterceptor(AuthorizationInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelperImpl(apiService : ApiService): ApiHelperImpl = ApiHelperImpl(apiService)

}
