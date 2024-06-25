# FaceTimeClone :video_camera: 

<a href="https://www.linkedin.com/in/adel-ayman-2497ab1b3/">
    <img src="https://img.shields.io/badge/LinkedIn-blue?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn Badge"/>
  </a>
  
[![Support Palestine](https://raw.githubusercontent.com/Ademking/Support-Palestine/main/Support-Palestine.svg)](https://www.map.org.uk)
[![StandWithPalestine](https://raw.githubusercontent.com/Safouene1/support-palestine-banner/master/StandWithPalestine.svg)](https://github.com/Safouene1/support-palestine-banner/Markdown-pages/Support.md)

This repository contains the **FaceTimeClone** app, a real-time video calling application built with Kotlin and Jetpack Compose. The app follows best practices and employs SOLID principles and clean code techniques.it implements Coroutines,Ktor-client for HTTP requests,MVVM architecture,Clean architecture,Hilt,Navigation component,Jitsi Meet and more...



![](https://github.com/adelayman1/AndroidFaceTimeClone/blob/master/images/screen1.png)

## App Overview

<img src="https://github.com/adelayman1/AndroidFaceTimeClone/blob/master/images/screen0.png" width="700" />

<img src="https://github.com/adelayman1/AndroidFaceTimeClone/blob/master/images/screen2.png" width="700" />
<img src="https://github.com/adelayman1/AndroidFaceTimeClone/blob/master/images/screen3.png" width="700" />
<img src="https://github.com/adelayman1/AndroidFaceTimeClone/blob/master/images/screen4.png" width="700" />


## Built With 🛠

*  [Kotlin](https://kotlinlang.org/)
*  [Jetpack compose](https://developer.android.com/develop/ui/compose)
*  [Coroutines](https://developer.android.com/kotlin/coroutines)
*  [StateFlow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/) 
*  MVVM architecture
*  Clean architecture
*  SOLID principles
*  [Ktor-Client](https://ktor.io/docs/client-create-multiplatform-application.html)
*  [Navigation component](https://developer.android.com/guide/navigation)
*  [Jitsi Meet](https://jitsi.github.io/handbook/docs/intro/)
*  [Hilt](https://developer.android.com/training/dependency-injection/hilt-jetpack) 
*  [Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging/)
*  [Serialization](https://kotlinlang.org/docs/serialization.html/)
*  Repository pattern


## Server Side API

For the server-side implementation of the FaceTimeClone app, you can find the API built with Ktor here:
[FaceTimeCloneAPI](https://github.com/adelayman1/FaceTimeCloneAPI): You can find docs [here](https://face-time-api-nextra-documentation.vercel.app).


## Project Structure

```
facetimeclonecompose
 ┃ ┃ ┃ ┣ data
 ┃ ┃ ┃ ┃ ┣ di
 ┃ ┃ ┃ ┃ ┃ ┗ modules
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ CoroutinesModule.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ NetworkModule.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ RepositoryModule.kt
 ┃ ┃ ┃ ┃ ┣ repositories
 ┃ ┃ ┃ ┃ ┃ ┣ RoomRepositoryImpl.kt
 ┃ ┃ ┃ ┃ ┃ ┗ UserRepositoryImpl.kt
 ┃ ┃ ┃ ┃ ┣ sources
 ┃ ┃ ┃ ┃ ┃ ┣ local
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ dataSources
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ UserLocalDataSource.kt
 ┃ ┃ ┃ ┃ ┃ ┗ remote
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ dataSources
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ RoomsRemoteDataSource.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ UserRemoteDataSource.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ requestModels
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CreateRoomRequestModel.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ EditFcmTokenRequestModel.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ GetUserProfileByEmailRequestModel.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ GetUserProfileByIdRequestModel.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ JoinRoomRequestModel.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ LoginRequestModel.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ RegisterRequestModel.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ RoomTypeEnumRequestModel.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ responseModels
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ BaseApiResponse.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ParticipantResponseModel.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ RoomResponseModel.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ RoomTypeResponseModel.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ UserResponseModel.kt
 ┃ ┃ ┃ ┃ ┗ utilities
 ┃ ┃ ┃ ┃ ┃ ┣ ApiExtensions.kt
 ┃ ┃ ┃ ┃ ┃ ┣ Constants.kt
 ┃ ┃ ┃ ┃ ┃ ┗ PreferenceDataStoreHelper.kt
 ┃ ┃ ┃ ┣ domain
 ┃ ┃ ┃ ┃ ┣ models
 ┃ ┃ ┃ ┃ ┃ ┣ ParticipantModel.kt
 ┃ ┃ ┃ ┃ ┃ ┣ RoomModel.kt
 ┃ ┃ ┃ ┃ ┃ ┣ RoomTypeModel.kt
 ┃ ┃ ┃ ┃ ┃ ┣ UserModel.kt
 ┃ ┃ ┃ ┃ ┃ ┗ ValidateResult.kt
 ┃ ┃ ┃ ┃ ┣ repositories
 ┃ ┃ ┃ ┃ ┃ ┣ RoomRepository.kt
 ┃ ┃ ┃ ┃ ┃ ┗ UserRepository.kt
 ┃ ┃ ┃ ┃ ┣ usecases
 ┃ ┃ ┃ ┃ ┃ ┣ CheckIsAccountValidUseCase.kt
 ┃ ┃ ┃ ┃ ┃ ┣ CreateAccountUseCase.kt
 ┃ ┃ ┃ ┃ ┃ ┣ CreateAudioRoomUseCase.kt
 ┃ ┃ ┃ ┃ ┃ ┣ CreateLinkRoomUseCase.kt
 ┃ ┃ ┃ ┃ ┃ ┣ CreateVideoRoomUseCase.kt
 ┃ ┃ ┃ ┃ ┃ ┣ DeleteUserAccountUseCase.kt
 ┃ ┃ ┃ ┃ ┃ ┣ GetRoomInfoUseCase.kt
 ┃ ┃ ┃ ┃ ┃ ┣ GetUserProfileUseCase.kt
 ┃ ┃ ┃ ┃ ┃ ┣ GetUserRoomsUseCase.kt
 ┃ ┃ ┃ ┃ ┃ ┣ JoinRoomUseCase.kt
 ┃ ┃ ┃ ┃ ┃ ┣ LaunchJitsiMeetingUseCase.kt
 ┃ ┃ ┃ ┃ ┃ ┣ LoginUseCase.kt
 ┃ ┃ ┃ ┃ ┃ ┣ SendVerificationEmailUseCase.kt
 ┃ ┃ ┃ ┃ ┃ ┣ UpdateUserFcmTokenUseCase.kt
 ┃ ┃ ┃ ┃ ┃ ┣ ValidateConfirmPasswordUseCase.kt
 ┃ ┃ ┃ ┃ ┃ ┣ ValidateEmailUseCase.kt
 ┃ ┃ ┃ ┃ ┃ ┣ ValidateOtpCodeUseCase.kt
 ┃ ┃ ┃ ┃ ┃ ┣ ValidatePasswordUseCase.kt
 ┃ ┃ ┃ ┃ ┃ ┣ ValidateUserNameUseCase.kt
 ┃ ┃ ┃ ┃ ┃ ┗ VerifyOtpCodeUseCase.kt
 ┃ ┃ ┃ ┃ ┗ utilities
 ┃ ┃ ┃ ┃ ┃ ┣ Constants.kt
 ┃ ┃ ┃ ┃ ┃ ┗ Exceptions.kt
 ┃ ┃ ┃ ┣ framework
 ┃ ┃ ┃ ┃ ┗ services
 ┃ ┃ ┃ ┃ ┃ ┣ Constants.kt
 ┃ ┃ ┃ ┃ ┃ ┗ FCMService.kt
 ┃ ┃ ┃ ┣ presentation
 ┃ ┃ ┃ ┃ ┣ createRoomScreen
 ┃ ┃ ┃ ┃ ┃ ┣ components
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ InputFieldChips.kt
 ┃ ┃ ┃ ┃ ┃ ┣ uiStates
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ NewRoomUiEvent.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ NewRoomUiState.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ ParticipantUserUiState.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ ParticipantsInputFieldUiState.kt
 ┃ ┃ ┃ ┃ ┃ ┣ CreateRoomScreen.kt
 ┃ ┃ ┃ ┃ ┃ ┗ CreateRoomViewModel.kt
 ┃ ┃ ┃ ┃ ┣ homeScreen
 ┃ ┃ ┃ ┃ ┃ ┣ components
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ DefaultCard.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ RoomCard.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ RoomListItem.kt
 ┃ ┃ ┃ ┃ ┃ ┣ uiStates
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ HomeUiEvent.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ ItemPosition.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ RoomItemUiState.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ RoomsUiState.kt
 ┃ ┃ ┃ ┃ ┃ ┣ HomeScreen.kt
 ┃ ┃ ┃ ┃ ┃ ┗ HomeViewModel.kt
 ┃ ┃ ┃ ┃ ┣ incomingCallScreen
 ┃ ┃ ┃ ┃ ┃ ┣ IncomingCallScreen.kt
 ┃ ┃ ┃ ┃ ┃ ┗ IncomingCallViewModel.kt
 ┃ ┃ ┃ ┃ ┣ loginScreen
 ┃ ┃ ┃ ┃ ┃ ┣ components
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ CenterLoadingBar.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ TransparentInputField.kt
 ┃ ┃ ┃ ┃ ┃ ┣ uiStates
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ InputFieldUiState.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ LoginUiEvent.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ LoginUiState.kt
 ┃ ┃ ┃ ┃ ┃ ┣ LoginScreen.kt
 ┃ ┃ ┃ ┃ ┃ ┗ LoginViewModel.kt
 ┃ ┃ ┃ ┃ ┣ mappers
 ┃ ┃ ┃ ┃ ┃ ┗ RoomToUiStateMapper.kt
 ┃ ┃ ┃ ┃ ┣ otpScreen
 ┃ ┃ ┃ ┃ ┃ ┣ components
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ OtpCodeView.kt
 ┃ ┃ ┃ ┃ ┃ ┣ uiStates
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ OtpUiEvent.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ OtpUiState.kt
 ┃ ┃ ┃ ┃ ┃ ┣ OtpCodeScreen.kt
 ┃ ┃ ┃ ┃ ┃ ┗ OtpVIewModel.kt
 ┃ ┃ ┃ ┃ ┣ registerScreen
 ┃ ┃ ┃ ┃ ┃ ┣ uiStates
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ RegisterUiEvent.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ RegisterUiState.kt
 ┃ ┃ ┃ ┃ ┃ ┣ RegisterScreen.kt
 ┃ ┃ ┃ ┃ ┃ ┗ RegisterViewModel.kt
 ┃ ┃ ┃ ┃ ┣ splashScreen
 ┃ ┃ ┃ ┃ ┃ ┣ uiStates
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ SplashUiState.kt
 ┃ ┃ ┃ ┃ ┃ ┗ SplashViewModel.kt
 ┃ ┃ ┃ ┃ ┣ ui
 ┃ ┃ ┃ ┃ ┃ ┗ theme
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ Color.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ Theme.kt
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ Type.kt
 ┃ ┃ ┃ ┃ ┗ utilities
 ┃ ┃ ┃ ┃ ┃ ┣ Constants.kt
 ┃ ┃ ┃ ┃ ┃ ┣ DateAndTimeUtils.kt
 ┃ ┃ ┃ ┃ ┃ ┣ RoomItemPositionUtil.kt
 ┃ ┃ ┃ ┃ ┃ ┗ Screen.kt
 ┃ ┃ ┃ ┣ FaceTimeApp.kt
 ┃ ┃ ┃ ┗ MainActivity.kt
```

## Future Enhancements

- Implement an Edit button mechanism
- Develop a room information screen


## LICENSE
``` MIT License

Copyright (c) 2022 adelayman1

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.```
