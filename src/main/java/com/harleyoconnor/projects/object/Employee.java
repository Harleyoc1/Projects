package com.harleyoconnor.projects.object;

import com.harleyoconnor.projects.util.HashManager;
import com.harleyoconnor.serdes.ClassSerDes;
import com.harleyoconnor.serdes.IndexedSerDesable;
import com.harleyoconnor.serdes.SerDes;
import com.harleyoconnor.serdes.database.DefaultDatabase;
import com.harleyoconnor.serdes.exception.NoSuchRowException;
import com.harleyoconnor.serdes.field.*;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Harley O'Connor
 */
public final class Employee extends IndexedSerDesable<Employee> {

    private static final HashManager HASH_MANAGER = new HashManager();

    public static final PrimaryField<Employee, Integer> PRIMARY_FIELD = createPrimaryField(Employee.class);
    public static final Field<Employee, String> EMAIL_FIELD = new MutableField<>("email", Employee.class, String.class, true, false, Employee::getEmail, Employee::setEmail);
    public static final ForeignField<Employee, Integer, Department> DEPARTMENT_FIELD = new MutableForeignField<>("department_id", Employee.class, Department.PRIMARY_FIELD, false, true, Employee::getDepartment, Employee::setDepartment);

    public static final SerDes<Employee, Integer> SER_DES = ClassSerDes.Builder.of(Employee.class, Integer.class)
            .primaryField(PRIMARY_FIELD).field("hire_date", Date.class, Employee::getHireDate)
            .field("first_name", String.class, Employee::getFirstName, Employee::setFirstName)
            .field("last_name", String.class, Employee::getLastName, Employee::setLastName)
            .field(EMAIL_FIELD)
            .field("password", String.class, Employee::getPassword, Employee::setPassword)
            .field("wage", Double.class, Employee::getWage, Employee::setWage)
            .field(DEPARTMENT_FIELD).build();

    public static Employee fromEmail(final String email) {
        return SER_DES.getLoadedObjects().stream().filter(employee -> employee.getEmail().equals(email)).findFirst()
                .orElseGet(() -> SER_DES.deserialise(DefaultDatabase.get().selectUnchecked(SER_DES.getTable(), EMAIL_FIELD.getName(), email)));
    }

    public static boolean emailExists(final String email) {
        return DefaultDatabase.get().valueExists(SER_DES.getTable(), EMAIL_FIELD.getName(), email);
    }

    private final Date hireDate;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private double wage;

    private Department department;

    public Employee(int id, Date hireDate) {
        super(id);
        this.hireDate = hireDate;
    }

    public Employee() {
        this.hireDate = Date.from(Instant.now());
    }

    public Employee(String firstName, String lastName, String email, String password, Department department) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = HASH_MANAGER.hash(password);
        this.department = department;
    }

    /**
     * Gets the {@link #hireDate} for this {@link Employee} object.
     *
     * @return The {@link #hireDate} for this {@link Employee} object.
     */
    public Date getHireDate() {
        return this.hireDate;
    }

    /**
     * Gets the {@link #firstName} for this {@link Employee} object.
     *
     * @return The {@link #firstName} for this {@link Employee} object.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Sets the {@link #firstName} for this {@link Employee} object
     * to the given {@code firstName}.
     *
     * @param firstName The new {@link String} object to set.
     * @return This {@link Employee} object for chaining.
     */
    public Employee setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Gets the {@link #lastName} for this {@link Employee} object.
     *
     * @return The {@link #lastName} for this {@link Employee} object.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Sets the {@link #lastName} for this {@link Employee} object
     * to the given {@code lastName}.
     *
     * @param lastName The new {@link String} object to set.
     * @return This {@link Employee} object for chaining.
     */
    public Employee setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Gets the {@link #email} for this {@link Employee} object.
     *
     * @return The {@link #email} for this {@link Employee} object.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the {@link #email} for this {@link Employee} object
     * to the given {@code email}.
     *
     * @param email The new {@link String} object to set.
     * @return This {@link Employee} object for chaining.
     */
    public Employee setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Gets the {@link #password} for this {@link Employee} object.
     *
     * @return The {@link #password} for this {@link Employee} object.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the {@link #password} for this {@link Employee} object
     * to the given {@code password}.
     *
     * @param password The new {@link String} object to set.
     * @return This {@link Employee} object for chaining.
     */
    public Employee setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Gets the {@link #wage} for this {@link Employee} object.
     *
     * @return The {@link #wage} for this {@link Employee} object.
     */
    public double getWage() {
        return this.wage;
    }

    /**
     * Sets the {@link #wage} for this {@link Employee} object
     * to the given {@code wage}.
     *
     * @param wage The new {@code double} to set.
     * @return This {@link Employee} object for chaining.
     */
    public Employee setWage(double wage) {
        this.wage = wage;
        return this;
    }

    /**
     * Gets the {@link #department} for this {@link Employee} object.
     *
     * @return The {@link #department} for this {@link Employee} object.
     */
    public Department getDepartment() {
        return this.department;
    }

    /**
     * Sets the {@link #department} for this {@link Employee} object
     * to the given {@code department}.
     *
     * @param department The new {@link Department} object to set.
     * @return This {@link Employee} object for chaining.
     */
    public Employee setDepartment(Department department) {
        this.department = department;
        return this;
    }

    /**
     * Checks if this {@link Employee} is the {@code head} of their {@link Department}.
     *
     * @return {@code true} if this {@link Employee} is head of their {@link Department};
     *         {@code false} if not.
     */
    public boolean isHead() {
        return this.department.getHead().equals(this);
    }

    /**
     * Authenticates the given {@code password}, checking it matches the stored hashed
     * {@link #password}.
     *
     * @param password The {@code password} to authenticate.
     * @return {@code true} if the {@code password} is authentic;
     *         {@code false} otherwise.
     */
    public boolean authenticate(final String password) {
        return HASH_MANAGER.authenticate(this.password, password);
    }

    public List<MeetingRoomBooking> getBookings() {
        final List<MeetingRoomBooking> bookings = new LinkedList<>();
        final var database = DefaultDatabase.get();

        try {
            final var resultSet = database.select(MeetingRoomBooking.SER_DES.getTable(), MeetingRoomBooking.EMPLOYEE_FIELD.getName(), this.getId());

            do {
                bookings.add(MeetingRoomBooking.SER_DES.deserialise(database, resultSet));
            } while (resultSet.next());
        } catch (final NoSuchRowException e) {
            return Collections.emptyList();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return bookings;
    }

    @Override
    public PrimaryField<Employee, Integer> getPrimaryField() {
        return PRIMARY_FIELD;
    }

    @Override
    public SerDes<Employee, Integer> getSerDes() {
        return SER_DES;
    }

}
