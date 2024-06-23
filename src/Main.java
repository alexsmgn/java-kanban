import tasks.Task;
import tasks.SimpleTask;
import tasks.Epic;
import tasks.SubTask;
import manager.TaskManager;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();
        taskManager.addEpic(new Epic("Покупка новой квартиры" ,"оформление документов"));
        taskManager.addEpic(new Epic("Ремонт в квартире", "Ремонт в ванной"));

        taskManager.addSubTask(new SubTask("Прописка", "Поставить штамп в паспорте",1));
        taskManager.addSubTask(new SubTask("Договор","Подписать договор купли-продажи", 1));
        taskManager.addSubTask(new SubTask("Плитка" , "Купить плитку на пол",2));
        taskManager.addSubTask(new SubTask("Ванна", "Заказать ванну", 2));

        taskManager.addTask(new SimpleTask("Подстричь кошку","вызвать на понедельник парикмахера"));
        taskManager.addTask(new SimpleTask("Ремонт тв","Позвонить в сервисный центр после 9:00"));


        System.out.println("список всех эпиков");
        for (Epic epic : taskManager.printAllEpics()) {
            System.out.println(epic);
        }

        System.out.println("список всех подзадач");
        for (SubTask subTask : taskManager.printAllSubTasks()) {
            System.out.println(subTask);
        }

        System.out.println("список всех задач");
        for (Task simpleTask : taskManager.printAllSimpleTasks()) {
            System.out.println(simpleTask);
        }

        System.out.println("список подзадач конкретного эпика");
        for (SubTask subTask : taskManager.printSubTasksByEpicId(1)) {
            System.out.println(subTask);
        }

        System.out.println("удаление задачи по ИД");
        taskManager.delSimpleTaskById(7);
        System.out.println("список всех задач");
        for (Task simpleTask : taskManager.printAllSimpleTasks()) {
            System.out.println(simpleTask);
        }

        System.out.println("удаление подзадачи по ИД");
        taskManager.delSubTaskById(3);
        System.out.println("список всех подзадач");
        for (SubTask subTask : taskManager.printAllSubTasks()) {
            System.out.println(subTask);
        }

        System.out.println("удаление эпика по ИД");
        taskManager.delEpicById(1);
        System.out.println("список всех эпиков");
        for(Epic epic: taskManager.printAllEpics()) {
            System.out.println(epic);
        }













    }
}

