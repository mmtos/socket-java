# LOG
- [x] logback 설정 및 yml 설정파일 적용
- [x] ThreadPool 사용
- [x] ServerSocket API 읽고 설정 가능한 Option 파악
- [x] Socket을 통한 데이터 송수신 예시 작성
- [x] Server(= MyServer)와 Task 분리, AbstractTask 정의 (Template Method 이용 목적)
- [x] Task Flag 및 Handler 정의(하나의 serverSocket으로 여러가지 Task를 처리. Task의 Runnable 구현 제거)
- [x] MyClient 정의 
- [x] Server 테스트 코드 작성 
- [x] Terminate 코드정의 (Terminate 코드받기 전까지 통신 지속)
- [ ] myServer 생성시 keep alive 검토 후 도입 (재연결 비용 감소 목적)

# Link 
- ThreadExcutor : https://codechacha.com/ko/java-executors/
- execute() vs submit() : https://passiflore.tistory.com/35
- Socket Example : https://www.baeldung.com/a-guide-to-java-sockets

- Test
  - https://kgvovc.tistory.com/58
  - https://mangkyu.tistory.com/143


# Error
- 서버, 클라이언트 둘다 Read를 기다리는 경우 무한 대기 발생
  - 한쪽이 write한 만큼 한쪽은 read 할 수 있도록 맞춰 줘야함
