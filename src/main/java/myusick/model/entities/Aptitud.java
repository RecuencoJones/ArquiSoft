package myusick.model.entities;

import javax.persistence.*;

@Entity
public class Aptitud {

	@Id
	@Column(name = "idAptitud", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idaptitud;

	@Column(name = "nombre", nullable = false, length = 45)
	private String nombre;

	/*------GETTERS/SETTERS------*/
	
	public int getIdaptitud() {
		return idaptitud;
	}

	public void setIdaptitud(int idaptitud) {
		this.idaptitud = idaptitud;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
