package com.harleyoconnor.projects.object;

import com.harleyoconnor.serdes.ClassSerDes;
import com.harleyoconnor.serdes.IndexedSerDesable;
import com.harleyoconnor.serdes.SerDes;
import com.harleyoconnor.serdes.database.Database;
import com.harleyoconnor.serdes.field.PrimaryField;

/**
 * @author Harley O'Connor
 */
public final class Department extends IndexedSerDesable<Department> {

    public static final PrimaryField<Department, Integer> PRIMARY_FIELD = createPrimaryField(Department.class);

    public static final SerDes<Department, Integer> SER_DES = ClassSerDes.Builder.of(Department.class, Integer.class).primaryField(PRIMARY_FIELD)
            .uniqueField("name", String.class, Department::getName, Department::setName)
            .field("head", Employee.PRIMARY_FIELD, Department::getHead, Department::setHead).build();

    private String name;
    private Employee head;

    public Department(int id) {
        super(id);
    }

    public Department(Database database, String name, Employee head) {
        super(database);
        this.name = name;
        this.head = head;
    }

    /**
     * Gets the {@link #name} for this {@link Department} object.
     *
     * @return The {@link #name} for this {@link Department} object.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the {@link #name} for this {@link Department} object
     * to the given {@code name}.
     *
     * @param name The new {@link String} object to set.
     * @return This {@link Department} object for chaining.
     */
    public Department setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets the {@link #head} for this {@link Department} object.
     *
     * @return The {@link #head} for this {@link Department} object.
     */
    public Employee getHead() {
        return this.head;
    }

    /**
     * Sets the {@link #head} for this {@link Department} object
     * to the given {@code head}.
     *
     * @param head The new {@link Employee} object to set.
     * @return This {@link Department} object for chaining.
     */
    public Department setHead(Employee head) {
        this.head = head;
        return this;
    }

    @Override
    public PrimaryField<Department, Integer> getPrimaryField() {
        return PRIMARY_FIELD;
    }

    @Override
    public SerDes<Department, Integer> getSerDes() {
        return SER_DES;
    }

}
