package myusick.model.dto;

/**
 * Created by david on 20/03/2015.
 */
public class PublicationDTO {
    
    private int id;
    private long date;
    private String user;
    private int user_id;
    private String content;

    public PublicationDTO(int id, int date, String user, int user_id, String content) {
        this.id = id;
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
}
