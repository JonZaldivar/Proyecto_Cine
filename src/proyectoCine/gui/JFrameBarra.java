package proyectoCine.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class JFrameBarra extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private JLabel progressLabel;
    private Image imagen;
    private JLabel labelEstado;

    public JFrameBarra() {
    	setTitle("Barra de Progreso");
    	setSize(400, 80);
    	setLocationRelativeTo(null);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLayout(new GridLayout(1, 1));

    	
    	JLabel progressLabel = new JLabel("");
    	this.progressLabel = progressLabel;
    	Image imagen = null;
    	
    	java.net.URL url = getClass().getResource("/correr.png");
    	if (url == null) url = getClass().getResource("/correr.png");

    	if (url != null) {
    	    ImageIcon icon = new ImageIcon(url);
    	    Image img = icon.getImage();
    	    
    	    
    	    
    	    int ancho = 50; 
    	    int alto  = 30;   
    	    imagen = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    	    this.imagen = imagen;
    	}
    	
    	progressLabel.setIcon(new ImageIcon(imagen));
    	
    	
    	

    	progressLabel.setOpaque(true); 
    	progressLabel.setBackground(new Color(144, 238, 144));
    	progressLabel.setForeground(Color.BLACK); 
    	progressLabel.setPreferredSize(new Dimension(350, 30));
    	progressLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 
 
    	JPanel panelCentral = new JPanel();
    	panelCentral.setBackground(new Color(173, 216, 230));
    	panelCentral.setOpaque(true);
    	panelCentral.add(progressLabel);

    	add(panelCentral);
    	
    	

    }

    
    public void iniciarAnimacion(Runnable onFinish) {
        HiloBarra hilo = new HiloBarra(progressLabel, imagen, onFinish);
        hilo.start();
    }


    public JLabel getLabelEstado() {
        return labelEstado;
    }

    public static void main(String[] args) {
        JFrameBarra ventana = new JFrameBarra();
        ventana.setVisible(true);
    }
    
    private class HiloBarra extends Thread {
        private int progreso = 0;
        private JLabel label;
        private Image image;
        private Runnable onFinish;

        public HiloBarra(JLabel label, Image image,Runnable onFinish) {
            this.label = label;
            this.image = image;
            this.onFinish = onFinish;
        }

        @Override
        public void run() {
            int labelWidth = label.getPreferredSize().width;
            int imgWidth = image.getWidth(null);

            while (progreso <= 100) {
                final int xOffset = (int)((labelWidth - imgWidth) * (progreso / 100.0));

                SwingUtilities.invokeLater(() -> {
                    // Solo mueve la imagen, no la escalamos de nuevo
                    label.setBorder(BorderFactory.createEmptyBorder(0, xOffset, 0, 0));
                });

                progreso++;

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
            
            if(onFinish!=null) {
            	SwingUtilities.invokeLater(onFinish);
            }
        }
        
        
    }


}
