package com.example.HibernatePostgreSecurityJWT.entities;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * entidad para eliminar el llamado muchos a muchos entre user y role
 */
@Entity
@Table(name="user_role")
public class UserRole implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) no genera, se uso funcion personalizada
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "fk_userrole_user"))
    private User userId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "role_id",
            foreignKey = @ForeignKey(name = "fk_userrole_role"))
    private Role roleId;

    public UserRole() {
    }

    public UserRole(Long id, User userId, Role roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }


}

