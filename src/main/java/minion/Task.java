package minion;

public class Task {
    protected String description;
    protected boolean isDone;
    protected static int totalNumberOfTasks = 0;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        totalNumberOfTasks++;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public String getDescription() {
        return this.description;
    }

    public static int getTotalNumberOfTasks(){
        return totalNumberOfTasks;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }
}