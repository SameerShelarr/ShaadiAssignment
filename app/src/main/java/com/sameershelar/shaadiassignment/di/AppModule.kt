package com.sameershelar.shaadiassignment.di

import android.app.Application
import androidx.room.Room
import com.sameershelar.shaadiassignment.data.local.dao.MemberDao
import com.sameershelar.shaadiassignment.data.local.db.ShaadiDatabase
import com.sameershelar.shaadiassignment.data.remote.api.ShaadiAPI
import com.sameershelar.shaadiassignment.data.repository.ShaadiRepository
import com.sameershelar.shaadiassignment.utils.AppConstants.BASE_URL
import com.sameershelar.shaadiassignment.utils.AppConstants.DATABASE_NAME
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

    @Provides
    @Singleton
    fun providesDatabase(
        app: Application,
    ) = Room.databaseBuilder(app, ShaadiDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun providesAddedDrinkDao(db: ShaadiDatabase) = db.memberDao()

    @Provides
    @Singleton
    fun providesShaadiRepository(memberDao: MemberDao, shaadiAPI: ShaadiAPI) = ShaadiRepository(memberDao, shaadiAPI)

    @Provides
    @Singleton
    fun providesShaadiAPI(): ShaadiAPI =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ShaadiAPI::class.java)
}