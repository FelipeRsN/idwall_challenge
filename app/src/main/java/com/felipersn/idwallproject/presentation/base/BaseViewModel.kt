package com.felipersn.idwallproject.presentation.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private var mCompositeDisposable: CompositeDisposable? = null

    /**
     * Contains common cleanup actions needed for all ViewModel, if any.
     * Subclasses may override this.
     */
    override fun onCleared() {
        super.onCleared()
        onStopDisposable()
    }

    fun onStopDisposable() {
        getCompositeDisposable().clear()
    }

    protected fun addDisposable(disposable: Disposable) {
        getCompositeDisposable().add(disposable)
    }

    private fun getCompositeDisposable(): CompositeDisposable {
        if (mCompositeDisposable == null || mCompositeDisposable!!.isDisposed) {
            mCompositeDisposable = CompositeDisposable()
        }
        return mCompositeDisposable as CompositeDisposable
    }
}