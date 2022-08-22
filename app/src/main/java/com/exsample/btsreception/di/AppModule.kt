package com.exsample.btsreception.di

import android.app.Application
import com.exsample.btsreception.networking.Server.IS_TESTER
import com.exsample.btsreception.networking.Server.SERVER_DEVELOPMENT
import com.exsample.btsreception.networking.Server.SERVER_PRODUCTION
import com.exsample.btsreception.networking.UserServer
import com.exsample.readnumbercall.database.AppDtabase
import com.exsample.readnumbercall.database.CallDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /**
     * Retrofit Related
     */
    @Provides
    fun server(): String =  if(IS_TESTER)  SERVER_DEVELOPMENT else SERVER_PRODUCTION


    @Provides
    @Singleton
    fun retrofitClient(): Retrofit =
        Retrofit.Builder().baseUrl(server())
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides
    @Singleton
    fun userServer(): UserServer = retrofitClient().create(UserServer::class.java)

    /**
     * Retrofit Room
     */

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