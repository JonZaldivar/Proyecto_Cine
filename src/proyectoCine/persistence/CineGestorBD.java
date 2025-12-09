package proyectoCine.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import proyectoCine.domain.Actor;
import proyectoCine.domain.Actor.Pais;
import proyectoCine.domain.Pelicula;
import proyectoCine.domain.Pelicula.Clasificacion;
import proyectoCine.domain.Pelicula.Genero;

public class CineGestorBD {

    // Logger
    private static final Logger LOGGER = Logger.getLogger(CineGestorBD.class.getName());

    // Rutas de los CSV
    private final String CSV_PELICULAS = "resources/peliculas.csv";
    private final String CSV_ACTORES = "resources/actores.csv";
    private final String CSV_PELICULA_ACTORES = "resources/pelicula_actor.csv";

    // Configuración SQLite
    private final String DRIVER = "org.sqlite.JDBC";
    private final String DATABASE_URL = "jdbc:sqlite:resources/cine.db";

    public CineGestorBD() {
        try {
            Class.forName(DRIVER);
            LOGGER.info("Driver cargado correctamente.");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al inicializar la base de datos", e);
        }
    }

    // Crear tablas si no existen
    public void crearTablas() {
        String sqlPeliculas = "CREATE TABLE IF NOT EXISTS Pelicula (" +
                "id INTEGER PRIMARY KEY," +
                "titulo VARCHAR NOT NULL," +
                "director VARCHAR," +
                "duracion INTEGER," +
                "genero VARCHAR," +
                "clasificacion VARCHAR," +
                "descripcion VARCHAR," +
                "puntuacion REAL" +
                ");";

        String sqlActores = "CREATE TABLE IF NOT EXISTS Actor (" +
                "id INTEGER PRIMARY KEY," +
                "nombre VARCHAR NOT NULL," +
                "fecha_nacimiento VARCHAR," +
                "pais VARCHAR" +
                ");";
        
        String sqlPeliculaActores = "CREATE TABLE IF NOT EXISTS PeliculaActor (" +
                "id_pelicula INTEGER NOT NULL," +
                "id_actor INTEGER NOT NULL," +
                "PRIMARY KEY(id_actor, id_pelicula)" +
                ");";


        try (Connection con = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = con.createStatement()) {

            stmt.execute(sqlPeliculas);
            stmt.execute(sqlActores);
            stmt.execute(sqlPeliculaActores);

            LOGGER.info("Tablas creadas correctamente.");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al crear las tablas", e);
        }
    }

