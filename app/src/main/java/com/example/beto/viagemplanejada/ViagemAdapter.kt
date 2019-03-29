package com.example.beto.viagemplanejada

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beto.viagemplanejada.database.FireBaseData
import com.example.beto.viagemplanejada.model.Publicacao

import com.google.firebase.database.DataSnapshot

import java.text.SimpleDateFormat

class ViagemAdapter(internal var items: MutableList<Publicacao>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var context: Context
    fun setData(list: MutableList<Publicacao>){
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_layout, parent, false)
        context = parent.context
        return ViagemViewHolder(itemView, context)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val publicacao = items[position]

        val viagemViewHolder = holder as ViagemViewHolder

        viagemViewHolder.paisTextView.setText(publicacao!!.pais)

        val dtConvert = publicacao!!.dtViagem
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")

        viagemViewHolder.dtTextView.text = dateFormat.format(dtConvert)
        viagemViewHolder.itemView.setOnClickListener {
            val id = items[position].id
            val intent = Intent(context, AddPublicacao::class.java)
            intent.putExtra("id", id)
            context!!.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun addItem(novaPublicacao: Publicacao) {
        items.add(novaPublicacao)

        notifyItemInserted(items.size - 1)
    }


    fun changeItem(changed: Publicacao) {
        for (i in items.indices) {
            if (changed.id == items[i].id) {
                items[i] = changed
                notifyItemChanged(i)
            }
        }
    }

    fun removeItem(removed: Publicacao) {
        for (i in items.indices) {
            if (removed.id == items[i].id) {
                items.removeAt(i)
                notifyItemRemoved(i)
            }
        }
    }

    inner class ViagemViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {

        var paisTextView: TextView
        var dtTextView: TextView
        var deleteButton: ImageButton

        init {

            paisTextView = itemView.findViewById(R.id.lblPais)
            dtTextView = itemView.findViewById(R.id.txtDtViagem)
            deleteButton = itemView.findViewById(R.id.imgDeleteBtn)



            deleteButton.setOnClickListener {
                val index = adapterPosition
                //val idToRemove = items[index].key

                val viagemRepository = ViagemRepository(context)
                viagemRepository.removePublicacao(items[index])
                items.removeAt(index)
                notifyItemRemoved(index)


            }
        }
    }
}
