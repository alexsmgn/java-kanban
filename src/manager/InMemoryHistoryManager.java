package manager;

import tasks.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InMemoryHistoryManager implements HistoryManager {

    public final LinkedList history = new LinkedList();

    @Override
    public List<Task> getHistory() {
        return history.getTaskList();
    }

    @Override
    public void add(Task task) {
        history.addLast(task);
    }

    @Override
    public void remove(int id) {
        if (!history.customMap.containsKey(id)) {
            return;
        }
        Node node = history.customMap.remove(id);
        history.removeNode(node);
    }
}

:class LinkedList {

    int id;
    private Node<Task> head;
    private Node<Task> tail;

    public HashMap<Integer, Node<Task>> customMap = new HashMap<>();

    public void addFirst(Task element) {
        final Node<Task> oldHead = head;
        final Node<Task> newNode = new Node<>(null, element, oldHead);
        head = newNode;
        if (oldHead == null)
            tail = newNode;
        else
            oldHead.prev = newNode;
    }

    public void addLast(Task element) {
        id = element.getId();
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(tail, element, null);
        tail = newNode;
        if (oldTail != null)
            oldTail.next = newNode;
        else
            head = newNode;
        customMap.put(id, newNode);
    }

    public List<Task> getTaskList() {
        List<Task> list = new ArrayList<>();
        Node<Task> element = head;
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

            node.prev = null;
            node.next = null;

            if (node == head) {
                head = next;
            }
            if (node == tail) {
                tail = prev;
            }
        }
    }
}



