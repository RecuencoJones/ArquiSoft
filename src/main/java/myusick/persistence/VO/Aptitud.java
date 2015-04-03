package myusick.persistence.VO;
/*
 * Elemento VO que registra un dato concreto de la entidad "Aptitud"
 * @author Sandra Campos
 */

import java.io.Serializable;

public class Aptitud implements Serializable{


	private int idAptitud;
	private String nombre;

    public Aptitud(int idAptitud, String nombre) {
        this.idAptitud = idAptitud;
        this.nombre = nombre;
    }

    /*------GETTERS/SETTERS------*/

    public int getIdAptitud() {
        return idAptitud;
    }

    public void setIdAptitud(int idAptitud) {
        this.idAptitud = idAptitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
