package myusick.controller.dto;

import java.io.Serializable;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class PostDTO implements Serializable {

    private int id;
    private String avatar;
    private long date;
    private String user;
    private int user_id;
    private String content;
    private boolean type;

    public PostDTO(){}
    
    public PostDTO(int id, String avatar, long date, String user, int user_id, String content) {
        this.id = id;
        this.avatar = avatar;
        this.date = date;
        this.user = user;
        this.user_id = user_id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isType() {
        return type;
    }

    /**
     * *
     * @param type true if group, false if person
     */
    public void setType(boolean type) {
        this.type = type;
    }
}
