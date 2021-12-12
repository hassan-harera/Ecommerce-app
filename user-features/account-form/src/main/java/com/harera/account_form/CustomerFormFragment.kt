package com.harera.account_form

import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.google.type.LatLng
import com.harera.account_form.databinding.FragmentAccountFormBinding
import com.harera.choose_location.ChooseLocationActivity
import com.harera.common.afterTextChanged
import com.harera.common.base.BaseFragment
import com.harera.common.utils.BitmapUtils
import com.harera.common.utils.location.LocationUtils
import com.harera.common.utils.navigation.Arguments.LOCATION_RESULT
import com.opensooq.supernova.gligar.GligarPicker
import java.io.IOException
import java.util.*

class CustomerFormFragment : BaseFragment() {
    companion object {
        private val CHOOSE_LOCATION_CODE: Int = 3005
        private val IMAGE_REQ_CODE = 3004
    }
    private lateinit var bind: FragmentAccountFormBinding
    private val customerFormViewModel: CustomerFormViewModel by viewModels()
    private lateinit var locationResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val location = it.data!!.extras!![LOCATION_RESULT] as LatLng
                    customerFormViewModel.setLocation(location)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentAccountFormBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        customerFormViewModel.apply {
            image.observe(viewLifecycleOwner) {
                bind.image.setImageBitmap(it)
            }

            firstName.observe(viewLifecycleOwner) {
                bind.firstName.setText(it)
            }

            lastName.observe(viewLifecycleOwner) {
                bind.lastName.setText(it)
            }

            location.observe(viewLifecycleOwner) {
                updateLocation(it)
            }
        }

        customerFormViewModel.user.observe(viewLifecycleOwner) {

        }

        customerFormViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(state = it)
        }

        customerFormViewModel.exception.observe(viewLifecycleOwner) {
            handleError(exception = it)
        }

        customerFormViewModel.codeConfirmed.observe(viewLifecycleOwner) {
            if(it) {
                checkCodeRight()
            }
        }

        customerFormViewModel.medicineFormState.observe(viewLifecycleOwner) {
            it.isValid?.let {
                bind.add.isEnabled = it
            }

            if (it.firstNameError != null) {
                bind.firstName.error = getString(it.firstNameError)
            } else if (it.lastNameError != null) {
                bind.lastName.error = getString(it.lastNameError)
            } else if (it.addressError != null) {
                Toast.makeText(context, getString(R.string.empty_location_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkCodeRight() {

    }

    private fun onAddingFailed(exception: Exception) {
        handleError(exception)
        bind.add.isEnabled = true
    }

    private fun onAddingSuccess() {
        handleSuccess()
        bind.add.isEnabled = true
    }

    private fun updateLocation(location: LatLng) {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            bind.location.text = LocationUtils.getLocationAddressName(location.latitude, location.longitude, geocoder)
        } catch (e: IOException) {
            handleError(e)
        }
    }

    private fun setupListeners() {
        bind.firstName.afterTextChanged {
            customerFormViewModel.setFirstName(it)
        }

        bind.lastName.afterTextChanged {
            customerFormViewModel.setLastName(it)
        }

        bind.image.setOnClickListener {
            onImageClicked()
        }

        bind.location.setOnClickListener {
            goToChooseLocation()
        }

        bind.add.setOnClickListener {
            bind.add.isEnabled = false
            customerFormViewModel.encapsulateUserForm()
        }
    }


    private fun goToChooseLocation() {
        val intent = Intent(context, ChooseLocationActivity::class.java)
        locationResult.launch(intent)
    }

    private fun onImageClicked() {
        GligarPicker()
            .requestCode(IMAGE_REQ_CODE)
            .limit(1)
            .withFragment(this)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null && resultCode == Activity.RESULT_OK && requestCode == IMAGE_REQ_CODE) {
            val imageBitmap = BitmapUtils.convertImagePathToBitmap(data)
            imageBitmap?.let {
                customerFormViewModel.setImage(it)
            }
        }
    }
}