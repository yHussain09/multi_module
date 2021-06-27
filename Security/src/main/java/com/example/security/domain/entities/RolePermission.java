package com.example.security.domain.entities;

import com.example.security.base.EntityBase;
import com.example.security.domain.entities.pks.RolePermissionPK;

import javax.persistence.*;

@Entity
@Table(name = "role_permission")
@IdClass(RolePermissionPK.class)
public class RolePermission extends EntityBase {

    @Id
    @Column(name = "role_id")
    private Long roleId;

    @Id
    @Column(name = "permission_id")
    private Long permissionId;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false, referencedColumnName = "id", nullable = false)
    private Role role;

    /*@ManyToOne
    @JoinColumn(name = "privilege_id", insertable = false, updatable = false, referencedColumnName = "id", nullable = false)
    private Privilege privilege;*/

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.role = role;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    /*public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }*/
}
