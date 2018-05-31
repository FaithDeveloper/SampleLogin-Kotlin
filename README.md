# Sample Login for Kotlin

`코틀린` , `RxJava`, `Realm` 으로 개발한 간단한 로그인 프로젝트 입니다.

## 요구사항

```
* 간단한 로그인 화면을 구성한다.
1) 로딩화면 :
  - 자동로그인 시 realm에 저장한 데이터와 SharedPreference의 데이터 비교하여 만족할 시 로그인 성공 화면으로 이동
  - 자동로그인이 아닌 경우 또는 비교한 데이터 값이 일치하지 않았을 경우 로그인 화면으로 이동
1) 회원가입 :
  - 아이디, 비밀번호, 비밀번호 확인, 이메일로 구성
  - 회원 가입 시 사용자 입력 항목을 다 입력했는지 확인
  - 회원 가입 시 올바른 이메일 형식을 사용하는지 확인
  - 회원 가입 시 패스워드와 패스워드 확인 문자가 동일한지 확인
  - 회원 가입 시 패스워드는 대문자, 소문자, 특수문자, 숫자를 포함하여 8자 이상만 가능
  - 회원 가입 조건 만족하지 않을 시 '회원 가입하기' 버튼 비활성화
  - Realm을 사용하여 사용자의 회원 정보를 저장합니다.
2) 메인화면 :
  - 아이디 , 비밀번호가 입력 되었는지 확인
  - 아이디, 비밀번호가 모두 입력 되었을 때만 로그인하는 버튼 활성화 시킬 것(조건 만족 안할 시 비활성화)
  - 저장된 아이디, 비밀번호가 일치 했을 경우만 로그인 성공
    (Realm에 저장된 값과 사용자가 입력한 값과의 비교)
  - 로그인 성공화면에 입력된 ID을 로그인 성공페이지에 전송(Intent) 
  - 자동로그인 기능이 있어 자동로그인 시 realm에 저장한 데이터와 SharedPreference의 데이터 비교한다.
3) 로그인 성공화면 :
 - 메인화면에서 사용자가 입력한 ID 값을 전달 받아 보여줌 (Intent.getStringExtra 사용)
 - 메인화면에서 초기화 버튼이 있어 저장된 아이디, 비밀번호를 초기화 하고 회원가입 페이지로 이동한다.
 - 메인화면에서 회원가입 버튼이 있어 회원가입 페이지로 이동시킵니다.
4) 공통
 - Kotlin으로 작성할 것
 - 아이디, 비밀번호, 비밀번호확인, 이메일은 무조건 1줄로만 나오게 합니다.
 - 비밀번호, 비밀번호 확인은 inputType 을 password로 설정합니다.
 - 이메일의 inputType은 Email Address로 설정합니다.
 - 올바르게 입력 했는지 확인은 RxJava로 체크합니다.
 - anko을 활용한 toast, startActivty 구현한다.
 - android.support:design 을 활용하여 로그인, 회원가입 UI을 구성한다.
```

## 실행화면

<img src="https://github.com/FaithDeveloper/SampleLogin-Kotlin/blob/master/raw/master/sample_login.gif" data-canonical-src="https://github.com/FaithDeveloper/SampleLogin-Kotlin/blob/master/raw/master/sample_login_20180531.gif" width="300" height="533" />

## 관련 소스

#### 1.Android Support Degin 을 활용한 화면 구성

`android.support.design.widget.TextInputLayout` 을 활용하여 Input Text 화면 구성하였습니다. 

##### activity_siginup_degin.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/black"
    android:gravity="center_horizontal"
    android:orientation="vertical"
    android:paddingLeft="15dp"
    android:paddingRight="15dp">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="15dp"
        android:text="@string/join_name"
        android:textColor="@color/white"
        android:textSize="30dp" />

    <!-- 아이디 중복 확인 UI 구성 -->
    <android.support.design.widget.TextInputLayout
        android:id="@+id/editIDLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textColorHint="@color/white">

        <EditText
            android:id="@+id/editID"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:backgroundTint="@color/white"
            android:hint="@string/id"
            android:inputType="text"
            android:maxLength="12"
            android:maxLines="1"
            android:textColor="@color/white" />
    </android.support.design.widget.TextInputLayout>

    <!-- 아이디 중복 확인 UI 구성 -->
    <Button
        android:id="@+id/btnCheckExistID"
        android:layout_width="wrap_content"
        android:layout_height="50dp"
        android:layout_gravity="center_vertical|right"
        android:layout_marginLeft="10dp"
        android:background="@drawable/round_green"
        android:text="중복체크"
        android:textColor="@color/white" />

    <!-- 패스워드 UI 구성 -->
    <android.support.design.widget.TextInputLayout
        android:id="@+id/editPWDLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="@string/pwd"
        android:textColorHint="@color/white">

        <EditText
            android:id="@+id/editPWD"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:backgroundTint="@color/white"
            android:inputType="textPassword"
            android:maxLength="20"
            android:maxLines="1"
            android:textColor="@color/white" />
    </android.support.design.widget.TextInputLayout>

    <!-- 패스워드 확인 UI 구성 -->
    <android.support.design.widget.TextInputLayout
        android:id="@+id/editPWDConfirmLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="@string/pwd_confirm"
        android:textColorHint="@color/white">

        <EditText
            android:id="@+id/editPWDConfirm"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:backgroundTint="@color/white"
            android:inputType="textPassword"
            android:maxLength="20"
            android:maxLines="1"
            android:textColor="@color/white" />
    </android.support.design.widget.TextInputLayout>

    <!-- 이메일 입력 UI 구성 -->
    <android.support.design.widget.TextInputLayout
        android:id="@+id/editEmailLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="@string/email"
        android:textColorHint="@color/white">

        <EditText
            android:id="@+id/editEmail"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:backgroundTint="@color/white"
            android:inputType="textEmailAddress"
            android:maxLength="20"
            android:maxLines="1"
            android:textColor="@color/white" />
    </android.support.design.widget.TextInputLayout>

    <!-- 회원가입 버튼 -->
    <Button
        android:id="@+id/btnDone"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:layout_marginTop="5dp"
        android:text="@string/join_done" />
</LinearLayout>
```
