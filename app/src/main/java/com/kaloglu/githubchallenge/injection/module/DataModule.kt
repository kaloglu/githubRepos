package com.kaloglu.githubchallenge.injection.module

import android.app.Application
import android.arch.persistence.room.Room
import com.kaloglu.githubchallenge.injection.scopes.PerApplication
import com.kaloglu.githubchallenge.data.cache.GithubDb
import com.kaloglu.githubchallenge.data.cache.RepoDao
import com.kaloglu.githubchallenge.data.cache.UserDao
import dagger.Module
import dagger.Provides

@Module
abstract class DataModule {

    @Module
    companion object {

        @JvmStatic
        @PerApplication
        @Provides
        fun provideGithubDb(app: Application): GithubDb {
            return Room
                    .databaseBuilder(app, GithubDb::class.java, "github.db")
                    .fallbackToDestructiveMigration()
                    .build()
        }

        @JvmStatic
        @PerApplication
        @Provides
        fun provideUserDao(db: GithubDb): UserDao {
            return db.userDao()
        }

        @JvmStatic
        @PerApplication
        @Provides
        fun provideRepoDao(db: GithubDb): RepoDao {
            return db.repoDao()
        }
    }
}