    // Cargar CSV de películas
    public void cargarPeliculas() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PELICULAS));
             Connection con = DriverManager.getConnection(DATABASE_URL)) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split(";");

                if (p.length != 8) continue;

                PreparedStatement ps = con.prepareStatement(
                        "INSERT OR IGNORE INTO Pelicula " +
                        "(id,titulo,director,duracion,genero,clasificacion,descripcion,puntuacion) " +
                        "VALUES (?,?,?,?,?,?,?,?)"
                );

                ps.setInt(1, Integer.parseInt(p[0]));
                ps.setString(2, p[1]); // titulo
                ps.setString(3, p[2]); // director
                ps.setInt(4, Integer.parseInt(p[3])); // duracion
                ps.setString(5, p[4]); // genero
                ps.setString(6, p[5]); // clasificacion
                ps.setString(7, p[6]); // descripcion
                ps.setDouble(8, Double.parseDouble(p[7])); // puntuacion

                ps.executeUpdate();
            }

            LOGGER.info("Películas cargadas.");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cargar las películas", e);
        }
    }
    
    public void cargarPeliculaActores() {
    	try(BufferedReader br = new BufferedReader(new FileReader(CSV_PELICULA_ACTORES));
    		Connection con = DriverManager.getConnection(DATABASE_URL)) {
    		
    		String linea;
    		while((linea = br.readLine()) != null) {
    			String[] a = linea.split(";");
    			
    			if(a.length!=2) continue;
    			
    			String sql = "INSERT OR IGNORE INTO PeliculaActor (id_pelicula,id_actor) VALUES(?,?);";
    			PreparedStatement ps = con.prepareStatement(sql);
    			
    			ps.setInt(1, Integer.parseInt(a[0]));
    			ps.setInt(2, Integer.parseInt(a[1]));
    			
    			ps.executeUpdate();
    				
    			
    		}
    		
    		LOGGER.info("Peliculas y actores respectivos cargados.");
    			
    	} catch(Exception e) {
    		LOGGER.log(Level.SEVERE, "Error al cargar los datos de peliculas y sus respectivos actores", e);
    	}
    }
   
    

    // Cargar CSV de actores
    public void cargarActores() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_ACTORES));
             Connection con = DriverManager.getConnection(DATABASE_URL)) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] a = linea.split(";");

                if (a.length != 4) continue;

                PreparedStatement ps = con.prepareStatement(
                        "INSERT OR IGNORE INTO Actor (id,nombre,fecha_nacimiento,pais) VALUES (?,?,?,?)"
                );

                ps.setInt(1, Integer.parseInt(a[0]));
                ps.setString(2, a[1]); // nombre
                ps.setString(3, a[2]); // fecha_nacimiento
                ps.setString(4, a[3]); // pais

                ps.executeUpdate();
            }

            LOGGER.info("Actores cargados.");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cargar los actores", e);
        }
    }

    public void insertarPeliculas(Pelicula... peliculas) {
        // Aquí puedes implementar el método usando LOGGER también
        LOGGER.info("insertarPeliculas aún no implementado.");
    }

    public void borrarDatos() {
        try (Connection con = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate("DELETE FROM Pelicula");
            stmt.executeUpdate("DELETE FROM Actor");
            stmt.executeUpdate("DELETE FROM PeliculaActor");

            LOGGER.info("Todos los datos han sido borrados.");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al borrar los datos", e);
        }
    }
    

    public void borrarBaseDatos() {
        String sql1 = "DROP TABLE IF EXISTS Pelicula;";
        String sql2 = "DROP TABLE IF EXISTS Actor;";
        String sql3 = "DROP TABLE IF EXISTS PeliculaActor";

        // Primero borramos las tablas
        try (Connection con = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = con.createStatement()) {

            stmt.execute(sql1);
            stmt.execute(sql2);
            stmt.execute(sql3);

            LOGGER.info("Tablas borradas correctamente.");

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al borrar las tablas: " + e.getMessage(), e);
        }

        // Luego eliminamos físicamente el archivo de la base de datos
        try {
            Files.deleteIfExists(Paths.get("resources/cine.db"));
            LOGGER.info("Archivo de base de datos eliminado correctamente.");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al borrar el archivo de la base de datos: " + e.getMessage(), e);
        }
    }
    
    public void InsertarPelicula(Pelicula... peliculas) {
        String sqlPelicula = "INSERT INTO Pelicula " +
                "(id,titulo,director,duracion,genero,clasificacion,descripcion,puntuacion) " +
                "VALUES (?,?,?,?,?,?,?,?)";

        String sqlPeliculaActor = "INSERT INTO PeliculaActor (id_pelicula, id_actor) VALUES (?,?)";

        try (Connection con = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement psPelicula = con.prepareStatement(sqlPelicula);
             PreparedStatement psPeliculaActor = con.prepareStatement(sqlPeliculaActor)) {

            for (Pelicula p : peliculas) {
               
                psPelicula.setInt(1, p.getId());
                psPelicula.setString(2, p.getTitulo());
                psPelicula.setString(3, p.getDirector());
                psPelicula.setInt(4, p.getDuracion());
                psPelicula.setString(5, p.getGenero().toString());
                psPelicula.setString(6, p.getClasificacion().toString());
                psPelicula.setString(7, p.getResumen());
                psPelicula.setDouble(8, p.getValoracion());

                if (psPelicula.executeUpdate() != 1) {
                    LOGGER.warning(String.format("No se ha insertado la Pelicula: %s", p));
                } else {
                    
                    for (Actor a : p.getActores()) {
                        psPeliculaActor.setInt(1, p.getId());
                        psPeliculaActor.setInt(2, a.getId());

                        if (psPeliculaActor.executeUpdate() != 1) {
                            LOGGER.warning(String.format(
                                    "No se ha insertado la relación Pelicula-Actor: Pelicula=%s, Actor=%s",
                                    p, a));
                        } else {
                            LOGGER.info(String.format(
                                    "Se ha insertado la relación Pelicula-Actor: Pelicula=%s, Actor=%s",
                                    p, a));
                        }
                    }

                    LOGGER.info(String.format("Se ha insertado la Pelicula: %s", p));
                }
            }

            LOGGER.info(String.format("Se han insertado %d peliculas", peliculas.length));

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al insertar la película", e);
        }
    }

    
    public void insertarActores(Actor... actores) {
        String sql = "INSERT INTO Actor (id,nombre,fecha_nacimiento,pais) VALUES (?,?,?,?)";

        try (Connection con = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (Actor a : actores) {
                ps.setInt(1, a.getId());
                ps.setString(2, a.getNombre());
                ps.setString(3, a.getFechaNacimientoStr());
                ps.setString(4, a.getPais().toString());

                if (ps.executeUpdate() != 1) {
                    LOGGER.warning(String.format("No se ha insertado el Actor: %s", a));
                } else {
                    LOGGER.info(String.format("Se ha insertado el Actor: %s", a));
                }
            }
            
            LOGGER.info(String.format("Se han insertado %d actores", actores.length));

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al insertar los actores", e);
        }
    }
    
    public void InsertarPeliculaActor(int idActor, int idPelicula) {
        String sql = "INSERT OR IGNORE INTO PeliculaActor (id_pelicula, id_actor) VALUES (?,?)";

        try (Connection con = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPelicula);
            ps.setInt(2, idActor);

            if (ps.executeUpdate() == 1) {
                LOGGER.info(String.format("Se ha insertado la relación Pelicula-Actor: Pelicula=%d, Actor=%d",
                        idPelicula, idActor));
            } else {
                LOGGER.info(String.format("La relación Pelicula-Actor ya existía: Pelicula=%d, Actor=%d",
                        idPelicula, idActor));
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al insertar la relación Pelicula-Actor", e);
        }
    }
    
    public List<Pelicula> getPeliculas() {
        List<Pelicula> peliculas = new ArrayList<>();
        String sqlPeliculas = "SELECT * FROM Pelicula";
        String sqlActores = "SELECT a.id, a.nombre, a.fecha_nacimiento, a.pais " +
                            "FROM Actor a " +
                            "JOIN PeliculaActor pa ON a.id = pa.id_actor " +
                            "WHERE pa.id_pelicula = ?";

        try (Connection con = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sqlPeliculas)) {

            while (rs.next()) {
                Pelicula p = new Pelicula();
                p.setId(rs.getInt("id"));
                p.setTitulo(rs.getString("titulo"));
                p.setDirector(rs.getString("director"));
                p.setDuracion(rs.getInt("duracion"));
                p.setGenero(Genero.valueOf(rs.getString("genero")));
                p.setClasificacion(Clasificacion.valueOf(rs.getString("clasificacion")));
                p.setResumen(rs.getString("descripcion"));
                p.setValoracion(rs.getDouble("puntuacion"));

                // Inicializar la lista de actores si es null
                if (p.getActores() == null) {
                    p.setActores(new ArrayList<>());
                }

                try (PreparedStatement psActores = con.prepareStatement(sqlActores)) {
                    psActores.setInt(1, p.getId());
                    try (ResultSet rsActores = psActores.executeQuery()) {
                        while (rsActores.next()) {
                            Actor a = new Actor();
                            a.setId(rsActores.getInt("id"));
                            a.setNombre(rsActores.getString("nombre"));
                            a.setFechaNacimientoStr(rsActores.getString("fecha_nacimiento"));
                            a.setPais(Pais.valueOf(rsActores.getString("pais")));
                            p.getActores().add(a);
                        }
                    }
                }

                peliculas.add(p);
            }

            LOGGER.info(String.format("Se han recuperado %d películas", peliculas.size()));

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener las películas", e);
        }

        return peliculas;
    }

    
    public Actor actorPorId(int id_actor) {
        String sql = "SELECT * FROM Actor WHERE id = ?";

        try (Connection con = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_actor);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Actor actor = new Actor();
                    actor.setId(rs.getInt("id"));
                    actor.setNombre(rs.getString("nombre"));
                    actor.setFechaNacimientoStr(rs.getString("fecha_nacimiento"));
                    actor.setPais(Pais.valueOf(rs.getString("pais")));
                    return actor;
                }
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener el actor con id " + id_actor, e);
        }

        return null; 
    }

    public Pelicula peliculaPorId(int id_pelicula) {
        String sql = "SELECT * FROM Pelicula WHERE id = ?";

        try (Connection con = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_pelicula);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Pelicula p = new Pelicula();
                    p.setId(rs.getInt("id"));
                    p.setTitulo(rs.getString("titulo"));
                    p.setDirector(rs.getString("director"));
                    p.setDuracion(rs.getInt("duracion"));
                    p.setGenero(Genero.valueOf(rs.getString("genero")));
                    p.setClasificacion(Clasificacion.valueOf(rs.getString("clasificacion")));
                    p.setResumen(rs.getString("descripcion"));
                    p.setValoracion(rs.getDouble("puntuacion"));

                    
                    String sqlActores = "SELECT a.id, a.nombre, a.fecha_nacimiento, a.pais " +
                                        "FROM Actor a " +
                                        "JOIN PeliculaActor pa ON a.id = pa.id_actor " +
                                        "WHERE pa.id_pelicula = ?";
                    try (PreparedStatement psActores = con.prepareStatement(sqlActores)) {
                        psActores.setInt(1, id_pelicula);
                        try (ResultSet rsActores = psActores.executeQuery()) {
                            while (rsActores.next()) {
                                Actor a = new Actor();
                                a.setId(rsActores.getInt("id"));
                                a.setNombre(rsActores.getString("nombre"));
                                a.setFechaNacimientoStr(rsActores.getString("fecha_nacimiento"));
                                a.setPais(Pais.valueOf(rsActores.getString("pais")));
                                p.getActores().add(a);
                            }
                        }
                    }

                    return p;
                }
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener la película con id " + id_pelicula, e);
        }

        return null; 
    }
    
    
    
    



    
    
    
    
    

    
    
}


    








