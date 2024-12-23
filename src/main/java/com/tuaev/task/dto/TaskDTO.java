package com.tuaev.task.dto;

import com.tuaev.task.entity.User;

public class TaskDTO {

    private Long id;
    private String title;
    private String description;
    private String status;
    private UserDTO userDTO;

    public TaskDTO(Long id, String title, String description, String status, UserDTO userDTO) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.userDTO = userDTO;
    }

    public TaskDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserDTO getUser() {
        return userDTO;
    }

    public void setUser(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", user=" + userDTO +
                '}';
    }
}
