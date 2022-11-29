package com.example.wordgames

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface APIServicePut {
    @Headers("Content-Type: application/json")
    @PUT("/pg_score_player.php")
    suspend fun updateScore(@Query("usernameput") usernameput: String?, @Query("score") score: Int): Response<ResponseBody>

    @Headers("Content-Type: application/json")
    @PUT("/pg_score_player.php")
    suspend fun updateLevel(@Query("usernameput") usernameput: String?, @Query("level") score: String): Response<ResponseBody>
}