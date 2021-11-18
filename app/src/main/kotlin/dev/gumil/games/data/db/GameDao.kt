package dev.gumil.games.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.gumil.games.data.Game

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(games: List<Game>)

    @Query("SELECT * FROM game")
    suspend fun getAllGames(): List<Game>

    @Query("DELETE FROM game")
    suspend fun clearAll()
}
