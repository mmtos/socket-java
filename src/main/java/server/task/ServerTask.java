package server.task;

import java.io.*;

public interface ServerTask {
    void doProcess(BufferedReader reader, PrintWriter writer) throws IOException;
}
