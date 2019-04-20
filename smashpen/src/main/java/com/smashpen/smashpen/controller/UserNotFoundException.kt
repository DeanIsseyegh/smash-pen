package com.smashpen.smashpen.controller

class UserNotFoundException: Exception {

    constructor(userId: Long): super("Could not find user with id $userId")

    constructor(username: String): super("Could not find user with username $username")

}
