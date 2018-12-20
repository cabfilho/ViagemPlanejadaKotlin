package com.example.beto.viagemplanejada;

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

import java.util.List;

public class ViagemAdapter extends RecyclerView.Adapter {

    List<DataSnapshot> items;

    public ViagemAdapter(List<DataSnapshot> viagens) {
        items = viagens;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.view_holder_layout, parent, false);
        return new ViagemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Publicacao publicacao = items.get(position).getValue(Publicacao.class);

        ViagemViewHolder viagemViewHolder = (ViagemViewHolder) holder;

        viagemViewHolder.paisTextView.setText(publicacao.getPais());
        viagemViewHolder.dtTextView.setText(publicacao.getDtViagem().toString());

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


    public class ViagemViewHolder extends RecyclerView.ViewHolder{

        public TextView paisTextView;
        public TextView dtTextView;
        public ImageButton deleteButton;

        public ViagemViewHolder(@NonNull View itemView) {
            super(itemView);

            paisTextView = itemView.findViewById(R.id.txtPais);
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
