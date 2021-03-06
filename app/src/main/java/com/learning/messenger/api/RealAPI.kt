package com.learning.messenger.api

import android.graphics.Bitmap
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.learning.messenger.data.Message
import com.learning.messenger.data.Person
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

class RealAPI : IMessengerAPI {
    companion object {
        private val api : IMessengerService by lazy {
            val gson = GsonBuilder().create()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://messenger-anduril.herokuapp.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val service = retrofit.create(IMessengerService::class.java)
            service
        }
    }
    override fun login(username: String, password: String): Single<Int> {
        return api.login(LoginRequest(username, password)).map {
            it.userId
        }
    }

    override fun changePassword(userId: Int, password: String): Single<Boolean> {
        TODO("Not yet implemented")
    }

    override fun getContacts(userId: Int): Single<List<Person>> {
        return api.getContacts(userId)
    }

    override fun getMessages(
        userId: Int,
        personId: Int,
        numberOfLastMessages: Int
    ): Single<List<Message>> {
        TODO("Not yet implemented")
    }

    override fun sendMessage(
        userId: Int,
        recipientId: Int,
        message: String,
        attachment: Bitmap?
    ): Single<Boolean> {
        TODO("Not yet implemented")
    }
}

private interface IMessengerService {
    @POST("/login")
    fun login(@Body request: LoginRequest): Single<LoginResponse>

    @GET("/user/{userId}/contacts")
    fun getContacts(@Path("userId") userId: Int): Single<List<Person>>
}

private data class LoginRequest(val user: String, val password: String)
private data class LoginResponse(val userId: Int)