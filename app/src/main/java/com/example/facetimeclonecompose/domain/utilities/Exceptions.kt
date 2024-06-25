package com.example.facetimeclonecompose.domain.utilities

class UserNotFoundException(): Exception("User Not Found")
class UserLoggedInException: Exception("User Is Already Logged In")
class UserNotVerifiedException(): Exception("User Isn't Verified")
class UserVerifiedException(): Exception("User Is Already Verified")
open class InvalidInputTextException(errorMsg:String): Exception(errorMsg)
class InvalidEmailException(error:String): InvalidInputTextException(error)
class InvalidPasswordException(error:String): InvalidInputTextException(error)
class InvalidNameException(error:String): InvalidInputTextException(error)
class InvalidConfirmPasswordException(error:String): InvalidInputTextException(error)
class InvalidOtpException(error:String): InvalidInputTextException(error)
class AccessDeniedException:Exception("User Don't Have Access")
class RoomNotFoundException:Exception("Room Not Found")