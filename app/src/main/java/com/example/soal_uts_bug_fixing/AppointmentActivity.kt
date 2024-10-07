package com.example.soal_uts_bug_fixing

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment
import androidx.wear.compose.materialcore.is24HourFormat
import com.example.soal_uts_bug_fixing.databinding.ActivityAppointmentBinding
import com.example.soal_uts_bug_fixing.databinding.ActivityFormBinding
import android.text.format.DateFormat
import android.view.View
import android.widget.RadioButton
import com.example.soal_uts_bug_fixing.databinding.DialogExitBinding
import java.util.Calendar

class AppointmentActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, DialogExit.DialogListener
{
    private lateinit var binding: ActivityAppointmentBinding

    companion object{
        const val EXTRA_TELEFON = "extra_phone"
        const val EXTRA_ALAMAT = "extra_alamat"
        const val EXTRA_TIPE = "extra_tipe"
        const val EXTRA_TANGGAL = "extra_tanggal"
        const val EXTRA_WAKTU = "extra_waktu"
    }

    private lateinit var dateInput : String
    private lateinit var timeInput : String
    private lateinit var tipePertemuan : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dateInput = ""
        timeInput = ""
        tipePertemuan =""

        with(binding){

            kalenderTxt.setOnClickListener {
                val datePicker = DatePicker()      //salah pemanggilan button
                datePicker.show(supportFragmentManager, "datePicker")
            }

            timerTxt.setOnClickListener {
                val timePicker = TimePicker() //salah pemanggilan button
                timePicker.show(supportFragmentManager, "timePicker")
            }

            submitBtn.setOnClickListener {
                if(fieldNotEmpty()){ //salah pemanggilan button
                    val dialog = DialogExit()
                    dialog.show(supportFragmentManager, "DialogExit")
                }else{
                    Toast.makeText(this@AppointmentActivity, "MASIH ADA KOLOM YANG KOSONG", Toast.LENGTH_SHORT).show()
                }
            }


            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radioButton1 -> {
                        tipePertemuan = radioButton1.text.toString()
                    }
                    R.id.radioButton2 -> {
                        tipePertemuan = radioButton2.text.toString()
                    }
                }
                if(tipePertemuan=="Online"){
                    inputLayout.visibility = View.GONE
                }else{
                    inputLayout.visibility = View.VISIBLE
                }
            }

        }

    }


    override fun onDateSet(view: android.widget.DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        dateInput = String.format("%02d-%02d-%04d", dayOfMonth, month + 1, year)
        binding.kalenderTxt.text = dateInput
    }

    override fun onTimeSet(view: android.widget.TimePicker?, hourOfDay: Int, minute: Int) {
        timeInput = String.format("%02d:%02d", hourOfDay, minute)
        binding.timerTxt.text = timeInput
    }


//  AKSI SETELAH KONFIRMASI DIALOG BOX
    override fun onDialogResult(result: Boolean) {
        val nama = intent.getStringExtra(FormActivity.EXTRA_NAMA)
        val identitas = intent.getStringExtra(FormActivity.EXTRA_IDENTITAS)
        val gender = intent.getStringExtra(FormActivity.EXTRA_GENDER)

            if (result) {
                val intentToResult = Intent(this@AppointmentActivity, ResultActivity::class.java)
                intentToResult.putExtra(EXTRA_TELEFON, binding.kontakEdt.text.toString())
                intentToResult.putExtra(EXTRA_TANGGAL, binding.kalenderTxt.text.toString())
                intentToResult.putExtra(EXTRA_WAKTU, binding.timerTxt.text.toString())
                intentToResult.putExtra(EXTRA_TIPE, tipePertemuan)


                intentToResult.putExtra(FormActivity.EXTRA_NAMA, nama)
                intentToResult.putExtra(FormActivity.EXTRA_IDENTITAS, identitas)
                intentToResult.putExtra(FormActivity.EXTRA_GENDER, gender)

                if(tipePertemuan=="Offline"){
                    intentToResult.putExtra(EXTRA_ALAMAT, binding.lokasiEdt.text.toString())
                }
                startActivity(intentToResult)
            }
    }

    fun fieldNotEmpty(): Boolean {
        with(binding) {
            if (kontakEdt.text.toString().isEmpty()) {
                Toast.makeText(this@AppointmentActivity, "Masukkan kontak", Toast.LENGTH_SHORT).show()
                return false
            }

            if (tipePertemuan.isEmpty()) {
                Toast.makeText(this@AppointmentActivity, "Pilih tipe pertemuan", Toast.LENGTH_SHORT).show()
                return false
            }

            if (dateInput.isEmpty()) {
                Toast.makeText(this@AppointmentActivity, "Pilih tanggal", Toast.LENGTH_SHORT).show()
                return false
            }

            if (timeInput.isEmpty()) {
                Toast.makeText(this@AppointmentActivity, "Pilih waktu", Toast.LENGTH_SHORT).show()
                return false
            }

            if (tipePertemuan == "Offline" && lokasiEdt.text.toString().isEmpty()) {
                Toast.makeText(this@AppointmentActivity, "Masukkan lokasi pertemuan", Toast.LENGTH_SHORT).show()
                return false
            }

            return true
        }
    }


}

class DatePicker : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)  //datepicker belum dikasih  variable calender year month day

        return DatePickerDialog(
            requireActivity(),
            activity as DatePickerDialog.OnDateSetListener,
            year,
            month,
            day
        )

    }
}

class TimePicker : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(
            requireActivity(),
            activity as TimePickerDialog.OnTimeSetListener,
            hour,
            minute,
            DateFormat.is24HourFormat(requireActivity())
        )
    }
}


class DialogExit : DialogFragment() {

    interface DialogListener {
        fun onDialogResult(result: Boolean)
    }
    private lateinit var listener: DialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as DialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement DialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val binding = DialogExitBinding.inflate(inflater)

        with(binding) {
            yesBtn.setOnClickListener {
                listener.onDialogResult(true)
                dismiss()
            }
            noBtn.setOnClickListener {
                listener.onDialogResult(false)
                dismiss()
            }
        }
        builder.setView(binding.root)
        return builder.create()
    }
}
