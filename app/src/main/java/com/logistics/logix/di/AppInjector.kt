package com.logistics.logix.di

import android.app.Application
import android.content.SharedPreferences
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.logistics.logix.database.ModelPreferences
import com.logistics.logix.database.provideSettingsPreferences
import com.logistics.logix.database.provideUserPreferences
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


val preferencesModule = module {
    single(named("userPrefs")) { provideUserPreferences(androidApplication()) }
    single(named("settingsPrefs")) { provideSettingsPreferences(androidApplication()) }

    single<SharedPreferences.Editor> {
        provideUserPreferences(androidApplication()).edit()
    }
    single { ModelPreferences(get()) }
}

val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(makeLoggingInterceptor())
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl("BASE_URL")
                .client(makeOkHttpClient())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }

}

private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level =
            HttpLoggingInterceptor.Level.BODY
    return logging
}

private fun makeOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
            .addInterceptor(makeLoggingInterceptor())
            .connectTimeout(300, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .build()
}
