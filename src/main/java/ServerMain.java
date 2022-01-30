import config.GlobalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task.OpenServerSocket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
    static Logger log = LoggerFactory.getLogger(ServerMain.class);
    public static void main(String[] args) {
        GlobalConfig config = GlobalConfig.getConfig();
        log.info("Config 정보 : {}", config);
        //https://codechacha.com/ko/java-executors/
        // server socket은 하나만 열어 둘거기 때문에 newSingleThreadExecutor 를 사용.
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new OpenServerSocket());

        executorService.shutdown();

    }
}
