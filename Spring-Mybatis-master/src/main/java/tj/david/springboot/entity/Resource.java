package tj.david.springboot.entity;

import java.io.Serializable;

/**
 * Created by David on 2016/7/26.
 */
public class Resource implements Serializable {

    private static final long serialVersionUID = 702532093895354487L;

    private int id;
    private String resourceCode;
    private String url;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
