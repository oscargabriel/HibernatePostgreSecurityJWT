package com.example.HibernatePostgreSecurityJWT.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, unique = true, nullable = false)
    private String username;

    @Column(length = 30, nullable = false)
    private String password;

    @Column(length = 30, unique = true, nullable = true)
    private String document;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(name="last_name", length = 30, nullable = false)
    private String lastName;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @Column(length = 50, nullable = true)
    private String phone;

    @Column(length = 50, nullable = true)
    private Date birthdate;

    public User() {
    }

    public User(Long id, String username, String password, String document, String name, String lastName, String email, String phone, Date birthdate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.document = document;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.birthdate = birthdate;
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", document='" + document + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
}
