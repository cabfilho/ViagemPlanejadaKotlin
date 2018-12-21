package com.example.beto.viagemplanejada;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    RecyclerView viagemRecyclerView;
    DatabaseReference databaseRef;
    ChildEventListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viagemRecyclerView = findViewById(R.id.viagemRecyclerView);
        progressBar = findViewById(R.id.progressBar);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseRef = firebaseDatabase.getReference("Publicacao");
        final ViagemAdapter adapter = new ViagemAdapter(new ArrayList<DataSnapshot>());
        viagemRecyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getApplicationContext());
        viagemRecyclerView.setLayoutManager(lm);

        viagemRecyclerView.addItemDecoration(
                new DividerItemDecoration(getApplicationContext(),
                        DividerItemDecoration.VERTICAL));

        progressBar.setVisibility(View.VISIBLE);
        Context context = this;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = null;
        if ( cm != null ) {
             ni = cm.getActiveNetworkInfo();

        }
        if(ni != null && ni.isConnected()){
            databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null && dataSnapshot.exists()){

                    } else {
                        Snackbar.make(findViewById(R.id.root),
                                "Insira uma publicação",
                                Snackbar.LENGTH_LONG).show();
                    }

                    progressBar.setVisibility(View.GONE);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            listener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ViagemAdapter adapter = (ViagemAdapter) viagemRecyclerView.getAdapter();
                    adapter.addItem(dataSnapshot);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ViagemAdapter adapter = (ViagemAdapter) viagemRecyclerView.getAdapter();
                    adapter.changeItem(dataSnapshot);
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    ViagemAdapter adapter = (ViagemAdapter) viagemRecyclerView.getAdapter();
                    adapter.removeItem(dataSnapshot);
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            databaseRef.addChildEventListener(listener);
        }else{
            Snackbar.make(findViewById(R.id.root),
                    "Conecte a Internet",
                    Snackbar.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);

        }

    }
    public void addPublicacao(View view){
        Intent intent = new Intent(this, AddPublicacao.class);
        startActivity(intent);
    }
}
