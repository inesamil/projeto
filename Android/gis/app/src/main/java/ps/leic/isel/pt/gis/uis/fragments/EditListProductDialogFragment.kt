package ps.leic.isel.pt.gis.uis.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.layout_edit_list_product_dialog.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.ListProduct
import ps.leic.isel.pt.gis.utils.TextViewUtils

class EditListProductDialogFragment : DialogFragment() {

    private var listener: OnEditListProductDialogFragmentInteractionListener? = null
    private lateinit var listProduct: ListProduct

    private lateinit var quantityText: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditListProductDialogFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnEditListProductDialogFragmentInteractionListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        // Get the layout inflater
        val inflater = activity!!.layoutInflater

        arguments?.let {
            val productId = it.getInt(PRODUCT_ID_TAG)
            val productName = it.getString(PRODUCT_NAME_TAG)
            val productQuantity = it.getShort(PRODUCT_QUANTITY_TAG)
            listProduct = ListProduct(productId, productName, null, productQuantity)
        }

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val view = inflater.inflate(R.layout.layout_edit_list_product_dialog, null)
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.save, { _, _ -> listener?.onEditListProduct(listProduct) })
                .setNegativeButton(R.string.cancel, { _, _ -> this@EditListProductDialogFragment.dialog.cancel() })

        view.productNameText.text = listProduct.productName
        quantityText = view.quantityText
        quantityText.text = listProduct.quantity.toString()

        view.plusButton.setOnClickListener {
            listProduct.quantity = TextViewUtils.incNumberText(quantityText, 1, Short.MAX_VALUE.toInt()).toShort()
        }

        view.minusButton.setOnClickListener {
            listProduct.quantity = TextViewUtils.decNumberText(quantityText, 1).toShort()
        }

        return builder.create()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnEditListProductDialogFragmentInteractionListener {
        fun onEditListProduct(listProduct: ListProduct)
    }

    /**
     * EditListProductDialogFragment Factory
     */
    companion object {
        const val TAG: String = "EditListProductDialogFragment"
        private const val PRODUCT_ID_TAG = "PRODUCT_ID"
        private const val PRODUCT_NAME_TAG = "PRODUCT_NAME"
        private const val PRODUCT_QUANTITY_TAG = "PRODUCT_QUANTITY"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment EditListProductDialogFragment.
         */
        @JvmStatic
        fun newInstance(productId: Int, productName: String, productQuantity: Short) = EditListProductDialogFragment().apply {
            arguments = Bundle().apply {
                putInt(PRODUCT_ID_TAG, productId)
                putString(PRODUCT_NAME_TAG, productName)
                putShort(PRODUCT_QUANTITY_TAG, productQuantity)
            }
        }
    }
}