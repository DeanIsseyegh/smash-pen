package com.smashpen.smashpen.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.GeneratedValue
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import java.util.HashSet

@Entity
class User {

    @OneToMany private var characterNotes: MutableSet<CharacterNotes> = HashSet()
    @Id
    @GeneratedValue
    val id: Long? = null
    @JsonIgnore
    var password: String? = null
    var username: String? = null
    var enabled: Boolean? = null

    constructor(enabled: Boolean?, name: String?, password: String?) {
        this.enabled = enabled
        this.username = name
        this.password = password
    }

    internal constructor() {}

}