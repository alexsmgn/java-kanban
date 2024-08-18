package manager;

class InMemoryTaskManagerTest extends TaskManagerTests<InMemoryTaskManager> {

    InMemoryTaskManagerTest() {
        taskManager = new InMemoryTaskManager();
    }
}