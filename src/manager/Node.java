package manager;
import tasks.Task;
public class Node <T> {

    public tasks.Task task;
    public Node<tasks.Task> prev;
    public Node<tasks.Task> next;

    public Node(Node<tasks.Task> prev, Task task, Node<tasks.Task> next) {
        this.task = task;
        this.prev = prev;
        this.next = next;
    }
}
