package com.sg.jdbctcomplexexample.entity;

import java.util.Objects;

/**
 *
 * @author kylerudy
 */
public class Employee {
    int id;
    String firstName;
    String lastName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
      if (this == o )  return true;
      if (o == null || getClass() != o.getClass()) return false;
      Employee emp = (Employee) o;
      return (id == emp.id && firstName.equals(emp.firstName) && lastName.equals(emp.lastName));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
