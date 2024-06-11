import java.util.ArrayList;

public class Epic extends Task {

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void setSubTaskIds(ArrayList<Integer> subTaskIds) {
        this.subTaskIds = subTaskIds;
    }

    ArrayList<Integer> subTaskIds = new ArrayList<>();


    public Epic(int id, String title, String target, Status status) {
        super(id, title, target, status);

    }


}
