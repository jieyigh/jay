package tj.david.springboot.entity;

import java.io.Serializable;

/**
 * Created by David on 2016/7/26.
 */
public class Auth implements Serializable {

    private static final long serialVersionUID = -5194984272638684419L;

    private int id;
    private String authCode;
    private String authName;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
