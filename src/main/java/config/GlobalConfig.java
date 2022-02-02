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
    static final Logger log = LoggerFactory.getLogger(GlobalConfig.class);
    private final String configFilePath = "target/classes/global-config.yml";

    private static final GlobalConfig singleInstance = new GlobalConfig();
    private final int serverSocketPort;
    private final int queueSize;
    private final int timeout;
    private final int taskThreadCnt;


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

        Map server = (Map) configMap.get("server");
        Map serverSocket = (Map) server.get("socket");

        this.serverSocketPort = (int) serverSocket.get("port");
        this.queueSize = (int) serverSocket.get("queuesize");
        this.timeout = (int) serverSocket.get("timeout");
        this.taskThreadCnt = (int) serverSocket.get("taskthread");

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

    public int getQueueSize() {
        return queueSize;
    }

    public int getTimeout() {
        return timeout;
    }

    public int getTaskThreadCnt() {
        return taskThreadCnt;
    }

    @Override
    public String toString() {
        return "GlobalConfig{" +
                "serverSocketPort=" + serverSocketPort +
                ", queueSize=" + queueSize +
                ", timeout=" + timeout +
                ", taskThreadCnt=" + taskThreadCnt +
                '}';
    }
}
