package com.mahnoosh.data

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import javax.inject.Inject

class UserRepositoryOne @Inject constructor(val userDao: UserDao) : Syncable {
    fun getUsers() = listOf(User("mahnoosh"))

    suspend fun addUser(users: User) = userDao.insertAll(users.toUserEntity())
    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        return synchronizer.changeListSync(
            dataFetcher = { getUsers() },
            modelUpdater = {userList->
            addUser(userList[0])
        })
    }
}

data class User(val name: String)


fun User.toUserEntity() = UserEntity(name = this.name)

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    @ColumnInfo(name = "name") val name: String
)

@Dao
interface UserDao {

    @Insert
    suspend fun insertAll(users: UserEntity)
}