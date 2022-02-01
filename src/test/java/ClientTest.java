import client.MyClient;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.MyServer;

public class ClientTest {
    static Logger log = LoggerFactory.getLogger(ClientTest.class);
    MyClient myClient;

    @BeforeEach
    void 테스트_준비(){
        log.info("테스트를 준비합니다.");
        this.myClient = new MyClient();
    }

    @Test
    @DisplayName("EchoTask_성공_테스트")
    void EchoTask_성공_테스트(){
        String response = myClient.sendMessage("abcde");
        log.debug(response);
        Assertions.assertEquals(MyServer.SUCCESS,response);
    }

    @Test
    @DisplayName("EchoTask 실패 테스트 - 5자 미만")
    void EchoTask_실패_테스트(){
        String response = myClient.sendMessage("abe");
        log.debug(response);
        Assertions.assertTrue(response.contains(MyServer.FAILURE));
    }

    @Test
    @DisplayName("EchoTask 실패 테스트 - 5자 초과")
    void test(){
        String response = myClient.sendMessage("abeafawf");
        log.debug(response);
        Assertions.assertTrue(response.contains(MyServer.FAILURE));
    }

    @Test
    @DisplayName("EchoTask 연속 통신 테스트")
    void test2(){
        String response = myClient.sendMessage("abeaf");
        log.debug(response);

        String response2 = myClient.sendMessage("fkewo");
        log.debug(response2);

        Assertions.assertEquals(MyServer.SUCCESS,response);
        Assertions.assertEquals(MyServer.SUCCESS,response2);
    }


    @AfterEach
    void 테스트_종료(){
        myClient.sendTerminateCode();
        myClient.close();
        log.info("테스트를 종료합니다.");
    }
}
