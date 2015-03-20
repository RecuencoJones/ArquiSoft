package myusick.model.dto;

/**
 * Created by david on 20/03/2015.
 */
public class ShortGroupDTO {
    
    private int id;
    private String name;

    public ShortGroupDTO(int id, String name) {
        this.id = id;
        this.name = name;
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
}
