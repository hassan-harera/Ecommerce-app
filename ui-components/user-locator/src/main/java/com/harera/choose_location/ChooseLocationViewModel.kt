package com.harera.choose_location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.harera.common.base.BaseViewModel
import com.harera.common.local.UserDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseLocationViewModel @Inject constructor(
    userDataStore: UserDataStore
) : BaseViewModel(userDataStore) {

    private val _location: MutableLiveData<LatLng> = MutableLiveData<LatLng>()
    val location: LiveData<LatLng> = _location

    fun setLocation(location: LatLng) {
        _location.value = location
    }
}