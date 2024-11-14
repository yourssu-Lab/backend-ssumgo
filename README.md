# backend-ssumgo

2024 유어슈 루키톤 BackEnd-ssumgo repository 입니다.
<div align="center">
<img width="1002" alt="스크린샷 2024-04-24 오후 3 38 33" src="https://github.com/wjdalswl/Yourssu-Incubating/assets/109158284/8e4920ec-172b-47d4-82cd-0d770ef46566">
</div>

## **🧑‍💻 Member**

<div>

|                              Leopold<br/>([@dwl21](https://github.com/dwl21))                              |
|:----------------------------------------------------------------------------------------------------------:|
| <img width="300" align="center" alt="IMG_6678" src="https://avatars.githubusercontent.com/u/76774809?v=4"> |
</div>


## 구현할 기능 목록

### 인증
- [x] 로그인
    - [x] 클라이언트에게 아이디, 비밀번호를 받아 숨쉴때 Auth 서버에서 AccessToken, RefreshToken을 발급 받는다.
    - [x] AccessToken으로 숨쉴때 User 정보를 가져온다.
    - [x] Ssumgo-AccessToken을 만든다.
    - [x] 숨쉴때 User 정보를 Ssumgo Student nickname, profileUrls에 업데이트 한다.
    - [x] Ssumgo에 가입되지 않은 회원이면 계정을 새로 만든다.
    - [x] 클라이언트에게 ssumgo-AccessToken과 RefreshToken과 ssumgo StudentId를 반환한다.

- [x] 토큰 재발급
    - [x] 클라이언트에게 RefreshToken을 받아서 AccessToken, RefreshToken을 재발급 받는다.
    - [x] AccessToken으로 숨쉴때 User 정보를 가져온다.
    - [x] 숨쉴때 User 정보를 Ssumgo Student nickname, profileUrls에 업데이트 한다.
    - [x] 클라이언트에게 ssumgo-AccessToken과 RefreshToken과 StudentId를 반환한다.

### 인가
- [x] 권한 확인
    - [x] AccessToken을 받아서 권한을 확인한다.
    - [x] 권한이 있으면 Argument Resolved로 ssumgo student id를 받아와서 요청을 처리한다.
    - [ ] 유효하지 않는 토큰이면 401 Unauthorized을 반환한다.
    - [  ] 권한이 없으면 403 Forbidden을 반환한다.
