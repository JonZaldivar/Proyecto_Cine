package proyectoCine.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import proyectoCine.domain.Pelicula;
import proyectoCine.domain.Horario;
import proyectoCine.domain.Sala;

public class JFrameReserva extends JFrame {

    private Pelicula pelicula;
    private Horario horario;
    private Sala sala;
    private String asientosSeleccionados;
    private double precioTotal;

    // Constructor correcto que se llama desde JFrameSala
    public JFrameReserva(Pelicula pelicula, Horario horario, Sala sala, String asientosSeleccionados, double precioTotal) {
        this.pelicula = pelicula;
        this.horario = horario;
        this.sala = sala;
        this.asientosSeleccionados = asientosSeleccionados;
        this.precioTotal = precioTotal;

        inicializarVentana();
        crearComponentes();
        setVisible(true);
    }

    private void inicializarVentana() {
        setTitle("Resumen de Reserva - Cine");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 245));
    }
    private void crearComponentes() {
        crearPanelTitulo();
        add(crearPanelResumen(), BorderLayout.CENTER);
        crearPanelBotones();
    }
    private void crearPanelTitulo() {
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(178, 34, 34));
        panelTitulo.setPreferredSize(new Dimension(600, 80));
        JLabel lblTitulo = new JLabel("🎬 RESUMEN DE TU RESERVA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);
    }
    
    
    
    
}
   