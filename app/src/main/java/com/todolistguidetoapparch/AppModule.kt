package com.todolistguidetoapparch

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideString() = "hubba hubba!"

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {

        return Room.databaseBuilder(app, TodoDatabase::class.java, "my_db")
            .build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db : TodoDatabase): TodoRepository {
        return ProdTodoRepository(db)
    }
}