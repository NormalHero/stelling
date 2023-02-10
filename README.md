# stelling
- http://3.35.208.238:8989/main/index (인스턴스 종료)
- 누구나 작가가 되어 소설을 쓰거나 일러스트를 등록 할 수 있다.
- 프로젝트 협업 레포지토리 : https://github.com/team1-project-stelling/stelling 
- 프로젝트 이슈 : https://github.com/team1-project-stelling/stelling/issues
- 박민수 노션 : https://luminous-sweatpants-b64.notion.site/Hello-Avery-c45b1be3690649ba85d674d5d8b33f47


# 프로젝트 목적
디지털 콘텐츠 대세로 웹툰 시장은 8800억, 웹 소설은 4000억으로 평가 받고 한해 신규 웹툰은 2700여편이 넘는다. 또한 웹소설 등록 작가는 4만여명에 달합니다. 웹소설 작가와 일러스트 작가는 서로가 수요와 공급에 해당하기에 스텔링에서는 웹소설과 일러스트 서비스를 모두 제공하고 작가들간의 양방향 채팅을 지원하여 매칭을 돕습니다. 이를 통해 누구나 작가가 되어 컨텐츠를 제작할 수 있고 서비스이용자는 다양한 검색 지원 기능을 통해 쉽게 자신의 취향에 맞는 서비스를 제공하여 작가와 사용자 모두의 진입장벽을 낮추었으면 하는 바람으로 제작하게 되었습니다.

# 구현 기술 스택 
- springBoot 
- JPA
- MySQL (8.0.27)
- MyBatis (3.5.9)
- HTML, CSS, JS
- Tomcat v9
- JDK 11.0.12
- Ajax
- intelliJ
- webSocket
- kakao 로그인 api
- 아임포트 결제 api
- aws 서버 
- Ubuntu Server 22.04 LTS

# 팀원 
- 원재희
    - Persistence Tier
    - 일러스트 페이지
    - DB
- 박민수
    - Business Tier
    - 랭킹 페이지
    - 카테고리 페이지
    - 내 서재
    - 1:1 게시글
    - AWS 배포
    - 팀원들 오류 확인
    - DB
- 서예원
    - Presentation Tier
    - 소설회차페이지
    - 소설 작성
    - 소설 상세보기
    - 프론트, 미디어쿼리
- 정찬렬 
    - 마이페이지
    - 프론트
- 최종현
    - 채팅 socket구현
    - 미디어쿼리, 프론트
- 이지훈 
    - 로그인/회원가입
    - 카카오 로그인 API
    - 유효성 검사
- 신재민 
    - 메인 페이지
    - 결제 API
    - DB

# ERD
![ERD](https://user-images.githubusercontent.com/96901629/171799546-f2a4bb15-f7db-4408-bf4b-f9cd2de36ec4.png)

