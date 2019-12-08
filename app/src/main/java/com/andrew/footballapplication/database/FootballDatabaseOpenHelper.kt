package com.andrew.footballapplication.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.andrew.footballapplication.model.match.MatchFavorite
import org.jetbrains.anko.db.*

class FootballDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteMatch.db", null, 1) {
    companion object {
        private var instance: FootballDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): FootballDatabaseOpenHelper {
            if (instance == null) {
                instance = FootballDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as FootballDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(MatchFavorite.TABLE_FAVORITE, true,
                MatchFavorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            MatchFavorite.ID_EVENT to TEXT + UNIQUE,
            MatchFavorite.DATE_EVENT to TEXT,
            MatchFavorite.TIME_EVENT to TEXT,
            MatchFavorite.HOME_TEAM to TEXT,
            MatchFavorite.AWAY_TEAM to TEXT,
            MatchFavorite.HOME_SCORE to TEXT,
            MatchFavorite.AWAY_SCORE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(MatchFavorite.TABLE_FAVORITE, true)
    }
}

val Context.database: FootballDatabaseOpenHelper
    get() = FootballDatabaseOpenHelper.getInstance(applicationContext)