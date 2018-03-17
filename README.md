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
* Imagine the layout and the app process.
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
![MVP Patterns](https://raw.githubusercontent.com/pahlevikun/Android-Midtrans/master/MVP.png)
<br>Just like what I said before, I use MVP Patterns because for me its easier to understand than MVVM patterns. In some reference, MVP is a derivative from the well known MVC (Model View Controller), which for a while now is gaining importance in the development of Android applications. 
The MVP pattern allows separate the presentation layer from the logic, so that everything about how the interface works is separated from how we represent it on screen. Ideally the MVP pattern would achieve that same logic might have completely different and interchangeable views.
#### Why Use MVP
For an application to be easily extensible and maintainable we need to define well separated layers. What do we do tomorrow if, instead of retrieving the same data from a database, we need to do it from a web service? We would have to redo our entire view .
MVP makes views independent from our data source. We divide the application into at least three different layers, which let us test them independently. With MVP we are able to take most of logic out from the activities so that we can test it without using instrumentation tests.
#### Current MVP
![Current MVP](https://raw.githubusercontent.com/pahlevikun/Android-Midtrans/master/MVP2.png)
<br>So this is my current MVP Pattern, I use 4 type as general (Model, View, Presenter, Etc) Model just like description before with Pojo and Database. View contains 2 type of component I use for UI and Adapter. Presenter contains interface and prenseter itself (impl). The last is Etc, it contains APIConfig, retrofit, and utils. But for this project I cancel use utils, previously I want to make some class that parse the request code like what is 200, 201, 404, 500, and many more but I think it will spent many time.
## Challenge
* JSON in API isn't valid, so I manipulate it first.
* I'm not familiar with Sent_at value, so I manipulate it.
* The data in JSON is only for same sender (1 people in chat room). This is a test and the objective is for making aesthetic UI, so I manipulate it just like 2 people in same chat room for better UI example (better than all box in left only).
