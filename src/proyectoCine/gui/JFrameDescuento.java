package proyectoCine.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import proyectoCine.domain.Descuento;

public class JFrameDescuento extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	private List<Descuento> descuentos;
	boolean pulsado = false;
	
	private String codigoGenerado = null;
	private int porcentajeGenerado = 0;

	
	
	
	public JFrameDescuento(ArrayList<Descuento> descuentos) {
		this.descuentos = descuentos;
		this.setSize(350,250);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(3,1));
		;
		JLabel labelInfo = new JLabel("PRESIONA EL BOTÓN PARA GENERAR DESCUENTO",SwingConstants.CENTER);
		labelInfo.setOpaque(true);
		labelInfo.setBackground(new Color(173, 216, 230));
		this.add(labelInfo);
		
		JPanel panelBoton = new JPanel(new FlowLayout());
		panelBoton.setOpaque(true);
		panelBoton.setBackground(new Color(173, 216, 230));
		JButton boton = new JButton("¡PULSE AQUI!");
		HiloFondoDescuento hiloColor = new HiloFondoDescuento(
				boton,
			    new Color(135, 206, 250),
			    new Color(30, 144, 255),
			    800
			);
		
		hiloColor.start();
		panelBoton.add(boton);
		this.add(panelBoton);
		
		JPanel panelDescuento = new JPanel(new GridLayout(2,1));
		
		JLabel labelInfo2 = new JLabel("Su descuento y código de descuento son : ",SwingConstants.CENTER);
		labelInfo2.setOpaque(true);
		labelInfo2.setBackground(new Color(173, 216, 230));
		panelDescuento.add(labelInfo2);
		
		JPanel panelNumero = new JPanel(new FlowLayout());
		panelNumero.setOpaque(true);
		panelNumero.setBackground(new Color(173, 216, 230));
		JTextField labelDescuento = new JTextField("Unknown");
		labelDescuento.setEditable(false);
		labelDescuento.setHorizontalAlignment(JTextField.CENTER);

		labelDescuento.setPreferredSize(new Dimension(210, 28));
		labelDescuento.setBorder(BorderFactory.createLineBorder(Color.black, 4));
		labelDescuento.setOpaque(true);
		HiloFondoDescuento hiloLabel = new HiloFondoDescuento(labelDescuento, new Color(255, 223, 128),new Color(204, 158, 45),1000);
		hiloLabel.start();
		labelDescuento.setFont(new Font("Serif",Font.BOLD,15));
		labelDescuento.setBackground(Color.yellow);
		panelNumero.add(labelDescuento);
		
		 boton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	
	            	if(pulsado == false) {
	            		pulsado = true;
	            		Random rand = new Random();
		            	String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
						 StringBuilder codigo = new StringBuilder();
		                    for (int j = 0; j < 6; j++) {
		                        codigo.append(caracteres.charAt(rand.nextInt(caracteres.length())));
		                    }
		                    
		                String cod = codigo.toString();
		                
		                Descuento descuento = descuentos.get(rand.nextInt(descuentos.size()));
		                String valorDescuento = descuento.getValor();
		                
		                codigoGenerado=cod;
		                if(!valorDescuento.isEmpty()) {
		                	porcentajeGenerado = Integer.parseInt(valorDescuento);
		                }else {
		                	porcentajeGenerado=0;
		                }
		                
		                JFramePrincipal framePrincipal = (JFramePrincipal) SwingUtilities.getWindowAncestor(JFrameDescuento.this);
		                if (framePrincipal != null) {
		                    framePrincipal.setCodigoDescuento(codigoGenerado, porcentajeGenerado);
		                    System.out.println("Código actualizado en JFramePrincipal: " + codigoGenerado);
		                }
		            	HiloDescuento hiloDescuento = new HiloDescuento(labelDescuento);
		        		hiloDescuento.start();
		        		

		        		
		        		new Thread(() -> {
		        	        try {
		        	            Thread.sleep(5000);
		        	            hiloDescuento.detener();
		        	            
		        	            if(valorDescuento != null && !valorDescuento.isEmpty()) {
		        	            	SwingUtilities.invokeLater(() -> {
			        	                labelDescuento.setText(valorDescuento +"%  : " + cod);
			        	                
			        	            });
		        	            } else {
		        	            	SwingUtilities.invokeLater(() -> {
			        	                labelDescuento.setText("Pruebe suerte la próxima vez");
			        	            });
		        	            }
		        	            
		        	           
		        	        } catch (InterruptedException ex) {
		        	            ex.printStackTrace();
		        	        }
		        	    }).start();
		        		
		        		
		        		
		            } else {
		            	JOptionPane.showMessageDialog(
	            	            null,                          
	            	            "Ya ha pulsado el botón anteriormente",
	            	            "Error",                       
	            	            JOptionPane.ERROR_MESSAGE      
	            	        );
		            }
	            	}
	            	
	            	
	        });
		
		panelDescuento.add(panelNumero);
		this.add(panelDescuento);
		
		
		
		
	}
	
	public String getCodigoGenerado() {
	    return codigoGenerado;
	}
	
	public int getPorcentajeGenerado() {
		return porcentajeGenerado;
	}
	
	 public static void main(String[] args) {
		 ArrayList<Descuento> listaDescuentos = new ArrayList<>(Arrays.asList(Descuento.values()));
		 JFrameDescuento ventana = new JFrameDescuento(listaDescuentos);
		 ventana.setVisible(true);
	 }
	 
	 private class HiloFondoDescuento extends Thread {

	        private JButton boton = null;
	        private JComponent componente = null;
	        private Color color1;
	        private Color color2;
	        private int duracion; // tiempo en milisegundos para ir de color1 a color2

	        public HiloFondoDescuento(JButton boton, Color color1, Color color2, int duracion) {
	            this.boton = boton;
	            this.color1 = color1;
	            this.color2 = color2;
	            this.duracion = duracion;
	        }
	        
	        public HiloFondoDescuento(JComponent componente, Color color1, Color color2, int duracion) {
	        	this.componente=componente;
	            this.color1 = color1;
	            this.color2 = color2;
	            this.duracion = duracion;
	        }
	        
	        
	        //ChatGPT
	        @Override
	        public void run() {
	            int pasos = 50;
	            int delay = duracion / pasos;

	            float ratio = 0f;
	            boolean subiendo = true; // indica si sube hacia color2 o baja hacia color1
	            
	            
	            while (true) {
	                // Calcular color interpolado
	                int r = (int) (color1.getRed()   + ratio * (color2.getRed()   - color1.getRed()));
	                int g = (int) (color1.getGreen() + ratio * (color2.getGreen() - color1.getGreen()));
	                int b = (int) (color1.getBlue()  + ratio * (color2.getBlue()  - color1.getBlue()));
	                Color nuevoColor = new Color(r, g, b);
	                
	                if(boton!=null) {
	                	SwingUtilities.invokeLater(() -> boton.setBackground(nuevoColor));
	                } else {
	                	SwingUtilities.invokeLater(() -> componente.setBackground(nuevoColor));
	                }
	                

	                // Actualizar ratio
	                if (subiendo) {
	                    ratio += 1.0 / pasos;
	                    if (ratio >= 1f) {
	                        ratio = 1f;
	                        subiendo = false;
	                    }
	                } else {
	                    ratio -= 1.0 / pasos;
	                    if (ratio <= 0f) {
	                        ratio = 0f;
	                        subiendo = true;
	                    }
	                }
	                
	                //ChatGPT

	                try {
	                    Thread.sleep(delay);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
	 
	 private class HiloDescuento extends Thread{
		 private JTextField campo;
		 private boolean running = true;
		 
		 public HiloDescuento(JTextField campo) {
			 this.campo = campo;
		 }
		 
		 public void detener() {
		        running = false;
		    }
		 
		 @Override 
		 public void run() {
			 Random rand = new Random();
			 while(running) {
				 for(int i = 1;i<5;i++) {
					 if (!running) break;
					 String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
					 StringBuilder codigo = new StringBuilder();
	                    for (int j = 0; j < 6; j++) {
	                        codigo.append(caracteres.charAt(rand.nextInt(caracteres.length())));
	                    }
	                    
	                    String cod = codigo.toString();
	                    campo.setText(cod);
	                    try {
				            Thread.sleep(50); 
				        } catch (InterruptedException e) {
				            e.printStackTrace(); 
				        }  
	                 
				 }
				 
				 
				 
				 
			 }
		 }
	 }

}
