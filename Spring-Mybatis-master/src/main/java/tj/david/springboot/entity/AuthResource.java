package tj.david.springboot.entity;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by David on 2016/7/26.
 */
public class AuthResource implements Serializable {

    private static final long serialVersionUID = -8395174213897864580L;

    private int id;
    private int authId;
    private int resourceId;

    @Transient private String url;
    @Transient private String authCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
