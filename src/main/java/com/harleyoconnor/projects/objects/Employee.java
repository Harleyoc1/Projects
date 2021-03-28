package com.harleyoconnor.projects.objects;

import com.harleyoconnor.projects.Projects;
import com.harleyoconnor.projects.serialisation.AbstractSerDesable;
import com.harleyoconnor.projects.serialisation.ClassSerDes;
import com.harleyoconnor.projects.serialisation.SerDes;
import com.harleyoconnor.projects.serialisation.fields.Field;
import com.harleyoconnor.projects.serialisation.fields.PrimaryField;

/**
 * @author Harley O'Connor
 */
public final class Employee extends AbstractSerDesable<Employee, Integer> {

    public static final Field<Employee, Integer> PRIMARY_FIELD = new PrimaryField<>("id", Employee.class, Integer.class, Employee::getId);

    public static final SerDes<Employee, Integer> SER_DES = ClassSerDes.Builder.of(Employee.class, Integer.class)
            .primaryField("id", Integer.class, Employee::getId)
            .field("first_name", String.class, Employee::getFirstName, Employee::setFirstName)
            .field("last_name", String.class, Employee::getLastName, Employee::setLastName)
            .uniqueField("email", String.class, Employee::getEmail, Employee::setEmail)
            .field("password", String.class, Employee::getPassword, Employee::setPassword)
            .foreignField("department_id", Department.PRIMARY_FIELD, Employee::getDepartment, Employee::setDepartment).build();

    public static Employee fromEmail(final String email) {
        return SER_DES.getLoadedObjects().stream().filter(employee -> employee.getEmail().equals(email)).findFirst()
                .orElseGet(() -> SER_DES.deserialise(Projects.getDatabaseController().select(SER_DES.getTable(), "email", email)));
    }

    private final int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Department department;

    public Employee(int id) {
        this.id = id;
    }

    public Employee(int id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public SerDes<Employee, Integer> getSerDes() {
        return SER_DES;
    }

}
