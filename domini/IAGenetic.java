package domini;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Algoritme inspirat en:
 * https://lirias.kuleuven.be/bitstream/123456789/164803/1/kbi_0806.pdf
 */

public class IAGenetic extends IA implements Serializable {

    /**
     * ArrayList que desa els codis aleatòriament generats dels quals s'extreuen
     * els candidats
     */
    private ArrayList<Codi> poblacio;

    /**
     * ArrayList que desa els candidats dels quals es treu el Codi que est torna
     */
    private ArrayList<Codi> candidats;


    /**
     * Màximes generacions per les quals s'ha d'evolucionar la poblacio
     */
    private int maxGen = 60;

    /**
     * Màxim tamany de candidats abans de triar la resposta.
     */
    private int maxSize = 180;


    /**
     * Constructora de la classe, inicialitza la IA a partir de l'amplada
     * proporcionada.
     * @param amplada Amplada de cada codi de la resposta que s'ha de generar.
     * @param colors ArrayString contenint tots els possibles colors, incloient
     * valor buit si cal.
     * @param hiHaBuits boolean indicant si hi pot haver caselles buides en la solució "*"
     * @param hiHaRepetits boolean indicant si hi pot haver colors repetits en
     * una resposta donada.
     */
    public IAGenetic(int amplada, ArrayList<String> colors, boolean hiHaBuits, boolean hiHaRepetits){
        super(amplada, colors, hiHaBuits, hiHaRepetits);
    }



    /**
     * intercambia els valors de c1 i c2 de la posició i i j de cada Codi
     * @param c1 Primer Codi a modificar
     * @param c2 Segon Codi a modificar
     */
    private void xCross1(Codi c1, Codi c2){
        ArrayList<String> A1 = new ArrayList<>(c1.getCombinacio());
        ArrayList<String> A2 = new ArrayList<>(c2.getCombinacio());

        String buffer;
        Random random = new Random();
        for (int i = random.nextInt(A1.size()); i < A1.size(); i++){
            buffer = A1.get(i);
            A1.set(i,A2.get(i));
            A2.set(i,buffer);
        }
        c1.setCombinacio(A1);
        c2.setCombinacio(A2);
    }

    /**
     * intercambia els valors de c1 i c2 de la posició i i j de cada Codi
     * @param c1 Primer Codi a modificar
     * @param c2 Segon Codi a modificar
     */
    private void xCross2(Codi c1, Codi c2){
        ArrayList<String> A1 = new ArrayList<>(c1.getCombinacio());
        ArrayList<String> A2 = new ArrayList<>(c2.getCombinacio());

        String buffer;
        Random random = new Random();
        int i = random.nextInt(A1.size());
        int j = random.nextInt(A1.size()-i)+i;
        for (; i < j ; i++){
            buffer = A1.get(i);
            A1.set(i,A2.get(i));
            A2.set(i,buffer);
        }

        c1.setCombinacio(A1);
        c2.setCombinacio(A2);
    }

    /**
     * Cambiar el valor d'un color de c per un altre aleatoriament generat
     * @param c Codi a modificar
     */
    private void colorChange(Codi c){
        ArrayList<String> A1 = new ArrayList<>(c.getCombinacio());
        Random random = new Random();
        int i = random.nextInt(A1.size());
        int j = random.nextInt(colors.size());
        A1.set(i,colors.get(j));

        c.setCombinacio(A1);
    }

    /**
     * intercambia les posicions de dos elements de c
     * @param c Codi a modificar
     */
    private void permutation(Codi c) {
        ArrayList<String> A1 = new ArrayList<>(c.getCombinacio());
        Random random = new Random();
        int i = random.nextInt(A1.size());
        int j = random.nextInt(A1.size());

        String buffer = A1.get(i);
        A1.set(i,A1.get(j));
        A1.set(j,buffer);

        c.setCombinacio(A1);
    }

    /**
     * Inverteix les posicions dels elements de c d'entre dos punts
     * @param c Codi a modificar
     */
    private void inversion(Codi c){
        ArrayList<String> A1 = new ArrayList<>(c.getCombinacio());
        String buffer;
        Random random = new Random();
        int i = random.nextInt(A1.size());
        int j = random.nextInt(A1.size());
        if(i > j) {
            int k = i;
            i = j;
            j = k;
        }

        while(i < j){
            buffer = A1.get(i);
            A1.set(i,A1.get(j));
            A1.set(j,buffer);

            i++;
            j--;
        }

        c.setCombinacio(A1);
    }

