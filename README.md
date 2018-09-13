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

```
dependencies {
    implementation 'com.github.abnerescocio:edittext-mask:1.0'
}
```

## How to use ##

**Step 1.** Use the class `TextInputEditTextMask` on your xml layout

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:focusableInTouchMode="true"
    tools:context=".MainActivity">

    <android.support.design.widget.TextInputLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <com.abnerescocio.lib.TextInputEditTextMask
            android:id="@+id/text_input_edit_text_mask"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="phone"
            app:mask="phone"
            app:mask_errorMsg="Wrong phone number"
            app:required="true"
            app:required_errorMsg="Required field*"
            />

    </android.support.design.widget.TextInputLayout>

</LinearLayout>
```
**Step 2.** Understending attrs

The class has 6 public attrs that can be set on your xml or on your class java/kotlin

**Mask:** The mask that you need put on field
* `app:mask`: `phone`, `email`, `brazilian_cpf` and more
* `setMask(identifier: Int)`: `TextInputEditTextMask.PHONE`, `TextInputEditTextMask.EMAIL`, `TextInputEditTextMask.BRAZILIAN_CPF` and more

**Mask message error to wrong mask:** A string that will show a message when user input a wrong mask
* `app:mask_errorMsg`: "Invalid value" or `@string/invalid_value`
* `setMaskErrorMsg(msg: String)`: "Invalid value" or `getString(R.string.invalid_value)`

**Required field** A boolean value that define if field is required or not
* `app:required`: true or false
* `setRequired(isRequired: Boolean)`: true or false

**Required message error to empty field** A string that will show a message when user not type any thing
* `app:required_errorMsg`: "Required field" or `@string/required_field`
* `setRequiredErrorMsg(msg: String)`: "Required field" or `getString(R.string.required_field)`

**Range** The Regular Expression that delimiter the user inputs. For example, if the user must be input a value between 1 to 4 use `app:range="[1-4]"`
* `app:range`: "[1-4]", "[a-z]", "[A-Z]", "[0-99]" and infinite possibles
* `setRange(regex: String)`: "[1-4]", "[a-z]", "[A-Z]", "[0-99]" and infinite possibles

**Range message error to wrong interval** A string that will show a message when user not type a value inside range
* `app:range_errorMsg`: "Invalid range. Need be between 1 to 4" or `@string/invalid_range`
* `setRangeErrorMsg(msg: String)`: "Invalid range. Need be between 1 to 4" or `getString(R.string.invalid_range)`
