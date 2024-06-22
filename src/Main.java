import tasks.Task;
import tasks.SimpleTask;
import tasks.Epic;
import tasks.SubTask;
import manager.TaskManager;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();
        taskManager.addEpic(new Epic(taskManager.getNextId(), "Покупка новой квартиры" ,
                "оформление документов"));
        taskManager.addEpic(new Epic(taskManager.getNextId(), "Ремонт в квартире", "Ремонт в ванной"));

        taskManager.addSubTask(new SubTask(taskManager.getNextId(), "Прописка", "Поставить штамп в паспорте",
                1));
        taskManager.addSubTask(new SubTask(taskManager.getNextId(), "Договор",
                "Подписать договор купли-продажи", 1));
        taskManager.addSubTask(new SubTask(taskManager.getNextId(), "Плитка" , "Купить плитку на пол",
                2));
        taskManager.addSubTask(new SubTask(taskManager.getNextId(), "Ванна", "Заказать ванну", 2));

        taskManager.addTask(new SimpleTask(taskManager.getNextId(), "Подстричь кошку",
                "вызвать на понедельник парикмахера"));
        taskManager.addTask(new SimpleTask(taskManager.getNextId(), "Ремонт тв",
                "Позвонить в сервисный центр после 9:00"));









        taskManager.printSimpleTaskById(6);
        System.out.println(taskManager.printSubTasksByEpicId(1));




        System.out.println(taskManager.printAllSimpleTasks());
        System.out.println(taskManager.printAllEpics());
        System.out.println(taskManager.printAllSubTasks());



        taskManager.delSubTaskById(3);

        //System.out.println("Список подзадач: " + taskManager.subTasks);

        taskManager.tasksDel();

        //System.out.println("Список задач: " + taskManager.simpleTasks);
        //System.out.println("Список Epic задач: " + taskManager.epics);

        taskManager.delEpicById(3);

        //System.out.println("Список Epic задач: " + taskManager.epics);





    }
}

