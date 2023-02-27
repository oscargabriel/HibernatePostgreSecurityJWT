package com.example.HibernatePostgreSecurityJWT.entities;

import com.example.HibernatePostgreSecurityJWT.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
//import org.hibernate.envers.Audited;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * entidad usuario
 */
@Entity
@Table(name = "users")
@Audited
public class User implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) no genera, se uso funcion personalizada
    private Long id;


    @Column(unique = true, length = 30, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String document;

    @Column(nullable = false,length = 50)
    private String name;

    @Column(name="last_name", nullable = false,length = 50)
    private String lastName;

    @Column(unique = true, nullable = false,length = 50)
    private String email;

    @Column(nullable = true,length = 50)
    private String phone;

    @Column(name = "birt_date")
    private LocalDateTime birtDate;

    @Column(name = "edit_date")
    private LocalDateTime editDate;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;


    /* coleccion enbebida que almacena la lista con el id del empleado
    @ElementCollection
    private List<String> preferencias = new ArrayList<>();
    puede ser Set (no admite duplicados), Map (clave, valor)
    */

    /* enumareciones para gestionar roles
    @Enumerated(EnumType.STRING)
    EmployeeCategory category;

    public enum EmployeeCategory {
    JUNIOR, SENIOR, ANALYST
}

    */

    public User() {
    }

    public User(Long id, String username, String password, String document, String name, String lastName, String email, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.document = document;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
    public User(UserDto userDto) {
        this.id = userDto.getId();
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
        this.document = userDto.getDocument();
        this.name = userDto.getName();
        this.lastName = userDto.getLastName();
        this.email = userDto.getEmail();
        this.phone = userDto.getPhone();
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

    public LocalDateTime getBirtDate() {
        return birtDate;
    }

    public void setBirtDate(LocalDateTime birtDate) {
        this.birtDate = birtDate;
    }

    public LocalDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDateTime editDate) {
        this.editDate = editDate;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * Método que se ejecuta antes de insertar una entidad Employee en base de datos
     * Antes de crear.
     */
    @PrePersist//antes de crear
    public void prePersist() {
        this.setEditDate(LocalDateTime.now());
    }
    @PreUpdate//antes de editar
    public void preUpdate() {
        this.setEditDate(LocalDateTime.now());
    }
    @PreRemove//antes de eliminar un empleado
    public void preRemove() {
        // this.setCars(new ArrayList<>());
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
                '}';
    }
}
