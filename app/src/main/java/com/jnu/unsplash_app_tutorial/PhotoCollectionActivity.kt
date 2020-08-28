package com.jnu.unsplash_app_tutorial

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jnu.unsplash_app_tutorial.model.Photo

class PhotoCollectionActivity: AppCompatActivity() {

    companion object{
        const val TAG: String = "로그"
    }

    private var photoList = ArrayList<Photo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_collection)

        Log.d(TAG,"PhotoCollectionActivity - onCreate() called")

        val bundle = intent.getBundleExtra("array_bundle")

        val searchTerm= intent.getStringExtra("search_term")

        photoList = bundle?.getSerializable("photo_array_list") as ArrayList<Photo>
        
        Log.d(TAG,"PhotoCollectionActivity - onCreate() called / searchTerm : $searchTerm, photoList.co unt() : ${photoList.count()}")

    }

}