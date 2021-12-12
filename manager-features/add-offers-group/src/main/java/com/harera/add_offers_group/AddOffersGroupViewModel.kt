package com.harera.add_offers_group

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.harera.common.base.BaseViewModel
import com.harera.common.local.UserDataStore
import com.harera.common.utils.Validity
import com.harera.model.model.OffersGroup
import com.harera.repository.abstraction.CategoryRepository
import com.harera.repository.abstraction.OfferRepository
import javax.inject.Inject

class AddOffersGroupViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val offerRepository: OfferRepository,
    userDataStore: UserDataStore,
) : BaseViewModel(userDataStore) {

    private val _offersGroupName: MutableLiveData<String> = MutableLiveData()
    val offersGroupName: LiveData<String> = _offersGroupName

    private val _image: MutableLiveData<Bitmap> = MutableLiveData()
    val image: LiveData<Bitmap> = _image

    private val _imageUrl: MutableLiveData<String> = MutableLiveData()
    val imageUrl: LiveData<String> = _imageUrl

    private val _operationCompleted: MutableLiveData<Boolean> = MutableLiveData()
    val operationCompleted: LiveData<Boolean> = _operationCompleted

    private val _formState: MutableLiveData<OffersGroupFormState> = MutableLiveData()
    val formState: LiveData<OffersGroupFormState> = _formState

    fun setOffersGroupName(name: String) {
        _offersGroupName.value = name
        checkFormValidity()
    }

    fun setImage(imageBitmap: Bitmap) {
        _image.value = imageBitmap
        checkFormValidity()
    }

    private fun checkFormValidity() {
        if (_offersGroupName.value.isNullOrBlank() || Validity.checkName(_offersGroupName.value!!)
                .not()
        ) {
            _formState.value =
                OffersGroupFormState(offersGroupName = R.string.is_required)
        } else if (_image.value == null) {
            _formState.value =
                OffersGroupFormState(imageError = R.string.is_required)
        } else {
            _formState.value = OffersGroupFormState(isValid = true)
        }
    }

    suspend fun submitForm() {
        if (formState.value!!.isValid) {
            uploadCategoryImage()
        }
    }

    private suspend fun encapsulateForm() {
        OffersGroup(
            offersGroupName = offersGroupName.value!!,
            imagerUrl = imageUrl.value!!,
        ).let {
            setOffersGroup(it)
        }
    }

    private suspend fun setOffersGroup(offersGroup: OffersGroup) {
        offerRepository
            .setOffersGroup(offersGroup)
            .onSuccess { successful ->
                _operationCompleted.postValue(successful)
            }
            .onFailure {
                handleException(it)
            }
    }

    private suspend fun uploadCategoryImage() {
        offerRepository
            .uploadOffersGroupImage(offersGroupName = offersGroupName.value!!, image.value!!)
            .onSuccess {
                _imageUrl.postValue(it)
                encapsulateForm()
            }
            .onFailure {
                handleException(it)
            }
    }
}