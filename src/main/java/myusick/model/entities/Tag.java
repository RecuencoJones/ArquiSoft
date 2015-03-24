package myusick.model.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Tag {

	@Id
	@Column(name = "idtag", nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idtag;

	@Column(name = "nombretag", nullable = false, length = 60)
	private String nombretag;
	
	/*------GETTERS/SETTERS------*/
	public int getIdtag() {
		return idtag;
	}

	public void setIdtag(int idtag) {
		this.idtag = idtag;
	}

	public String getNombreTag() {
		return nombretag;
	}

	public void setNombreTag(String nombre) {
		this.nombretag = nombre;
	}
}
