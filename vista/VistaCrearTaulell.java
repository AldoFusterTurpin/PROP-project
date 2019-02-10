package vista;
import domini.CtrlDomini;
import domini.RegistreTaulells;
import domini.ModelTaulell;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import persistencia.CtrlPersistencia;


//AUXILIAR CLASSES

/**
 * Create a circlular panel
 */
class CirclePanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        g.drawOval(1, 1, g.getClipBounds().width-1, g.getClipBounds().height-1);
        g.fillOval(1, 1, g.getClipBounds().width-1, g.getClipBounds().height-1);
    }
}

/**
 * Create a panel with image

 */
class ImagePanel extends JPanel {
    private Image img;
    protected void setImg(String path){
      try{
            URL url = getClass().getResource(path);
            img = ImageIO.read(url);
      }catch(Exception ex){
            System.err.println("Exception: "+ex.getMessage());
            JOptionPane.showMessageDialog(null, "An error ocurred while loading an image.\n"+ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
      }
    }
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }
}


/**
 *

 */
public class VistaCrearTaulell extends javax.swing.JFrame {

	/**
	 *Model de taulell seleccionat que es representa
	 */
    private ModelTaulell mt = new ModelTaulell("");
	/**
	 *Copia del registre de taulells
	 */
    private RegistreTaulells rt;
	/**
	 *Boolea que indica si s'ha modificat el taulell carregat
	 */
    private boolean boardModified;
	/**
	 *Boolea per gestionar el binding entre els sliders i els spinners
	 */
    private boolean binding=false;
	/**
	 *Boolea que indica si els colors estan correctament configurats, es a dir si no tenen cap color no definit
	 */
    private boolean isColorsSet;
	/**
	 *Id en el registre de l'ultim taulell carregat
	 */
    private int currentID;
	/**
	 *Boolea que indica si ya s'ha definit el fons
	 */
    private boolean setB=false;
	/**
	 *Jpanel del tipus ImagePanel que conte el fons
	 */
    private ImagePanel background;
	/**
	 *Jpanel que representa l'espai ocupat per la representacio del taulell
	 */
    private JPanel board;

    private CtrlDomini ctrlDomini;

    private CtrlPersistencia ctrlPersistencia;



//*****************SET*********************//
	/**
	 * Configura el registre de taulells
	 *@param registre es el registre usat
	 */
    public void setRegistreTaulells (RegistreTaulells registre){
        rt= registre;

    }

