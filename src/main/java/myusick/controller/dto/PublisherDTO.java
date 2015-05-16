package myusick.controller.dto;

import java.io.Serializable;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class PublisherDTO implements Serializable {

    private int id;
    private String name;
    private String avatar;
    private boolean type;

    public PublisherDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public PublisherDTO(int id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    public PublisherDTO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PublisherDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
