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
    //Panel del titulo
    private void crearPanelTitulo() {
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(178, 34, 34));
        panelTitulo.setPreferredSize(new Dimension(600, 80));
        JLabel lblTitulo = new JLabel("ESUMEN DE TU RESERVA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);
    }
    
    //Panel grande del resumen de reserva
    private JPanel crearPanelResumen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 245));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(crearSeccion("PELICULA", "Título: " + pelicula.getTitulo(), "Duración: " + pelicula.getDuracion() + " min"));
        panel.add(Box.createVerticalStrut(15));
        panel.add(crearSeccion("SESIÓN", "Horario: " + horario.toString()));
        panel.add(Box.createVerticalStrut(15));
        panel.add(crearSeccion("ASIENTOS", "Asientos seleccionados: " + asientosSeleccionados));
        panel.add(Box.createVerticalStrut(15));
        panel.add(crearPrecio());
        return panel;
    }
//Crea un recuadro
    private JPanel crearSeccion(String titulo, String... textos) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(15, 15, 15, 15)));
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(178, 34, 34));
        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(10));
        for (String texto : textos) {
            JLabel lbl = new JLabel(texto);
            lbl.setFont(new Font("Arial", Font.PLAIN, 14));
            panel.add(lbl);
        }
        return panel;
    }

    
}
   