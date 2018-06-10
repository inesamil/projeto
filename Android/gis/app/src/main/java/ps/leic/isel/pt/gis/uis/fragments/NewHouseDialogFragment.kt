package ps.leic.isel.pt.gis.uis.fragments

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import kotlinx.android.synthetic.main.layout_new_house_dialog.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.model.body.HouseBody
import ps.leic.isel.pt.gis.model.dtos.CharacteristicsDto
import ps.leic.isel.pt.gis.model.dtos.HouseDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.utils.EditTextUtils
import ps.leic.isel.pt.gis.utils.RestrictionsUtils
import ps.leic.isel.pt.gis.viewModel.HousesViewModel

class NewHouseDialogFragment : DialogFragment() {

    private var housesViewModel: HousesViewModel? = null
    private lateinit var action: Action

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        // Get the layout inflater
        val inflater = activity!!.layoutInflater

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val view = inflater.inflate(R.layout.layout_new_house_dialog, null)
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.add, { _, _ ->
                    addHouse(view)
                })
                .setNegativeButton(R.string.cancel, { _, _ -> this@NewHouseDialogFragment.getDialog().cancel() })

        // Set listeners
        setButtonsListeners(view)

        return builder.create()
    }

    private fun setButtonsListeners(view: View) {
        // Set Plus buttons listeners
        view.babiesPlusBtn.setOnClickListener {
            EditTextUtils.incNumberText(
                    view.babiesNumEditText,
                    RestrictionsUtils.characteristicsMinValue,
                    RestrictionsUtils.characteristicsMaxValue)
        }

        view.childrenPlusBtn.setOnClickListener {
            EditTextUtils.incNumberText(
                    view.childrenNumEditText,
                    RestrictionsUtils.characteristicsMinValue,
                    RestrictionsUtils.characteristicsMaxValue)
        }

        view.adultsPlusBtn.setOnClickListener {
            EditTextUtils.incNumberText(
                    view.adultsNumEditText,
                    RestrictionsUtils.characteristicsMinValue,
                    RestrictionsUtils.characteristicsMaxValue)
        }

        view.seniorsPlusBtn.setOnClickListener {
            EditTextUtils.incNumberText(
                    view.seniorNumEditText,
                    RestrictionsUtils.characteristicsMinValue,
                    RestrictionsUtils.characteristicsMaxValue)
        }

        // Set Minus buttons listeners
        view.babiesMinusBtn.setOnClickListener {
            EditTextUtils.decNumberText(
                    view.babiesNumEditText,
                    RestrictionsUtils.characteristicsMinValue)
        }
        view.childrenMinusBtn.setOnClickListener {
            EditTextUtils.decNumberText(
                    view.childrenNumEditText,
                    RestrictionsUtils.characteristicsMinValue)
        }

        view.adultsMinusBtn.setOnClickListener {
            EditTextUtils.decNumberText(
                    view.adultsNumEditText,
                    RestrictionsUtils.characteristicsMinValue)
        }

        view.seniorsMinusBtn.setOnClickListener {
            EditTextUtils.decNumberText(
                    view.seniorNumEditText,
                    RestrictionsUtils.characteristicsMinValue)
        }
    }

    private fun addHouse(view: View) {
        val houseName: String = view.housesnameEditText.text.toString()
        val babiesNumber: Short = view.babiesNumEditText.text.toString().toShortOrNull() ?: 0
        val childrenNumber: Short = view.childrenNumEditText.text.toString().toShortOrNull() ?: 0
        val adultsNumber: Short = view.adultsNumEditText.text.toString().toShortOrNull() ?: 0
        val seniorsNumber: Short = view.seniorNumEditText.text.toString().toShortOrNull() ?: 0

        val house: HouseBody = HouseBody(houseName, babiesNumber, childrenNumber, adultsNumber, seniorsNumber)

        housesViewModel = ViewModelProviders.of(this).get(HousesViewModel::class.java)
        housesViewModel?.addHouse(house)?.observe(this, Observer {
            when {
                it?.status == Status.SUCCESS -> onSuccess(it.data!!)
                it?.status == Status.ERROR -> onError(it.message)
            }
        })
    }

    private fun onSuccess(house: HouseDto) {
        //TODO: o que fazer em caso de sucesso. Voltar para o fragmento qe lancou este dialog e atualizar com a casa qe foi inserida
    }

    private fun onError(message: String?) {
        //TODO: o que fazer em caso de erro. Mensagem a dizer que nao foi possivel inserir, ou então ver qual a excecao e se for de autenticacao por exemplo dizer qe n ta autenticado, cenas do genero
    }

    /**
     * NewHouseDialogFragment Factory
     */
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment NewListDialogFragment.
         */
        @JvmStatic
        fun newInstance() = NewHouseDialogFragment()
    }
}