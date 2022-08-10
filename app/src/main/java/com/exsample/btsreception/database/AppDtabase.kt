package com.exsample.readnumbercall.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.exsample.btsreception.model.ClientList
import com.exsample.readnumbercall.model.CallNumber

@Database(entities = [CallNumber::class, ClientList::class], version = 4)
abstract class AppDtabase: RoomDatabase() {

    abstract fun callDao(): CallDao

    companion object{
        private var DB_INSTANCE: AppDtabase? = null

        fun getAppDBInstance(context: Context): AppDtabase{
            if (DB_INSTANCE == null){
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDtabase::class.java,
                    "DB_CALL_numbers"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return DB_INSTANCE!!
        }
    }

}