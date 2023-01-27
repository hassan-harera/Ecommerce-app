package com.harera.add_product

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.harera.add_product.databinding.FragmentAddProductBinding
import com.harera.common.base.BaseFragment
import com.harera.common.utils.BitmapUtils
import com.harera.model.model.Product
import com.opensooq.supernova.gligar.GligarPicker
import com.squareup.picasso.Picasso


class AddProductFragment : BaseFragment() {
    companion object {
        private const val IMAGE_REQ_CODE = 3004
    }

    private val editProductViewModel: AddProductViewModel by viewModels()
    private lateinit var bind: FragmentAddProductBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentAddProductBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        editProductViewModel.image.observe(viewLifecycleOwner) {
            bind.image.setImageBitmap(it)
        }

        editProductViewModel.product.observe(viewLifecycleOwner) {
            updateUI(it)
        }
    }

    private fun updateUI(it: Product) {
        Picasso.get().load(it.productPictureUrls.first()).into(bind.image)
    }

    private fun onAddingFailed(exception: Exception) {
        bind.edit.isEnabled = true
    }

    private fun onAddingSuccess() {
        handleSuccess()
        bind.edit.isEnabled = true
    }

    private fun setupListeners() {
        bind.edit.setOnClickListener {
            editProductViewModel.updateProduct()
        }

        bind.image.setOnClickListener {
            onImageClicked()
        }

        bind.edit.setOnClickListener {
            editProductViewModel.updateProduct()
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
                editProductViewModel.setImage(it)
            }
        }
    }
}