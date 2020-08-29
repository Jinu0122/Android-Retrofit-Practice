package com.jnu.unsplash_app_tutorial.utlis

object Constants {
    const val TAG : String = " 로그"
}

enum class SERCH_TYPE {
    PHOTO, USER
}

enum class RESPONSE_STATUS{
    OKAY,
    FAIL,
    NO_CONTENT
}


object  API {
    const val BASE_URL : String = "https://api.unsplash.com/"
    // 우리가 받은 키값으로 해야함
    const val CLIENT_ID : String = "Twbq1_ElbuzkDQ7bluOYQP0DON9l9fU5XK2cObHnA6Y"

    const val SEARCH_PHOTOS : String = "search/photos"
    const val SEARCH_USERS : String = "search/users"


}
