---
layout: docs
---

# Android Installation Guide

1. Add the maven repository to your project's `build.gradle`:

```groovy
allprojects {
    repositories {
        google()
        jcenter()
        maven { url  "https://dl.bintray.com/salesforce-mobile/android" }
    }
}
```

2. Add the `kotlin-kapt` plugin to your app's `build.gradle`:

```groovy
apply plugin: 'kotlin-kapt'
```

3. Add the Nimbus runtime dependency and annotation processor to your app's `build.gradle`:

```groovy
dependencies {
    implementation 'com.salesforce.nimbus:nimbus:1.0.0'
    kapt 'com.salesforce.nimbus:nimbus-compiler:1.0.0'
}
```

4. Initialize the Nimbus Bridge and attach it to your `WebView`:

```kotlin
class MainActivity : AppCompatActivity() {

    private val bridge: NimbusBridge = NimbusBridge()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val webView = findViewById<WebView>(R.id.webview)
        bridge.add(DeviceInfoPluginBinder(DeviceInfoPlugin(this)))
        bridge.attach(webView)
        bridge.loadUrl("http://10.0.2.2:3000")
        WebView.setWebContentsDebuggingEnabled(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        bridge.detach()
    }
}
```

# NimbusJS

1. Add NimbusJS to your app's `build.gradle`:
```groovy
dependencies {
    implementation 'com.salesforce.nimbus:nimbus:1.0.0'
    implementation 'com.salesforce.nimbus:nimbusjs:1.0.0'
    kapt 'com.salesforce.nimbus:nimbus-compiler:1.0.0'
}
```

2. Inject nimbus.js:

You can refer directly to the nimbus.js source included as a resource in the NimbusJS AAR or you can use `NimbusJSUtilities.injectedNimbusStream(...)` to inject nimbus.js into a page load stream intercepted in your webview.
