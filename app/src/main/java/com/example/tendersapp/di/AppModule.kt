package com.example.tendersapp.di


import com.example.tendersapp.data.remote.TendersApiService
import com.example.tendersapp.data.repository.TendersRepository
import com.example.tendersapp.data.repository.TendersRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://tenders.guru/api/pl/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTendersApiService(retrofit: Retrofit): TendersApiService {
        return retrofit.create(TendersApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTendersRepository(
        apiService: TendersApiService
    ): TendersRepository {
        return TendersRepositoryImpl(apiService)
    }
}