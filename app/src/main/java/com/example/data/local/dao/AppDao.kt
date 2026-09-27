package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entities.BookmarkEntity
import com.example.data.local.entities.FastingEntity
import com.example.data.local.entities.LastReadEntity
import com.example.data.local.entities.TasbihEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TasbihDao {
    @Query("SELECT * FROM tasbih_records ORDER BY timestamp DESC")
    fun getAllRecords(): Flow<List<TasbihEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: TasbihEntity): Long

    @Query("DELETE FROM tasbih_records WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM tasbih_records")
    suspend fun clearAll()
}

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks ORDER BY timestamp DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM bookmarks WHERE type = :type ORDER BY timestamp DESC")
    fun getBookmarksByType(type: String): Flow<List<BookmarkEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE type = :type AND itemId = :itemId)")
    fun isBookmarked(type: String, itemId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity): Long

    @Query("DELETE FROM bookmarks WHERE type = :type AND itemId = :itemId")
    suspend fun deleteByItemId(type: String, itemId: String)

    @Query("DELETE FROM bookmarks WHERE id = :id")
    suspend fun deleteById(id: Long)
}

@Dao
interface FastingDao {
    @Query("SELECT * FROM fasting_records ORDER BY dateString ASC")
    fun getAllFasting(): Flow<List<FastingEntity>>

    @Query("SELECT * FROM fasting_records WHERE dateString = :dateString LIMIT 1")
    suspend fun getFastingByDate(dateString: String): FastingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(record: FastingEntity)
}

@Dao
interface LastReadDao {
    @Query("SELECT * FROM last_read WHERE id = 1 LIMIT 1")
    fun getLastRead(): Flow<LastReadEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLastRead(lastRead: LastReadEntity)
}
