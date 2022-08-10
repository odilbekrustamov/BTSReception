package com.exsample.btsreception.di

import android.app.Application
import com.exsample.readnumbercall.database.AppDtabase
import com.exsample.readnumbercall.database.CallDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun appDatabase(contect: Application): AppDtabase {
        return  AppDtabase.getAppDBInstance(contect)
    }

    @Provides
    @Singleton
    fun callDao(appDtabase: AppDtabase): CallDao{
        return appDtabase.callDao()
    }

}