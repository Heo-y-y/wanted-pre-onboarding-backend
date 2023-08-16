# wanted 사전과제
## 지원자
### 허윤영
### 개발 환경
- IDE: IntelliJ IDEA Ultimate
- Language: Java 11
- Framework: Spring Boot
- Build Management: Gradle
- DB: MySQL 8.0
### 애플리케이션 실행 방법
MySQL DB 스키마 "wanted 생성 또는 application.yml 파일의 spring.datasource.url 항목에 적힌 "wanted" 을 적용하고 싶은 "본인의 스키마 명(ex.mydb)" 으로 변경하시고 진행해주세요.
USERNAME : MySQL DB 계정
PASSWORD : 계정의 비밀번호
### 데이터베이스 테이블 구조
![스크린샷 2023-08-17 오전 12 12 42](https://github.com/Heo-y-y/wanted-pre-onboarding-backend/assets/112863029/edd2e4de-2877-44d2-978e-fa0e3baadcf0)
### 구현한 API의 데모 영상 링크
[구현 영상](https://www.youtube.com/watch?v=ovl2_sSPQkw)
### 구현 방법 및 이유
- Spring Boot를 사용하여 개발
- Spring Data JPA를 사용하여 DB와 연동
- 회원 같은 경우에는 리플레쉬 토큰을 따로 저장하기위해 Redis를 사용했습니다.
- 그리고 각 응답 메세지를 테스트하는 사람이 알기 쉽게 커스텀 API를 만들어서 응답을 진행하도록 했습니다.
### API 명세서
[API 명세서](https://documenter.getpostman.com/view/24205657/2s9Y5R168h)
