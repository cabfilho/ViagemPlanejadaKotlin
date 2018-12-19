package com.example.beto.viagemplanejada;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPublicacao extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publicacao);
        firebaseDatabase = FirebaseDatabase.getInstance();

    }

    public void savePublicacao(View view){
        EditText dtViagem = findViewById(R.id.txtDtViagem);

        


        DatabaseReference databaseRef = firebaseDatabase.getReference();
//
//        Publicacao car = new Publicacao(dtViagem.getText().toString(),
//                modelEditText.getText().toString(),
//                Integer.parseInt(yearEditText.getText().toString()),
//                Double.parseDouble(priceEditText.getText().toString()));


        databaseRef.child("Publicacao").push().setValue(dtViagem);


        finish();

    }
}
