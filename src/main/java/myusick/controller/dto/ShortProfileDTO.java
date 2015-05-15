package myusick.controller.dto;

/**
 * Created by david on 09/05/2015.
 */
public class ShortProfileDTO {
    private int id;
    private String name;
    private String avatar;
    private boolean type;

    public ShortProfileDTO() {
    }

    public ShortProfileDTO(int id, String name, String avatar, boolean type) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.type = type;
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

    @Override
    public String toString() {
        return "ShortProfileDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", type=" + type +
                '}';
    }
}
