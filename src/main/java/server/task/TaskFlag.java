package server.task;

import java.util.EnumSet;

public enum TaskFlag {
    ECHO_TASK("A"),NULL_TASK("");

    String flag;

    TaskFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public static TaskFlag getTaskFlag(String flag) throws UnsupportedOperationException {
        EnumSet<TaskFlag> tasks = EnumSet.allOf(TaskFlag.class);
        for (TaskFlag task : tasks) {
            if (task.flag.equals(flag)) return task;
        }
        throw new UnsupportedOperationException("can't find the task flag");
    }
}
