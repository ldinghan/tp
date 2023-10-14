package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.gradedtest.GradedTest;
import seedu.address.model.GradedTestListBook;
import seedu.address.model.ReadOnlyGradedTestList;

/**
 * An Immutable TaskList that is serializable to JSON format.
 */
@JsonRootName(value = "gradedTestList")
public class JsonSerializableGradedTestList {

    public static final String MESSAGE_DUPLICATE_TASK = "Task list contains duplicate gradedTest(s).";

    private final List<JsonAdaptedGradedTest> gradedTests = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableGradedTestList} with the given gradedTest.
     */
    @JsonCreator
    public JsonSerializableGradedTestList(@JsonProperty("gradedTests")
            List<JsonAdaptedGradedTest> gradedTests) {
        this.gradedTests.addAll(gradedTests);
    }

    /**
     * Converts a given {@code ReadOnlyGradedTestList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableGradedTestList}.
     */
    public JsonSerializableGradedTestList(ReadOnlyGradedTestList source) {
        gradedTests.addAll(source.getGradedTestList().stream().map(JsonAdaptedGradedTest::new).collect(Collectors.toList()));
    }

    /**
     * Converts this gradedTest list into the model's {@code GradedTestListBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public GradedTestListBook toModelType() throws IllegalValueException {
        GradedTestListBook gradedTestList = new GradedTestListBook();
        for (JsonAdaptedGradedTest jsonAdaptedGradedTest : gradedTests) {
            GradedTest gradedTest = jsonAdaptedGradedTest.toModelType();
            if (gradedTestList.hasGradedTest(gradedTest)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASK);
            }
            gradedTestList.addGradedTest(gradedTest);
        }
        return gradedTestList;
    }
}
