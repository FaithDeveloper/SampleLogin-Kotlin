# 샘플 로그인 페이지 만들기

`코틀린` , `RxJava`, `Realm` 으로 개발한 간단한 로그인 프로젝트 입니다.

<br/>

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

<br/>

## 실행화면

<img src="https://github.com/FaithDeveloper/SampleLogin-Kotlin/blob/master/raw/master/sample_login_20180531.gif" data-canonical-src="https://github.com/FaithDeveloper/SampleLogin-Kotlin/blob/master/raw/master/sample_login_20180531.gif" width="300" height="533" />

<br/>

##  Android Support Degin 을 활용한 화면 구성

`android.support.design.widget.TextInputLayout` 을 활용하여 Input Text 화면 구성하였습니다. 

실행 화면을 보시면 `회원가입` 에서 아이디, 비밀번호 입력을 시도하면 Input Text 화면이 변경되는 것을 확인할 수 있습니다. 이처럼 기존의 EditText Widget 의 디자인을 깔끔하고 다양하게 표현할 수 있도록 도와줍니다. 특히 Android Support Degin은 Rx을 활용하면 그 영향력은 파격적입니다.

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
    <!-- 생략...-->
</LinearLayout>
```

<br/>

## RxJava, RxBinding을 활용한 회원가입 페이지 만들기

> **Rx란?**
>
> 'Reactive Extensions'이며 'ReactiveX'라고도 불립니다. 
>
> 이벤트를 이벤트 스트림에 전달하고, 이벤트 스트림을 관찰하다가 원하는 이벤트를 감지하면 이에 따른 동작을 수행합니다. 비동기 라이브러리로 반응하는 프로그램이라고하여 **Reactive Programming**으로 부릅니다.

 Rx을 알기 위해서는 옵서버블(Observable)와 옵서버(Observer) 개념을 알고 있어야 합니다.



### **옵서버블(Observable)**

"이벤트"를 만들어 내는(emit) 주체로, "이벤트 스트림"을 통해 만든 이벤트를 보냅니다. 한개부터 여러개까지 생성 가능하며, 특정 조건을 만족 시 이벤트를 만들어 내지 않을 수도 있으며, 유지하여 계속해서 이벤트를 만들 수도 있습니다.



### **옵서버(Observer)**

옵서버블에 만들어진 이벤트에 "반응(react)"합니다. 이벤트를 받았을 때 수행할 작업을 정의합니다. 반응을 하기 위해서 지속적으로 옵서버블을 관찰해야하는데 이를 '옵서버가 옵서버블을 "구독(subscribe)"다'라고 표현합니다.

![img](https://t1.daumcdn.net/cfile/tistory/99E021465B18F37915)



### 연산자(Operators)

이벤트 스트림을 통해 전달되는 이벤트를 변환 합니다. 갖고있는 값 변경 뿐 아니라 특정 조건을 만족하는 이벤트만 이벤트 스트림에 흘려 보내거나 이벤트 개수를 바꿔주는 등 다양한 작업을 수행할 수 있습니다. 
Rx에서 제공하는 Operators 를 확인하려면 [여기](http://reactivex.io/documentation/operators.html) 를 눌러주세요. 한국어로 번역도 되어 있네요. 

![img](https://t1.daumcdn.net/cfile/tistory/99790B455B18F38419)

> **연산자 체인**
>
> 거의 모든 연산자들은 Observable 상에서 동작하고 Observable을 리턴합니다. 이 접근 방법은 연산자들을 연달아 호출 할 수 있는 연산자 체인을 제공합니다. 연산자 체인에서 각각의 연산자는 이전 연산자가 리턴한 Observable을 기반으로 동작하며 동작에 따라 Observable을 변경합니다. Observable의 연산자 체인은 원본 Observable과 독립적으로 실행될 수 없고 *순서대로* 실행되어야 한다

**RxBinding과 연산자를 사용한 옵서버블 만든 예**

```kotlin
 val observableId = RxTextView.textChanges(editView)
                .map({ t -> !t.isEmpty()})
```



### 스케줄러(Scheduler)

스케줄러(Scheduler)는 작업을 수행할 스레드(thread)를 지정 합니다. 다양한 스레드를 지정할 수 있으며 Android에서 UI 업데이트하는 메인 스레드도 사용 가능합니다. 스케줄러는 observeOn() 메서드를 사용하여 지정합니다. 

> **스케줄러 호출 시점**
> 1) observeOn() 메서드 호출한 직후에 오는 연산자 
> 2) 옵서버에서 수행되는 작업 앞의 observeOn() 메서드에서 지정한 스레드



### 디스포저블(Disposable) 

옵서버가 옵서버블을 구독할 때 생성되는 객체로, 옵서버블에서 만드는 이벤트 스트림과 이에 필요한 리소스를 관리합니다. 한가지 예로써 구독 해제(unsubscribe)을 말할 수 있습니다.  디스포저블을 통하여 구독 해제한 경우 옵서버블은 이를 감지하여 유지하고 있던 리소스를 해제합니다.



### 컴포지트 디스포저블(Composite Disposable)

Composite Disposable을 사용하여 여러 개의 디스포저블 객체를 하나의 객체에서 사용 가능합니다. Android에서 View에 해당되는 액티비티나 프래그먼트에서 사용한다면 리소스 관리에 도움이 될 것입니다.

```kotlin
// Composite Disposable
internal val viewDisposables = CompositeDisposable()
// disposable
val disposableEmail = RxTextView.textChanges(inputDataField[3])
	.map { t -> t.isEmpty() || Pattern.matches(Constants.EMAIL_RULS, t) }
	.subscribe({
 	   reactiveInputTextViewData(3, it)
	}){
  	  //Error Block
	}
viewDisposables.add(disposable)
```

