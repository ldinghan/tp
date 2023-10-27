package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Shows a list of assignment names.
 */
public class ViewAllAssignmentsCommand extends Command {

    public static final String COMMAND_WORD = "viewallassignments";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a list of assignment names.";

    public static final String MESSAGE_SUCCESS = "Showing all assignment names";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, true);
    }
}
