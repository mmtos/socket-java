import config.GlobalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerMain {
    static Logger log = LoggerFactory.getLogger(ServerMain.class);
    public static void main(String[] args) {
        GlobalConfig config = GlobalConfig.getConfig();
        System.out.println(config);
    }
}
