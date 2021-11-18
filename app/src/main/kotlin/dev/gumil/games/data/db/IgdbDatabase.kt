package dev.gumil.games.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.gumil.games.data.Game

@Database(entities = [Game::class], version = 1)
@TypeConverters(Converters::class)
abstract class IgdbDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}
