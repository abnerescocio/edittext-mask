# edittext-mask
Put masks to edit fields with this library. It is so easy!

Select one inside of various masks for your `edittext` and write something like this `app:mask="phone"`

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

[![](https://jitpack.io/v/abnerescocio/edittext-mask.svg)](https://jitpack.io/#abnerescocio/edittext-mask)

```
dependencies {
    implementation 'com.github.abnerescocio:edittext-mask:**version**'
}
```

## How to use ##

**Step 1.** Use the class `TextInputEditTextMask` on your xml layout

```
...

    <android.support.design.widget.TextInputLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <com.abnerescocio.lib.TextInputEditTextMask
            android:id="@+id/text_input_edit_text_mask"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:mask="email"
            app:mask_errorMsg="Invalid email"
            app:regex_auxiliary="^(?!.*test).*$"
            app:regex_auxiliary_errorMsg="You can't use test"
            app:required="true"
            app:required_errorMsg="Required field*"
            />

    </android.support.design.widget.TextInputLayout>

...
```
### Use of required field
![screenshot1](https://github.com/abnerescocio/edittext-mask/blob/alpha/screenshot_1.png)

### Use of email mask
![screenshot2](https://github.com/abnerescocio/edittext-mask/blob/alpha/screenshot_2.png)

### Use of regex auxiliary
![screenshot3](https://github.com/abnerescocio/edittext-mask/blob/alpha/screenshot_3.mp4)

**Step 2.** The use of attrs

Mask | Example
------------ | -------------
phone | 85 98765-4321
email | abner@email.com
credit card | 2236 9958 4578 5131
cpf | 986.909.630-14
cnpj | 32.338.068/0001-71
cep | 60650-140
ip | 192.168.1.10
web url | www.google.com

Regex auxliary | Example
------------ | -------------
Can't use test | ^(?!.*test).*$
Need input 5 characters | \\d{5,}
Use just letters | [a-zA-Z]
Use just numbers | [0-9]

Required | Example
------------ | -------------
Need be | true or false
