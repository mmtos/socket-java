package client;

import config.GlobalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.MyServer;
import server.task.TaskFlag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyClient {
    static Logger log = LoggerFactory.getLogger(MyClient.class);
    private int port;
    private String host="127.0.0.1";
    private Socket clientSocket;

    public MyClient() {
        this.port = GlobalConfig.getConfig().getServerSocketPort();
        try {
            this.clientSocket = new Socket(this.host,this.port);
        } catch (IOException e) {
            log.error("Socket 연결 실패... host : {} port : {}",host,port);
            throw new RuntimeException("Socket 연결 실패",e);
        }
    }

    public String sendMessage(String message){
        StringBuilder response = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            writer.append(TaskFlag.ECHO_TASK.getFlag());
            writer.append("\n");
            writer.println(message);
            String firstLine = reader.readLine();
            response.append(firstLine);
            if (MyServer.FAILURE.equals(firstLine)) {
                response.append("\n");
                String failureInfo = reader.readLine();
                response.append(failureInfo);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return response.toString();
    }

    public void sendTerminateCode(){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            writer.println(MyServer.TERMINATE_CODE);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void close(){
        if (clientSocket != null && !clientSocket.isClosed()) {
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException("error for closing socket",e);
            }
        }
    }
}
