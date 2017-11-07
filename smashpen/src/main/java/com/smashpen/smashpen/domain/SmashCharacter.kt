package com.smashpen.smashpen.domain

import javax.persistence.*

@Entity
class SmashCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1

    var name: String? = null

    constructor(name: String) {
        this.name = name.toLowerCase()
    }
}
