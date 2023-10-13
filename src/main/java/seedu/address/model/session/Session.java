package seedu.address.model.session;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;

import seedu.address.model.person.Person;

/**
 * Represents a class for managing a session, which can hold a list of students and session-specific details.
 */
public class Session {
    private final int sessionNumber;
    private Set<Person> students;

    /**
     * Creates a session with a session number and an initial set of students.
     *
     * @param sessionNumber The unique identifier for this session.
     * @param presentStudents The set of students present in this session.
     */
    public Session(int sessionNumber, Set<Person> presentStudents) {
        requireAllNonNull(sessionNumber, presentStudents);
        this.sessionNumber = sessionNumber;
    }

    /**
     * Creates a session with a session number and a single student. Useful for adding students individually.
     *
     * @param sessionNumber The unique identifier for this session.
     * @param student The student to add to this session.
     */
    public Session(int sessionNumber, Person student) {
        requireAllNonNull(sessionNumber, student);
        this.sessionNumber = sessionNumber;
        this.students.add(student);
    }

    /**
     * Creates a session with just a session number. Useful for creating empty sessions.
     *
     * @param sessionNumber The unique identifier for this session.
     */
    public Session(int sessionNumber) {
        requireNonNull(sessionNumber);
        this.sessionNumber = sessionNumber;
    }

    /**
     * Adds a student to the session.
     *
     * @param p The student to add.
     */
    public void addStudent(Person p) {
        students.add(p);
    }

    /**
     * Removes a student from the session.
     *
     * @param key The student to remove.
     */
    public void removeStudent(Person key) {
        students.remove(key);
    }

    /**
     * Checks if this session is equal to another object.
     *
     * @param other The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        Session otherSession = (Session) other;
        return students.equals(otherSession.students)
                && otherSession.sessionNumber == sessionNumber;
    }

    /**
     * Checks if this session is the same as another session.
     *
     * @param other The session to compare with.
     * @return True if the sessions are the same, false otherwise.
     */
    public boolean isSameSession(Session other) {
        return other.equals(this);
    }

    /**
     * Retrieves the set of students in this session.
     *
     * @return The set of students.
     */
    public Set<Person> getStudents() {
        return students;
    }

    /**
     * Retrieves the session number, a unique identifier for this session.
     *
     * @return The session number.
     */
    public int getSessionNumber() {
        return sessionNumber;
    }

    /**
     * Generates a human-readable representation of the session, including the session number and the set of students.
     *
     * @return A string representation of the session.
     */
    public String getSessionInfo() {
        return String.format("%d - %s", sessionNumber, students);
    }
}
