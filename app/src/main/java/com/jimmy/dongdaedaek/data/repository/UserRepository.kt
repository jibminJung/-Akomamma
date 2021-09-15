package com.jimmy.dongdaedaek.data.repository

import com.jimmy.dongdaedaek.domain.model.User

interface UserRepository {
    suspend fun getUser():User?
    suspend fun saveUser(user:User)
}