# FaceTimeClone :video_camera: 

<a href="https://www.linkedin.com/in/adel-ayman-2497ab1b3/">
    <img src="https://img.shields.io/badge/LinkedIn-blue?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn Badge"/>
  </a>
  
This repository contains a **FaceTimeClone app** that implements Coroutines , mvvm architecture , clean architecture , navigation component , hilt , etc.... using kotlin language


![](https://github.com/adelayman1/AndroidFaceTimeClone/blob/master/images/screen1.png)

## App Overview

<img src="https://github.com/adelayman1/AndroidFaceTimeClone/blob/master/images/screen0.png" width="700" />

<img src="https://github.com/adelayman1/AndroidFaceTimeClone/blob/master/images/screen2.png" width="700" />
<img src="https://github.com/adelayman1/AndroidFaceTimeClone/blob/master/images/screen3.png" width="700" />
<img src="https://github.com/adelayman1/AndroidFaceTimeClone/blob/master/images/screen4.png" width="700" />


## Built With 🛠

*  [Kotlin](https://kotlinlang.org/) 
*  [Coroutines](https://developer.android.com/kotlin/coroutines)
*  [LiveData](https://developer.android.com/topic/libraries/architecture/livedata/) 
*  mvvm architecture
*  Clean architecture
*  [Retrofit](https://square.github.io/retrofit/)
*  [Navigation component](https://developer.android.com/guide/navigation)
*  [JitsiMeet](https://jitsi.github.io/handbook/docs/intro/)
*  [Hilt](https://developer.android.com/training/dependency-injection/hilt-jetpack) 
*  [Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging/) 
*  [Firebase Realtime Database](https://firebase.google.com/docs/database/) 
*  Single activity concept 
*  Repository pattern
*  [ViewBinding](https://developer.android.com/topic/libraries/view-binding) 

## Project Structure


    faceTimeClone      
    ├── data                   
    │   ├── api              
    |   │   └── FcmApiService.kt                           
    |   |   
    │   ├── di                  
    |   │   └── module        
    |   |       ├──NetworkModule.kt         
    |   |       └──ApiModule.kt
    |   |
    │   ├── repository
    |   |   ├──UserRepositoryImpl.kt      
    |   |   └──RoomRepositoryImpl.kt
    |   |
    |   |
    |   ├── models             
    |   |   ├──ApiResponse.kt   
    |   |   ├──SurahDetailsModel.kt 
    |   |   ├──SurahModel.kt 
    |   |   └──VerseModel.kt 
    |   |
    |   |
    |   |── utils
    |   |   └──DateAndTimeUtils.kt 
    |
    |       
    |── domain 
    |   ├──enities
    |   |   ├──ErrorEnity.kt
    |   |   └──Result.kt
    |   |
    |   |──repository
    |   |   ├──UserRepository.kt      
    |   |   └──RoomRepository.kt
    |   |
    |   |
    |   |──services   
    |   |   └──FirebaseMessagingService.kt
    |   |
    |   |    
    |   |──usecases
    |   |  ├──AgreeCallUseCase.kt
    |   |  ├──CheckIsLoggedInUseCase.kt
    |   |  ├──CheckIsUserInDataBaseUseCase.kt
    |   |  ├──CreateFcmRequestUseCase.kt
    |   |  ├──CreateRoomCallUseCase.kt
    |   |  ├──CreateRoomLinkUseCase.kt
    |   |  ├──DeclineCallUseCase.kt
    |   |  ├──GetAllCallsUseCase.kt    
    |   |  ├──GetUserTokenUseCase.kt  
    |   |  ├──KickOutAllRoomUsersUseCase.kt  
    |   |  ├──LoginUseCase.kt  
    |   |  ├──SignUpUseCase.kt      
    |   |  └──UpdateTokenUseCase.kt
    |   |
    |
    |
    ├── presentation     
    │   ├── baseScreen
    |   |   ├──MainActivity.kt
    |   |   └──MainViewModel.kt
    |   |    
    │   ├── homeScreen
    |   |   ├──HomeFragment.kt
    |   |   ├──HomeViewModel.kt
    |   |   └──CallsAdapter.kt
    |   |
    │   ├── homeScreen
    |   |   ├──HomeFragment.kt
    |   |   ├──HomeViewModel.kt
    |   |   └──CallsAdapter.kt
    |   |
    │   ├── homeScreen
    |   |   ├──HomeFragment.kt
    |   |   ├──HomeViewModel.kt
    |   |   └──CallsAdapter.kt
    |   |
    │   ├── incomingCallScreen
    |   |   ├──IncomingActivity.kt
    |   |   └──IncomingCallViewModel.kt
    |   |
    │   ├── loginScreen
    |   |   ├──LoginFragment.kt
    |   |   └──LoginViewModel.kt
    |   |
    │   ├── newFaceTimeScreen
    |   |   ├──NewFaceTimeFragment.kt
    |   |   └──NewFaceTimeViewModel.kt
    |   |
    │   ├── outgoingCallScreen
    |   |   ├──OutgoingCallActivity.kt
    |   |   └──OutgoingCallViewModel.kt
    |   |  
    │   ├── signUpScreen
    |   |   ├──SignUpFragment.kt
    |   |   └──SignUpViewModel.kt
    |
    |
    └── FaceTimeCloneApp.kt
    
       
## LICENSE
```MIT License

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
