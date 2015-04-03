package myusick.persistence.VO;
/*
 * Elemento VO que registra un dato concreto de la entidad "Publicante"
 * @author Sandra Campos
 */

public class Publicante {

	private int uuid;
	private boolean tipoPublicante;

    public Publicante(int uuid, boolean tipoPublicante) {
        this.uuid = uuid;
        this.tipoPublicante = tipoPublicante;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public boolean isTipoPublicante() {
        return tipoPublicante;
    }

    public void setTipoPublicante(boolean tipoPublicante) {
        this.tipoPublicante = tipoPublicante;
    }
}
