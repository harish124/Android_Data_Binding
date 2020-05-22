# Live data and Viewmodel

def lifecycle_version = "2.2.0"

def arch_version = "2.1.0"

implementation "androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version"

# LiveData
implementation "androidx.lifecycle:lifecycle-livedata:$lifecycle_version"

implementation "androidx.lifecycle:lifecycle-common-java8:$lifecycle_version"

# FancyToast Shasank:

allprojects {
 repositories {
  ...
  maven { url "https://jitpack.io" }
 }
}

dependencies {
 ...
 implementation 'com.github.Shashank02051997:FancyToast-Android:0.1.6'
}

# Material Design

implementation 'com.google.android.material:material:<version>'

visit https://maven.google.com/web/index.html for latest version

curr latest: 1.2.0-alpha05

#Butter-Knife

