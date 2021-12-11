# veneer
### reactive buttons for Jetpack Compose

veneer is a library for reactive buttons. The buttons react depededing upon the roll, pitch and azimuth angle of the phone which is  calculated using accelerometer and magnetic field sensor. 


![grab-landing-page](https://github.com/winnie1312/grab/blob/master/grab-landingpage-winnie.gif)




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
    RadialReflectiveButton(
                rotationValue = rollAngle, onClick = { println("pressed") },
                shape = RoundedCornerShape(50)
            ) {
                Icon(
                    Icons.Outlined.Pause, contentDescription = "content description", tint = GREY600,

                    )
            }
```


