# FullScreenGallery
A full screen gallery with pinch to zoom and tap to hide navigation that pages through a set of image urls

### Installation
Add the following dependency to your build.gradle file
```xml
	repositories {
		maven { url "https://jitpack.io" }
	}
	
	dependencies {
		compile 'com.github.jt-gilkeson:fullscreengallery:0.1'
	}
```

### How to use it

Add SimpleFragmentActivity to your AndroidManifest
```xml
  <activity android:name="com.jt.gallery.GalleryActivity"/>
```

#### Basic Usage
To launch the gallery, simply use the newIntent method then start the activity.

```kotlin
    startActivity(
        GalleryActivity.newIntent(
            this,
            listOf(
                "http://www.gstatic.com/webp/gallery/1.jpg",
                "http://www.gstatic.com/webp/gallery/2.jpg",
                "http://www.gstatic.com/webp/gallery/3.jpg",
                "http://www.gstatic.com/webp/gallery/4.jpg",
                "http://www.gstatic.com/webp/gallery/5.jpg"
            )
        )
    )
```

