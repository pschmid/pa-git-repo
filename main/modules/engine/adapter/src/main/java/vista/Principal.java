/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.event.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;

import modelo.Archivitos;
import fiuba.pyp.AdaptedOperationManeger;
import fiuba.pyp.Operation;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import static java.lang.Thread.sleep;

public class Principal extends javax.swing.JFrame implements ActionListener {

    public AdaptedOperationManeger adaptedOperationManeger;
    private int lastDeleteCaretPosition;
    private String lastText;
    private int portNumber=9001;
    private int lastLocalPosition;

    public Principal() {
        initComponents();
        setTitle("EDITOR_" + Integer.toString(portNumber));
        txtNumLineas.append(numLin + "\n");
        areaTexto.setComponentPopupMenu(jPopupMenu1);
        poneAcciones();
        start();

    }

    //Este método sirve para agregar las acciones a cada componente
    private void poneAcciones() {
         areaTexto.addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent evt) {
                presionaBorrado(evt);
            }
            //Debe caputarse los eventos al presionar una tecla
            public void keyPressed(KeyEvent evt) {
                presionaTecla(evt);
            }
            public void keyTyped(KeyEvent evt) {
            }
        });
        btnAbrir.addActionListener(this);
        btnNuevo.addActionListener(this);
        btnGuardar.addActionListener(this);
        jmiAbrir.addActionListener(this);
        jmiNuevo.addActionListener(this);
        jmiGuardar.addActionListener(this);
        jmiGuardarComo.addActionListener(this);
        jmiCopiar.addActionListener(this);
        jmiCortar.addActionListener(this);
        jmiPegar.addActionListener(this);
        jpmiCopiar.addActionListener(this);
        jpmiCortar.addActionListener(this);
        jpmiPegar.addActionListener(this);
        jmiAcerca.addActionListener(this);
        jmiSalir.addActionListener(this);
       
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lastDeleteCaretPosition = 0; lastLocalPosition = 0;lastText = "";
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jpmiCopiar = new javax.swing.JMenuItem();
        jpmiCortar = new javax.swing.JMenuItem();
        jpmiPegar = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaTexto = new javax.swing.JTextArea();
        jToolBar1 = new javax.swing.JToolBar();
        btnNuevo = new javax.swing.JButton();
        btnAbrir = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNumLineas = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnArchivo = new javax.swing.JMenu();
        jmiAbrir = new javax.swing.JMenuItem();
        jmiNuevo = new javax.swing.JMenuItem();
        jmiGuardar = new javax.swing.JMenuItem();
        jmiGuardarComo = new javax.swing.JMenuItem();
        jmiSalir = new javax.swing.JMenuItem();
        mnEdicion = new javax.swing.JMenu();
        jmiCopiar = new javax.swing.JMenuItem();
        jmiCortar = new javax.swing.JMenuItem();
        jmiPegar = new javax.swing.JMenuItem();
        mnFormato = new javax.swing.JMenu();
        chbxAjusteLinea = new javax.swing.JCheckBoxMenuItem();
        mnVista = new javax.swing.JMenu();
        chbxNumLinea = new javax.swing.JCheckBoxMenuItem();
        mnAyuda = new javax.swing.JMenu();
        jmiAcerca = new javax.swing.JMenuItem();

        jpmiCopiar.setText("Copiar");
        jPopupMenu1.add(jpmiCopiar);

        jpmiCortar.setText("Cortar");
        jPopupMenu1.add(jpmiCortar);

        jpmiPegar.setText("Pegar");
        jPopupMenu1.add(jpmiPegar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JC Editor");

        areaTexto.setColumns(20);
        areaTexto.setRows(5);
        jScrollPane1.setViewportView(areaTexto);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jToolBar1.setRollover(true);

//        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.gif"))); // NOI18N
        btnNuevo.setFocusable(false);
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnNuevo);

//        btnAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abrir.png"))); // NOI18N
        btnAbrir.setFocusable(false);
        btnAbrir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAbrir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnAbrir);

