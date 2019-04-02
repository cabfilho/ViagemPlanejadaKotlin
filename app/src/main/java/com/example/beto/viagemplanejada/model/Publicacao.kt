package com.example.beto.viagemplanejada.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Publicacao {
    var pais: String? = null
    var cidade: String? = null
    var dtViagem: String? = null
    var dtPublicacao: String? = null
    var rating: Float = 0.toFloat()
    @PrimaryKey var id:String=""


    constructor() {}


}
