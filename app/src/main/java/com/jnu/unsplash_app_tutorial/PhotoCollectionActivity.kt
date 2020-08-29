package com.jnu.unsplash_app_tutorial

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.jnu.unsplash_app_tutorial.model.Photo
import com.jnu.unsplash_app_tutorial.recyclerview.PhotoGridRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_photo_collection.*

class PhotoCollectionActivity: AppCompatActivity() {

    companion object{
        const val TAG: String = "로그"
    }
    // 데이터
    private var photoList = ArrayList<Photo>()

    // 어답터
    private lateinit var photoGridRecyclerViewAdapter: PhotoGridRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_collection)

        Log.d(TAG,"PhotoCollectionActivity - onCreate() called")

        val bundle = intent.getBundleExtra("array_bundle")

        val searchTerm= intent.getStringExtra("search_term")

        photoList = bundle?.getSerializable("photo_array_list") as ArrayList<Photo>
        
        Log.d(TAG,"PhotoCollectionActivity - onCreate() called / searchTerm : $searchTerm, photoList.co unt() : ${photoList.count()}")

        top_app_bar.title = searchTerm


        this.photoGridRecyclerViewAdapter = PhotoGridRecyclerViewAdapter()

        this.photoGridRecyclerViewAdapter.submitList(photoList)

        my_photo_recyclerview.layoutManager = GridLayoutManager(this,2, GridLayoutManager.VERTICAL, false)
        my_photo_recyclerview.adapter= this.photoGridRecyclerViewAdapter

    }

}