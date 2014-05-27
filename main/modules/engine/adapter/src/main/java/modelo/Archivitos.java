package modelo;

import vista.Principal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class Archivitos {

    
    public static String getPoneNombre() {
        return poneNombre;
    }

    public static void setPoneNombre(String aPoneNombre) {
        poneNombre = aPoneNombre;
    }
    private String nombreArchivo;
    private static String poneNombre;
    // crea archivo
    public void crearArchivo(String datos) throws IOException {
        try {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FiltroArchivo());
            fc.setDialogTitle("Guardar archivos de texto");
            fc.showSaveDialog(new Principal());
            fc.getFileFilter();
            String path = fc.getSelectedFile().getPath();
            File guardar = new File(path + ".txt");
            setNombreArchivo(path+".txt");
            if (guardar != null) {
                FileWriter guardaTexto = new FileWriter(guardar);
                guardaTexto.write(datos);
                guardaTexto.close();
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
//actualiza el contenido en un archivo
     public void crearArchivoUno(String datos,String nombre) throws IOException {
        try {            
            File guardar = new File(nombre);
            setNombreArchivo(nombre);
            if (guardar != null) {
                FileWriter guardaTexto = new FileWriter(guardar);
                guardaTexto.write(datos);
                guardaTexto.close();
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
     //metodo para abrir archivos
    public String abrirArchivo() throws IOException {
        String s = "", cad = "";
        try {
            JFileChooser jf = new JFileChooser();
            jf.setFileFilter(new FiltroArchivo());
            jf.setDialogTitle("Abrir Archivos de Texto");
            jf.showOpenDialog(new Principal());
            File abrir = jf.getSelectedFile();
            setNombreArchivo(abrir.getName());
            if (abrir.getAbsolutePath().endsWith(".txt")) {
                if (abrir != null) {
                    FileReader archivo = new FileReader(abrir);
                    BufferedReader leer = new BufferedReader(archivo);
                    while ((s = leer.readLine()) != null) {
                        cad += s + "\n";
                    }
                    leer.close();
                }
            }else {JOptionPane.showMessageDialog(null, "Solo se permiten archivos con extension *.txt");}
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
        return cad;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
}
