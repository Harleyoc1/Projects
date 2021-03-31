package com.harleyoconnor.projects.object;

import com.harleyoconnor.projects.Projects;
import com.harleyoconnor.projects.serialisation.AbstractSerDesable;
import com.harleyoconnor.projects.serialisation.ClassSerDes;
import com.harleyoconnor.projects.serialisation.SerDes;
import com.harleyoconnor.projects.serialisation.field.Field;
import com.harleyoconnor.projects.serialisation.field.PrimaryField;

/**
 * @author Harley O'Connor
 */
public final class Department extends AbstractSerDesable<Department, Integer> {

    public static final Field<Department, Integer> PRIMARY_FIELD = new PrimaryField<>("id", Department.class, Integer.class, Department::getId);

    public static final SerDes<Department, Integer> SER_DES = ClassSerDes.Builder.of(Department.class, Integer.class)
            .primaryField("id", Integer.class, Department::getId)
            .uniqueField("name", String.class, Department::getName, Department::setName)
            .field("head", Employee.PRIMARY_FIELD, Department::getHead, Department::setHead).build();

    private final int id;
    private String name;
    private Employee head;

    public Department(int id) {
        this.id = id;
    }

    public Department(String name, Employee head) {
        this.id = Projects.getDatabaseController().getMaxOrDefault(SER_DES.getTable(), PRIMARY_FIELD.getName(), 1) + 1;
        this.name = name;
        this.head = head;
    }

    public int getId() {
        return id;
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
    public SerDes<Department, Integer> getSerDes() {
        return SER_DES;
    }

}
