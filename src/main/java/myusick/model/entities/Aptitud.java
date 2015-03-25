package myusick.model.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Aptitud")
public class Aptitud {

	@Id
	@Column(name = "idAptitud", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idAptitud;

	@Column(name = "nombre", nullable = false, length = 45)
	private String nombre;

    /*------RELACIONES------*/

    @ManyToMany(mappedBy = "aptitudes")
    private Set<Persona> personasConAptitud;

    public Aptitud(String nombre) {
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

    public Set<Persona> getPersonasConAptitud() {
        return personasConAptitud;
    }

    public void setPersonasConAptitud(Set<Persona> personasConAptitud) {
        this.personasConAptitud = personasConAptitud;
    }
}
