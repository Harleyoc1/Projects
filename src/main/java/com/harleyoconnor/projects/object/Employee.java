package com.harleyoconnor.projects.object;

import com.harleyoconnor.projects.Projects;
import com.harleyoconnor.projects.util.HashManager;
import com.harleyoconnor.serdes.ClassSerDes;
import com.harleyoconnor.serdes.IndexedSerDesable;
import com.harleyoconnor.serdes.SerDes;
import com.harleyoconnor.serdes.database.Database;
import com.harleyoconnor.serdes.field.Field;
import com.harleyoconnor.serdes.field.MutableField;
import com.harleyoconnor.serdes.field.PrimaryField;

import java.time.Instant;
import java.util.Date;

/**
 * @author Harley O'Connor
 */
public final class Employee extends IndexedSerDesable<Employee> {

    private static final HashManager HASH_MANAGER = new HashManager();

    public static final PrimaryField<Employee, Integer> PRIMARY_FIELD = createPrimaryField(Employee.class);
    public static final Field<Employee, String> EMAIL_FIELD = new MutableField<>("email", Employee.class, String.class, true, Employee::getEmail, Employee::setEmail);

    public static final SerDes<Employee, Integer> SER_DES = ClassSerDes.Builder.of(Employee.class, Integer.class)
            .primaryField(PRIMARY_FIELD).field("hire_date", Date.class, Employee::getHireDate).field(EMAIL_FIELD)
            .field("first_name", String.class, Employee::getFirstName, Employee::setFirstName)
            .field("last_name", String.class, Employee::getLastName, Employee::setLastName)
            .field("password", String.class, Employee::getPassword, Employee::setPassword)
            .field("wage", Double.class, Employee::getWage, Employee::setWage)
            .field("department_id", Department.PRIMARY_FIELD, Employee::getDepartment, Employee::setDepartment).build();

    public static Employee fromEmail(final Database database, final String email) {
        return SER_DES.getLoadedObjects().stream().filter(employee -> employee.getEmail().equals(email)).findFirst()
                .orElseGet(() -> SER_DES.deserialise(database, database.selectUnchecked(SER_DES.getTable(), EMAIL_FIELD.getName(), email)));
    }

    public static boolean emailExists(final String email) {
        return Projects.getDatabase().valueExists(SER_DES.getTable(), EMAIL_FIELD.getName(), email);
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

    public Employee(Database database, String firstName, String lastName, String email, String password, Department department) {
        super(database);
        this.hireDate = Date.from(Instant.now());
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

    @Override
    public PrimaryField<Employee, Integer> getPrimaryField() {
        return PRIMARY_FIELD;
    }

    @Override
    public SerDes<Employee, Integer> getSerDes() {
        return SER_DES;
    }

}
