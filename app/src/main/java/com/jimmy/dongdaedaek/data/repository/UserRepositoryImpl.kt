package com.jimmy.dongdaedaek.data.repository

import com.jimmy.dongdaedaek.data.preference.PreferenceManager
import com.jimmy.dongdaedaek.domain.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    val preferenceManager: PreferenceManager,
    val dispatcher: CoroutineDispatcher
):UserRepository {
    override suspend fun getUser(): User? = withContext(dispatcher) {
        preferenceManager.getString(KEY_USER_ID)?.let { User(it) }
    }

    override suspend fun saveUser(user: User) = withContext(dispatcher) {
        preferenceManager.putString(KEY_USER_ID, user.id!!)
    }

    companion object {
        private const val KEY_USER_ID = "KEY_USER_ID"
    }
}