package config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class GlobalConfig {
    static Logger log = LoggerFactory.getLogger(GlobalConfig.class);
    private final String configFilePath = "target/classes/global-config.yml";

    private static GlobalConfig singleInstance = new GlobalConfig();
    private final int serverSocketPort;
    private final int clientSocketPort;
    private final int queueSize;
    private final int timeout;

    private GlobalConfig() {
        //https://github.com/FasterXML/jackson-dataformats-text/tree/2.14/yaml
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Map configMap = null;
        try(FileInputStream fis = new FileInputStream(configFilePath)){
            configMap = mapper.readValue(fis, Map.class);
        }catch (FileNotFoundException e){
            log.error("global-config.yml 파일을 찾을 수 없습니다. ");
        }catch (IOException e){
            log.error("global-config.yml 파일 Read 실패");
        }
        if(log.isDebugEnabled() && configMap != null){
            log.debug(configMap.toString());
        }
        Map server = (Map) configMap.get("server");
        Map serverSocket = (Map) server.get("socket");

        this.serverSocketPort = (int) serverSocket.get("port");
        this.queueSize = (int) serverSocket.get("queuesize");
        this.timeout = (int) serverSocket.get("timeout");

        Map client = (Map) configMap.get("client");
        Map clientSocket = (Map) client.get("socket");
        this.clientSocketPort = (int) clientSocket.get("port");

    }

    public static GlobalConfig getConfig(){
        return singleInstance;
    }

    public static GlobalConfig getSingleInstance() {
        return singleInstance;
    }

    public int getServerSocketPort() {
        return serverSocketPort;
    }

    public int getClientSocketPort() {
        return clientSocketPort;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public int getTimeout() {
        return timeout;
    }


    @Override
    public String toString() {
        return "GlobalConfig{" +
                "serverSocketPort=" + serverSocketPort +
                ", clientSocketPort=" + clientSocketPort +
                ", queueSize=" + queueSize +
                ", timeout=" + timeout +
                '}';
    }
}
