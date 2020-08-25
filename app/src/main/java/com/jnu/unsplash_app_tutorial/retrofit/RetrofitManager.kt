package com.jnu.unsplash_app_tutorial.retrofit

import android.util.Log
import com.google.gson.JsonElement
import com.jnu.unsplash_app_tutorial.utlis.API
import com.jnu.unsplash_app_tutorial.retrofit.IRetrofit
import com.jnu.unsplash_app_tutorial.utlis.Constants.TAG
import com.jnu.unsplash_app_tutorial.utlis.RESPONSE_STATE
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {

    companion object {
        val instance = RetrofitManager()
    }
    // http 콜 만들기
    // 레트로핏 인터페이스 가져오기
    private val iRetrofit : IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    // 사진 검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, String) -> Unit){

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

                completion(RESPONSE_STATE.FAIL, t.toString())
            }

            // 응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
               Log.d(TAG,"RetrofitManager - onResponse() called / response : ${response.body()}")

                completion(RESPONSE_STATE.OKAY,response.body().toString())
            }

        })

    }

}