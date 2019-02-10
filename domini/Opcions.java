package domini;
import java.io.Serializable;


public class Opcions implements Serializable{
    //PRIVAT
    private final boolean permetBuit;
    private final boolean permetDuplicats;


    //PUBLIC
    public Opcions(boolean permetBuit, boolean permetDuplicats) {
        this.permetBuit = permetBuit;
        this.permetDuplicats = permetDuplicats;

        //this.permetTempsPerMoviment = false;
    }

    public boolean getPermetBuit() {
        return permetBuit;
    }

    public boolean getPermetDuplicats() {
        return permetDuplicats;
    }

    public String getTotesOpcions() {
        String ret = "PermetBuit: " + getPermetBuit()
            + "\n" + "PermetDuplicats: " + getPermetDuplicats()
            + "\n";
        return ret;
    }



}
