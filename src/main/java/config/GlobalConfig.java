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
    private final String serverSocketPort;
    private final String clientSocketPort;
    private final String queueSize;
    private final String timeout;


    private GlobalConfig() {
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
        Map<String,String> serverSocket = (Map) server.get("socket");

        this.serverSocketPort = serverSocket.get("port");
        this.queueSize = serverSocket.get("queuesize");
        this.timeout = serverSocket.get("timeout");

        Map client = (Map) configMap.get("client");
        Map<String,String> clientSocket = (Map) client.get("socket");
        this.clientSocketPort = clientSocket.get("port");

    }

    public static GlobalConfig getConfig(){
        return singleInstance;
    }

    public String getServerSocketPort() {
        return serverSocketPort;
    }

    public String getConfigFilePath() {
        return configFilePath;
    }

    public String getClientSocketPort() {
        return clientSocketPort;
    }

    public String getQueueSize() {
        return queueSize;
    }

    public String getTimeout() {
        return timeout;
    }

    @Override
    public String toString() {
        return "GlobalConfig{" +
                ", serverSocketPort='" + serverSocketPort + '\'' +
                ", clientSocketPort='" + clientSocketPort + '\'' +
                ", queueSize='" + queueSize + '\'' +
                ", timeout='" + timeout + '\'' +
                '}';
    }
}
