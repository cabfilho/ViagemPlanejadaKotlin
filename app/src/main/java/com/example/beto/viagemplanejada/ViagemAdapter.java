package com.example.beto.viagemplanejada;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ViagemAdapter extends RecyclerView.Adapter {

    List<DataSnapshot> items;
    private Context context;

    public ViagemAdapter(List<DataSnapshot> viagens) {
        items = viagens;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.view_holder_layout, parent, false);
        context = parent.getContext();
        return new ViagemViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Publicacao publicacao = items.get(position).getValue(Publicacao.class);

        ViagemViewHolder viagemViewHolder = (ViagemViewHolder) holder;

        viagemViewHolder.paisTextView.setText(publicacao.getPais());

        Date dtConvert = publicacao.getDtViagem();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        viagemViewHolder.dtTextView.setText(dateFormat.format(dtConvert));
        viagemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = items.get(position).getKey();
                Intent intent = new Intent(context, AddPublicacao.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return items.size();
    }




    public void addItem(DataSnapshot novaPublicacao){
        items.add(novaPublicacao);

        notifyItemInserted(items.size()-1);
    }


    public void changeItem(DataSnapshot changed){
        for (int i = 0; i < items.size(); i++) {
            if (changed.getKey().equals(items.get(i).getKey())){
                items.set(i, changed);
                notifyItemChanged(i);
            }
        }
    }

    public void removeItem(DataSnapshot removed){
        for (int i = 0; i < items.size(); i++) {
            if (removed.getKey().equals(items.get(i).getKey())){
                items.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    public String recuperaId(int index){

        String id = items.get(index).getKey();
        return id;
    }
    public class ViagemViewHolder extends RecyclerView.ViewHolder{

        public TextView paisTextView;
        public TextView dtTextView;
        public ImageButton deleteButton;

        public ViagemViewHolder(@NonNull View itemView) {
            super(itemView);

            paisTextView = itemView.findViewById(R.id.lblPais);
            dtTextView = itemView.findViewById(R.id.txtDtViagem);
            deleteButton = itemView.findViewById(R.id.imgDeleteBtn);



            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    String idToRemove = items.get(index).getKey();

                    DatabaseReference ref = FirebaseDatabase
                            .getInstance()
                            .getReference("Publicacao")
                            .child(idToRemove);
                    ref.removeValue();
                }
            });
        }
    }
}
