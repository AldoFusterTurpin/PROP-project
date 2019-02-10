package vista;

import domini.CtrlDomini;
import domini.Partida;

public class CtrlVista {
    private CtrlDomini ctrlDomini;

    public CtrlVista() {

    }

    public void assignarCtrlDomini(CtrlDomini ctrlDomini) {
        this.ctrlDomini = ctrlDomini;
    }

    /*
    public CtrlDomini getCtrlDomini(){
        return ctrlDomini;
    }
    */

    //desde la vistaPantallaBenvinguda ja es mostrarà(invocarà) el menú principal
    public void mostrarVistaPantallaBenvinguda() {
        VistaPantallaBenvinguda.main(null, ctrlDomini);
    }

    public void jugarPartida(Partida p) {
       //(ModelTaulell model, Opcions config, Jugador codemaker, Jugador codebreaker)
        VistaPartida.main(p, ctrlDomini);
    }

}
