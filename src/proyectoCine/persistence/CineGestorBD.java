package proyectoCine.persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class CineGestorBD {

    // Rutas de los CSV
    private final String CSV_PELICULAS = "resources/peliculas.csv";
    private final String CSV_ACTORES = "resources/actores.csv";

    // Configuración SQLite
    private final String DRIVER = "org.sqlite.JDBC";
    private final String DATABASE_URL = "jdbc:sqlite:resources/cine.db";

    public CineGestorBD() {
        try {
            Class.forName(DRIVER);
            System.out.println("Driver cargado correctamente.");

            // Crear la base de datos y tablas
            crearTablas();

            // Cargar los CSV
            cargarPeliculas();
            cargarActores();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Crear tablas si no existen
    private void crearTablas() {
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

        try (Connection con = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = con.createStatement()) {

            stmt.execute(sqlPeliculas);
            stmt.execute(sqlActores);

            System.out.println("Tablas creadas correctamente.");

        } catch (Exception e) {
            System.out.println("Error al crear las tablas:");
            e.printStackTrace();
        }
    }

    // Cargar CSV de películas
    private void cargarPeliculas() {
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

            System.out.println("Películas cargadas.");

        } catch (Exception e) {
            System.out.println("Error al cargar las películas:");
            e.printStackTrace();
        }
    }

    // Cargar CSV de actores
    private void cargarActores() {
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

            System.out.println("Actores cargados.");

        } catch (Exception e) {
            System.out.println("Error al cargar los actores:");
            e.printStackTrace();
        }
    }
}





