# Consumer ProGuard rules for shared module
# These rules will be automatically applied to any app that includes this library

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

# ===== Shared Module Classes =====

# Keep all shared module classes
-keep class com.cibc.us.app.android.** { *; }
-keepclassmembers class com.cibc.us.app.android.** { *; }

# Keep network classes for serialization
-keep class com.cibc.us.app.android.network.** { *; }
-keepclassmembers class com.cibc.us.app.android.network.** { *; }

# ===== Retrofit and Gson Rules =====

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

# ===== General Rules =====

# Keep annotations
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes EnclosingMethod

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
