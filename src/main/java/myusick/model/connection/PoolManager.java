package myusick.model.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Pool de conexiones en MySQL
 *
 * @author Sandra Campos
 */
public class PoolManager {
    /*
     * Atributos de la clase
     */
    private static PoolManager instancia = new PoolManager();
    private static final int maxConexiones = 100;
    private static AtomicInteger numConexiones;

    static {
        numConexiones = new AtomicInteger(0);
    }


    /**
     * Constructor vacio y privado para que nadie pueda acceder a el,
     * convencion del patron Singleton
     */
    private PoolManager() {}


    /**
     *
     * @return instancia del pool manager a manejar, convencion del patron
     * singleton
     */
    public static PoolManager getInstance()  {
        return instancia;
    }

    /**
     * Acci�n de coger una de las conexiones disponibles en el poolJDBC.
     *
     * @return instancia de conexion a la BD
     */
    public synchronized Connection getConnection() {
        while (numConexiones.get() >= maxConexiones) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try{
            numConexiones.getAndIncrement();
            Connection con = ConnectionAdmin.getConnection();
            /* Desactivamos el auto-commit porque no va a hacer falta en ninguna transaccion */
            con.setAutoCommit(false);
            return con;
        }catch(SQLException e){
            return null;
        }
    }

    /**
     * Devolvemos la conexion al array de recursos liberados
     *
     * @param conexion conexion a la BD que queremos liberar
     */
    public synchronized void returnConnection(Connection conexion) {
        try{
            conexion.close();
            numConexiones.decrementAndGet();
            notifyAll();    //avisamos a todos los threads de la devolucion
        }catch(SQLException e){}
    }

}
