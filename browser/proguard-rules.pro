-optimizationpasses 5
-allowaccessmodification
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose

-keep class com.google.errorprone.annotations.** { *; }
-dontwarn com.google.errorprone.annotations.**

-dontnote io.reactivex.**
-dontwarn io.reactivex.**

-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8

-dontwarn rx.**

-dontwarn okio.**

-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }

-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions