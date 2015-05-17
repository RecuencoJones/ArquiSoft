package myusick.controller.dto;

/**
 * Created by david on 11/05/2015.
 */
public class EditDTO {

    //importante
    private int id;
    private boolean type;

    //comunes
    private String nombre;
    private String descripcion;
    private String avatar;

    //persona
    private String password;
    private String repassword;
    private int telefono;

    public EditDTO() {
    }

    public EditDTO(int id, boolean type, String nombre, String descripcion, String avatar, String password, String repassword, int telefono) {
        this.id = id;
        this.type = type;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.avatar = avatar;
        this.password = password;
        this.repassword = repassword;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