//        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/SAVE.GIF"))); // NOI18N
        btnGuardar.setFocusable(false);
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnGuardar);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        txtNumLineas.setBackground(new java.awt.Color(192, 192, 192));
        txtNumLineas.setColumns(3);
        txtNumLineas.setEditable(false);
        txtNumLineas.setRows(1);
//        jScrollPane2.setViewportView(txtNumLineas);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.LINE_START);

        mnArchivo.setText("Archivo");

        jmiAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
//        jmiAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abrir.png"))); // NOI18N
        jmiAbrir.setText("Abrir");
        mnArchivo.add(jmiAbrir);

        jmiNuevo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
//        jmiNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        jmiNuevo.setText("Nuevo");
        mnArchivo.add(jmiNuevo);

        jmiGuardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
//        jmiGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/SAVE.GIF"))); // NOI18N
        jmiGuardar.setText("Guardar");
        mnArchivo.add(jmiGuardar);

//        jmiGuardarComo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/gwget.png"))); // NOI18N
        jmiGuardarComo.setText("Guardar como");
        mnArchivo.add(jmiGuardarComo);

        jmiSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
//        jmiSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir.png"))); // NOI18N
        jmiSalir.setText("Salir");
        mnArchivo.add(jmiSalir);

        jMenuBar1.add(mnArchivo);

        mnEdicion.setText("Edición");

        jmiCopiar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
//        jmiCopiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/copiar.gif"))); // NOI18N
        jmiCopiar.setText("Copiar");
        mnEdicion.add(jmiCopiar);

        jmiCortar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
//        jmiCortar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cortar.gif"))); // NOI18N
        jmiCortar.setText("Cortar");
        mnEdicion.add(jmiCortar);

        jmiPegar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
//        jmiPegar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pegar.gif"))); // NOI18N
        jmiPegar.setText("Pegar");
        mnEdicion.add(jmiPegar);

        jMenuBar1.add(mnEdicion);

        mnFormato.setText("Formato");

        chbxAjusteLinea.setText("Ajuste de Linea");
        chbxAjusteLinea.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chbxAjusteLineaStateChanged(evt);
            }
        });
        mnFormato.add(chbxAjusteLinea);

        jMenuBar1.add(mnFormato);

        mnVista.setText("Vista");

        chbxNumLinea.setSelected(true);
        chbxNumLinea.setText("Ver Nro de Linea");
        chbxNumLinea.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chbxNumLineaStateChanged(evt);
            }
        });
        mnVista.add(chbxNumLinea);

        jMenuBar1.add(mnVista);

        mnAyuda.setText("Ayuda");

        jmiAcerca.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.CTRL_MASK));
