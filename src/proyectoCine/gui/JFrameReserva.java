package proyectoCine.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import proyectoCine.domain.Horario;
import proyectoCine.domain.Pelicula;
import proyectoCine.domain.Sala;

public class JFrameReserva extends JFrame {

    private Pelicula pelicula;
    private Horario horario;
    private Sala sala;
    private String asientos;
    private double precioTotal;

    // para que el usuario rellene con sus daos
    private JTextField txtNombre;
    private JTextField txtCorreo;

    public JFrameReserva(Pelicula pelicula, Horario horario, Sala sala, String asientos, double precioTotal) {
        this.pelicula = pelicula;
        this.horario = horario;
        this.sala = sala;
        this.asientos = asientos;
        this.precioTotal = precioTotal;

        ventana();
        this.setVisible(true);
    }

    //crea la ventana
    private void ventana() {

    	
        Color azulFondo = new Color(217,234,246);
        Color blanco = Color.WHITE;
        this.setTitle("Confirmación de reserva");
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(azulFondo);

        // panel superior
        JPanel panelCabecera = new JPanel();
        panelCabecera.setBackground(azulFondo);

        java.net.URL logoUrl = getClass().getResource("/DeustoCine.png");
        ImageIcon logo = new ImageIcon(logoUrl);
        panelCabecera.add(new JLabel(logo));

        this.add(panelCabecera, BorderLayout.NORTH);

        // panel central para el resumen
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(azulFondo);
        panelCentral.setBorder(new EmptyBorder(10,10,10,10));

        
    }
}
