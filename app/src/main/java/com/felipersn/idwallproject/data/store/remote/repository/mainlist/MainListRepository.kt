package com.felipersn.idwallproject.data.store.remote.repository.mainlist

import com.felipersn.idwallproject.data.store.remote.dto.mainlist.FeedResponseDTO
import com.felipersn.idwallproject.data.store.remote.services.MainListService
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainListRepository @Inject constructor(private val mainListService: MainListService) {

    fun getFeed(token: String, category: String?): Maybe<FeedResponseDTO> {
        val remote = getFeedRemote(token, category)

        return Observable.concatArray(remote)
            .filter { list -> list != null }
            .firstElement()
    }

    private fun getFeedRemote(token: String, category: String?): Observable<FeedResponseDTO> {
        return mainListService.getFeed(token, category)
            .cache()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}