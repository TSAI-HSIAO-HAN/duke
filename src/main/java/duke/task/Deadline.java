package duke.task;

import duke.exception.DukeException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The deadline task the user does.
 */
public class Deadline extends Task {
    protected Date time; //The deadline.

    /**
     * Initiates the object.
     *
     * @param description The description of the deadline.
     * @param time The time of the deadline.
     * @throws DukeException When time is in wrong format.
     */
    public Deadline(String description, Date time) throws DukeException {
        super(description);
        this.type = Type.D;
        this.time = time;

    }

    /**
     * Turns deadline to string.
     *
     * @return Deadline in string.
     */
    @Override
    public String toString() {
        return String.format("%s (by: %s)", super.toString(), time);
    }

    /**
     * Turns deadline to file format.
     *
     * @return Deadline in file format.
     */
    @Override
    public String toFile() {
        String doneState = isDone ? "1" : "0";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        return String.format("%s | %s | %s | %s", type, doneState, description, formatter.format(time));
    }
}
