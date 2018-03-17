# Android-Midtrans
Simple application, design like Chat App based on Kotlin.
<br>![Main Screen Preview](https://raw.githubusercontent.com/pahlevikun/Android-Midtrans/master/MainScreen.png)
## Purpose of The App
This Application is use for assigment as an Android Developer, this is use only for my assignment so for other people maybe different.
<br>
## Support 
Support from Android 4.4 KitKat / Minimum API 19
## Engineering Process
Honestly, for the first time I open the files is around 15th March where the interview held on 12th March because I'm very hectic with task in my office. I have 4 days to finish it (but I think I can finish it around 1-2 days). So this is my step-by-step on it :
* Read all of assigment task.
* Open the API (I use browser with jsonbeauty plugins, so this is my fault) and from my point it easy (evidently the json isn't valid).
* Start creating the project, setting up dependencies and patterns first.
* Init project to git.
* Setup styles, colors, and other.
* Creating layout.
* Creating model and database.
* Creating retrofit base request and API Config (for networking).
* Creating interface, presenter, and view activity.
* Testing and find bugs (but I'm not use Unit Testing).
* Adding simple documentation in project.
* Creating this documentations.
## Dependencies
I use some dependencies, usually I use this dependencies as starter kit.
* Support used for vectorDrawable
* Design used for some view like CoordinatorLayout
* Recyclerview used for making recyclerview
* Appcompat used for old version support
* ConstraintLayout used for implementing constraint layout
* Retrofit used for network request
* Intuit used for responsive scale for view and text (sdp and ssp)
* Picasso used for handling image
* CircleImageView used for make circleimage
* Link-preview used for making link preview
<br>And this is my dependecies :
```gradle
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jre7:$kotlin_version"
    implementation "com.android.support:support-v4:$support_version"
    implementation "com.android.support:design:$support_version"
    implementation "com.android.support:recyclerview-v7:$support_version"
    implementation "com.android.support:appcompat-v7:$support_version"
    implementation 'com.android.support.constraint:constraint-layout:1.0.2'
    //Connection
    implementation 'com.squareup.retrofit2:retrofit:2.3.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.2.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.9.1'
    implementation 'com.google.code.gson:gson:2.8.0'
    //External
    implementation "com.intuit.sdp:sdp-android:$intuit_version"
    implementation "com.intuit.ssp:ssp-android:$intuit_version"
    implementation 'com.squareup.picasso:picasso:2.5.2'
    implementation 'de.hdodenhof:circleimageview:2.2.0'
    implementation 'org.jsoup:jsoup:1.10.3'
    implementation 'com.leocardz:link-preview:2.0.0@aar'
```
## Design Patterns
<br>![Main Screen Preview](https://raw.githubusercontent.com/pahlevikun/Android-Midtrans/master/MVP.png)
