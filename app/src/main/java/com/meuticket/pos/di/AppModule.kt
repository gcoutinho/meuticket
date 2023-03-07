package com.meuticket.pos.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.meuticket.pos.core.network.Api
import com.meuticket.pos.core.storage.LocalStorage
import com.meuticket.pos.core.storage.LocalStorageImpl
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.Type
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    abstract fun provideLocalStorage(localStorage: LocalStorageImpl): LocalStorage

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideResources(application: Application) = application.resources

        @JvmStatic
        @Provides
        @Singleton
        fun provideSharedPreferences(application: Application): SharedPreferences {
            return application.getSharedPreferences("meu_ticket", Context.MODE_PRIVATE)
        }

        @JvmStatic
        @Provides
        @Singleton
        fun provideMoshi(): Moshi =
            Moshi.Builder()
                .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
                .build()

        @Provides
        @JvmStatic
        @Singleton
        @Named("BaseUrl")
        fun provideBaseUrl(): String =
            "https://www.com.br/"

        @Singleton
        @JvmStatic
        @Provides
        @Named("LoggingInterceptor")
        fun provideLoggingInterceptor(): Interceptor =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        @JvmStatic
        @Provides
        @Singleton
        fun provideOkHttpCache(application: Application): Cache {
            val cacheSize: Long = 10 * 1024 * 1024
            return Cache(application.cacheDir, cacheSize)
        }

        @Singleton
        @JvmStatic
        @Provides
        fun providesOkHttp(
            @Named("LoggingInterceptor")
            loggingInterceptor: Interceptor
        ): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        @Singleton
        @JvmStatic
        @Provides
        fun provideRetrofit(oktHttpClient: OkHttpClient, moshi: Moshi, @Named("BaseUrl") baseUrl: String): Retrofit =
            Retrofit.Builder()
                .client(oktHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(nullOnEmptyConverterFactory)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        private val nullOnEmptyConverterFactory = object : Converter.Factory() {
            fun converterFactory() = this
            override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit) = object :
                Converter<ResponseBody, Any?> {
                val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
                override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
            }
        }

        @Singleton
        @JvmStatic
        @Provides
        fun providesApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    }
}