	/**
	 * Defineix la llista de colors del taulell
	 *@param ar es la llista rebuda
	 */
    public void setColorList(ArrayList<String> ar){
        try {
            mt.setColors(ar);
            boardModified=true;
            isColorsSet=true;
            updateColors();
        } catch (Exception ex) {
             System.err.println("Exception: "+ex.getMessage());
            JOptionPane.showMessageDialog(null, "Invalid color list.\n"+ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }

//***************FI_SET********************//


//*****************GET*********************//
	/**
	 * Obte la actual llista de colors del taulell a editar
	 *@return La actual configuracio de colors del taulell a editar
	 */
    public ArrayList<String> getColorList(){
        return (ArrayList<String>) mt.getColors();
    }
//***************FI_GET********************//


//*****AUXILIARS_REPRESENTACIO_GRAFICA*****//

	/**
	 * Funcio auxiliar per crear un CirclePanel a partir de uns valors
	 * @param size tamany tant amplada com alçada que prendra el resultat
	 * @param color el color que tindra el cercle en el panell
	 * @param posX la posicio en X on es dibuixara
	 * @param posY la posicio en Y on es dibuixara
	 * @param nam nom del panell
	 * @return El CirclePanel configurat
	 */
    private CirclePanel drawCircle(int size, String color, int posX, int posY, String name){
        CirclePanel aux = new CirclePanel();
        aux.setForeground(Color.decode(color));
        aux.setFocusable(false);
        aux.setName(name);
        aux.setOpaque(true);
        aux.setSize(size,size);
        aux.setLocation(posX,posY);
        return aux;
    }

	/**
	 * Funcio auxiliar per crear un ImagePanel a partir de uns valors
	 * @param w amplada del panell a crear
	 * @param h alçada del panell a crear
	 * @param path string que indica la ubicacio de la imatge
	 * @param posX la posicio en X on es dibuixara
	 * @param posY la posicio en Y on es dibuixara
	 * @param nam nom del panell
	 * @return El ImagePanel configurat
	 */
    private ImagePanel drawImage(int w, int h, String path, int posX, int posY, String name){
        ImagePanel aux = new ImagePanel();
        aux.setImg(path);
        aux.setFocusable(false);
        aux.setName(name);
        aux.setOpaque(true);
        aux.setSize(w,h);
        aux.setLocation(posX,posY);
        return aux;
    }
//****FI_AUXILIARS_REPRESENTACIO_GRAFICA****//



//******ACTUALITZAR_VALORS_EN_PANTALLA******//

	/**
	 *Actualitza la representacio grafica de la llista de colors
	 */
    private void updateColors(){
        colors.removeAll();
        colors.repaint();
        colors.setLayout(null);
        for(int i=0;i<mt.getNColors();++i){
            if(!"*".equals(mt.getColors().get(i))){
                CirclePanel aux = drawCircle(36, mt.getColors().get(i),45*(i%10)+2,(int) +45*(i/10)+2,"color"+i);
                CirclePanel back = drawCircle(40, "#000000",45*(i%10),(int) +45*(i/10),"back"+i);
                colors.add(aux);
                colors.add(back);
                //Si es quadrat:
                //aux.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            } else {
                ImagePanel aux = drawImage(40,40,"/vista/empty.png",45*(i%10),45*(i/10),"color"+i);
                colors.add(aux);
                isColorsSet=false;
            }
        }
    }

	/**
	 * Actualitza la representacio grafica del taulell
	 */
    private void updateBoard(){
        representacioTaulell.removeAll();
        representacioTaulell.repaint();
        representacioTaulell.setLayout(null);

        int numEsp=mt.getAmplada();
        int panelH= representacioTaulell.getHeight()-12;
        int panelW= representacioTaulell.getWidth()-18;
        int sizeX =Math.min((int) panelW/numEsp,panelW/7);
        int sizeY= (int) panelH/mt.getAlcada();

        int size=Math.min(sizeX, sizeY);

        board=new JPanel();
        board.setLocation(9+(panelW-numEsp*size)/2,6);
        board.setSize(numEsp*size,panelH);
        board.setOpaque(false);
        board.setLayout(null);
        representacioTaulell.add(board);
        String path="/vista/hole.png";
         for(int i=0; i<mt.getAmplada(); ++i){
            for(int j=0; j<mt.getAlcada(); ++j){
                ImagePanel aux = drawImage(size,size,path,i*size,panelH-(j+1)*size,"pos"+i+j);
                board.add(aux);
            }
        }

    }

	/**
	 * Actualitza els valors de la interficie als del taulell carregat
	 */
    private void setInterfaceValuesAsBoard(){
        inputName.setText(mt.getNom());
        alçadaSlider.setValue(mt.getAlcada());
        ampladaSlider.setValue(mt.getAmplada());
        nColors.setValue(mt.getNColors());
        nColors.setModel(new javax.swing.SpinnerNumberModel(mt.getNColors(), mt.getAmplada(), 30, 1));
        isColorsSet=true;
        updateColors();
        updateBoard();
        boardModified = false;
    	currentID=rt.getIDFromName(llistaRegistres.getSelectedItem().toString());
    }

	/**
	 *Actualitza la llista que que es mostra amb els taulells disponibles
	 */
    private void updateBoardList(){
        ArrayList<String> al= rt.getIDNoms();
        String[] s = new String[al.size()];
        s = al.toArray(s);
        llistaRegistres.setModel(new javax.swing.DefaultComboBoxModel<>(s));
    }

	/**
	 * Defineix el fons
	 */
    private void setBackground(){
        if(!setB){
            background = drawImage( this.getWidth(), this.getHeight(), "/vista/main_menu.jpg", 0, 0, "background");
            this.add(background);
            setB=true;
        }
        else {
            this.remove(background);
            background=drawImage( this.getWidth(), this.getHeight(), "/vista/main_menu.jpg", 0, 0, "background");
            background.repaint();
            this.add(background);

        }
    }

	/**
	 * En cas de redimensionament de la finestra mante el panell base en el centre
	 */
    private void resize(){
       int w= base.getWidth();
       int h = base.getHeight();
       base.setLocation((this.getWidth()-w)/2, (this.getHeight()-h)/2);
    }
//*****FI_ACTUALITZAR_VALORS_EN_PANTALLA*****//

//**************CANVIS_DE_VALORS*************//

	/**
	 * Modifica l'amplada del taulell representat, a mes s'encarrega de
	 * la correcta representacio en slider i spinner també actualitza
	 * el model del spinner nColors per tal de garantir que es compleixen
	 * els colors minims
	 */
    private void modificarAmplada(int newValue){
        binding=true;
        ampladaSlider.setValue(newValue);
        amplada.setValue(newValue);
        binding=false;
        try{
            mt.setAmplada(newValue);
            int currentColors=mt.getNColors();
            if(currentColors<newValue){
                currentColors=newValue;
            }
            nColors.setValue(currentColors);
            nColors.setModel(new javax.swing.SpinnerNumberModel(currentColors, newValue, 8, 1));
            boardModified = true;
            updateBoard();
        }catch(Exception ex){
            System.err.println("Exception: "+ex.getMessage());
            JOptionPane.showMessageDialog(null, "Invalid value.\n"+ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }

	/**
	 * Modifica l'alçada del taulell representat, a mes s'encarrega de
	 * la correcta representacio en slider i spinner
	 */
    private void modificarAlçada(int newValue){
        binding=true;
        alçadaSlider.setValue(newValue);
        alçada.setValue(newValue);
        binding=false;
        try {
            mt.setAlcada(newValue);
            boardModified = true;
            updateBoard();
        }catch(Exception ex){
            System.err.println("Exception: "+ex.getMessage());
            JOptionPane.showMessageDialog(null, "Invalid value.\n"+ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }

	/**
	 * Actualitza el taulell representat per un altre, si s'havia fet alguna modificacio no guardada
	 * un dialeg demana confirmacio
	 */
    private void callToLoadBoard(){
        try {
            if(boardModified){
                Object[] options = { "Yes", "Cancel" };
                int n = JOptionPane.showOptionDialog(null,
                    "If you load this board all changes will be lost. Are you sure?", //->missatge que es mostrarà
                    "Warning", //->títol del cuadre de dialeg que es mostrarà
                    JOptionPane.YES_NO_CANCEL_OPTION, //tipus d'opcions mostrades
                    JOptionPane.QUESTION_MESSAGE, //icona de 'question'(pregunta)
                    null, //no usar icona personalitzat
                    options, //els titols dels butons
                    options[1]); //quin té el focus(iluminat)
                if (n == JOptionPane.YES_OPTION){
                    String c=llistaRegistres.getSelectedItem().toString();
                    mt=rt.getTaulell(rt.getIDFromName(c));
                    setInterfaceValuesAsBoard();
                }
            }
            else{
                String c=llistaRegistres.getSelectedItem().toString();
                mt=rt.getTaulell(rt.getIDFromName(c));
                setInterfaceValuesAsBoard();
            }

        }catch (Exception ex) {
        	System.err.println("Exception: "+ex.getMessage());
        	JOptionPane.showMessageDialog(null, "Error while loading the board.\n"+ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
    	}
    }

//************FI_CANVIS_DE_VALORS************//

//****************CREADORA*******************//
    /**
     * Crea un nou VistaCrearTaulell()
     */
       private VistaCrearTaulell(CtrlDomini ctrlDomini) {
       try {
          ctrlPersistencia = new CtrlPersistencia();
          this.ctrlDomini = ctrlDomini;
          rt = this.ctrlDomini.getRegistreTaulells();
            initComponents();
            mt=rt.getTaulell(0);
            setInterfaceValuesAsBoard();
        } catch (Exception ex) {
             System.err.println("Exception: "+ex.getMessage());
            JOptionPane.showMessageDialog(null, "The files are damaged, Restart the game.\n"+ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }
//***************FI_CREADORA***************//

//***REPRESENTACIO_GRAFICA_GENERAT AUTO****//

    /**
     * Inicialitzacio dels elements, "creat automaticament"
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        base = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        alçada = new javax.swing.JSpinner();
        alçadaSlider = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        representacioTaulell = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        amplada = new javax.swing.JSpinner();
        ampladaSlider = new javax.swing.JSlider();
        nColors = new javax.swing.JSpinner();
        inputName = new javax.swing.JTextField();
        llistaRegistres = new javax.swing.JComboBox<>();
        loadButton = new javax.swing.JButton();
        colors = new javax.swing.JPanel();
        deleteButton = new javax.swing.JButton();
        AddBoard = new javax.swing.JButton();
        editColorsButton = new javax.swing.JButton();
        backB = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(14, 31, 229));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(java.awt.Color.darkGray);
        setIconImages(null);
        setMinimumSize(new java.awt.Dimension(1050, 700));
        setPreferredSize(new java.awt.Dimension(1062, 716));
        setSize(new java.awt.Dimension(1062, 716));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        getContentPane().setLayout(null);

        base.setBackground(new java.awt.Color(0, 0, 255));
        base.setMaximumSize(new java.awt.Dimension(1000, 600));
        base.setMinimumSize(new java.awt.Dimension(1000, 600));
        base.setOpaque(false);
        base.setPreferredSize(new java.awt.Dimension(1000, 600));
        base.setRequestFocusEnabled(false);

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Load an existing board:");

        alçada.setModel(new javax.swing.SpinnerNumberModel(10, 1, 20, 1));
        alçada.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                alçadaStateChanged(evt);
            }
        });

        alçadaSlider.setMaximum(20);
        alçadaSlider.setMinimum(1);
        alçadaSlider.setMinorTickSpacing(10);
        alçadaSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        alçadaSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                alçadaSliderStateChanged(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Number of colors:");

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Board name:");

        representacioTaulell.setBackground(new java.awt.Color(99, 97, 100));
        representacioTaulell.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 47, 60), 5));
        representacioTaulell.setMaximumSize(new java.awt.Dimension(390, 450));
        representacioTaulell.setPreferredSize(new java.awt.Dimension(388, 451));

        javax.swing.GroupLayout representacioTaulellLayout = new javax.swing.GroupLayout(representacioTaulell);
        representacioTaulell.setLayout(representacioTaulellLayout);
        representacioTaulellLayout.setHorizontalGroup(
            representacioTaulellLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(representacioTaulellLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(372, Short.MAX_VALUE))
        );
        representacioTaulellLayout.setVerticalGroup(
            representacioTaulellLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(representacioTaulellLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(95, 95, 95))
        );

        amplada.setModel(new javax.swing.SpinnerNumberModel(4, 1, 8, 1));
        amplada.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ampladaStateChanged(evt);
            }
        });

        ampladaSlider.setMajorTickSpacing(10);
        ampladaSlider.setMaximum(8);
        ampladaSlider.setMinimum(1);
        ampladaSlider.setMinorTickSpacing(5);
        ampladaSlider.setToolTipText("");
        ampladaSlider.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ampladaSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ampladaSliderStateChanged(evt);
            }
        });

        nColors.setModel(new javax.swing.SpinnerNumberModel(4, 1, 8, 1));
        nColors.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                nColorsStateChanged(evt);
            }
        });

        inputName.setToolTipText("");
        inputName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputNameFocusLost(evt);
            }
        });

        ArrayList<String> al= rt.getIDNoms();
        String[] s = new String[al.size()];
        s = al.toArray(s);
        llistaRegistres.setModel(new javax.swing.DefaultComboBoxModel<>(s));
        llistaRegistres.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                llistaRegistresPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        loadButton.setText("Load");
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });

        colors.setBackground(new java.awt.Color(0, 255, 0));
        colors.setMaximumSize(new java.awt.Dimension(450, 135));
        colors.setMinimumSize(new java.awt.Dimension(450, 135));
        colors.setOpaque(false);
        colors.setPreferredSize(new java.awt.Dimension(450, 135));

        javax.swing.GroupLayout colorsLayout = new javax.swing.GroupLayout(colors);
        colors.setLayout(colorsLayout);
        colorsLayout.setHorizontalGroup(
            colorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        colorsLayout.setVerticalGroup(
            colorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 135, Short.MAX_VALUE)
        );

        deleteButton.setText("Delete Board");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        AddBoard.setText("Add Board");
        AddBoard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddBoardActionPerformed(evt);
            }
        });

        editColorsButton.setText("Edit Colors");
        editColorsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editColorsButtonActionPerformed(evt);
            }
        });

        backB.setText("Back");
        backB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout baseLayout = new javax.swing.GroupLayout(base);
        base.setLayout(baseLayout);
        baseLayout.setHorizontalGroup(
            baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, baseLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(amplada, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(273, 273, 273))
            .addGroup(baseLayout.createSequentialGroup()
                .addGroup(baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(baseLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6)
                            .addComponent(inputName, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(baseLayout.createSequentialGroup()
                                .addComponent(llistaRegistres, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(loadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(baseLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(baseLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nColors, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(editColorsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(colors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ampladaSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(representacioTaulell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(alçadaSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(alçada, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(baseLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(AddBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(baseLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(baseLayout.createSequentialGroup()
                                .addGap(711, 711, 711)
                                .addComponent(backB)))))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        baseLayout.setVerticalGroup(
            baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(baseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(baseLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputName, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(loadButton)
                            .addComponent(llistaRegistres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53)
                        .addGroup(baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nColors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editColorsButton))
                        .addGap(18, 18, 18)
                        .addComponent(colors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 158, Short.MAX_VALUE)
                        .addGroup(baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AddBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(deleteButton))
                        .addGap(25, 25, 25))
                    .addGroup(baseLayout.createSequentialGroup()
                        .addGroup(baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(alçadaSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                            .addComponent(representacioTaulell, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ampladaSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(baseLayout.createSequentialGroup()
                                .addComponent(amplada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, baseLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(backB))))
                    .addGroup(baseLayout.createSequentialGroup()
                        .addGap(215, 215, 215)
                        .addComponent(alçada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        getContentPane().add(base);
        base.setBounds(0, 0, 1030, 650);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

//**FI_REPRESENTACIO_GRAFICA_GENERAT AUTO**//

//****************BOTONS*******************//
	//Afegir taulell al registre
	/**
	 * Es crida en premer el boto d'afegir taulell, avisa si es substitueix o es crea un de nou
	 * tambe evita que es pugui editar el taulell per defecte i que no tingui colors per configurar
	 */
    private void AddBoardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBoardActionPerformed
        try{
            if(!isColorsSet) JOptionPane.showMessageDialog(null, "A board can't be added with unset colors", "Alert", JOptionPane.ERROR_MESSAGE);
            else if(boardModified && rt.freeName(mt.getNom())){
                Object[] options = { "Confirm", "Cancel" };
                int n = JOptionPane.showOptionDialog(null,
                    "A new board will be created with name: "+mt.getNom(), //->missatge que es mostrarà
                    "Information", //->títol del cuadre de dialeg que es mostrarà
                    JOptionPane.OK_CANCEL_OPTION, //tipus d'opcions mostrades
                    JOptionPane.QUESTION_MESSAGE, //icona de 'question'(pregunta)
                    null, //no usar icona personalitzat
                    options, //els titols dels butons
                    null); //no predefinir cap opcio

                if(n==0){
                    currentID=rt.afegirTaulell(mt);
                    updateBoardList();
                    boardModified = false;
                    ctrlPersistencia.guardarRegistreTaulells(rt);
                }
            }
            else if(boardModified && !rt.freeName(mt.getNom())){
                if(mt.getNom().equals("Default")){
                    JOptionPane.showMessageDialog(null, "The Default board can't be modified", "Alert", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    Object[] options = { "Confirm", "Cancel" };
                    int n = JOptionPane.showOptionDialog(null,
                        "The board "+mt.getNom()+" will be overwritten, are you sure?", //->missatge que es mostrarà
                        "Warning", //->títol del cuadre de dialeg que es mostrarà
                        JOptionPane.OK_CANCEL_OPTION, //tipus d'opcions mostrades
                        JOptionPane.QUESTION_MESSAGE, //icona de 'question'(pregunta)
                        null, //no usar icona personalitzat
                        options, //els titols dels butons
                        null); //no predefinir cap opcio
                    if(n==0){
                        int id_toDelete= rt.getIDFromName(mt.getNom());
                        rt.substituirTaulell(id_toDelete, mt);
                        updateBoardList();
                        boardModified=false;
                        ctrlPersistencia.guardarRegistreTaulells(rt);
                    }
                }
            }

        }catch (Exception e){
            System.err.println("Error while adding the board "+e.getMessage());
        }
    }//GEN-LAST:event_AddBoardActionPerformed

    //EliminarTaulell
	/**
	 * En premer el boto d'eliminar taulell, demana confirmacio i eliminar el taulell amb el nom designat, evita
	 * eliminar el taulell per defecte en aquest cas mostra un missatge d'error
	 */
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        try{
            if(currentID==0){
                JOptionPane.showMessageDialog(null, "Can't remove the Default board", "Alert", JOptionPane.ERROR_MESSAGE);
            }
            else{
                Object[] options = { "Yes", "No" };
                int n = JOptionPane.showOptionDialog(null,"The board "+rt.getTaulell(currentID).getNom()+" will be deleted, are you sure?",
                    "Warning", JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                if(n==0){
                    rt.eliminarTaulell(currentID);
                    mt=rt.getTaulell(0);
                    updateBoardList();
                    setInterfaceValuesAsBoard();
                    currentID=0;
                    ctrlPersistencia.guardarRegistreTaulells(rt);

                }
            }

        } catch(Exception e){
            System.err.println("Exception: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "There's been an error while removing the board.\n"+e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_deleteButtonActionPerformed

	/**
	 * Es crida en premer el boto d'editar colors, obre una a VistaColorChoice per editar la configuracio de colors
	 */
    private void editColorsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editColorsButtonActionPerformed
        VistaColorChoice.main(null);
        VistaColorChoice.setParent(this);
    }//GEN-LAST:event_editColorsButtonActionPerformed

	//Carrega taulell del registre
	/**
	 * En premer el boto de "carrega" carrega el taulell seleccionat en la llista
	 */
    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
        callToLoadBoard();
    }//GEN-LAST:event_loadButtonActionPerformed

    //GO BACK
	/**
	 * En premer el boto d'enrere permet tornar al menu anterior, si hi havia canvis no desats demana confirmacio
	 */
    private void backBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBActionPerformed
        if(boardModified){
            Object[] options = { "Yes", "Cancel" };
            int n = JOptionPane.showOptionDialog(null,
                "You have unsaved changes, are you sure you want to exit?", //->missatge que es mostrarà
                "Warning", //->títol del cuadre de dialeg que es mostrarà
                JOptionPane.YES_NO_CANCEL_OPTION, //tipus d'opcions mostrades
                JOptionPane.QUESTION_MESSAGE, //icona de 'question'(pregunta)
                null, //no usar icona personalitzat
                options, //els titols dels butons
                options[1]); //quin té el focus(iluminat)
            if (n == JOptionPane.YES_OPTION){
                dispose();
            }
        }
        else dispose();

    }//GEN-LAST:event_backBActionPerformed

//**************FI_BOTONS******************//



//**********MODIFICACIO_TAULELL************//

/**
	 * En seleccionar qualsevol altre element despres d'haver introduit el text defineix el nom del taulell
	 */
    private void inputNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inputNameFocusLost
        mt.setNom(inputName.getText().replace(" ", ""));
        boardModified=true;
    }//GEN-LAST:event_inputNameFocusLost


    //Canviar num colors
	/**
	 * En actualitzar el valor del spinner redefineix el nombre de colors del taulell, s'hi s'en
	 * afegeixen de mes els inicialitza com a buits
	 */
    private void nColorsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_nColorsStateChanged
        try{
            mt.setNColors((int) nColors.getValue());
            boolean empty = false;
            ArrayList<String> al = mt.getColors();
            String s="*";
            int i=0;
            while(al.size()>0 && !empty){
                s=al.remove(0);
                if(s.equals("*")) empty=true;
                ++i;
            }
            isColorsSet=!empty;
            boardModified = true;
            updateColors();
        }catch(Exception ex){
            System.err.println("Exception: "+ex.getMessage());
            JOptionPane.showMessageDialog(null, "Invalid value.\n"+ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_nColorsStateChanged


	//Canviar amplada
	/**
	 * En modificar el slider de l'amplada actualitza el seu valor en el model de taulell
	 */
    private void ampladaSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ampladaSliderStateChanged
        if (!ampladaSlider.getValueIsAdjusting()) modificarAmplada(ampladaSlider.getValue());
    }//GEN-LAST:event_ampladaSliderStateChanged

	/**
	 * En modificar el spinner de l'amplada actualitza el seu valor en el model de taulell
	 */
    private void ampladaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ampladaStateChanged
         modificarAmplada((int) amplada.getValue());
    }//GEN-LAST:event_ampladaStateChanged



    //Canviar alçada
	/**
	 * En modificar el slider de l'alçada actualitza el seu valor en el model de taulell
	 */
    private void alçadaSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_alçadaSliderStateChanged
       if (!alçadaSlider.getValueIsAdjusting()) modificarAlçada(alçadaSlider.getValue());
    }//GEN-LAST:event_alçadaSliderStateChanged

	/**
	 * En modificar el spinner de l'alçada actualitza el seu valor en el model de taulell
	 */
    private void alçadaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_alçadaStateChanged
         modificarAlçada((int) alçada.getValue());
    }//GEN-LAST:event_alçadaStateChanged


//*********FI_MODIFICACIO_TAULELL**********//

//*****************MISC********************//
	/**
	 * Es crida quan es redimensiona la finestra
	 */
    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        resize();
        setBackground();
    }//GEN-LAST:event_formComponentResized

	/**
	 * Quan s'oculta la llista de taulells torna a pintar els colors que podrien haver estat tapats
	 */
    private void llistaRegistresPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_llistaRegistresPopupMenuWillBecomeInvisible
        updateColors();
    }//GEN-LAST:event_llistaRegistresPopupMenuWillBecomeInvisible

//***************FI_MISC*******************//



    /**
     * @param args the command line arguments
     */
    public static void main(String args[], CtrlDomini ctrlDomini) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaCrearTaulell.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VistaCrearTaulell(ctrlDomini).setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBoard;
    private javax.swing.JSpinner alçada;
    private javax.swing.JSlider alçadaSlider;
    private javax.swing.JSpinner amplada;
    private javax.swing.JSlider ampladaSlider;
    private javax.swing.JButton backB;
    private javax.swing.JPanel base;
    private javax.swing.JPanel colors;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editColorsButton;
    private javax.swing.JTextField inputName;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JComboBox<String> llistaRegistres;
    private javax.swing.JButton loadButton;
    private javax.swing.JSpinner nColors;
    private javax.swing.JPanel representacioTaulell;
    // End of variables declaration//GEN-END:variables

}
