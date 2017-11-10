package com.smashpen.smashpen.domain

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
class CharacterNotes {

    @JsonIgnore
    @ManyToOne
    var user: User

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1

    @ManyToOne
    var smashCharacter: SmashCharacter? = null

    var notes: String = ""

    constructor(user: User, smashCharacter: SmashCharacter, notes: String) {
        this.smashCharacter = smashCharacter
        this.notes = notes
        this.user = user
    }

}
