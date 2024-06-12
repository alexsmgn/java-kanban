public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        Epic epic1 = new Epic(taskManager.getNextId(), "Покупка новой квартиры", "оформление документов.");

        taskManager.addEpic(epic1);
        //taskManager.addSubTask(new SubTask(taskManager.getNextId(), "Прописка",
                //"Поставить штамп в паспорте.", epic1.getId()));
        SubTask subTask1 = new SubTask(taskManager.getNextId(), "Прописка.", "Поставить штамп в паспорте.",
                epic1.getId());
        SubTask subTask2 = new SubTask(taskManager.getNextId(), "Договор.", "Подписать договор купли-продажи.",
                epic1.getId());
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);

        Epic epic2 = new Epic(taskManager.getNextId(), "Ремонт", "Поменять обои");

        taskManager.addEpic(epic2);

        SubTask subTask4 = new SubTask(taskManager.getNextId(), "Стены", "Поклеить обои", epic2.getId());
        taskManager.addSubTask(subTask4);

        SimpleTask simpleTask1 = new SimpleTask(taskManager.getNextId(), "Посдстричь кошку",
                "Вызвать на понедельник парикмахера.");
        SimpleTask simpleTask2 = new SimpleTask(taskManager.getNextId(), "Ремонт тв." ,
                "Посвонить в ресвисный центр после 9:00.");
        taskManager.addTask(simpleTask1);
        taskManager.addTask(simpleTask2);


        SubTask subTask3 = new SubTask(subTask2.getId(), "Договор.", "Подписать договор купли-продажи.",
                epic1.getId());
        subTask3.setStatus(Task.Status.IN_PROGRESS);
        taskManager.updateSubTask(subTask3);

        System.out.println(taskManager.printAllSimpleTasks());
        System.out.println(taskManager.printAllEpics());
        System.out.println(taskManager.printAllSubTasks());



        taskManager.delSubTaskById(3);

        System.out.println("Список подзадач: " + taskManager.subTasks);

        taskManager.tasksDel();

        System.out.println("Список задач: " + taskManager.simpleTasks);
        System.out.println("Список Epic задач: " + taskManager.epics);

        taskManager.delEpicById(3);

        System.out.println("Список Epic задач: " + taskManager.epics);





    }
}

