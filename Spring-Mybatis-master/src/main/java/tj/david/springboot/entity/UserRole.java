package tj.david.springboot.entity;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by David on 2016/7/26.
 */
public class UserRole implements Serializable {

    private static final long serialVersionUID = 5842318008642952070L;

    private int id;
    private int userId;
    private int roleId;

    @Transient
    private String roleName; //该字段不是数据库中的字段
    @Transient
    private String roleCode; //该字段不是数据库中的字段

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
