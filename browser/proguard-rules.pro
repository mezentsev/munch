# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/irbis/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses MunchWebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-optimizationpasses 5
#-allowaccessmodification
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature

-keepattributes Exceptions

-keepclasseswithmembers class * {
@retrofit2.http.* <methods>;
}

-keep class com.ample.main.model.** { *; }
-keep class com.ample.main.viewmodel.** { *; }

#okhttp
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.*

-keep class javax.inject.** { *; }
-keep class io.reactivex.** { *; }

# okio
-dontwarn okio.**
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn java.nio.file.Path
-dontwarn java.nio.file.OpenOption
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

-dontwarn com.squareup.okhttp.**

-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

# crashlytics
-keep public class * extends java.lang.Exception
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
-dontwarn com.google.appengine.api.urlfetch.**
# If you are using custom exceptions, add this line so that custom     exception types are skipped during obfuscation:
-keep public class * extends java.lang.Exception

# For Fabric to properly de-obfuscate your crash reports, you need to remove this line from your ProGuard config:
-printmapping mapping.txt

-keepnames class * implements android.os.Parcelable {
 public static final ** CREATOR;
}

-dontnote okhttp3.**, okio.**, retrofit2.**, pl.droidsonroids.**, io.reactivex.**
-dontwarn com.google.errorprone.annotations.*