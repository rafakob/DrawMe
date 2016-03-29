# DrawMe #
With **DrawMe** you can easily create views with custom background shapes using only XML layout files. Quick example:

_Standard way:_
```xml
<!--drawable/back.xml-->
<shape 
    xmlns:android="http://schemas.android.com/apk/res/android" 
    android:shape="rectangle">

    <corners android:radius="5dp" />
    <solid android:color="@android:color/white" />
    <stroke
        android:width="3dp"
        android:color="@android:color/black" />
</shape>


<!--layout/activity_main.xml-->
<FrameLayout
    android:layout_width="100dp"
    android:layout_height="100dp"
    android:background="@drawable/back.xml"/>
```

**_With DrawMe:_**
```xml
<com.rafakob.drawme.DrawMeFrameLayout
    android:layout_width="100dp"
    android:layout_height="100dp"
    drawme:backColor="@android:color/white"
    drawme:strokeColor="@android:color/black"
    drawme:radius="5dp"
    drawme:stroke="3dp"/>
```


Library also supports different states: **normal**, **pressed**, **disabled**. 
It means that you can define different background and stroke colors for each state. The best thing about it is that DrawMe is "compatible" with pre and post Lollipop. On API +21 it uses **native** ripple press effect, and below it uses standard StateListDrawable. Here's example:

_Standard way:_
```xml
<!--drawable/custom_button.xml-->
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_enabled="false">
        <shape android:shape="rectangle">
            <solid android:color="@color/colorDarkGray"/>
        </shape>
    </item>
    <item android:state_pressed="false">
        <shape android:shape="rectangle">
            <solid android:color="@color/colorButtonGreen"/>
        </shape>
    </item>
    <item android:state_pressed="true">
        <shape android:shape="rectangle">
            <solid android:color="@color/colorButtonGreenFocused"/>
        </shape>
    </item>
</selector>


<!--drawable-v21/custom_button.xml-->
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_enabled="false">
        <shape android:shape="rectangle">
            <solid android:color="@color/colorDarkGray"/>
        </shape>
    </item>
    <item android:state_enabled="true">
        <ripple
            xmlns:android="http://schemas.android.com/apk/res/android"
            android:color="@color/colorButtonGreenFocused">
            <item>
                <color android:color="@color/colorButtonGreen"/>
            </item>
        </ripple>
    </item>
</selector>


<!--layout/activity_main.xml-->
<Button
    android:layout_width="match_parent"
    android:layout_height="50dp"
    android:background="@drawable/custom_button.xml"/>
```

**_With DrawMe:_**
```xml
<com.rafakob.drawme.DrawMeButton
    android:layout_width="match_parent"
    android:layout_height="50dp"
    drawme:backColor="@color/colorButtonGreen"
    drawme:backColorPressed="@color/colorButtonGreenFocused"
    drawme:backColorDisabled="@color/colorDarkGray"
    drawme:shapeRadiusHalfHeight="true"/>
```
**_Result_**

_Pre Lollipop_

![](http://i.imgur.com/mOYsFsu.gif)

_API +21_

![](http://i.imgur.com/NgFrb78.gif)






## Install ##
Add it in your root build.gradle at the end of repositories:
```java
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```
Add the dependency:
```java
dependencies {
        compile 'com.github.rafakob:DrawMe:0.1.0'
}
```

> If you find any bugs feel free to report an issue or create a pull request. Suggestions and feature requestes are welcome.

## Features ##

### License ###
```
Copyright 2016 Rafał Kobyłko

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
