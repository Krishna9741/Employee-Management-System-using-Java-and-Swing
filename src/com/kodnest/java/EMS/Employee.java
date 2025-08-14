package com.kodnest.java.EMS;

public class Employee {
    private int id;
    private String name;
    private int salary;
    private String department;
    private String position;

    // Constructor without ID (for insert)
    public Employee(String name, int salary, String department, String position) {
        this.name = name;
        this.salary = salary;
        this.department = department;
        this.position = position;
    }

    // Optional: Constructor with ID (for reading from DB)
    public Employee(int id, String name, int salary, String department, String position) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
        this.position = position;
    }

    // ✅ Getters (these fix your DAO error)
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    // (Optional) Setters if you plan to modify employee later
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
