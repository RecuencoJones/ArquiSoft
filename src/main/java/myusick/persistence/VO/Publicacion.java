package myusick.persistence.VO;

import java.io.Serializable;
/*
 * Elemento VO que registrara los datos de la entidad "Publicacion"
 * @author Sandra
 */
public class Publicacion implements Serializable {

	private int idpublicacion;
	private long fecha;
	private String contenido;
    private String publicante_uuid;

    public Publicacion(int idpublicacion, long fecha, String contenido, String publicante_uuid) {
        this.idpublicacion = idpublicacion;
        this.fecha = fecha;
        this.contenido = contenido;
        this.publicante_uuid = publicante_uuid;
    }

    public int getIdpublicacion() {
        return idpublicacion;
    }

    public void setIdpublicacion(int idpublicacion) {
        this.idpublicacion = idpublicacion;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getPublicante_uuid() {
        return publicante_uuid;
    }

    public void setPublicante_uuid(String publicante_uuid) {
        this.publicante_uuid = publicante_uuid;
    }
}
