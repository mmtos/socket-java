package server.task;

import exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class EchoTask implements ServerTask{
    static final Logger log = LoggerFactory.getLogger(EchoTask.class);

    private boolean checkInputMessage(String message){
        return message.length() == 5;
    }

    private void echo(String message){
        log.info(message);
    }

    @Override
    public void doProcess(BufferedReader reader, PrintWriter writer) throws IOException, CustomException{
        String inputs = reader.readLine();
        if(!checkInputMessage(inputs)) {
            throw new CustomException("message의 길이가 5자여야 합니다.");
        }
        echo(inputs);
    }
}
