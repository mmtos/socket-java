package task;

import config.GlobalConfig;
import exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class OpenServerSocket implements Runnable{
    static Logger log = LoggerFactory.getLogger(OpenServerSocket.class);

    private boolean checkInputMessage(String message){
        return message.length() == 5;
    }
    private void doProcess(String message){
        // 단순히 로그만 찍습니다.
        log.info(message);
    }
    @Override
    public void run() {
        GlobalConfig config = GlobalConfig.getConfig();
        try(ServerSocket serverSocket = new ServerSocket(config.getServerSocketPort(),config.getQueueSize())){
            log.info(serverSocket.toString());
            serverSocket.setSoTimeout(config.getTimeout());
            while(true) {
                try(Socket socket = serverSocket.accept()){
                    try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    ) {
                        String inputs = in.readLine();
                        if(!checkInputMessage(inputs)) {
                            throw new CustomException("커스텀 예외 발생..");
                        }
                        doProcess(inputs);
                        out.println("통신 완료");
                    }catch (CustomException e){
                        log.warn("message 유효성 검사 실패 : {}", e);
                    }
                }catch (SocketTimeoutException e){
                    log.warn("timeout 발생.. accept 재시도");
                }
            }
        }catch (IOException e){
            log.error("ServerSocket 생성 실패...",e);
        }
    }
}
