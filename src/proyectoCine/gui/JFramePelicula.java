package proyectoCine.gui;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import proyectoCine.domain.Pelicula;

public class JFramePelicula extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private List<Pelicula> peliculas;
	
	private JTable tablaPeluculas;
	private DefaultTableModel modeloDatosPeliculas;
	private JTable tablaActores;
	private DefaultTableModel modeloDatosActores;
	private JScrollPane scrollPaneActores;
	private JTextField txtFiltro;
	
	//FILTRAR COMICS
		private void filtrarPeliculas() {
			//Se vacían las dos tablas
			this.modeloDatosPeliculas.setRowCount(0);
			this.modeloDatosActores.setRowCount(0);
			
			//Se añaden a la tabla sólo los comics que contengan el texto del filtro
			this.peliculas.forEach(c -> {
				if (c.getTitulo().contains(this.txtFiltro.getText())) {
					this.modeloDatosPeliculas.addRow(
						new Object[] {c.getId(), c.getDirector(), c.getTitulo(), c.getActores().size()} );
				}
			});
		}
	
}
