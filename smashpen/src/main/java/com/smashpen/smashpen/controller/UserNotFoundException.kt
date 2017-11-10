package com.smashpen.smashpen.controller

class UserNotFoundException(userId: Long?) : Exception("Could not find user with id " + userId!!)
