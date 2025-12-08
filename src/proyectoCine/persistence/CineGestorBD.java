package proyectoCine.persistence;

import java.util.Properties;
import java.util.logging.Logger;


public class CineGestorBD {
	
	private final String PROPERTIES_FILE = "resources/config/app.properties";
	private final String CSV_ACTORES = "resources/actores.csv";
	private final String CSV_PELICULAS = "resources/peliculas.csv";
	
	private Properties properties;
	private String driverName;
	private String databaseFile;
	private String connectionString;
	
	private static Logger logger = Logger.getLogger(CineGestorBD.class.getName());

}
