# veneer
### reactive buttons for Jetpack Compose
[![](https://jitpack.io/v/Shivamdhuria/veneer.svg)](https://jitpack.io/#Shivamdhuria/veneer)


veneer is a library for reactive buttons. The buttons react depededing upon the roll, pitch and azimuth angle of the phone which is  calculated using accelerometer and magnetic field sensor. 

![Veneer Example](https://github.com/Shivamdhuria/veneer/blob/main/assets/veneer.gif)


# Installing
To download it from the jitpack, add these lines in your root build.gradle at the end of repositories:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

And then add to the module's build.gradle

```gradle
implementation 'com.github.Shivamdhuria:veneer:VERSION'"
```

# How to use

To add a reflective button, you need to first instantiate veneer. Make sure you stop veneer as in OnStop() or onPause() as veneer relies upon data from accelerometer and magnetic field sensor.  

```Kotlin
   override fun onResume() {
        super.onResume()
        Veneer.init(this)
    }

    override fun onPause() {
        super.onPause()
        Veneer.stop()
    }

```

To use any veneer button just add veneer composable, as you'd add a regular button composable. 

```Kotlin
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val scrollState = rememberScrollState()

            val azimuthAngle by Veneer.azimuthAngle.collectAsState()
            val pitchAngle by Veneer.pitchAngle.collectAsState()
            val rollAngle by Veneer.rollAngle.collectAsState()

            SurfaceTheme() {
                Surface() {
                    Box(modifier = Modifier.fillMaxSize()) {
                      fun RadialReflectiveButton(rotationValue = rollAngle, onClick =  }
                      ,shape = RoundedCornerShape(50)) {
                Icon(Icons.Outlined.Pause, contentDescription = "content description", tint = GREY600)
            }
                    }
                }
            }
        }
    }
```


