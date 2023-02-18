package com.example.HibernatePostgreSecurityJWT.entities;

import jakarta.persistence.*;

@Entity
@Table(name="user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "fk_userrole_user"))
    private User userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id",
            foreignKey = @ForeignKey(name = "fk_userrole_role"))
    private Role roleId;

    public UserRole() {
    }

    public UserRole(User userId, Role roleId) {
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

