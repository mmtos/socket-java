package server.task;

import exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class EchoTask implements ServerTask{
    static Logger log = LoggerFactory.getLogger(EchoTask.class);

    private boolean checkInputMessage(String message){
        return message.length() == 5;
    }

    private void echo(String message){
        log.info(message);
    }

    @Override
    public void doProcess(BufferedReader reader, PrintWriter writer) throws IOException{
        try {
            String inputs = reader.readLine();
            if(!checkInputMessage(inputs)) {
                throw new CustomException("커스텀 예외 발생..");
            }
            echo(inputs);
        }catch (CustomException e){
            log.warn("message 유효성 검사 실패 : {}", e);
        }
    }
}
