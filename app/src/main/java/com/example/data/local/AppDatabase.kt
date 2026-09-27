package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.BookmarkDao
import com.example.data.local.dao.FastingDao
import com.example.data.local.dao.LastReadDao
import com.example.data.local.dao.TasbihDao
import com.example.data.local.entities.BookmarkEntity
import com.example.data.local.entities.FastingEntity
import com.example.data.local.entities.LastReadEntity
import com.example.data.local.entities.TasbihEntity

@Database(
    entities = [
        TasbihEntity::class,
        BookmarkEntity::class,
        FastingEntity::class,
        LastReadEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tasbihDao(): TasbihDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun fastingDao(): FastingDao
    abstract fun lastReadDao(): LastReadDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "noor_muslim.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
