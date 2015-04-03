package myusick.persistence.VO;

/*
 * Elemento VO que registra un dato concreto de la entidad "Tag"
 * @author Sandra Campos
 */

import java.io.Serializable;

public class Tag implements Serializable {

	private int idtag;
	private String nombretag;

    public Tag(int idtag, String nombretag) {
        this.idtag = idtag;
        this.nombretag = nombretag;
    }

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
