package com.harera.add_category

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.harera.common.base.BaseViewModel
import com.harera.common.local.UserDataStore
import com.harera.common.utils.Validity
import com.harera.model.model.Category
import com.harera.repository.abstraction.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    userDataStore: UserDataStore,
) : BaseViewModel(userDataStore) {

    private val _categoryName: MutableLiveData<String> = MutableLiveData()
    val categoryName: LiveData<String> = _categoryName

    private val _image: MutableLiveData<Bitmap> = MutableLiveData()
    val image: LiveData<Bitmap> = _image

    private val _imageUrl: MutableLiveData<String> = MutableLiveData()
    val imageUrl: LiveData<String> = _imageUrl

    private val _operationCompleted: MutableLiveData<Boolean> = MutableLiveData()
    val operationCompleted: LiveData<Boolean> = _operationCompleted

    private val _formState: MutableLiveData<CategoryFormState> = MutableLiveData()
    val formState: LiveData<CategoryFormState> = _formState

    private val _category: MutableLiveData<Category> = MutableLiveData()
    val category: LiveData<Category> = _category

    fun setCategoryName(name: String) {
        _categoryName.value = name
        checkFormValidity()
    }

    fun setImage(imageBitmap: Bitmap) {
        _image.value = imageBitmap
        checkFormValidity()
    }

    private fun checkFormValidity() {
        if (categoryName.value == null) {
            _formState.value =
                CategoryFormState(categoryNameError = R.string.empty_category_name_error)
        } else if (!Validity.checkCategoryName(categoryName.value!!)) {
            _formState.value = CategoryFormState(categoryNameError = R.string.wrong_category_name)
        } else if (image.value == null) {
            _formState.value =
                CategoryFormState(categoryImageError = R.string.empty_image_error)
        } else {
            _formState.value =
                CategoryFormState(isValid = true)
        }
    }

    fun submitForm() {
        viewModelScope.launch(Dispatchers.IO) {
            uploadCategoryImage()
            encapsulateForm()
            addCategory(category = category.value!!)
        }
    }

    private suspend fun addCategory(category: Category) {
        updateLoading(true)

        categoryRepository
            .addCategory(category = category)
            .onSuccess {
                updateLoading(true)
                _operationCompleted.postValue(it)
            }
            .onFailure {
                updateLoading(true)
                handleException(it)
            }
    }

    private fun encapsulateForm() {
        Category(
            categoryName = categoryName.value!!,
            categoryImagerUrl = imageUrl.value!!,
        ).let {
            _category.postValue(it)
        }
    }

    private suspend fun uploadCategoryImage() {
        categoryRepository
            .uploadCategoryImage(
                categoryName = categoryName.value!!,
                imageBitmap = image.value!!,
            )
            .onSuccess {
                _imageUrl.postValue(it)
            }
            .onFailure {
                handleException(it)
            }
    }
}