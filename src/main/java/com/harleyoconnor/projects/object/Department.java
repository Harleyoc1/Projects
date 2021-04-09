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

    public static final SerDes<Department, Integer> SER_DES = ClassSerDes.Builder.of(Department.class, Integer.class)
            .primaryField(PRIMARY_FIELD)
            .uniqueField("name", String.class, Department::getName, Department::setName)
            .field("head", Employee.PRIMARY_FIELD, Department::getHead, Department::setHead).build();

    private String name;
    private Employee head;

    public Department(Database database, String name, Employee head) {
        super(database);
        this.name = name;
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getHead() {
        return head;
    }

    public void setHead(Employee head) {
        this.head = head;
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
