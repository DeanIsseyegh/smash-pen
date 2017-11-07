package com.smashpen.smashpen.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.HashSet
import javax.persistence.*

@Entity
class User {

    @OneToMany private var characterNotes: MutableSet<CharacterNotes> = HashSet()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1

    @JsonIgnore
    private var password: String? = null
    private var username: String? = null
    private var enabled: Boolean? = null

    constructor(enabled: Boolean?, name: String?, password: String?) {
        this.enabled = enabled
        this.username = name
        this.password = password
    }

}