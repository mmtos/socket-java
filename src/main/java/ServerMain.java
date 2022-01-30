import config.GlobalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.controller.TaskController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
    static Logger log = LoggerFactory.getLogger(ServerMain.class);
    public static void main(String[] args) {
        GlobalConfig config = GlobalConfig.getConfig();
        log.info("Config 정보 : {}", config);

        // server socket은 하나만 열어 둘거기 때문에 newSingleThreadExecutor 를 사용.
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new TaskController());

        executorService.shutdown();

    }
}