//        jmiAcerca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/wink.gif"))); // NOI18N
        jmiAcerca.setText("Acerca de ...");
        mnAyuda.add(jmiAcerca);

        jMenuBar1.add(mnAyuda);

        setJMenuBar(jMenuBar1);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-867)/2, (screenSize.height-558)/2, 867, 558);
    }// </editor-fold>//GEN-END:initComponents

    //Para poner ajuste de linea
    private void chbxAjusteLineaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chbxAjusteLineaStateChanged
        if (chbxAjusteLinea.isSelected()) {
            areaTexto.setLineWrap(true);
        } else {
            areaTexto.setLineWrap(false);
        }
    }//GEN-LAST:event_chbxAjusteLineaStateChanged

    //Para mostrar u ocultar numeros de linea
    private void chbxNumLineaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chbxNumLineaStateChanged
        if (chbxNumLinea.isSelected()) {
            txtNumLineas.setVisible(true);
        } else {
            txtNumLineas.setVisible(false);
        }
    }//GEN-LAST:event_chbxNumLineaStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(final String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                // the Ip and port to use locally
                try {
                    if (args.length != 4){
                        System.out.println("Usage:");
                        System.out.println("localIp localbindport bootIP bootPort");
                        System.out.println("example 192.168.56.101 9001 192.168.56.1 9001");
                        return;
                    }
                    InetAddress localIpAddr = InetAddress.getByName(args[0]);
                    int bindport = Integer.parseInt(args[1]);
                    InetSocketAddress localIP = new InetSocketAddress(localIpAddr, bindport);

                    // build the bootaddress from the command line args
                    InetAddress bootaddr = InetAddress.getByName(args[2]);
                    int bootport = Integer.parseInt(args[3]);
                    InetSocketAddress bootaddress = new InetSocketAddress(bootaddr, bootport);

                    // launch our node!
                    AdaptedOperationManeger dt = new AdaptedOperationManeger(localIP, bootaddress);
                    Principal p = new Principal();
                    p.setVisible(true);
                    p.adaptedOperationManeger = dt;

                }
                catch (UnknownHostException ex){
                    java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaTexto;
    private javax.swing.JButton btnAbrir;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JCheckBoxMenuItem chbxAjusteLinea;
    private javax.swing.JCheckBoxMenuItem chbxNumLinea;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem jmiAbrir;
    private javax.swing.JMenuItem jmiAcerca;
    private javax.swing.JMenuItem jmiCopiar;
    private javax.swing.JMenuItem jmiCortar;
    private javax.swing.JMenuItem jmiGuardar;
    private javax.swing.JMenuItem jmiGuardarComo;
    private javax.swing.JMenuItem jmiNuevo;
    private javax.swing.JMenuItem jmiPegar;
    private javax.swing.JMenuItem jmiSalir;
    private javax.swing.JMenuItem jpmiCopiar;
    private javax.swing.JMenuItem jpmiCortar;
    private javax.swing.JMenuItem jpmiPegar;
    private javax.swing.JMenu mnArchivo;
    private javax.swing.JMenu mnAyuda;
    private javax.swing.JMenu mnEdicion;
    private javax.swing.JMenu mnFormato;
    private javax.swing.JMenu mnVista;
    private javax.swing.JTextArea txtNumLineas;
    // End of variables declaration//GEN-END:variables
    Archivitos a;
    private int numLin = 1, numHoja = 1, guardado = 0, abierto = 0;
    private Vector<String> filas = new Vector<String>();

    //Aqui se ubican todas las acciones realizadas por cada uno de los botones o jmenuitems
    @Override
    public void actionPerformed(ActionEvent e) {
        //Aqui se especifica la accion a realizar para abrir un archivo
        if (e.getSource() == jmiAbrir || e.getSource() == btnAbrir) {
            try {
                a = new Archivitos();
                areaTexto.setText(a.abrirArchivo());
                guardaFilas();
                poneNumLineas();
                this.setTitle(a.getNombreArchivo());
                abierto++;
            } catch (IOException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } //Aqui se ubica el codigo para un nuevo documento y se ubica controles
        else if (e.getSource() == jmiNuevo || e.getSource() == btnNuevo) {
            if (areaTexto.getText().isEmpty()) {//En caso de que el area de texto esté vacia
                this.setTitle("Sin Titulo_" + numHoja);
                guardado = 0;
            } else {//Si existe texto en el area de texto
                String titulo = this.getTitle();
                String l = titulo.substring(0, titulo.length() - 1);
                if (l.equalsIgnoreCase("Sin Titulo_") || guardado > 0) {
                    int p = JOptionPane.showConfirmDialog(null, "¿Desea guardar los cambios?", "Guardar", JOptionPane.YES_NO_OPTION);
                    if (p == JOptionPane.YES_OPTION) {
                        if (l.equalsIgnoreCase("Sin Titulo_")) {
                            guarda();
                        } else {
                            guardaDos();
                        }
                    }
                    areaTexto.setText(null);
                    this.setTitle("Sin Titulo_" + numHoja);
                    guardado = 0;
                } else if (abierto > 0) {
                    numLin = 1;
                    areaTexto.setText(null);
                    txtNumLineas.setText(null);
                    txtNumLineas.append(numLin + "\n");
                    this.setTitle("Sin Titulo_" + numHoja);
                    guardado = 0;
                    abierto = 0;
                }
            }
            numHoja = numHoja + 1;
        } else if (e.getSource() == jmiGuardar || e.getSource() == btnGuardar) {
            String titulo = this.getTitle();
            String l = titulo.substring(0, titulo.length() - 1);
            if (l.equalsIgnoreCase("Sin Titulo_")) {
                guarda();
            } else {
                guardaDos();
            }
            guardado++;
        } else if (e.getSource() == jmiGuardarComo) {
            guarda();
            guardado++;
        } else if (e.getSource() == jmiSalir) {
            System.exit(0);
        } else if (e.getSource() == jmiCopiar || e.getSource() == jpmiCopiar) {
            areaTexto.copy();
            System.out.println("Si llega copiar");
        } else if (e.getSource() == jmiCortar || e.getSource() == jpmiCortar) {
            areaTexto.cut();
        } else if (e.getSource() == jmiPegar || e.getSource() == jpmiPegar) {
            areaTexto.paste();
            System.out.println("Si llega pegar");
        } else if (e.getSource() == jmiAcerca) {
            new Acerca(this, true).setVisible(true);
        }
    }

    //Metodo que permite guardar mostrando el cuadro de guardado
    private void guarda() {
        String s = areaTexto.getText();
        try {
            a = new Archivitos();
            a.crearArchivo(s);
            this.setTitle(a.getNombreArchivo());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error de E/S");
        }
    }

    //Este método es para guardar sin que se pida el nombre de archivo
    private void guardaDos() {
        String s = areaTexto.getText();
        try {
            a.crearArchivoUno(s, a.getNombreArchivo());
            this.setTitle(a.getNombreArchivo());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error de E/S");
        }
    }

    //Este metodo sirve principalmente para ir añadiendo numeros de linea.
    //Captura a su vez los eventos de teclado que interesean (letras, digitos o simbolos)
    public void presionaTecla(KeyEvent evt) {
        String c = Character.toString(evt.getKeyChar());
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            numLin++;
            txtNumLineas.append(numLin + "\n");
        	this.sendEventToManager("INSERT", "\n", areaTexto.getCaretPosition() );

        }else if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            guardaFilas();
            txtNumLineas.setText("");
            numLin = 1;

            if (!(areaTexto.getText().length() == areaTexto.getCaretPosition() && lastText.equals("") && areaTexto.getText().equals("") && evt.getKeyCode() == KeyEvent.VK_DELETE )){
                int pos = areaTexto.getCaretPosition()+1;
                if (evt.getKeyCode() == KeyEvent.VK_DELETE)
                    this.sendEventToManager("DELETE", c, pos );
            }
            lastDeleteCaretPosition = areaTexto.getCaretPosition();
            lastText = areaTexto.getText();
            for (String s : filas) {
                txtNumLineas.append(numLin + "\n");
                numLin++;
            }
            numLin--;
            if (areaTexto.getText().equals("")) {
                txtNumLineas.append(1 + "\n");
                numLin = 1;
            }
        }else if (Character.isLetterOrDigit(evt.getKeyCode()) || Character.isSpaceChar(evt.getKeyCode()) || isSymbol(evt.getKeyCode())
                 || evt.getKeyChar() == '¿' || evt.getKeyChar() == '?' || evt.getKeyChar() == '¡' || evt.getKeyChar() == 'ñ' || evt.getKeyChar() == '¨') {
            this.sendEventToManager("INSERT", c, areaTexto.getCaretPosition());
        }
    }
     private void presionaBorrado(KeyEvent evt){
         String c = Character.toString(evt.getKeyChar());
         if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            guardaFilas();
            txtNumLineas.setText("");
            numLin = 1;

            if (!(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE && lastDeleteCaretPosition == 0 && areaTexto.getCaretPosition() == 0)){
//                if (!(areaTexto.getText().length() == areaTexto.getCaretPosition() && lastText.equals("") && areaTexto.getText().equals("") && evt.getKeyCode() == KeyEvent.VK_DELETE )){
                    int pos = areaTexto.getCaretPosition()+1;
                    if (evt.getKeyCode() == KeyEvent.VK_DELETE)
                    this.sendEventToManager("DELETE", c, pos );
//                }
            }
            lastDeleteCaretPosition = areaTexto.getCaretPosition();
            lastText = areaTexto.getText();
            for (String s : filas) {
                txtNumLineas.append(numLin + "\n");
                numLin++;
            }
            numLin--;
            if (areaTexto.getText().equals("")) {
                txtNumLineas.append(1 + "\n");
                numLin = 1;
            }
        }
    }

    //Determina si se trata de un simbolo
    private boolean isSymbol(int code){
        if (code == KeyEvent.VK_SEMICOLON || code == KeyEvent.VK_COMMA || code == KeyEvent.VK_COLON
            ||  code == KeyEvent.VK_MINUS || code == KeyEvent.VK_PERIOD || code == KeyEvent.VK_SLASH
            ||  code == KeyEvent.VK_EQUALS || code == KeyEvent.VK_BACK_SLASH || code == KeyEvent.VK_OPEN_BRACKET
            ||  code == KeyEvent.VK_CLOSE_BRACKET || code == KeyEvent.VK_BRACELEFT || code == KeyEvent.VK_BRACERIGHT
            ||  code == KeyEvent.VK_ASTERISK || code == KeyEvent.VK_PLUS || code == KeyEvent.VK_LESS
            ||  code == KeyEvent.VK_GREATER || code == KeyEvent.VK_AT || code == KeyEvent.VK_CIRCUMFLEX
            ||  code == KeyEvent.VK_INVERTED_EXCLAMATION_MARK || code == KeyEvent.VK_DEAD_ACUTE || code == KeyEvent.VK_DEAD_TILDE
            )
            return true;
        return false;
    }

    //En este método, cada fila del editor se va guardando en un vector
    public void guardaFilas() {
        filas = new Vector<String>();
        StringTokenizer st = new StringTokenizer(areaTexto.getText(), "\n");
        while (st.hasMoreTokens()) {
            filas.add(st.nextToken());
        }
    }

    //genera los numeros de linea del editor, se utiliza principalmente para cuando se abre un archivo
    public void poneNumLineas() {
        for (String s : filas) {
            numLin++;
            txtNumLineas.append(numLin + "\n");
        }
    }

    //Envia el evento a la capa inferior de la arquitectura
    public void sendEventToManager(String type,String character, int position){
        lastLocalPosition = areaTexto.getCaretPosition();
        this.adaptedOperationManeger.captureEventFromApp(type, character, position);
    }

    //Este es el hilo que se encarga de buscar las operaciones remotas en la capa inferior de la arquitectura
    public void start(){
        Runnable remoteHandler = new Runnable() {
            @Override
            public void run() {
                try {
                    while(true){
                        if (adaptedOperationManeger != null){
                            Operation op = adaptedOperationManeger.sendOperationToApp();
                            if (op != null){
                                //System.out.println(op.toString());
                                executeRemoteOperation(op);
                                }
                            else{
                                sleep(5);
                            }
                        }
                        else{
                            sleep(100);
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread threadR = new Thread(remoteHandler);
        threadR.start();
    }

    //Ejecuta las operaciones remotas que haya encontrado
    public void executeRemoteOperation(Operation operation){
//        areaTexto.setCaretPosition(lastLocalPosition);
        if (operation.isInsert()){
            areaTexto.insert(operation.getObj().getObj(), operation.getPosition() );
        }
        else{
            //Borra reemplazando con nada.
            guardaFilas();
            if (!areaTexto.equals("")){
                try {
                    areaTexto.getDocument().remove(operation.getPosition()-1, 1); //.replaceRange("", operation.getPosition()-1, operation.getPosition() );
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
