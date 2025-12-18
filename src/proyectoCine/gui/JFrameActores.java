package proyectoCine.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import proyectoCine.domain.Actor;
import proyectoCine.domain.Pelicula;

public class JFrameActores extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private JLabel lblFoto;           // Label para la foto del actor
    private JLabel lblNombre;         // Label para el nombre del actor
    private JLabel lblInfo;           // Label para info adicional (fecha, país)
    private Thread hiloActores;       // Hilo que controla el carrusel
    private Pelicula pelicula;
    private List<Pelicula> listaPeliculas;
    
    public JFrameActores(Pelicula pelicula, List<Pelicula> listaPeliculas) {
        this.pelicula = pelicula;
        this.listaPeliculas = listaPeliculas;
        
        // Configuración de la ventana
        setTitle("Actores de " + pelicula.getTitulo());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // No permitir cerrar manual
        setSize(500, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);
        
        // Panel central para la foto del actor
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(Color.WHITE);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        lblFoto = new JLabel();
        lblFoto.setHorizontalAlignment(JLabel.CENTER);
        panelCentro.add(lblFoto, BorderLayout.CENTER);
        
        add(panelCentro, BorderLayout.CENTER);
        
        // Panel inferior para información del actor
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(Color.WHITE);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        lblNombre = new JLabel();
        lblNombre.setFont(new Font("Arial", Font.BOLD, 20));
        lblNombre.setHorizontalAlignment(JLabel.CENTER);
        panelInferior.add(lblNombre, BorderLayout.NORTH);
        
        lblInfo = new JLabel();
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblInfo.setHorizontalAlignment(JLabel.CENTER);
        panelInferior.add(lblInfo, BorderLayout.CENTER);
        
        add(panelInferior, BorderLayout.SOUTH);
        
        // Iniciar el carrusel de actores
        iniciarCarruselActores();
    }
    
    // Método para limpiar el contenido
    private void resetLabels() {
        SwingUtilities.invokeLater(() -> {
            lblFoto.setIcon(null);
            lblNombre.setText("");
            lblInfo.setText("");
        });
    }
    
    // Método para normalizar el nombre del actor para búsqueda de imágenes
    private String normalizarNombre(String nombre) {
        return nombre.toLowerCase()                    // Convertir a minúsculas
                     .replace("á", "a")               // Eliminar tildes
                     .replace("é", "e")
                     .replace("í", "i")
                     .replace("ó", "o")
                     .replace("ú", "u")
                     .replace("ñ", "n")
                     .replace(" ", "_");              // Espacios por guiones bajos
    }
    
    // Método para actualizar la información del actor
    private void actualizarActor(Actor actor) {
        SwingUtilities.invokeLater(() -> {
            // Actualizar nombre
            lblNombre.setText(actor.getNombre());
            
            // Actualizar info (fecha y país)
            String info = String.format("<html><center>Fecha de nacimiento: %s<br>País: %s</center></html>", 
                actor.getFechaNacimientoStr(), 
                actor.getPais().toString());
            lblInfo.setText(info);
            
            // Normalizar el nombre para buscar la imagen
            String nombreNormalizado = normalizarNombre(actor.getNombre());
            
            // Cargar foto del actor
            java.net.URL urlImagen = getClass().getResource("/" + nombreNormalizado + ".jpg");
            if (urlImagen == null) {
                urlImagen = getClass().getResource("/" + nombreNormalizado + ".png");
            }
            
            ImageIcon icon;
            if (urlImagen != null) {
                icon = new ImageIcon(urlImagen);
            } else {
                // Imagen por defecto si no se encuentra la foto del actor
                urlImagen = getClass().getResource("/actor_default.png");
                if (urlImagen != null) {
                    icon = new ImageIcon(urlImagen);
                } else {
                    icon = new ImageIcon(); // Icono vacío
                }
            }
            
            // Escalar la imagen
            Image img = icon.getImage().getScaledInstance(300, 350, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);
            lblFoto.setIcon(scaledIcon);
        });
    }
    
    // Método que inicia el hilo del carrusel de actores
    private void iniciarCarruselActores() {
        hiloActores = new Thread(() -> {
            resetLabels();
            
            List<Actor> actores = pelicula.getActores();
            int numActores = actores.size();
            
            // Recorrer cada actor
            for (int i = 0; i < numActores; i++) {
                Actor actorActual = actores.get(i);
                
                // Mostrar el actor actual
                actualizarActor(actorActual);
                
                try {
                    // Esperar 3 segundos antes de pasar al siguiente actor
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            
            // Una vez mostrados todos los actores, cerrar ventana y volver
            SwingUtilities.invokeLater(() -> {
                this.dispose();
                JFramePelicula ventanaPelicula = new JFramePelicula(pelicula, listaPeliculas);
                ventanaPelicula.setVisible(true);
            });
        });
        
        hiloActores.start();
    }
}