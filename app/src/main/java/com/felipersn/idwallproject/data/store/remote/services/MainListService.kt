package com.felipersn.idwallproject.data.store.remote.services

import com.felipersn.idwallproject.data.store.remote.dto.mainlist.FeedResponseDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MainListService {

    @GET("/feed")
    fun getFeed(@Header("Authorization") token: String?, @Query("category") category: String?): Observable<FeedResponseDTO>

}