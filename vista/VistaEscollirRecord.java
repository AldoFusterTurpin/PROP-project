package vista;

import domini.CtrlDomini;
import javax.swing.JOptionPane;

public class VistaEscollirRecord {
    public static void main(String args[], CtrlDomini ctrlDomini) {

        //els diferents criteris d'ordenació del rànking.
        //Les diferents opcions que se li mostrarà al usuari de l'aplicació
        String[] opcions = {
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
                                                            "Select the record that you want", //títol del diàleg
                                                            "Select one", ////missatge que es mostra
                                                            JOptionPane.QUESTION_MESSAGE,
                                                            null, //icona predeterminada
                                                            opcions, //les opcions que es mostrarán
                                                            opcions[0]); //l'opció inicial


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
            "Streak of games won",
            "Streak of games lost"
        };

        String infoJugador [] = null;

        //títol de la taula
        String title = "Record of ";

        //important per evitar "Null pointer excpetion" si usuari apreta cancel
        if (input != null) {
            if (input.equals(opcions[0])) {
                infoJugador = ctrlDomini.recordPuntuacio();
                title += "score";
            }

            else if (input.equals(opcions[1])){
                infoJugador = ctrlDomini.recordPartidesJugades();
                title += "games played";
            }

            else if (input.equals(opcions[2])) {
                infoJugador = ctrlDomini.recordPartidesGuanyades();
                title += "games won";
            }

            else if (input.equals(opcions[3])){
                infoJugador = ctrlDomini.recordPartidesPerdudes();
                title += "games lost";
            }

            else if (input.equals(opcions[4])) {
                infoJugador = ctrlDomini.recordPercentatgePartidesGuanyades();
                title += "percentage of games won";
            }

            else if (input.equals(opcions[5])) {
                infoJugador = ctrlDomini.recordPercentatgePartidesPerdudes();
                title += "percentage of games lost";
            }

            else if (input.equals(opcions[6])) {
                infoJugador = ctrlDomini.recordPuntuacioMitjana();
                title += "average score";
            }

            else if (input.equals(opcions[7])) {
                infoJugador = ctrlDomini.recordMaximaRatxaVictories();
                title += "streak of games won";
            }

            else if (input.equals(opcions[8])) {
                infoJugador = ctrlDomini.recordMaximaRatxaDerrotes();
                title += "streak of games lost";
            }

            String[][] infoRecord = new String[1][];

            infoRecord[0] = infoJugador;
            VistaTaulaJugadors.main(infoRecord, columns, title);
        }
    }
}
