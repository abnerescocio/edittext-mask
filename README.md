# edittext-mask
Put masks to edit fields with this library. It is so easy!

Select one inside of various masks for your `edittext` and write something as `app:mask="phone"`

An error handler is default and throw an message everytime that the mask is no match with inputed text, the message to wrong phone number is `Invalid phone number` for example. This message can be changed, to this, create the follow resource string `<string name="no_match_phone">Your message to wrong phone number</string>`

Look this followings steps to more details

## How to add ##

**Step 1.** Add the JitPack repository to your build file

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

**Step 2.** Add the dependency

```
dependencies {
    implementation 'com.github.abnerescocio:edittext-mask:1.0'
}
```
