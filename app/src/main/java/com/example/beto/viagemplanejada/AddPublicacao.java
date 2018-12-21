package com.example.beto.viagemplanejada;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPublicacao extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    FirebaseDatabase firebaseDatabase;

    EditText dtViagem ;
    EditText cidade;
    EditText pais;
    RatingBar ratingBar;
    Spinner spinner;
    String id;
    List<Pais> paises;
    String paisSelected;
    Publicacao publicacao = new Publicacao();
    List<String> paisesTraduzidos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publicacao);
        firebaseDatabase = FirebaseDatabase.getInstance();
        Call<List<Pais>> call;
        id = "";
        dtViagem = findViewById(R.id.editDtViagem);
        cidade = findViewById(R.id.editCidade);
        pais = findViewById(R.id.editPais);
        ratingBar =findViewById(R.id.ratingBar);


        spinner = (Spinner) findViewById(R.id.spinner);


        Bundle extras = getIntent().getExtras();

        if(extras != null){
             id = (String) extras.get("id");
        }

        if(StringUtils.isNotBlank(id)) {
            DatabaseReference databaseRef = firebaseDatabase.getReference().child("Publicacao").child(id);
            databaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        publicacao = dataSnapshot.getValue(Publicacao.class);
                        pais.setText(publicacao.getPais());
                        cidade.setText(publicacao.getCidade());
                        dtViagem.setText(publicacao.getDtViagem().toString());
                        ratingBar.setRating(publicacao.getRating());

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        try {

            call = new RetrofitConfig().getPaisService().buscarPais();
            call.enqueue(new Callback<List<Pais>>() {
                @Override
                public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {

                   paises =  response.body();




                    for(Pais paisTraduzido : paises){

                        paisesTraduzidos.add(paisTraduzido.getTranslations().getBr());

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(),
                            android.R.layout.simple_spinner_item,
                            paisesTraduzidos);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setOnItemSelectedListener(AddPublicacao.this);
                    adapter.getAutofillOptions("teste");

                    spinner.setAdapter(adapter);



                }

                @Override
                public void onFailure(Call<List<Pais>> call, Throwable t) {
                    Log.e("PaisService   ", "Erro ao buscar o pais:" + t.getMessage());
                }
            });
        }catch (Exception e){
            Log.e("RetrofitConfig", e.getMessage());
        }







    }

    public void savePublicacao(View view) throws ParseException {

        ratingBar.getRating();
        Boolean bTemErro = false;
        if(StringUtils.isBlank(pais.getText().toString())) {
            pais.setError("País é um campo obrigatório");
            bTemErro = true;
        }
        if(StringUtils.isBlank(dtViagem.getText().toString())) {
            dtViagem.setError("Data da viagem é um campo obrigatório");
            bTemErro = true;
        }
        if(StringUtils.isBlank(cidade.getText().toString())) {
            cidade.setError("Cidade da viagem é um campo obrigatório");
            bTemErro = true;
        }
        if(!bTemErro) {
            Date dtConvert = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dtConvert = dateFormat.parse(dtViagem.getText().toString());

            DatabaseReference databaseRef = firebaseDatabase.getReference();

            Publicacao publicacao = new Publicacao(paisSelected,
                    cidade.getText().toString(),
                    dtConvert,
                    new Date(), ratingBar.getRating());

            if(StringUtils.isBlank(id)){
                databaseRef.child("Publicacao").push().setValue(publicacao);

            }else{
                databaseRef.child("Publicacao").child(id).setValue(publicacao);
            }
            finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        paisSelected = (String) adapterView.getItemAtPosition(i);


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
