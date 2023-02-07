package com.meuticket.pos.core.storage.di;

import android.app.Application;

import androidx.room.Room;

import com.meuticket.pos.core.storage.AppDatabase;
import com.meuticket.pos.core.storage.dao.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    @Singleton
    @Provides
    AppDatabase providesRoomDatabase(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, "ticket-db").build();
    }

    @Singleton
    @Provides
    UserDao providesUserDao(AppDatabase AppDatabase) {
        return AppDatabase.userDao();
    }
}