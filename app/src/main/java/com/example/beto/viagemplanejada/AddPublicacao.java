package com.example.beto.viagemplanejada;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

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
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPublicacao extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    List<Pais> paises = new ArrayList<>();
    EditText dtViagem ;
    EditText cidade;
    EditText pais;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publicacao);
        firebaseDatabase = FirebaseDatabase.getInstance();
        dtViagem = findViewById(R.id.editDtViagem);
        cidade = findViewById(R.id.editCidade);
        pais = findViewById(R.id.editPais);
        ratingBar =findViewById(R.id.ratingBar);
        Bundle extras = getIntent().getExtras();
        String id = (String) extras.get("id");
        if(StringUtils.isNotBlank(id)) {
            DatabaseReference databaseRef = firebaseDatabase.getReference().child("Publicacao").child(id);
            databaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Publicacao publicacao = dataSnapshot.getValue(Publicacao.class);
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
            Call<List<Pais>> call = new RetrofitConfig().getPaisService().buscarPais();
            call.enqueue(new Callback<List<Pais>>() {
                @Override
                public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {

                     response.body();

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
        if(!bTemErro) {
            Date dtConvert = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dtConvert = dateFormat.parse(dtViagem.getText().toString());

            DatabaseReference databaseRef = firebaseDatabase.getReference();

            Publicacao publicacao = new Publicacao(pais.getText().toString(),
                    cidade.getText().toString(),
                    dtConvert,
                    new Date(), ratingBar.getRating());


            databaseRef.child("Publicacao").push().setValue(publicacao);


            finish();
        }
    }
}
