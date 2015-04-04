package myusick.model.DTO;

import java.io.Serializable;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class Errors implements Serializable {

    private boolean name;
    private boolean brithdate;
    private boolean address;
    private boolean phone;
    private boolean email;
    private boolean password;
    private boolean year;
    private boolean description;
    private boolean empty;

    public Errors(boolean name, boolean brithdate, boolean address, boolean phone, boolean email,
                  boolean password, boolean year, boolean description, boolean empty) {
        this.name = name;
        this.brithdate = brithdate;
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

    public void setName(boolean name) {
        this.name = name;
    }

    public boolean isBrithdate() {
        return brithdate;
    }

    public void setBrithdate(boolean brithdate) {
        this.brithdate = brithdate;
    }

    public boolean isAddress() {
        return address;
    }

    public void setAddress(boolean address) {
        this.address = address;
    }

    public boolean isPhone() {
        return phone;
    }

    public void setPhone(boolean phone) {
        this.phone = phone;
    }

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public boolean isPassword() {
        return password;
    }

    public void setPassword(boolean password) {
        this.password = password;
    }

    public boolean isYear() {
        return year;
    }

    public void setYear(boolean year) {
        this.year = year;
    }

    public boolean isDescription() {
        return description;
    }

    public void setDescription(boolean description) {
        this.description = description;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
