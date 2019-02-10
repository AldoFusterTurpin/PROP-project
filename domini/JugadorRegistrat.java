package domini;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class JugadorRegistrat extends Jugador implements Serializable {
    //PRIVAT
    //atributs per fer login
    private String nomUsuari;
    private String passwordUsuari;

    //per eliminar la partida actual
    int idPartidaActual;
    int nextId;//perquè cap id de partida coincideixi

    protected Map<Integer, Partida> partides_pausades;

    //PUBLIC
    //Constructors


    public JugadorRegistrat(String nomPublic, String nomUsuari, String passwordUsuari) {
        //Jugador (la classe pare) ja inicialitza totes les variables de Jugador
        super(nomPublic, true);
        this.nomUsuari = nomUsuari;
        this.passwordUsuari = passwordUsuari;
        nextId = 1;
        partides_pausades = new LinkedHashMap<>();
    }

    public Map<Integer, Partida>  getPartidesPausades() {
        return partides_pausades;
    }

    public void afegirPartidaPausada(Partida p) {
        partides_pausades.put(nextId, p);
        ++nextId;
    }

    public void setIdPartidaActual(int id) {
        idPartidaActual = id;
    }

    //per si es vol comprobar que cuadra el id de la partida i la que es vol eliminar
    public int getIdPartidaActual() {
        return idPartidaActual;
    }

    /**
     * Elimina la partida actual
     * @throws java.lang.Exception
    */

    public void eliminarPartidaActual() throws Exception{
        partides_pausades.remove(idPartidaActual);
    }

    @Override
    public String getNomUsuari() {
        return nomUsuari;
    }

    @Override
    public String getPasswordUsuari() {
        return passwordUsuari;
    }

    @Override
    public void setPasswordUsuari(String passwordUsuari) {
        this.passwordUsuari = passwordUsuari;
    }

    /**
     *
     * @return Tota la informació de l'usuari
     */
    /* Aquesta funció només s'usa per debuggar! Al ranking aquesta info no apareix
    @Override
    public String getInfoJugador() {
        String ret = super.getInfoJugador();
        ret += "nom usuari: " + getNomUsuari()
            + "\n" + "password: " + getPasswordUsuari()
            + "\n";

        return ret;
    }
    */
}
