package com.mahnoosh.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserEntity::class], // Tell the database the entries will hold data of this type
    version = 1
)

abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}