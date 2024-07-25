package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LinkedList {

    private Node head;
    private Node tail;

    final HashMap<Integer, Node> customMap = new HashMap<>();

    public void addLast(Task task) {
        Node newNode = new Node(task, tail, null);
        if (task != null) {
            if (tail == null) {
                head = newNode;
            } else {
                tail.next = newNode;
            }
            tail = newNode;
            customMap.put(task.getId(), newNode);
        }
    }

    public List<Task> getTaskList() {
        List<Task> list = new ArrayList<>();
        Node element = head;
        while (element != null) {
            list.add(element.task);
            element = element.next;
        }
        return list;
    }

    public void removeNode(Node node) {
        if (node != null) {
            customMap.remove(node.task.getId());
            Node prev = node.prev;
            Node next = node.next;

            if (prev != null) {
                prev.next = next;
            }
            if (next != null) {
                next.prev = prev;
            }

            if (node == head) {
                head = next;
                if (head != null) {
                    head.prev = null;
                }
            }

            if (node == tail) {
                tail = prev;
                if (tail != null) {
                    tail.next = null;
                }
            }
        }
    }
}

