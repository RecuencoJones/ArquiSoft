package myusick.controller.dto;

import java.io.Serializable;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class CreationDTO implements Serializable {

    private int creator;
    private String name;
    private String year;
    private String description;

    public CreationDTO(int creator, String name, String year, String description) {
        this.creator = creator;
        this.name = name;
        this.year = year;
        this.description = description;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
