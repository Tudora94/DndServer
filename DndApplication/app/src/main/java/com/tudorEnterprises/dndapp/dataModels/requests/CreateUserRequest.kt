package com.tudorEnterprises.dndapp.dataModels.requests

data class CreateUserRequest(val email: String, val username: String, val password: String, val confirmPassword: String)