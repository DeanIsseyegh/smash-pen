package com.smashpen.smashpen

class UserNotFoundException(userId: Long?) : Exception("Could not find user with id " + userId!!)
