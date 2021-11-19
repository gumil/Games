package dev.gumil.games.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.gumil.games.data.repository.GamesRepository
import dev.gumil.games.data.repository.GamesRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
@Suppress("UnnecessaryAbstractClass")
abstract class RepositoryModule {

    @Binds
    abstract fun bindGamesRepository(
        gamesRepositoryImpl: GamesRepositoryImpl
    ): GamesRepository
}
