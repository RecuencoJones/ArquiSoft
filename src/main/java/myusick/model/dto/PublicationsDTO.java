package myusick.model.dto;

import java.io.Serializable;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class PublicationsDTO implements Serializable {

    private int id;
    private String content;
    private long date;

    public PublicationsDTO() {
    }

    public PublicationsDTO(int id, String content, long date) {
        this.id = id;
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