    /**
     * Evoluciona la població actual a base de funcions aleatòries.
     */
    private void evolucionarPoblacio(){
        Random random = new Random();
        for(int i = 0; i < this.poblacio.size(); i += 2) {
            if (random.nextInt(100) < 50 )
                xCross1(this.poblacio.get(i),this.poblacio.get(i+1));
            else
                xCross2(this.poblacio.get(i),this.poblacio.get(i+1));
        }

        for(int i = 0; i < this.poblacio.size(); i++) {
            if (random.nextInt(100) < 30 )
                colorChange(this.poblacio.get(i));
            if (random.nextInt(100) < 30 )
                permutation(this.poblacio.get(i));
            if (random.nextInt(100) < 20 )
                inversion(this.poblacio.get(i));
        }
    }

    /**
     * Genera una valoracio per un codi donat
     * @param base Codi al qual comparar objectiu
     * @param objectiu el Codi del qual es vol obtenir la valoracio
     * @return la valoracio del codi intent respecte al codi secret
     */
    private Valoracio generarValoracio(Codi base, Codi objectiu){
        Valoracio val=new Valoracio(amplada);
        ArrayList<String> auxS=base.getCombinacio();
        ArrayList<String> auxI=objectiu.getCombinacio();
        for(int i=0;i<amplada;++i){
            if(auxS.get(i).equals(auxI.get(i))){
                try {
                    val.afegirPunt(2);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                auxS.set(i,"");
                auxI.set(i,"");
            }
        }
        for(int j=0;j<amplada;++j){
            if(!auxS.get(j).equals("")){
                for(int k=0; k<amplada;++k){
                    if(auxS.get(j).equals(auxI.get(k))){
                        try {
                            val.afegirPunt(1);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                        auxS.set(j,"");
                        auxI.set(k,"");
                        break;
                    }
                }
                if(!auxS.get(j).equals("")) {
                    try {
                        val.afegirPunt(0);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }
        val.sort();
        return val;
    }

    /**
     * Comprova si un codi c es compatible amb les valoracions i intents anteriors
     * @param c Codi a avaular
     * @return true si és un candidat vàlid, false si no.
     */
    private boolean isValidCandidate(Codi c){
        boolean isValid = true;
        for (int i = 0; i < this.intents.size() && isValid; i++){
            Valoracio v = this.valoracions.get(i);
            Valoracio v2 = generarValoracio(c,this.intents.get(i));

            isValid = v.isEqual(v2);


        }


        return isValid;
    }

    /**
     * Genera un codi inicial trivial per a començar l'algoritme.
     * @return Codi consistent en els possibles colors en ordre fins que s'ompli
     * tota la llargada del codi.
     */
    public Codi firstGuess(){

        Codi guess = new Codi(this.amplada);
        ArrayList<String> combinacio = new ArrayList();
        for(int i = 0; i < this.amplada; i++) {
            combinacio.add(colors.get(i%colors.size()));
        }
        guess.setCombinacio(combinacio);

        this.intents.add(guess);


        return guess;
    }

    /**
     * Genera la seguent guess de l'algoritme, assumint que s'hagi generat la primera.
     * @param val Valoracio de guess anterior
     * @return Codi guess, indicant la seguent guess de l'algoritme
     */
    public Codi nextGuess(Valoracio val){
        if(!this.intents.isEmpty()){

            Random random = new Random();
            this.ronda++;
            this.valoracions.add(val);
            if (ronda > 1) this.intents.add(lastIntent);

            int nGeneracions = 1;
            poblacio = new ArrayList();
            candidats = new ArrayList();

            int repopulations = 0;
            //inicialitzar candidats

            for (int i = 0; i < this.maxSize; i++) poblacio.add(generateRandomCode());
            boolean capResultat = true;
            while(capResultat){
                while (nGeneracions < this.maxGen && candidats.size() < this.maxSize) {
                    evolucionarPoblacio();
                    for(int i = 0; i < this.poblacio.size(); i++){
                        if (isValidCandidate(this.poblacio.get(i))) {
                            candidats.add(poblacio.get(i));
                            poblacio.set(i, generateRandomCode());
                        }
                    }

                    nGeneracions++;
                }

                if (candidats.isEmpty()) {
                    repopulations++;
                    if (repopulations == 10000) return generateRandomCode();
                    else if ((repopulations % 1000) == 999) {

                        ronda--;
                        intents.remove(intents.size()-1);
                        valoracions.remove(valoracions.size()-1);
                    }
                    poblacio = new ArrayList();
                    for (int i = 0; i < this.maxSize; i++) poblacio.add(generateRandomCode());

                } else {
                    capResultat = false;
                }
            }

            lastIntent = candidats.get(random.nextInt(candidats.size()));
            return lastIntent;
        } else {
            System.out.println("No es pot fer una nextGuess sense fer primer"
            + "una first guess.");
            return new Codi(this.amplada);
        }
    }
}
