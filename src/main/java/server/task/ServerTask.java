package server.task;

import exception.CustomException;

import java.io.*;

public interface ServerTask {
    void doProcess(BufferedReader reader, PrintWriter writer) throws IOException, CustomException;
}
