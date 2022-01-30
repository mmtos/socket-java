package server.task;

import exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class EchoTask extends AbstractServerTask implements Runnable{
    static Logger log = LoggerFactory.getLogger(EchoTask.class);

    private boolean checkInputMessage(String message){
        return message.length() == 5;
    }

    @Override
    protected void doProcess(InputStream in, OutputStream out) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            PrintWriter writer = new PrintWriter(out, true);
        ) {
            String inputs = reader.readLine();
            if(!checkInputMessage(inputs)) {
                throw new CustomException("커스텀 예외 발생..");
            }
            log.info(inputs);
            writer.println("통신 완료");
        }catch (CustomException e){
            log.warn("message 유효성 검사 실패 : {}", e);
        }catch (IOException e){
            log.error("BufferedReader 생성 실패...",e);
        }
    }
}
