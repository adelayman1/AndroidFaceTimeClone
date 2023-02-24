package com.example.facetimeclonecompose.domain.utilities

class UserNotFoundException(): Exception("User Not Found")
class UserLoggedInException: Exception("User Is Already Logged In")
class UserNotVerifiedException(): Exception("User Isn't Verified")
class UserVerifiedException(): Exception("User Is Already Verified")
class InvalidInputTextException(errorMsg:String): Exception(errorMsg)
