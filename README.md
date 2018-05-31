# Sample Login for Kotlin

`코틀린`으로 개발한 간단한 로그인 프로젝트 입니다.

## 요구사항

```
* 간단한 로그인 화면을 구성한다.
1) 회원가입 :
  - 아이디, 비밀번호, 비밀번호 확인, 이메일로 구성
  - 회원 가입 시 데이터가 다 들어갔는지 확인
  - 회원 가입 시 이메일에 @ 있는지 확인
  - 회원 가입 시 패스워드와 패스워드 확인 문자가 동일한지 확인
  - SharedPreference을 사용하요 캐쉬에 아이디, 비밀번호 1개만 저장
2) 메인화면 :
  - 아이디 , 비밀번호가 입력 되었는지 확인
  - 저장된 아이디, 비밀번호가 일치 했을 경우만 로그인 성공
    (SharedPreference에 저장된 값과 사용자가 입력한 값과의 비교)
  - 로그인 성공화면에 입력된 ID 전송 (Intent.putString(key, value)
3) 로그인 성공화면 :
 - 메인화면에서 사용자가 입력한 ID 값을 전달 받아 보여줌 (Intent.getStringExtra 사용)
 - (선택) 메인화면에서 초기화버튼이 있어 저장된 아이디, 비밀번호를 초기화 하고 회원가입 페이지로 이동한다.
4) 공통
 - Kotlin으로 작성할 것
 - 아이디, 비밀번호, 비밀번호확인, 이메일은 무조건 1줄로만 나오게 할 것
 - 비밀번호, 비밀번호 확인은 inputType 변경할 것(layout)
 - 이메일의 inputType 은 Email로 변경할 것 (textEmailAddress)
```

## 실행화면

<img width="500" height="899">https://github.com/FaithDeveloper/SampleLogin-Kotlin/blob/master/raw/master/sample_login.gif</img>
