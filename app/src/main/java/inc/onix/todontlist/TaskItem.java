package inc.onix.todontlist;

/**
 * Created by Anik on 4/13/2016.
 */
public class TaskItem {
    String Task;
    String Cause;
    int rowId;

    @Override
    public String toString() {
        return "TaskItem{" +
                "Task='" + Task + '\'' +
                ", Cause='" + Cause + '\'' +
                ", rowId=" + rowId +
                '}';
    }

    public TaskItem(String task, String cause, int rowId) {
        Task = task;
        Cause = cause;
        this.rowId = rowId;
    }



    public TaskItem(String task, String cause) {
        Task = task;
        Cause = cause;
    }

}
