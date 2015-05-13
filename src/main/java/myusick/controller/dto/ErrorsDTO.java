package myusick.controller.dto;

import java.io.Serializable;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class ErrorsDTO implements Serializable {

    private boolean name;
    private boolean birthdate;
    private boolean address;
    private boolean phone;
    private boolean email;
    private boolean password;
    private boolean year;
    private boolean description;
    private boolean empty;

    public ErrorsDTO() {
    }

    public ErrorsDTO(boolean name, boolean birthdate, boolean address, boolean phone, boolean email,
                     boolean password, boolean year, boolean description, boolean empty) {
        this.name = name;
        this.birthdate = birthdate;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.year = year;
        this.description = description;
        this.empty = empty;
    }

    public boolean isName() {
        return name;
    }

    public void setName() {
        this.name = true;
    }

    public boolean isBirthdate() {
        return birthdate;
    }

    public void setBirthdate() {
        this.birthdate = true;
    }

    public boolean isAddress() {
        return address;
    }

    public void setAddress() {
        this.address = true;
    }

    public boolean isPhone() {
        return phone;
    }

    public void setPhone() {
        this.phone = true;
    }

    public boolean isEmail() {
        return email;
    }

    public void setEmail() {
        this.email = true;
    }

    public boolean isPassword() {
        return password;
    }

    public void setPassword() {
        this.password = true;
    }

    public boolean isYear() {
        return year;
    }

    public void setYear() {
        this.year = true;
    }

    public boolean isDescription() {
        return description;
    }

    public void setDescription() {
        this.description = true;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty() {
        this.empty = true;
    }
}
