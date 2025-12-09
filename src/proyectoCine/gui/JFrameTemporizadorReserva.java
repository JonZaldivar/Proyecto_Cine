package proyectoCine.gui;

import javax.swing.*;
import java.awt.*;

public class JFrameTemporizadorReserva extends Thread {
    private int segundosRestantes = 600; // 10 minutos
    private boolean activo = true;
    private JLabel labelTiempo;

    public JFrameTemporizadorReserva(JLabel labelTiempo) {
        this.labelTiempo = labelTiempo;
        actualizarLabel();
    }

    public int getSegundosRestantes() {
        return segundosRestantes;
    }

    public void detener() {
        activo = false;
    }

    // Renombrado para coherencia
    public void setLabel(JLabel nuevoLabel) {
        this.labelTiempo = nuevoLabel;
        actualizarLabel();
    }

    @Override
    public void run() {
        while (activo && segundosRestantes > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            segundosRestantes--;
            SwingUtilities.invokeLater(() -> actualizarLabel());
        }

        if (segundosRestantes == 0) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null,
                        "El tiempo expiró. La reserva ha sido cancelada.",
                        "Tiempo agotado",
                        JOptionPane.WARNING_MESSAGE);
                System.exit(0); 
            });
        }
    }

    private void actualizarLabel() {
        if (labelTiempo != null) {
            int min = segundosRestantes / 60;
            int seg = segundosRestantes % 60;
            labelTiempo.setText(String.format("Tiempo restante: %02d:%02d", min, seg));
        }
    }
}
