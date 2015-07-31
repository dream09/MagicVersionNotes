MagicVersionNotes
=================
An Android library project that provides a simple way of presenting version notes on first run and update of an application.  This project was developed in [Android Studio](http://developer.android.com/tools/studio/).

Adding MagicVersionNotes to your project
----------------------------------------
**1. Gradle dependency (Android Studio)**

 - 	Add the following to your `build.gradle`:
 ```gradle
repositories {
	    maven { url "https://jitpack.io" }
}

dependencies {
	    compile 'com.github.dream09:MagicVersionNotes:2.0'
}
```

**2. Maven**
- Add the following to your `pom.xml`:
 ```xml
<repository>
       	<id>jitpack.io</id>
	    <url>https://jitpack.io</url>
</repository>

<dependency>
	    <groupId>com.github.dream09</groupId>
	    <artifactId>MagicVersionNotes</artifactId>
	    <version>2.0</version>
</dependency>
```

**3. Jar file only**
 - Get the [**latest release .jar file**](https://github.com/dream09/MagicEula/releases) from the releases area
 - Copy the **MagicVersionNotes-X.X.jar** file into the `libs` folder of your Android project
 - Start using the library

Using MagicVersionNotes
-----------------------
* Ensure you have imported the project as described above and that it is set as a library project.

* Setup in the *onCreate* method of your main activity, for example:
```
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

	// Get our version info ready for passing to the version notes.
    String version = "";
    try {
    	PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
    	version = pi.versionName;
    } catch (PackageManager.NameNotFoundException e) {
        e.printStackTrace();
    }

    // Check if version notes should be displayed.
	MagicVersionNotes myNotes = new MagicVersionNotes(this);
	if (!myNotes.versionCheck(version)) {
		myNotes.setAppName(getString(R.string.app_name));
		myNotes.setAppVersion(version);
		myNotes.setNotes(getString(R.string.app_version_notes));
		myNotes.showVersionNotes();
	}
}
```

Contributing to MagicVersionNotes
---------------------------------

If you wish to contribute please create a feature branch from the *develop* branch and name *feature-yourfeature*.
