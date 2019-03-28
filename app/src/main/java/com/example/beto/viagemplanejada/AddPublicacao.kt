package com.example.beto.viagemplanejada


import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import com.example.beto.viagemplanejada.model.Pais
import com.example.beto.viagemplanejada.model.Publicacao
import com.example.beto.viagemplanejada.services.RetrofitConfig

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPublicacao(): AppCompatActivity(), AdapterView.OnItemSelectedListener {
    internal lateinit var firebaseDatabase: FirebaseDatabase

    private var dtViagem: EditText? = null
    private var cidade: EditText? = null

    internal lateinit var ratingBar: RatingBar
    internal lateinit var spinner: Spinner
    internal lateinit var id: String
    internal var paises: List<Pais>? = null
    internal lateinit var paisSelected: String
    internal var publicacao: Publicacao? = Publicacao()
    internal var paisesTraduzidos: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val extras = intent.extras
        setContentView(R.layout.activity_add_publicacao)
        firebaseDatabase = FirebaseDatabase.getInstance()
        val call: Call<List<Pais>>
        id = ""
        dtViagem = findViewById(R.id.editDtViagem)
        cidade = findViewById(R.id.editCidade)
        dtViagem!!.addTextChangedListener(MaskEditUtil.mask(dtViagem!!, MaskEditUtil.FORMAT_DATE))



        spinner = findViewById<View>(R.id.spinner) as Spinner




        if (extras != null) {
            id = extras.get("id") as String
        }

        if (id.isNotBlank()) {
            val databaseRef = firebaseDatabase.reference.child("Publicacao").child(id)
            databaseRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        publicacao = dataSnapshot.getValue<Publicacao>(Publicacao::class.java!!)

                        cidade!!.setText(publicacao!!.cidade)
                        val dtConvert: String
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                        dtConvert = dateFormat.format(publicacao!!.dtViagem)
                        dtViagem!!.setText(dtConvert)
                        ratingBar.rating = publicacao!!.rating

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })

        }

        try {

            call = RetrofitConfig.paisService.buscarPais()
            call.enqueue(object : Callback<List<Pais>> {
                override fun onResponse(call: Call<List<Pais>>, response: Response<List<Pais>>) {

                    paises = response.body()




                    for (paisTraduzido in paises!!) {

                        paisesTraduzidos.add(paisTraduzido.translations!!.br!!)

                    }

                    val adapter = ArrayAdapter(getApplication(),
                            android.R.layout.simple_spinner_item,
                            paisesTraduzidos)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.onItemSelectedListener = this@AddPublicacao
                    val spinnerPostion = adapter.getPosition(publicacao!!.pais)

                    spinner.adapter = adapter
                    spinner.setSelection(spinnerPostion)


                }

                override fun onFailure(call: Call<List<Pais>>, t: Throwable) {
                    Log.e("PaisService   ", "Erro ao buscar o pais:" + t.message)
                }
            })
        } catch (e: Exception) {
            Log.e("RetrofitConfig", e.message)
        }


    }

    @Throws(ParseException::class)
    fun savePublicacao(view: View) {

        ratingBar.rating
        var bTemErro: Boolean = false

        if (dtViagem!!.text.toString().isBlank()) {
            dtViagem!!.error = "Data da viagem é um campo obrigatório"
            bTemErro = true
        }
        if (cidade!!.text.toString().isBlank()) {
            cidade!!.error = "Cidade da viagem é um campo obrigatório"
            bTemErro = true
        }

        if ((!bTemErro)) {
            var dtConvert = Date()
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            dtConvert = dateFormat.parse(dtViagem!!.text.toString())

            val databaseRef = firebaseDatabase.reference

            val publicacao = Publicacao(paisSelected,
                    cidade!!.text.toString(),
                    dtConvert,
                    Date(), ratingBar.rating)

            if (id.isBlank()) {
                databaseRef.child("Publicacao").push().setValue(publicacao)

            } else {
                databaseRef.child("Publicacao").child(id).setValue(publicacao)
            }
            finish()
        }
    }

    override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
        paisSelected = adapterView.getItemAtPosition(i) as String


    }

    override fun onNothingSelected(adapterView: AdapterView<*>) {


    }

}
