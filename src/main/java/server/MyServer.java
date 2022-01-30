package server;

import config.GlobalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.task.EchoTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class MyServer {
    static Logger log = LoggerFactory.getLogger(MyServer.class);
    private int port;
    private int backlog;
    private int timeout;
    private ServerSocket serverSocket;

    private void initServerSocket(){
        try{
            this.serverSocket = new ServerSocket(port,backlog);
        } catch (IOException e) {
            log.error("ServerSocket 생성 실패... port : {}, backlog : {}",port,backlog);
            throw new RuntimeException("ServerSocket 생성 실패...",e);
        }
        try {
            this.serverSocket.setSoTimeout(timeout);
        } catch (SocketException e) {
            log.error("timeout 세팅 실패... port : {}, backlog : {}, timeout: {}",port,backlog,timeout);
            throw new RuntimeException("timeout 세팅 실패... ",e);
        }
    }

    public MyServer() {
        this.port = GlobalConfig.getConfig().getServerSocketPort();
        this.backlog = GlobalConfig.getSingleInstance().getQueueSize();
        this.timeout = GlobalConfig.getSingleInstance().getTimeout();
        this.initServerSocket();
    }

    public MyServer(int port) {
        this.port = port;
        this.backlog = GlobalConfig.getSingleInstance().getQueueSize();
        this.timeout = GlobalConfig.getSingleInstance().getTimeout();
        this.initServerSocket();
    }

    public MyServer(int port, int backlog){
        this.port = port;
        this.backlog = backlog;
        this.timeout = GlobalConfig.getSingleInstance().getTimeout();
        this.initServerSocket();
    }

    public MyServer(int port, int backlog, int timeout) {
        this.port = port;
        this.backlog = backlog;
        this.timeout = timeout;
        this.initServerSocket();
    }

    public Socket accept() throws IOException{
        return serverSocket.accept();
    }

    public void close() {
        if(serverSocket != null && !serverSocket.isClosed()){
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException("error for closing server socket",e);
            }
        }
    }
}