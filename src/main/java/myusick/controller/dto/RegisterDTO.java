package myusick.controller.dto;

import java.io.Serializable;

/**
 * Created by Sandra Campos on 02/04/2015.
 */
public class RegisterDTO implements Serializable{

    private String name;
    private String lastname;
    private String birthdate;
    private String city;
    private String country;
    private String phone;
    private String email;
    private String password;
    private String repassword;

    public RegisterDTO() {
    }

    public RegisterDTO(String name, String lastname, String birthdate, String city,
                       String country, String phone, String email, String password, String repassword) {
        this.name = name;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.repassword = repassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    @Override
    public String toString() {
        return "RegisterDTO{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", repassword='" + repassword + '\'' +
                '}';
    }
}
