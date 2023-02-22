package com.example.HibernatePostgreSecurityJWT.entities;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * entidad para guardar los roles que pude tener un usuario
 */
@Entity
public class Role implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) no genera, se uso funcion personalizada
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    public Role() {
    }

    public Role(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
