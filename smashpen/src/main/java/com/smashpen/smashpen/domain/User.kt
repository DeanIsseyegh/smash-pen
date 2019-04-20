package com.smashpen.smashpen.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.HashSet
import javax.persistence.*

@Entity
class User(private var enabled: Boolean?, val username: String?, @JsonIgnore private val password: String?) {

    @OneToMany private var characterNotes: MutableSet<CharacterNotes> = HashSet()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1

}
