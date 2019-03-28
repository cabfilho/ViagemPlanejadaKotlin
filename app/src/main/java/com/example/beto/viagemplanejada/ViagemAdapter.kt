package com.example.beto.viagemplanejada

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beto.viagemplanejada.model.Publicacao

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

import java.text.SimpleDateFormat

class ViagemAdapter(internal var items: MutableList<DataSnapshot>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_layout, parent, false)
        context = parent.context
        return ViagemViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val publicacao = items[position].getValue<Publicacao>(Publicacao::class.java!!)

        val viagemViewHolder = holder as ViagemViewHolder

        viagemViewHolder.paisTextView.setText(publicacao!!.pais)

        val dtConvert = publicacao!!.dtViagem
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")

        viagemViewHolder.dtTextView.text = dateFormat.format(dtConvert)
        viagemViewHolder.itemView.setOnClickListener {
            val id = items[position].key
            val intent = Intent(context, AddPublicacao::class.java)
            intent.putExtra("id", id)
            context!!.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun addItem(novaPublicacao: DataSnapshot) {
        items.add(novaPublicacao)

        notifyItemInserted(items.size - 1)
    }


    fun changeItem(changed: DataSnapshot) {
        for (i in items.indices) {
            if (changed.key == items[i].key) {
                items[i] = changed
                notifyItemChanged(i)
            }
        }
    }

    fun removeItem(removed: DataSnapshot) {
        for (i in items.indices) {
            if (removed.key == items[i].key) {
                items.removeAt(i)
                notifyItemRemoved(i)
            }
        }
    }

    inner class ViagemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var paisTextView: TextView
        var dtTextView: TextView
        var deleteButton: ImageButton

        init {

            paisTextView = itemView.findViewById(R.id.lblPais)
            dtTextView = itemView.findViewById(R.id.txtDtViagem)
            deleteButton = itemView.findViewById(R.id.imgDeleteBtn)



            deleteButton.setOnClickListener {
                val index = adapterPosition
                val idToRemove = items[index].key

                val ref = FirebaseDatabase
                        .getInstance()
                        .getReference("Publicacao")
                        .child(idToRemove!!)
                ref.removeValue()
            }
        }
    }
}
