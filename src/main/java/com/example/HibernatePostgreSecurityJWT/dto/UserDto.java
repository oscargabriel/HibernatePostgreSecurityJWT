package com.example.HibernatePostgreSecurityJWT.dto;

import com.example.HibernatePostgreSecurityJWT.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


/**
 *
 */
public class UserDto {

    private Long id;

    private String username;

    private String password;

    private String document;

    private String name;

    private String lastName;

    private String email;

    private String phone;

    private LocalDate birthdate;

    private List<String> role;


    public UserDto(User user, List<String> rol) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.document = user.getDocument();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.birthdate = user.getBirthdate();
        this.role = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }
}
