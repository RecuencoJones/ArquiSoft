package myusick.model.dto;

/**
 * Created by david on 05/04/2015.
 */
public class TagDTO {
    
    private String nombre;
    private int publicante;

    public TagDTO() {
    }

    public TagDTO(String nombre, int publicante) {
        this.nombre = nombre;
        this.publicante = publicante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPublicante() {
        return publicante;
    }

    public void setPublicante(int publicante) {
        this.publicante = publicante;
    }
}
