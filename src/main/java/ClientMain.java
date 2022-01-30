import config.GlobalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.task.TaskFlag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientMain {
    static Logger log = LoggerFactory.getLogger(ClientMain.class);
    public static void main(String[] args) {
        final String localhost = "127.0.0.1";
        try(Socket clientSocket = new Socket(localhost, GlobalConfig.getConfig().getServerSocketPort())){
            //https://stackoverflow.com/questions/47175526/try-with-multiple-resource-in-java
            try(PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ){

                out.append(TaskFlag.ECHO_TASK.getFlag());
                out.append("\n");
                out.println("12345");

                String line = "";
                while((line = in.readLine()) != null ){
                    log.debug(line);
                }
            }
        }catch (IOException e){
            log.error("연결실패");
        }


    }
}
