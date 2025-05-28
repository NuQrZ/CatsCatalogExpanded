package myapp.catscatalogexpanded.ui.leaderBoardScreen.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import myapp.catscatalogexpanded.ui.leaderBoardScreen.repository.LeaderboardApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LeaderboardRetrofitInstance {

    @Provides @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rma.finlab.rs/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())   // ← use Gson here
            .build()
    }

    @Provides @Singleton
    fun provideLeaderboardApi(retrofit: Retrofit): LeaderboardApi =
        retrofit.create(LeaderboardApi::class.java)
}