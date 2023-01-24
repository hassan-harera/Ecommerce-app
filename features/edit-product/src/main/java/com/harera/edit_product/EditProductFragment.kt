package com.harera.edit_product

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.harera.common.base.BaseFragment
import com.harera.common.utils.BitmapUtils
import com.harera.common.utils.navigation.Arguments.PRODUCT_ID
import com.harera.edit_product.databinding.FragmentEditProductBinding
import com.harera.model.model.Product
import com.opensooq.supernova.gligar.GligarPicker
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EditProductFragment : BaseFragment() {
    companion object {
        private val IMAGE_REQ_CODE = 3004
    }

    private val editProductViewModel: EditProductViewModel by viewModels()
    private lateinit var bind: FragmentEditProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getString(PRODUCT_ID)?.let {
            editProductViewModel.setProductId(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentEditProductBinding.inflate(layoutInflater)
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

        editProductViewModel.productId.observe(viewLifecycleOwner) {
            lifecycleScope.launch(Dispatchers.IO) {
                editProductViewModel.getProduct()
            }
        }

        editProductViewModel.operationCompleted.observe(viewLifecycleOwner) {
            lifecycleScope.launch(Dispatchers.IO) {
                editProductViewModel.getProduct()
            }
        }

        editProductViewModel.product.observe(viewLifecycleOwner) {
            updateUI(it)
        }
    }

    private fun updateUI(it: Product) {
        bind.produtName.text = it.title
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