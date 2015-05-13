package myusick.model.vo;

import java.io.Serializable;

/*
 * Elemento VO que registra un dato concreto de la entidad "Persona"
 * @author Sandra Campos
 */
public class Persona implements Serializable{

    private int publicante_uuid;
	private String nombre;
	private String password;
	private String email;
	private long fechanacimiento;
	private String ciudad;
	private String pais;
	private long telefono;
    private String descripcion;
    private String apellidos;
    private String avatar;

    public Persona(){}
    public Persona(int publicante_uuid, String nombre, String password, String email,
                   long fechanacimiento, String ciudad, String pais, long telefono,
                   String descripcion, String apellidos, String avatar) {
        this.publicante_uuid = publicante_uuid;
        this.nombre = nombre;
        this.password = password;
        this.email = email;
        this.fechanacimiento = fechanacimiento;
        this.ciudad = ciudad;
        this.pais = pais;
        this.telefono = telefono;
        this.descripcion = descripcion;
        this.apellidos = apellidos;
        this.avatar = avatar;
    }

    public int getPublicante_uuid() {
        return publicante_uuid;
    }

    public void setPublicante_uuid(int publicante_uuid) {
        this.publicante_uuid = publicante_uuid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(long fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
