package server.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.MyServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

public abstract class AbstractServerTask implements Runnable {
    static Logger log = LoggerFactory.getLogger(AbstractServerTask.class);
    private MyServer myServer;

    protected abstract void doProcess(InputStream in, OutputStream out);

    public AbstractServerTask() {
        this.myServer = new MyServer();
    }

    public AbstractServerTask(MyServer myServer) {
        this.myServer = myServer;
    }

    @Override
    public void run() {
        while(true){
            try(Socket clientSocket = this.myServer.accept();) {
                doProcess(clientSocket.getInputStream(),clientSocket.getOutputStream());
            }catch (SocketTimeoutException e){
                log.warn("timeout 발생.. accept 재시도");
            }catch (IOException e) {
                log.error("accept 중 문제발생..",e);
            }catch (Exception e) {
                log.error("알수없는 오류 발생",e);
                new RuntimeException("알수없는 오류 발생",e);
            }
        }
    }
}
