package duke.storage;

import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The object to read and store the file.
 */
public class Storage {
    private String filePath; // The file's path.

    /**
     * Initiates the object.
     *
     * @param filePath The file's path.
     */
    public Storage(String filePath) {
        assert filePath != "";
        this.filePath = filePath;
    }

    /**
     * Loads the file.
     *
     * @return Tasks loaded from the file.
     * @throws DukeException When cannot found file or format incorrect.
     * @throws FileNotFoundException Would not happen.
     */
    public ArrayList<Task> load() throws DukeException, FileNotFoundException {
        ArrayList<Task> tasks = new ArrayList<Task>();

        File f = new File(filePath);
        if (!f.exists()) {
            throw new DukeException("File not found.");
        }

        Scanner readFileScanner = new Scanner(f);
        while (readFileScanner.hasNext()) {
            String[] todoTask = readFileScanner.nextLine().split(" \\| ");
            switch (todoTask[0]) {
            case "T":
                Task task1 = new Todo(todoTask[2]);
                if (todoTask[1].equals("1")) {
                    task1.markAsDone();
                }
                tasks.add(task1);
                break;
            case "D":
                Task task2 = new Deadline(todoTask[2], todoTask[3]);
                if (todoTask[1].equals("1")) {
                    task2.markAsDone();
                }
                tasks.add(task2);
                break;
            case "E":
                Task task3 = new Event(todoTask[2], todoTask[3]);
                if (todoTask[1].equals("1")) {
                    task3.markAsDone();
                }
                tasks.add(task3);
                break;
            default:
                throw new DukeException("Something in file go wrong.");
            }
        }
        readFileScanner.close();

        return tasks;
    }

    /**
     * Saves tasks to file.
     *
     * @param tasks The tasks to save.
     * @throws DukeException When file does not exist.
     */
    public void saveFile(ArrayList<Task> tasks) throws DukeException {
        try {
            FileWriter fw = new FileWriter(filePath);
            String data = tasks.get(0).toFile();
            if (tasks.size() > 1) {
                for (int i = 1; i < tasks.size(); i++) {
                    data = data + System.lineSeparator() + tasks.get(i).toFile();
                }
            }
            fw.write(data);
            fw.close();
        } catch (IOException e) {
            throw new DukeException("No such file.");
        }
    }
}
