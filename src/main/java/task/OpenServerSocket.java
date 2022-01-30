package task;

import config.GlobalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

public class OpenServerSocket implements Runnable{
    static Logger log = LoggerFactory.getLogger(OpenServerSocket.class);
    @Override
    public void run() {
        GlobalConfig config = GlobalConfig.getConfig();
        try(ServerSocket serverSocket = new ServerSocket(config.getServerSocketPort(),config.getQueueSize())){
            log.info(serverSocket.toString());
        }catch (IOException e){
            log.error("ServerSocket 생성 실패...{}",e);
        }
    }
}
