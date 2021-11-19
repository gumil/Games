package dev.gumil.games.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.gumil.games.data.db.IgdbDatabase
import dev.gumil.games.data.network.ApiFactory
import dev.gumil.games.data.network.IgdbApi

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideIgdbApi(): IgdbApi = ApiFactory.createApi()

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): IgdbDatabase =
        Room.databaseBuilder(
            context,
            IgdbDatabase::class.java,
            "games_db"
        ).build()
}
