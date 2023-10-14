package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.gradedtest.GradedTest;
import seedu.address.model.gradedtest.GradedTestList;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final AddressBook addressBook;
    private final TaskListBook taskList;
    private final GradedTestListBook gradedTestList;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Task> filteredTasks;
    private FilteredList<GradedTest> filteredGradedTest;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs,
                        ReadOnlyTaskList taskList, ReadOnlyGradedTestList gradedTestList) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.taskList = new TaskListBook(taskList);
        this.userPrefs = new UserPrefs(userPrefs);
        this.gradedTestList = new GradedTestListBook(gradedTestList);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredTasks = new FilteredList<>(this.taskList.getTaskList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(),
                new TaskListBook(), new GradedTestListBook());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public Path getTaskListFilePath() {
        return userPrefs.getTaskListFilePath();
    }

    @Override
    public void setTaskListFilePath(Path taskListFilePath) {
        requireNonNull(taskListFilePath);
        userPrefs.setTaskListFilePath(taskListFilePath);
    }

    @Override
    public Path getGradedTestListFilePath() {
        return userPrefs.getGradedTestListFilePath();
    }

    @Override
    public void setGradedTestListFilePath(Path gradedTestListFilePath) {
        requireNonNull(gradedTestListFilePath);
        userPrefs.setGradedTestListFilePath(gradedTestListFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }


    //=========== TaskListBook ================================================================================

    @Override
    public void setTaskList(ReadOnlyTaskList taskList) {
        this.taskList.resetData(taskList);
    }

    @Override
    public ReadOnlyTaskList getTaskList() {
        return taskList;
    }

    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return taskList.hasTask(task);
    }

    @Override
    public Task getTask(int index) {
        return taskList.getTask(index);
    }

    @Override
    public void deleteTask(Task target) {
        taskList.removeTask(target);
    }

    @Override
    public void addTask(Task task) {
        taskList.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        taskList.setTask(target, editedTask);
    }

    //=========== Graded Test List Book  ===============================================================
    @Override
    public void setGradedTestList(ReadOnlyGradedTestList gradedTestList) {
        this.gradedTestList.resetData(gradedTestList);
    }

    @Override
    public ReadOnlyGradedTestList getGradedTestList() {
        return gradedTestList;
    }

    /**
     * Returns true if a task with the same identity as {@code gradedTest} exists in the gradedTest.
     */
    public boolean hasGradedTest(GradedTest gradedTest) {
        requireNonNull(gradedTest);
        return gradedTestList.hasGradedTest(gradedTest);
    }

    @Override
    public GradedTest getGradedTest(int index) {
        return gradedTestList.getGradedTest(index);
    }

    @Override
    public void deleteGradedTest(GradedTest target) {
        gradedTestList.removeGradedTest(target);
    }

    /**
     * Adds the given gradedTest.
     * {@code gradedTest} must not already exist in the gradedTest list.
     */
    public void addGradedTest(GradedTest gradedTest) {
        gradedTestList.addGradedTest(gradedTest);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void setGradedTest(GradedTest target, GradedTest editedGradedTest) {
        requireAllNonNull(target, editedGradedTest);

        gradedTestList.setGradedTests(target, editedGradedTest);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }


    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && taskList.equals(otherModelManager.taskList)
                && filteredTasks.equals(otherModelManager.filteredTasks)
                && filteredGradedTest.equals(otherModelManager.filteredGradedTest);
    }

    //=========== Filtered GradedTest List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedAddressBook}
     */

    public ObservableList<GradedTest> getFilteredGradedTestList() {
        return filteredGradedTest;
    }

    public void updateFilteredGradedTest(Predicate<GradedTest> predicate) {
        requireNonNull(predicate);
        filteredGradedTest.setPredicate(predicate);
    }
}
