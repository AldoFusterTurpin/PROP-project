package vista;

import domini.CtrlDomini;
import javax.swing.JOptionPane;

//important! Aquesta classe no té una instància CtrlDomini com a atribut ja que
//la classe només es composa de una funció main on està programada la GUI i la lógica
//per això es comunica directament amb el Controlador del domini que se li passa
//com a paràmetre a la fun ció main(funció que es cridada desde VistaMenuPrincipal)
public class VistaEscollirRanking {


    public static void main(String[] a, CtrlDomini ctrlDomini) {

        //els diferents criteris d'ordenació del rànking.
        //Les diferents opcions que se li mostrarà al usuari de l'aplicació
        String[] opcions = {
            "Public name",
            "Score",
            "Games played",
            "Games won",
            "Games lost",
            "Percentage of games won",
            "Percentage of games lost",
            "Average score",
            "Streak of games won",
            "Streak of games lost"
        };

        String input = (String) JOptionPane.showInputDialog(null, //pare
                                                            "Sort Ranking by attribute", //títol del diàleg
                                                            "Select one", ////missatge que es mostra
                                                            JOptionPane.QUESTION_MESSAGE,
                                                            null, //icona predeterminada
                                                            opcions, //les opcions que es mostrarán
                                                            opcions[0]); //l'opció inicial


        //valor inicial trivial del contingut de la taula
        String rows[][]={
            {"","","","","","","","","","",""}
        };

        //la capçalera dels camps del Ranking
        String columns[]={
            "Public name",
            "Is human?",
            "Score",
            "Games played",
            "Games won",
            "Games lost",
            "% of games won",
            "% of games lost",
            "Average score",
            "Streak of games wont",
            "Streak of games lost"
        };

        //títol de la taula
        String title = "Ranking sorted by ";

        //important per evitar "Null pointer excpetion" si usuari apreta cancel
        if (input != null) {

            if (input.equals(opcions[0])) {
                rows = ctrlDomini.ordenarPerNomPublicGUI();
                title += "public name";
            }

            else if (input.equals(opcions[1])) {
                rows = ctrlDomini.ordenarPerPuntuacioGUI();
                title += "score";
            }

            else if (input.equals(opcions[2])) {
                rows = ctrlDomini.ordenarPerPartidesJugadesGUI();
                title += "games played";
            }

            else if (input.equals(opcions[3])) {
                rows = ctrlDomini.ordenarPerPartidesGuanyadesGUI();
                title += "games won";
            }

            else if (input.equals(opcions[4])) {
                rows = ctrlDomini.ordenarPerPartidesPerdudesGUI();
                title += "games lost";
            }

            else if (input.equals(opcions[5])) {
                rows = ctrlDomini.ordenarPerPercentatgePartidesGuanyadesGUI();
                title += "percentage of games won";
            }

            else if (input.equals(opcions[6])) {
                rows = ctrlDomini.ordenarPerPercentatgePartidesPerdudesGUI();
                title += "percentage of games lost";
            }

            else if (input.equals(opcions[7])) {
                rows = ctrlDomini.ordenarPerPuntuacioMitjanaGUI();
                title += "average score";
            }

            else if (input.equals(opcions[8])) {
                rows = ctrlDomini.ordenarPerMaximaRatxaVictoriesGUI();
                title += "streak of games won";
            }

            else if (input.equals(opcions[9])) {
                rows = ctrlDomini.ordenarPerMaximaRatxaDerrotesGUI();
                title += "streak of games lost";
            }

            VistaTaulaJugadors.main(rows, columns, title);
        }
  }
}
