package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import proyectoCine.gui.JFramePrincipal;
import proyectoCine.domain.Actor;
import proyectoCine.domain.Actor.Pais;
import proyectoCine.domain.Horario;
import proyectoCine.domain.Pelicula;
import proyectoCine.domain.Pelicula.Clasificacion;
import proyectoCine.domain.Pelicula.Genero;

public class Main {

    public static void main(String[] args) {

        List<Pelicula> cartelera = new ArrayList<Pelicula>();

        // Actores
        Actor actor1 = new Actor(1, "Robert Downey Jr.", LocalDate.of(1965, 4, 4), Pais.ESTADOS_UNIDOS);
        Actor actor2 = new Actor(2, "Chris Evans", LocalDate.of(1981, 6, 13), Pais.PAISES_BAJOS);
        Actor actor3 = new Actor(3, "Scarlett Johansson", LocalDate.of(1984, 11, 22), Pais.FILIPINAS);
        Actor actor4 = new Actor(4, "Leonardo DiCaprio", LocalDate.of(1974, 11, 11), Pais.JORDANIA);
        Actor actor5 = new Actor(5, "Emma Watson", LocalDate.of(1990, 4, 15), Pais.NAMIBIA);

        // Actores Batman
        Actor actorBatman1 = new Actor(6, "Christian Bale", LocalDate.of(1974, 1, 30), Pais.ESTADOS_UNIDOS);
        Actor actorBatman2 = new Actor(7, "Heath Ledger", LocalDate.of(1979, 4, 4), Pais.AUSTRALIA);

        // Horarios disponibles
        ArrayList<Horario> horarios1 = new ArrayList<>();
        horarios1.add(Horario.H0900);
        horarios1.add(Horario.H1400);
        horarios1.add(Horario.H1900);

        ArrayList<Horario> horarios2 = new ArrayList<>();
        horarios2.add(Horario.H1130);
        horarios2.add(Horario.H1630);
        horarios2.add(Horario.H2130);

        ArrayList<Horario> horarios3 = new ArrayList<>();
        horarios3.add(Horario.H0900);
        horarios3.add(Horario.H2130);

        // Horarios Batman
        ArrayList<Horario> horariosBatman = new ArrayList<>();
        horariosBatman.add(Horario.H1630);
        horariosBatman.add(Horario.H2130);

        // Crear películas con la nueva valoración (double)
        Pelicula pelicula1 = new Pelicula(
            1,
            "Avengers: Endgame",
            "Anthony Russo",
            181,
            Genero.ACCION,
            List.of(actor1, actor2, actor3),
            Clasificacion.MAYORES_12,
            "Los Vengadores se enfrentan a Thanos en la batalla final.",
            horarios1,
            4.8 // valoracion
        );

        Pelicula pelicula2 = new Pelicula(
            2,
            "Titanic",
            "James Cameron",
            195,
            Genero.DRAMA,
            List.of(actor4),
            Clasificacion.MAYORES_12,
            "Romance y tragedia a bordo del Titanic.",
            horarios2,
            4.5 // valoracion
        );

        Pelicula pelicula3 = new Pelicula(
            3,
            "Harry Potter y la Piedra Filosofal",
            "Chris Columbus",
            152,
            Genero.FANTASIA,
            List.of(actor5),
            Clasificacion.TODAS,
            "Un joven mago descubre su destino en Hogwarts.",
            horarios3,
            4.7 // valoracion
        );

        Pelicula peliculaBatman = new Pelicula(
            4,
            "Batman",
            "Christopher Nolan",
            152,
            Genero.ACCION,
            List.of(actorBatman1, actorBatman2),
            Clasificacion.MAYORES_16,
            "Batman enfrenta al Joker en una batalla por el alma de Gotham.",
            horariosBatman,
            4.9 // valoracion
        );

        // Añadir películas a la cartelera
        cartelera.add(pelicula1);
        cartelera.add(pelicula2);
        cartelera.add(pelicula3);
        cartelera.add(peliculaBatman);

        // Crear GUI
        SwingUtilities.invokeLater(() -> new JFramePrincipal(cartelera));
    }
}
