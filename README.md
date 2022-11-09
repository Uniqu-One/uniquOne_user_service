# ![logoIcon2](https://user-images.githubusercontent.com/49605999/200447509-d7f1e198-1525-4dd0-9f2e-ccd00e75a37e.svg) UnqueOne_Shop - back
# 스파로스 아카데미 2차 프로젝트 - 유니크원.

<br>

## 팀 회고 및 스프린트 회의 관리
### <u>[Notion](https://uniquone.notion.site/UniquOne-2f81712b2df0488cb1a0b458b7e3312a)</u>

<br>

## 📅 프로젝트 기간
- 22/09/13 - 22/11/11

<br>


## 🧑‍💻 팀원 소개

- 이형민(F/E) 
- 박도균(B/E) 
- 조성범(B/E)
- 이수연(B/E)

<br>

## 💭 커뮤니케이션 툴
- Notion
- Google Sheet
- Github
- Zoom
- Gather

<br>

#### * 프론트 프로젝트는 아래의 링크를 확인해주세요!
#### <u>[![logoIcon2](https://user-images.githubusercontent.com/49605999/200447509-d7f1e198-1525-4dd0-9f2e-ccd00e75a37e.svg) 스파로스 유니콘 F/E](https://github.com/curomame/UniquOne_FrontEnd)</u>

<br/>

#

## 프로젝트 소개
### 중고 의류 ~ 입니다.

### 요구사항 정의서, WBS , API 정의서
[요구사항정의서, WBS, API 정의서](https://docs.google.com/spreadsheets/d/192Y9wq_OwiqjKVOcOlI8nNdEh6mbQreG9CJ-gdXUJho/edit?usp=sharing)

### 구조 및 주요 기능.
MSA, Oauth2 로그인, 검색 ,댓글(계층형),SMS 서비스(메일,문자), <br>
채팅(Websocket), 알림(SSE), 거래, 리뷰, QnA , <br>
CI/CD,클라우드 서비스(AWS), 상품,팔로우, 좋아요 <br>

## ERD Model
![erd model](https://user-images.githubusercontent.com/49605999/200946980-c56e9432-2c68-4698-909e-792027409196.png)

## 기술 스택

### 언어
<img src="https://img.shields.io/badge/JAVA-007396?style=flate&logo=java&logoColor=white">

### 서버
<img src="https://img.shields.io/badge/Apache Tomcat-F8DC75?style=flat&logo=Apache Tomcat&logoColor=white"> <img src="https://img.shields.io/badge/Netty-000000?style=flat&logo=Netty&logoColor=white"> <img src="https://img.shields.io/badge/NGINX-009639?style=flat&logo=NGINX&logoColor=white">

### 프레임워크 / 툴킷
<img src="https://img.shields.io/badge/Spring-6DB33F?style=flat&logo=Spring&logoColor=white"> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat&logo=Spring Security&logoColor=white"> <img src="https://img.shields.io/badge/Spring Cloud-6DB33F?style=flat&logo=Spring Cloud&logoColor=white"> <img src="https://img.shields.io/badge/Hibernate(JPA)-59666C?style=flat&logo=Hibernate&logoColor=white">

<img src="https://img.shields.io/badge/IntelliJ IDEA-F05138?style=flat&logo=IntelliJ IDEA&logoColor=white"/> <img src="https://img.shields.io/badge/Github-181717?style=flat&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/PostMan-green?style=flat&logo=Postman&logoColor=white"/> <img src="https://img.shields.io/badge/MySQL Workbench-blue"/> <img src="https://img.shields.io/badge/Terminus-4D4D4D?style=flat&logo=Terminus&logoColor=white"> <img src="https://img.shields.io/badge/Medis-FD5F07?style=flat&logo=Medis&logoColor=white">

### DB
<img src="https://img.shields.io/badge/mysql-4479A1?style=flat&logo=mysql&logoColor=white"/> <img src="https://img.shields.io/badge/Redis-DC382D?style=flat&logo=Redis&logoColor=white"/> <img src="https://img.shields.io/badge/MongoDB-47A248?style=flat&logo=MongoDB&logoColor=white">

### CI / CD
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=Docker&logoColor=white"/> <img src="https://img.shields.io/badge/Jenkins-D24939?style=flat&logo=&logoColor=white"/>

### 퍼블릭 클라우드
<img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=flat&logo=Amazon AWS&logoColor=white"/>

<img src="https://img.shields.io/badge/Amazon AWS Ec2-232F3E?style=flat&logo=Amazon AWS&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon AWS RDS-232F3E?style=flat&logo=Amazon AWS&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon AWS S3-232F3E?style=flat&logo=Amazon AWS&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon AWS ElasticCache-232F3E?style=flat&logo=Amazon AWS&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon AWS IAM-232F3E?style=flat&logo=Amazon AWS&logoColor=white"/>

### 그외 라이브러리

## 📔 유저 서비스 디렉토리 구조
```
project
├─ public
│   └─ img   
│   
│   
├─ src
│   ├─main
│   │ ├─java
│   │ │  ├─ users
│   │ │  │   ├─ controller
│   │ │  │   ├─ domain
│   │ │  │   ├─ dto
│   │ │  │   ├─ repository
│   │ │  │   ├─ service
│   │ │  │   └─ typeEnum
│   │ │  ├─ utils
│   │ │  │   ├─ AuditingFields
│   │ │  │   ├─ controller
│   │ │  │   ├─ exception
│   │ │  │   ├─ generate
│   │ │  │   ├─ otp
│   │ │  │   ├─ security
│   │ │  │   ├─ swagger
│   │ │  │   └─ webconfig
│   │ │  └─ MsaUserServiceApplication.java
│   │ │
│   │ │     
│   │ └─ resources
│   │       ├─ application.yml
│   │       ├─ bootstrap.yml
│   │       └─ logback-spring.xml
│   │
│   └─ test
│   
│   
├─ .gitignore
├─ build.gradle
├─ Dockerfile
├─ gradlew
├─ gradlew.bat
├─ settings.gradle
└─ README.md
``` 
<br>
<hr>
<br>
