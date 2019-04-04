package com.example.beto.viagemplanejada


import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.beto.viagemplanejada.model.Pais
import com.example.beto.viagemplanejada.model.Publicacao
import com.example.beto.viagemplanejada.services.RetrofitConfig

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_add_publicacao.*


import java.text.ParseException
import java.text.SimpleDateFormat


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPublicacao(): AppCompatActivity(), AdapterView.OnItemSelectedListener {
    internal lateinit var firebaseDatabase: FirebaseDatabase

    private var dtViagem: EditText? = null
    private var cidade: EditText? = null
    private lateinit var repository: ViagemRepository
    internal lateinit var ratingBarIncuir: RatingBar
    internal lateinit var spinner: Spinner
    internal lateinit var id: String
    internal var paises: List<Pais>? = null
    internal lateinit var paisSelected: String
    internal var publicacao: Publicacao? = Publicacao()
    internal var paisesTraduzidos: MutableList<String> = ArrayList()
    private lateinit var addPublicacaoViewModel: AddPublicacaoViewModel
    private lateinit var viagemRepository:ViagemRepository

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        addPublicacaoViewModel = ViewModelProviders.of(this)
                .get(AddPublicacaoViewModel::class.java)

        val extras = intent.extras
        setContentView(R.layout.activity_add_publicacao)
      //  firebaseDatabase = FirebaseDatabase.getInstance()
        val call: Call<List<Pais>>
        id = ""
        dtViagem = findViewById(R.id.editDtViagem)
        cidade = findViewById(R.id.editCidade)
        dtViagem!!.addTextChangedListener(MaskEditUtil.mask(dtViagem!!, MaskEditUtil.FORMAT_DATE))
        ratingBarIncuir =  ratingBar
        repository = ViagemRepository(context = applicationContext)
        spinner = findViewById<View>(R.id.spinner) as Spinner

        addPublicacaoViewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viagemRepository = ViagemRepository(this)

        if (extras != null) {
            id = extras.get("id") as String
        }

        if (id.isNotBlank()) {

            publicacao = viagemRepository.getPublicacao(id)
            if(publicacao!= null) {
                cidade!!.setText(publicacao!!.cidade)



                dtViagem!!.setText(publicacao!!.dtViagem)
                ratingBar.rating = publicacao!!.rating
            }


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

            //val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            //var dtConvert = dateFormat.parse(dtViagem.toString())

            val publicacao = Publicacao(paisSelected,
                    cidade!!.text.toString(),
                    dtViagem!!.text.toString(),
                    "", ratingBar.rating)


            if (id.isBlank()) {

                repository.insertPublicacao(publicacao, {
                   false
                    true
                }, {
                    false
                })


            }else{
                publicacao.id = id.toInt()
                repository.updatePublicacao(publicacao)

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
