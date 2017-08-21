package com.smashpen.smashpen.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class SmashCharacter {

    @Id
    @GeneratedValue private val id: Long? = null

    @Column(unique = true)
    var name: String? = null

    internal constructor() {}

    constructor(name: String) {
        this.name = name.toLowerCase()
    }
}
