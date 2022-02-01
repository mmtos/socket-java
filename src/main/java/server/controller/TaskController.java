package server.controller;

import exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.MyServer;
import server.task.EchoTask;
import server.task.ServerTask;
import server.task.TaskFlag;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TaskController implements Runnable{
    static Logger log = LoggerFactory.getLogger(TaskController.class);
    private MyServer myServer;

    public TaskController() {
        this.myServer = new MyServer();
    }

    public TaskController(MyServer myServer) {
        this.myServer = myServer;
    }

    private ServerTask getSeverTask(TaskFlag task){
        switch (task){
            case ECHO_TASK:
                return new EchoTask();
        }
        return null;
    }

    private void doHandling(InputStream in, OutputStream out){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            PrintWriter writer = new PrintWriter(out, true);
        ) {
            //첫번째 라인의 값으로 task 판단
            String taskCode = reader.readLine();
            while(true){
                TaskFlag taskFlag = null;
                try {
                    taskFlag = TaskFlag.getTaskFlag(taskCode);
                } catch (UnsupportedOperationException e){
                    writer.append(MyServer.FAILURE + "\n");
                    writer.println(e.getMessage());
                }

                ServerTask serverTask = getSeverTask(taskFlag);

                if(serverTask != null){
                    try{
                        serverTask.doProcess(reader,writer);
                        writer.println(MyServer.SUCCESS);
                    }catch (CustomException e ){
                        log.warn("Task Handling 문제발생",e);
                        writer.append(MyServer.FAILURE + "\n");
                        writer.println(e.getMessage());
                    }
                }
                String nextMessage = reader.readLine();
                if(MyServer.TERMINATE_CODE.equals(nextMessage)) return;

                taskCode = nextMessage;
            }

        }catch (IOException e){
            log.error("BufferedReader 생성 실패...",e);
        }
    }

    @Override
    public void run() {
        while(true){
            try(Socket clientSocket = this.myServer.accept();) {
                doHandling(clientSocket.getInputStream(),clientSocket.getOutputStream());
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
