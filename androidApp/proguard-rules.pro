# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
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

# ===== Plaid SDK ProGuard Rules =====

# Keep all Plaid SDK classes
-keep class com.plaid.** { *; }
-keepclassmembers class com.plaid.** { *; }

# Keep Plaid Link SDK classes and interfaces
-keep interface com.plaid.link.** { *; }
-keep class com.plaid.link.** { *; }
-keepclassmembers class com.plaid.link.** { *; }

# Keep Plaid configuration classes
-keep class com.plaid.link.configuration.** { *; }
-keepclassmembers class com.plaid.link.configuration.** { *; }

# Keep Plaid result classes
-keep class com.plaid.link.result.** { *; }
-keepclassmembers class com.plaid.link.result.** { *; }

# Keep Plaid event classes
-keep class com.plaid.link.event.** { *; }
-keepclassmembers class com.plaid.link.event.** { *; }

# Keep Plaid exception classes
-keep class com.plaid.link.exception.** { *; }
-keepclassmembers class com.plaid.link.exception.** { *; }

# Keep Plaid internal classes that might be accessed via reflection
-keep class com.plaid.internal.** { *; }
-keepclassmembers class com.plaid.internal.** { *; }

# Keep serialization related classes
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Keep Parcelable implementations
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep annotations
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# ===== Retrofit and Gson Rules (for networking) =====

# Retrofit
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Keep data classes used with Gson
-keep class com.cibc.us.app.android.network.** { *; }

# ===== RxJava Rules =====

-dontwarn java.util.concurrent.Flow*
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# ===== OkHttp Rules =====

-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# ===== General Android Rules =====

# Keep custom application class
-keep class com.cibc.us.app.android.LinkSampleApplication { *; }

# Keep activity classes
-keep class com.cibc.us.app.android.MainActivity { *; }
-keep class com.cibc.us.app.android.MainActivityJava { *; }
-keep class com.cibc.us.app.android.MainActivityStartActivityForResult { *; }
-keep class com.cibc.us.app.android.MainActivityStartActivityForResultJava { *; }
