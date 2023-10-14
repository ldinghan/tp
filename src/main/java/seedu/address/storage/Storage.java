package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.*;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage,
        TaskListStorage, GradedTestListStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    Optional<ReadOnlyTaskList> readTaskList() throws DataLoadingException;

    @Override
    void saveTaskList(ReadOnlyTaskList taskList) throws IOException;

    @Override
    Optional<ReadOnlyGradedTestList> readGradedTestList() throws DataLoadingException;

    @Override
    void saveGradedTestList(ReadOnlyGradedTestList gradedTestList, Path filePath) throws IOException;

}
