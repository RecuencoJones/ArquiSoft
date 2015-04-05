package myusick.model.DTO;

import java.io.Serializable;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class Publications implements Serializable {

    private int id;
    private String name;
    private long fecha;

    public Publications(int id, String name, long fecha) {
        this.id = id;
        this.name = name;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }
}
