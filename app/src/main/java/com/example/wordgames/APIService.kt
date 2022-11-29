package com.example.wordgames

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @FormUrlEncoded
    @POST("/pg_score_player.php?")
    suspend fun createEmployee(@FieldMap params: HashMap<String?, String?>): Response<ResponseBody>

    @FormUrlEncoded
    @POST("/pg_score_player.php?")
    suspend fun createScore(@FieldMap params: HashMap<String?, String?>): Response<ResponseBody>
}