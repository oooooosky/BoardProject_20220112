1. 프로젝트 이름
    1. BoardProject
2. 기본패키지
    1. com.icia.board
3. dependency
    1. gradle project
    2. Spring web, lombok, Thymeleaf, Validation, Spring Data JPA, Mysql Driver
4. 서버포트
    1. 8093
5. 기본기능
    1. 기본주소 요청하면 index.html 출력
    2. MainController에서 기본주소 요청 처리
6. index.html
    1. 글쓰기 페이지(/board/save), 목록페이지(/board/) 요청 링크 있음.
7. BoardController
    1. 글쓰기 페이지 요청이 오면 글쓰기 페이지 출력
    2. 글쓰기페이지 위치
        1. templates/board/save.html
8. save.html
    1. 글쓰기 항목
        1. 작성자, 비밀번호, 제목, 내용
    2. 글쓰기 한 내용은 BoardSaveDTO에 담아서 컨트롤러로 전송됨.
9. BoardEntity
    1. id
    2. boardWriter
    3. boardPassword
    4. boardTitle
    5. boardContents
    6. boardDate(java.time.LocalDateTime)
    7. toSaveEntity 메서드도 설계해볼 것