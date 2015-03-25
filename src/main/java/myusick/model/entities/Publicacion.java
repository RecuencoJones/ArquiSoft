package myusick.model.entities;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "Publicacion")
public class Publicacion {

	@Id
	@Column(name = "idpublicacion", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idpublicacion;

	@Column(name = "fecha", nullable = false)
	private long fecha;

	@Column(name = "contenido", nullable = false, length = 144)
	private String contenido;

    @ManyToOne
    @JoinColumn(name = "Publicante_UUID")
    private Publicante publicante;


    public Publicacion(long fecha, String contenido) {
        this.fecha = fecha;
        this.contenido = contenido;
        this.publicante = publicante;
    }

	/*------GETTERS/SETTERS------*/

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

    public Publicante getPublicante() {
        return publicante;
    }

    public void setPublicante(Publicante publicante) {
        this.publicante = publicante;
    }
}
