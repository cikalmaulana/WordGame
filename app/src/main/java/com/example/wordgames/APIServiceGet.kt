package com.example.wordgames
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIServiceGet {
    @Headers("Content-Type: application/json")
    @GET("/blindApp/pg_score_player.php")
    suspend fun getUser(@Query("usernamelogin") usernamelogin: String?): Response<ResponseBody>

    @Headers("Content-Type: application/json")
    @GET("/blindApp/pg_score_player.php")
    suspend fun getScore(@Query("usernamescore") usernamescore: String?): Response<ResponseBody>
}