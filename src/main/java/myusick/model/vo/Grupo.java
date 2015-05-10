package myusick.model.vo;

import java.io.Serializable;

/*
 * Elemento VO que registrara los datos de la entidad "Grupo"
 * @author Sandra
 */
public class Grupo implements Serializable{

    private int publicante_uuid;
    private String nombre;
    private long anyofundacion;
    private String descripcion;
    private String avatar;

    public Grupo(int publicante_uuid, String nombre, long anyofundacion, String descripcion, String avatar) {
        this.publicante_uuid = publicante_uuid;
        this.nombre = nombre;
        this.anyofundacion = anyofundacion;
        this.descripcion = descripcion;
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

    public long getAnyofundacion() {
        return anyofundacion;
    }

    public void setAnyofundacion(long anyofundacion) {
        this.anyofundacion = anyofundacion;
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
}
