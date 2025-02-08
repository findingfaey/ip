package arin.storage;

import arin.task.Task;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading and saving tasks to a file.
 */
public class Storage {

    private String filePath;

    /**
     * Creates a Storage object with the specified file path.
     *
     * @param filePath The file path where tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the specified file and returns them as an ArrayList.
     *
     * @return An ArrayList of tasks loaded from the file.
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (!file.exists()) return tasks;
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String taskData = scanner.nextLine();
                Task task = Task.parseTask(taskData);
                tasks.add(task);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found, starting with an empty list.");
        }
        return tasks;
    }

    /**
     * Saves the given list of tasks to a file.
     *
     * @param tasks The list of tasks to be saved.
     */
    public void saveTasks(ArrayList<Task> tasks) {
        try (PrintWriter writer = new PrintWriter(new File(filePath))) {
            for (Task task : tasks) {
                writer.println(task.toSaveString());
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks to file.");
        }
    }
}
