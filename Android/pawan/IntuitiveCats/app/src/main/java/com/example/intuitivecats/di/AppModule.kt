package com.example.intuitivecats.di

import com.example.intuitivecats.network.BreedApi
import com.example.pagerv.PagerDataAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesHeaderInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->

            val request = chain.request().newBuilder()
                .header("x-api-key", "17d94b92-754f-46eb-99a0-65be65b5d18f")
                .build()
            chain.proceed(request)
        }
    }

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }


    @Provides
    fun providesHttpClient(
        headerInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(headerInterceptor)
            .build()
    }


    @Provides
    fun providesRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.thecatapi.com/")
            .client(httpClient)
            .build()
    }

    @Provides
    fun providesBreedApi(retrofit: Retrofit): BreedApi {
        return retrofit.create(BreedApi::class.java)
    }

    @Provides
    fun providesPagerDataAdapter(): PagerDataAdapter = PagerDataAdapter()
}
