public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        SubTask subTask1 = new SubTask(taskManager.nextId, "Прописка.", "Поставить штамп в паспорте.",
                Task.Status.NEW, taskManager.nextId);
        SubTask subTask2 = new SubTask(taskManager.nextId, "Договор.", "Подписать договор купли-продажи.",
                Task.Status.NEW, taskManager.nextId);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);

        Epic epic1 = new Epic(taskManager.nextId, "Покупка новой квартиры", "оформление документов.",
                Task.Status.NEW);
        epic1.subTaskIds.add(subTask1.getId());
        epic1.subTaskIds.add(subTask2.getId());
        taskManager.addEpic(epic1);

        SubTask subTask4 = new SubTask(taskManager.nextId, "Стены", "Поклеить обои", Task.Status.NEW,
                taskManager.nextId);
        taskManager.addSubTask(subTask4);

        Epic epic2 = new Epic(taskManager.nextId, "Ремонт", "Поменять обои" , Task.Status.NEW);
        epic2.subTaskIds.add(subTask4.getId());
        taskManager.addEpic(epic2);

        SimpleTask simpleTask1 = new SimpleTask(taskManager.nextId, "Посдстричь кошку",
                "Вызвать на понедельник парикмахера.", Task.Status.NEW);
        SimpleTask simpleTask2 = new SimpleTask(taskManager.nextId, "Ремонт тв." ,
                "Посвонить в ресвисный центр после 9:00.", Task.Status.NEW);
        taskManager.addTask(simpleTask1);
        taskManager.addTask(simpleTask2);


        SubTask subTask3 = new SubTask(subTask2.getId(), "Договор.", "Подписать договор купли-продажи.",
                Task.Status.IN_PROGRESS, epic1.getId());
        taskManager.update(subTask3);

        System.out.println("Список Epic задач: " + taskManager.epics);
        System.out.println("Список подзадач: " + taskManager.subTasks);
        System.out.println("Список задач: " + taskManager.simpleTasks);

        taskManager.delSubTaskById(3);

        System.out.println("Список подзадач: " + taskManager.subTasks);

        taskManager.tasksDel();

        System.out.println("Список задач: " + taskManager.simpleTasks);
        System.out.println("Список Epic задач: " + taskManager.epics);

        taskManager.delEpicById(3);

        System.out.println("Список Epic задач: " + taskManager.epics);

    }
}

