package tj.david.springboot.entity;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 角色表
 * Created by David on 2016/7/26.
 */
public class Role implements Serializable {


    private static final long serialVersionUID = -8049826267495758724L;

    private int id;
    private String roleName;
    private String roleCode;
    private int status;

    @Transient
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
