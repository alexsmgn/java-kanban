package manager;

import tasks.Task;

public class Node {

    public final Task task;
    public Node prev;
    public Node next;

    public Node(Task task, Node prev, Node next) {
        this.task = task;
        this.prev = prev;
        this.next = next;
    }
}
