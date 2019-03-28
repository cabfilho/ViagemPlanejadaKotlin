package com.example.beto.viagemplanejada.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
@Entity
class Publicacao {
    var pais: String? = null
    var cidade: String? = null
    var dtViagem: Date? = null
    var dtPublicacao: Date? = null
    var rating: Float = 0.toFloat()
    @PrimaryKey var id:String=""


    constructor() {}


    constructor(pais: String, cidade: String, dtViagem: Date, dtPublicacao: Date, rating: Float) {
        this.pais = pais
        this.cidade = cidade
        this.dtViagem = dtViagem
        this.dtPublicacao = dtPublicacao
        this.rating = rating
    }
}
