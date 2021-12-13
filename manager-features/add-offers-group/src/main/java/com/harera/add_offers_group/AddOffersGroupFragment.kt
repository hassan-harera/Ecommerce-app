package com.harera.add_offers_group

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.harera.add_offers_group.databinding.FragmentAddOffersGroupBinding
import com.harera.common.afterTextChanged
import com.harera.common.base.BaseFragment
import com.harera.common.utils.BitmapUtils
import com.opensooq.supernova.gligar.GligarPicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddOffersGroupFragment : BaseFragment() {
    companion object {
        private const val IMAGE_REQ_CODE = 3004
    }

    private lateinit var bind: FragmentAddOffersGroupBinding
    private val addCategoryViewModel: AddOffersGroupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        bind = FragmentAddOffersGroupBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        addCategoryViewModel.image.observe(viewLifecycleOwner) {
            bind.image.setImageBitmap(it)
        }

        bind.offerGroupName.setText(addCategoryViewModel.offersGroupName.value)

        addCategoryViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(state = it)
        }

        addCategoryViewModel.exception.observe(viewLifecycleOwner) {
            handleError(exception = it)
        }

        addCategoryViewModel.operationCompleted.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        addCategoryViewModel.formState.observe(viewLifecycleOwner) {
            bind.add.isEnabled = it.isValid

            if (it.offersGroupName != null) {
                bind.offerGroupName.error = getString(it.offersGroupName)
            } else if (it.imageError != null) {
                Toast
                    .makeText(
                        context,
                        getString(it.imageError),
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
    }

    private fun setupListeners() {
        bind.offerGroupName.afterTextChanged {
            addCategoryViewModel.setOffersGroupName(it)
        }

        bind.image.setOnClickListener {
            onImageClicked()
        }

        bind.add.setOnClickListener {
            bind.add.isEnabled = false
            lifecycleScope.launch(Dispatchers.IO) {
                addCategoryViewModel.submitForm()
            }
        }
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
                addCategoryViewModel.setImage(it)
            }
        }
    }
}