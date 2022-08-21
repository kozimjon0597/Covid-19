package uz.kozimjon.covid19app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.paperdb.Paper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.kozimjon.covid19app.api.ApiService
import uz.kozimjon.covid19app.utils.Constants
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    @Named("news")
    fun provideBaseUrlNews(): String = Constants.BASE_URL_NEWS


    @Provides
    @Singleton
    @Named("news")
    fun provideRetrofitNews(@Named("news")baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("news")
    fun provideApiService(@Named("news")retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


    // Coronavirus
    @Provides
    @Singleton
    @Named("coronavirus")
    fun provideBaseUrlCoronavirus(): String = Constants.BASE_URL_CORONAVIRUS

    @Provides
    @Singleton
    @Named("coronavirus")
    fun provideRetrofitCoronavirus(@Named("coronavirus") baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("coronavirus")
    fun provideApiServiceCoronavirus(@Named("coronavirus") retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}