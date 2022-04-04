package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JERSEY_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.util.Comparator;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE_PLAYER = COMMAND_WORD
            + ": To sort player\n"
            + "Parameters: "
            + PREFIX_HEIGHT + "ORDER or "
            + PREFIX_WEIGHT + "ORDER or "
            + PREFIX_JERSEY_NUMBER + "ORDER\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_HEIGHT + "asc";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sort players based on the specified criteria.\n"
            + MESSAGE_USAGE_PLAYER;

    private final Prefix itemList;
    private final Prefix sortingCriteria;
    private final String sortingOrder;

    /**
     * Constructs a {@code SortCommand}
     */
    public SortCommand(Prefix itemList, Prefix sortingCriteria, String sortingOrder) {
        requireAllNonNull(itemList, sortingCriteria, sortingOrder);
        this.itemList = itemList;
        this.sortingCriteria = sortingCriteria;
        this.sortingOrder = sortingOrder;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (itemList.equals(PREFIX_PLAYER)) {
            performSort(model);
            String criteria = "";
            String order = "";
            if (sortingCriteria.getPrefix().equals("h/")) {
                criteria = "height";
            }
            if (sortingCriteria.getPrefix().equals("w/")) {
                criteria = "weight";
            }
            if (sortingCriteria.getPrefix().equals("j/")) {
                criteria = "jersey";
            }
            order = sortingOrder.equals("asc") ? "ascending" : "descending";
            return new CommandResult(String.format(Messages.MESSAGE_SORTED, criteria, order));
        }
        throw new CommandException(MESSAGE_USAGE);
    }

    private void performSort(Model model) {
        if (sortingCriteria.equals(PREFIX_HEIGHT)) {
            if (sortingOrder.equals("asc")) {
                model.sortPersonsInMyGM(Comparator.comparing(Person::getHeight));
            } else {
                model.sortPersonsInMyGM((Person person1, Person person2) ->
                        person2.getHeight().compareTo(person1.getHeight()));
            }
        }

        if (sortingCriteria.equals(PREFIX_WEIGHT)) {
            if (sortingOrder.equals("asc")) {
                model.sortPersonsInMyGM(Comparator.comparing(Person::getWeight));
            } else {
                model.sortPersonsInMyGM((Person person1, Person person2) ->
                        person2.getWeight().compareTo(person1.getWeight())
                );
            }
        }

        if (sortingCriteria.equals(PREFIX_JERSEY_NUMBER)) {
            if (sortingOrder.equals("asc")) {
                model.sortPersonsInMyGM(Comparator.comparing(Person::getJerseyNumber)
                );
            } else {
                model.sortPersonsInMyGM((Person person1, Person person2) ->
                        person2.getJerseyNumber().compareTo(person1.getJerseyNumber())
                );
            }
        }
    }
}