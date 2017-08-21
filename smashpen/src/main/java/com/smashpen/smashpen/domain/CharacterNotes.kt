package com.smashpen.smashpen.domain

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.*

@Entity
class CharacterNotes {

    @JsonIgnore
    @ManyToOne private lateinit var user: User
    @Id
    @GeneratedValue
    val id: Long? = null
    @ManyToOne
    var smashCharacter: SmashCharacter? = null
    var notes: String = ""

    internal constructor() {}

    constructor(user: User, smashCharacter: SmashCharacter, notes: String) {
        this.smashCharacter = smashCharacter
        this.notes = notes
        this.user = user
    }
}
