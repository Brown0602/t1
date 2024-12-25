package com.tuaev.task.dto;

public class UserDTO {

    private Long id;
    private String userName;
    private String lastName;
    private int age;

    public UserDTO(Long id, String userName, String lastName, int age) {
        this.id = id;
        this.userName = userName;
        this.lastName = lastName;
        this.age = age;
    }

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
