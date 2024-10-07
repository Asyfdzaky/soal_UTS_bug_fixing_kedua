package com.example.soal_uts_bug_fixing

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.soal_uts_bug_fixing.databinding.ActivityFormBinding

class FormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormBinding
    companion object { //belum ada companion objectnya jadi variable tidak diketahui
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_IDENTITAS = "extra_identitas"
        const val EXTRA_GENDER = "extra_gender"
        const val EXTRA_KELUHAN = "extra_keluhan"
    }

    private lateinit var gendersArray : Array<String>
    private lateinit var genderInput : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gendersArray = resources.getStringArray(R.array.gender_descriptions) // array belum dipanggil


        with(binding){
            val adapterGenders = ArrayAdapter(this@FormActivity, R.layout.custom_spinner, gendersArray)
            adapterGenders.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            //

            genderSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View, position: Int, id: Long
                    ) {
                       genderInput = gendersArray[position]
                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {
                    }
                }

            submitBtn.setOnClickListener(){
                if(fieldNotEmpty()){
                    val intentToAppointment = Intent(this@FormActivity, AppointmentActivity::class.java)
                    intentToAppointment.putExtra(EXTRA_NAMA, namaEdt.text.toString())
                    intentToAppointment.putExtra(EXTRA_IDENTITAS, identitasEdt.text.toString())
                    intentToAppointment.putExtra(EXTRA_GENDER, genderInput)
                    intentToAppointment.putExtra(EXTRA_KELUHAN, keluhanEdt.text.toString())
                    //
                }else{
                    Toast.makeText(this@FormActivity, "MASIH ADA KOLOM YANG KOSONG", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    fun fieldNotEmpty(): Boolean {
        with(binding){
            if(namaEdt.text.toString()!="" && identitasEdt.text.toString()!="" && keluhanEdt.text.toString()!=""){
                return true
            }else{
                return false
            }
        }
    }
}