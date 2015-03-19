package myusick.model.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Aptitud {

	@Id
	@Column(name = "idaptitud", nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
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
