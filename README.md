# ConfirmU
[![](https://jitpack.io/v/sgitay/confirmusdk.svg)](https://jitpack.io/#sgitay/confirmusdk)
ConfirmU SDK for Android
========================

This open-source library allows you to integrate ConfirmU into your Android app.

USAGE
-----
SDKs are broken up into separate modules. To ensure the most optimized use of
space only install the modules that you intend to use. To get started, see the Installation section below.

INSTALLATION
------------
To utilize a feature listed above
include the appropriate dependency (or dependencies) listed below in your `app/build.gradle` file.

    dependencies {
    ...
    implementation 'com.github.sgitay.confirmusdk:applib:0.1.0'
    implementation 'com.github.sgitay.confirmusdk:linkedin-sdk:0.1.0'
    ...
    }


You may also need to add the following to your `project/build.gradle` file.

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
