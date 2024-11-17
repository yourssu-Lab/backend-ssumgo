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
- [ ] 권한 확인
    - [x] AccessToken을 받아서 권한을 확인한다.
    - [x] 권한이 있으면 Argument Resolved로 ssumgo student id를 받아와서 요청을 처리한다.
    - [ ] 유효하지 않는 토큰이면 401 Unauthorized을 반환한다.
    - [ ] 권한이 없으면 403 Forbidden을 반환한다.

### 학생
- [ ] 유저 정보 조회
    - [x] AccessToken으로 인증된 학생의 정보를 조회한다.
        - [ ] 인증 권한이 없으면 401 Unauthorized를 반환한다.
        - [ ] 유저 정보를 찾을 수 없으면 404 Not Found를 반환한다.
    - [x] 학생 아이디, 유어슈 아이디, 이름, 학과, 학번, 프로필 이미지 소/중/대를 반환한다.

### 과목
- [x] 전체 과목 조회
- [ ] 수강 과목 조회
    - [x] 학생의 수강 과목을 조회한다.
    - [ ] 인증에 실패하면 401 Unauthorized를 반환한다.
    - [x] 수강년도와 학기가 일치하는 수강 과목을 조회한다.
    - [x] 기본 수강년도는 2024, 기본 학기는 2학기이다.
    - [ ] 수강 과목이 없으면 404 Not Found를 반환한다.
- [ ] 과목 생성

### 게시글
- [x] 게시글 등록
- [ ] 과목별 게시글 조회
      - [x] 정렬 기준을 설정한다.
      - [x] 페이지네이션을 설정한다.
      - [ ] 검색 기능을 추가한다.
- [x] 게시글 상세 조회

### 답변
- [ ] 답변 등록
- [ ] 과목 대상멘토 답변 조회
    - [ ] 해당 멘토가 작성한 답변만 조회한다.
  - [ ] 페이지네이션을 설정한다.
- [ ] 답변 상세 조회
    - [ ] 답변의 게시글, 답변 작성자의 정보도 같이 반환한다.