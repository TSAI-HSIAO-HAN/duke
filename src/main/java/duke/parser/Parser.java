package duke.parser;

import duke.command.*;
import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The parser of the user command.
 */
public class Parser {

    /**
     * Parses the input command.
     *
     * @param fullCommand The full string of user's command.
     * @return The Command object to react.
     * @throws DukeException When input is in wrong format.
     */
    public static Command parse(String fullCommand) throws DukeException {
        if (fullCommand.equals("bye")) {
            return new ExitCommand();
        } else if (fullCommand.equals("list")) {
            return new ListCommand();
        } else if (fullCommand.startsWith("done")) {
            char num = fullCommand.charAt(fullCommand.length() - 1);
            int index = Character.getNumericValue(num);
            return  new DoneCommand(index);
        } else if (fullCommand.startsWith("delete")) {
            char num = fullCommand.charAt(fullCommand.length() - 1);
            int index = Character.getNumericValue(num);
            return new DeleteCommand(index);
        } else if (fullCommand.startsWith("todo")) {
            if (fullCommand.trim().length() == 4) {
                throw new DukeException(" ☹ OOPS!!! The description of a todo cannot be empty.");
            }
            String descrip = fullCommand.substring(5);
            Task task = new Todo(descrip);
            return new AddCommand(task);
        } else if (fullCommand.startsWith("event")) {
            if (fullCommand.trim().length() == 5) {
                throw new DukeException(" ☹ OOPS!!! The description of a event cannot be empty.");
            }
            String[] event = fullCommand.substring(6).split(" /at ");
            if (event.length != 2) {
                throw new DukeException(" ☹ OOPS!!! The description of a event is wrong.");
            }
            Task task = new Event(event[0], event[1]);
            return new AddCommand(task);
        } else if (fullCommand.startsWith("deadline")) {
            if (fullCommand.trim().length() == 8) {
                throw new DukeException(" ☹ OOPS!!! The description of a deadline cannot be empty.");
            }
            String[] event = fullCommand.substring(9).split(" /by ");
            if (event.length != 2) {
                throw new DukeException(" ☹ OOPS!!! The description of a event is wrong.");
            }
            Date actualTime = stringToDate(event[1]);
            Task task = new Deadline(event[0], actualTime);
            return new AddCommand(task);

        } else if (fullCommand.startsWith("find")) {
            String keyword = fullCommand.substring(5);
            return new FindCommand(keyword);
        } else {
            throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    public static Date stringToDate(String dateInString) throws DukeException {
        Date actualTime;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
            actualTime = formatter.parse(dateInString);
        } catch (ParseException e) {
            throw new DukeException("Time format wrong");
        }

        return actualTime;
    }

}
