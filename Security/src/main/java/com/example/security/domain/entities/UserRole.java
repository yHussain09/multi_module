package com.example.security.domain.entities;

import com.example.corejava.domain.entities.EntityBase;
import com.example.security.domain.entities.pks.UserRolePK;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
@IdClass(UserRolePK.class)
public class UserRole extends EntityBase {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "role_id")
    private Long roleId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false, referencedColumnName = "id", nullable = false)
    private User user;

    /*@ManyToOne
    @JoinColumn(name = "authority_id", insertable = false, updatable = false, referencedColumnName = "id", nullable = false)
    private Authority authority;*/

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /*public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }*/

    /* @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false, referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "authorityId", insertable = false, updatable = false, referencedColumnName = "id", nullable = false)
    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }*/
}
