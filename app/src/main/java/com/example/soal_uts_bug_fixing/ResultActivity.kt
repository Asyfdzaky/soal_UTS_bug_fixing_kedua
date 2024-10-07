package com.example.soal_uts_bug_fixing

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.soal_uts_bug_fixing.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // menerima data data belum ada
        val nama = intent.getStringExtra(FormActivity.EXTRA_NAMA)
        val identitas = intent.getStringExtra(FormActivity.EXTRA_IDENTITAS)
        val gender = intent.getStringExtra(FormActivity.EXTRA_GENDER)
        val tipe = intent.getStringExtra(AppointmentActivity.EXTRA_TIPE)
        val alamat = intent.getStringExtra(AppointmentActivity.EXTRA_ALAMAT)
        val tanggal = intent.getStringExtra(AppointmentActivity.EXTRA_TANGGAL)
        val waktu = intent.getStringExtra(AppointmentActivity.EXTRA_WAKTU)


        with(binding) {
            namaTxt.text = nama
            identitasTxt.text = identitas
            genderTxt.text = gender
            tipeTxt.text = tipe
            tanggalTxt.text = tanggal
            waktuTxt.text = waktu

            if (tipe == "Offline") {
                lokasiTitle.visibility = View.VISIBLE
                lokasiTxt.visibility = View.VISIBLE
                lokasiTxt.text = alamat
            }


            backBtn.setOnClickListener {
                val intentToHome = Intent(this@ResultActivity, MainActivity::class.java)
                startActivity(intentToHome)
            }
        }
    }
}
