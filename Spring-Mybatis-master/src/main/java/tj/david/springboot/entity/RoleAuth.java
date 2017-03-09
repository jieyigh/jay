package tj.david.springboot.entity;

import java.io.Serializable;

/**
 * Created by David on 2016/7/26.
 */
public class RoleAuth implements Serializable {

    private static final long serialVersionUID = -1494425382603477538L;

    private int id;
    private int roleId;
    private int authId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }
}
