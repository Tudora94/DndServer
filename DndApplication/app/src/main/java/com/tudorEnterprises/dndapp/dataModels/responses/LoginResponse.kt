package com.tudorEnterprises.dndapp.dataModels.responses

@Suppress("unused")
class LoginResponse(val success: Boolean, val token: String?, val message: String, val refreshToken: String?, val user: Int?)