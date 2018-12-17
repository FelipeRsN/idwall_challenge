package com.felipersn.idwallproject.presentation.ui.mainlist

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.felipersn.idwallproject.common.di.module.SharedPreferenceModule
import com.felipersn.idwallproject.common.tools.Resource
import com.felipersn.idwallproject.common.tools.SingleLiveEvent
import com.felipersn.idwallproject.data.store.remote.dto.mainlist.FeedResponseDTO
import com.felipersn.idwallproject.data.store.remote.repository.mainlist.MainListRepository
import com.felipersn.idwallproject.presentation.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class MainListViewModel @Inject constructor(
    private val mainListRepository: MainListRepository,
    private val mainListSharedPreferences: SharedPreferences
) : BaseViewModel() {

    //LiveData variables
    val feedLiveData = MutableLiveData<SingleLiveEvent<Resource<List<String?>>>>()

    //Data variables
    var categoryList = listOf("husky", "hound", "pug", "labrador")
    var category = categoryList[0]

    //Get feed from API
    fun getFeed() {
        feedLiveData.value = SingleLiveEvent(Resource.loading())

        addDisposable(mainListRepository
            .getFeed(getLoginToken(), category)
            .subscribe(
                { result -> onSuccessFeed(result) },
                { throwable ->
                    feedLiveData.value = SingleLiveEvent(Resource.error(throwable.message))
                }
            ))
    }

    private fun onSuccessFeed(feedResponseDTO: FeedResponseDTO) {
        if (feedResponseDTO.list != null) {

            //remove empty images
            val list: ArrayList<String?> = ArrayList(feedResponseDTO.list)
            list.removeAll(Arrays.asList("", null))

            feedLiveData.value = SingleLiveEvent(Resource.success(list.toList()))
        } else {
            feedLiveData.value = SingleLiveEvent(Resource.error())
        }
    }

    private fun getLoginToken(): String {
        val token = mainListSharedPreferences.getString(SharedPreferenceModule.SHARED_PREFERENCE_LOGIN_TOKEN, "")

        token?.let {
            return it
        }

        return ""
    }

    fun invalidateLoginToken(){
        mainListSharedPreferences.edit().clear().apply()
    }
}
