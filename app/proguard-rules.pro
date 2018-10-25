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


    # 设置混淆的压缩比率 0 ~ 7
    -optimizationpasses 5
    # 混淆后类名都为小写   Aa aA
    -dontusemixedcaseclassnames
    # 指定不去忽略非公共库的类
    -dontskipnonpubliclibraryclasses
    #不做预校验的操作
    -dontpreverify
    # 混淆时不记录日志
    -verbose
    # 混淆采用的算法.
    -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
    #保留代码行号，方便异常信息的追踪
    -keepattributes SourceFile,LineNumberTable


    #dump文件列出apk包内所有class的内部结构
    -dump class_files.txt
    #seeds.txt文件列出未混淆的类和成员
    -printseeds seeds.txt
    #usage.txt文件列出从apk中删除的代码
    -printusage unused.txt
    #mapping文件列出混淆前后的映射
    -printmapping mapping.txt




    #不提示V4包下错误警告
    -dontwarn android.support.v4.**
    #保持下面的V4兼容包的类不被混淆
    -keep class android.support.v4.**{*;}
    #避免混淆所有native的方法,涉及到C、C++
    -keepclasseswithmembernames class * {
            native <methods>;
    }
     #避免混淆枚举类
      -keepclassmembers enum * {
             public static **[] values();
             public static ** valueOf(java.lang.String);
     }
     #不混淆Parcelable和它的实现子类，还有Creator成员变量
         -keep class * implements android.os.Parcelable {
           public static final android.os.Parcelable$Creator *;
         }

         #不混淆Serializable和它的实现子类、其成员变量
         -keepclassmembers class * implements java.io.Serializable {
             static final long serialVersionUID;
             private static final java.io.ObjectStreamField[] serialPersistentFields;
             private void writeObject(java.io.ObjectOutputStream);
             private void readObject(java.io.ObjectInputStream);
             java.lang.Object writeReplace();
             java.lang.Object readResolve();
         }

         #避免混淆JSON类的构造函数
          #使用GSON、fastjson等框架时，所写的JSON对象类不混淆，否则无法将JSON解析成对应的对象
             -keepclassmembers class * {
                 public <init>(org.json.JSONObject);
             }

              # ==================okhttp start===================
                 -dontwarn com.squareup.okhttp.**
                 -keep class com.squareup.okhttp.** { *;}
                 -dontwarn okio.**
                 -keep class okio.**{*;}
                 -keep interface okio.**{*;}
                 # ==================okhttp end=====================

# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
# RxJava RxAndroid
-dontwarn sun.misc.**
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




 #避免混淆实体类
  -keep class com.xiaowei.bean.** { *; }

#避免混淆友盟
-dontwarn com.tencent.mm.**
-keep class com.tencent.mm.**{*;}

-dontwarn com.umeng.**
-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class [com.xiaowei].R$*{
public static final int *;
}

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
