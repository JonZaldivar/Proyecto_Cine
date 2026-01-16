package proyectoCine.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import proyectoCine.domain.Horario;
import proyectoCine.domain.Pelicula;
import proyectoCine.domain.Sala;
import proyectoCine.persistence.CineGestorBD;

public class JFrameHorarios extends JFrame {

    private static final long serialVersionUID = 1L;
    private JFramePelicula ventanaPelicula;
    
    public JFrameHorarios(
            Pelicula pelicula,
            List<Horario> horariosDisponibles,
            List<Pelicula> listaPeliculas,
            String codigoDescuento,
            int porcentajeDescuento,
            CineGestorBD gestor,
            JFramePelicula ventanaAnterior
    ) {
        setTitle("Horarios disponibles");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(217, 234, 246));
        setLayout(new BorderLayout(10, 10));

        // Título
        JLabel lblTitulo = new JLabel("Selecciona un horario", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelHorarios = new JPanel(new GridLayout(0, 2, 15, 15));
        panelHorarios.setBackground(new Color(217, 234, 246));
        panelHorarios.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        for (Horario horario : horariosDisponibles) {
            JButton btnHorario = new JButton(horario.toString());
            btnHorario.setFont(new Font("Arial", Font.BOLD, 16));
            btnHorario.setBackground(new Color(33, 150, 243));
            btnHorario.setForeground(Color.WHITE);
            btnHorario.setFocusPainted(false);
            btnHorario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(21, 101, 192), 2),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
            ));

            // Hover (Claude)
            btnHorario.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btnHorario.setBackground(new Color(25, 118, 210));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btnHorario.setBackground(new Color(33, 150, 243));
                }
            });

            // Abrir sala
            btnHorario.addActionListener(e -> {
                Sala salaDisponible = new Sala(101, 8, 10);
                JFrameSala ventanaSala = new JFrameSala(
                        salaDisponible,
                        pelicula,
                        horario,
                        listaPeliculas,
                        codigoDescuento,
                        porcentajeDescuento,
                        gestor
                );
                ventanaSala.setVisible(true);
                ventanaAnterior.dispose();
                dispose();
            });

            panelHorarios.add(btnHorario);
        }

        add(panelHorarios, BorderLayout.CENTER);

        // Botón volver
        JButton btnVolver = new JButton("← Volver");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 12));
        btnVolver.setBackground(new Color(33, 150, 243));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(21, 101, 192), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        // Hover (Claude)
        btnVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVolver.setBackground(new Color(25, 118, 210));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVolver.setBackground(new Color(33, 150, 243));
            }
        });
        btnVolver.addActionListener(e -> {
            ventanaPelicula.setVisible(true);
            dispose();
        });

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInferior.setBackground(new Color(217, 234, 246));
        panelInferior.add(btnVolver);

        add(panelInferior, BorderLayout.SOUTH);
    }
}