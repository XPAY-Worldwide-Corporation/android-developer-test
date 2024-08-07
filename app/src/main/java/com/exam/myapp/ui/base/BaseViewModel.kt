package com.exam.myapp.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(){

    val loadingVisibility : MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage : MutableLiveData<String> = MutableLiveData()

    abstract fun start()
}