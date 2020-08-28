package com.jnu.unsplash_app_tutorial.retrofit

import android.util.Log
import com.google.gson.JsonElement
import com.jnu.unsplash_app_tutorial.model.Photo
import com.jnu.unsplash_app_tutorial.utlis.API
import com.jnu.unsplash_app_tutorial.utlis.Constants.TAG
import com.jnu.unsplash_app_tutorial.utlis.RESPONSE_STATE
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat

class RetrofitManager {

    companion object {
        val instance = RetrofitManager()
    }
    // http 콜 만들기
    // 레트로핏 인터페이스 가져오기
    private val iRetrofit : IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    // 사진 검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, ArrayList<Photo>?) -> Unit){

        val term = searchTerm.let{
            it
        }?: ""
//        아래랑 위랑 같은말
//        val term = searchTerm ? : ""

        val call = iRetrofit?.searchPhotos(searchTerm = term).let{
            it
        }?: return

//        val call = iRetrofit?.searchPhotos(searchTerm = term) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement>{

            // 응답 실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG,"RetrofitManager - onFailure() called / t: $t")

                completion(RESPONSE_STATE.FAIL, null)
            }

            // 응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
               Log.d(TAG,"RetrofitManager - onResponse() called / response : ${response.body()}")

                when(response.code()){
                    200 -> {

                        //response.body 에 데이터가 있다면?
                        response.body()?.let {

                            var parsedPhotoDataArray = ArrayList<Photo>()

                            val body = it.asJsonObject
                            val results = body.getAsJsonArray("results")
                            
                            val total = body.get("total").asInt
                            
                            Log.d(TAG,"RetrofitManager - onResponse() called / total: $total")

                            results.forEach { resultItem ->
                                val resultItemObject = resultItem.asJsonObject

                                val user = resultItemObject.get("user").asJsonObject
                                val userName : String = user.get("username").asString
                                val likesCount = resultItemObject.get("likes").asInt
                                val thumbnailLink  = resultItemObject.get("urls").asJsonObject.get("thumb").asString
                                val createAt = resultItemObject.get("created_at").asString

                                val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                val formatter = SimpleDateFormat("yyyy년\nMM월 dd")

                                val outputDateString = formatter.format(parser.parse(createAt))

//                                Log.d(TAG,"RetrofitManager - onResponse() called / outputDateString $outputDateString ")

                                val photoItem = Photo(author = userName, likesCount = likesCount, thumbnail = thumbnailLink, createAt = outputDateString )

                                parsedPhotoDataArray.add(photoItem)

                            }

                            completion(RESPONSE_STATE.OKAY, parsedPhotoDataArray)

                        }

                    }
                }


            }

        })

    }

}