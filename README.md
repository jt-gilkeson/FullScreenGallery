# FullScreenGallery
A full screen gallery with pinch to zoom and tap to hide navigation that pages through a set of image urls

### Installation
Add the following dependency to your build.gradle file
```xml
repositories {
    maven { url "https://jitpack.io" }
}
	
dependencies {
    implementation 'com.github.jt-gilkeson:fullscreengallery:0.2'
}
```

### How to use it

Add GalleryActivity to your AndroidManifest
```xml
<activity android:name="com.jt.gallery.GalleryActivity"/>
```

#### Usage
To launch the gallery, simply use the newIntent method then start the activity.

Kotlin:
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

Java:
```java
startActivity(
    GalleryActivity.newIntent(
        this,
        Arrays.asList(
            "http://www.gstatic.com/webp/gallery/1.jpg",
            "http://www.gstatic.com/webp/gallery/2.jpg",
            "http://www.gstatic.com/webp/gallery/3.jpg",
            "http://www.gstatic.com/webp/gallery/4.jpg",
            "http://www.gstatic.com/webp/gallery/5.jpg"
        )
    )
);
```

#### Optional Parameters
When you call newIntent, you can optionally specify:

**currentImage**: initial image to display (index for imageList)

**useFullBrightness**: Set screen brightness to full (useful for displaying barcodes)
