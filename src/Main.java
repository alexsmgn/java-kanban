import manager.InMemoryHistoryManager;
import manager.Managers;
import manager.TaskManager;
import tasks.Task;
import tasks.SimpleTask;
import tasks.Epic;
import tasks.SubTask;
import manager.InMemoryTaskManager;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        inMemoryTaskManager.addEpic(new Epic("Покупка новой квартиры", "оформление документов"));
        inMemoryTaskManager.addEpic(new Epic("Ремонт в квартире", "Ремонт в ванной"));

        inMemoryTaskManager.addSubTask(new SubTask("Прописка", "Поставить штамп в паспорте", 1));
        inMemoryTaskManager.addSubTask(new SubTask("Договор", "Подписать договор купли-продажи", 1));
        inMemoryTaskManager.addSubTask(new SubTask("Плитка", "Купить плитку на пол", 2));
        inMemoryTaskManager.addSubTask(new SubTask("Ванна", "Заказать ванну", 2));

        inMemoryTaskManager.addTask(new SimpleTask("Подстричь кошку", "вызвать на понедельник парикмахера"));
        inMemoryTaskManager.addTask(new SimpleTask("Ремонт тв", "Позвонить в сервисный центр после 9:00"));


        System.out.println("список всех эпиков");
        for (Epic epic : inMemoryTaskManager.printAllEpics()) {
            System.out.println(epic);
        }

        System.out.println("список всех подзадач");
        for (SubTask subTask : inMemoryTaskManager.printAllSubTasks()) {
            System.out.println(subTask);
        }

        System.out.println("список всех задач");
        for (Task simpleTask : inMemoryTaskManager.printAllSimpleTasks()) {
            System.out.println(simpleTask);
        }

        System.out.println("список подзадач конкретного эпика");
        for (SubTask subTask : inMemoryTaskManager.printSubTasksByEpicId(1)) {
            System.out.println(subTask);
        }

        System.out.println("Печать задачи по ИД");
        SimpleTask task = inMemoryTaskManager.printSimpleTaskById(7);
        System.out.println(task);

        System.out.println("Печать эпика по ИД");
        Epic epic = inMemoryTaskManager.printEpicById(2);
        System.out.println(epic);

        System.out.println("Печать подзадачи по ИД");
        SubTask subTask = inMemoryTaskManager.printSubTaskById(3);
        System.out.println(subTask);

        System.out.println("Печать истории");
        for (Task history : inMemoryTaskManager.getHistory()) {
            System.out.println(history);
        }

        System.out.println("Печать задачи по ИД");
        SimpleTask task1 = inMemoryTaskManager.printSimpleTaskById(7);
        System.out.println(task1);

        System.out.println("Печать истории");
        for (Task history : inMemoryTaskManager.getHistory()) {
            System.out.println(history);
        }

        System.out.println("удаление задачи по ИД");
        inMemoryTaskManager.delSimpleTaskById(7);
        System.out.println("список всех задач");
        for (Task simpleTask : inMemoryTaskManager.printAllSimpleTasks()) {
            System.out.println(simpleTask);
        }

        System.out.println("удаление подзадачи по ИД");
        inMemoryTaskManager.delSubTaskById(3);
        System.out.println("список всех подзадач");
        for (SubTask subTask1 : inMemoryTaskManager.printAllSubTasks()) {
            System.out.println(subTask1);
        }

        System.out.println("удаление эпика по ИД");
        inMemoryTaskManager.delEpicById(1);
        System.out.println("список всех эпиков");
        for (Epic epic1 : inMemoryTaskManager.printAllEpics()) {
            System.out.println(epic1);
        }

        System.out.println("Печать истории");
        for (Task history : inMemoryTaskManager.getHistory()) {
            System.out.println(history);
        }

    }


}

