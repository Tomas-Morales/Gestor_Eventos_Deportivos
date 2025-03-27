package co.edu.poli.ces3.gestoreventosdeportivos.dao;

import co.edu.poli.ces3.gestoreventosdeportivos.models.Jugador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JugadorDAO {
    private static List<Jugador> listaJugadores = new ArrayList<>();
    private static int idSecuencial = 1;

    public List<Jugador> listarTodos() {
        return new ArrayList<>(listaJugadores);
    }

    public boolean numeroAsignado(int numeroCamiseta, int idEquipo) {
        for (Jugador item : listaJugadores) {
            if (item.getEquipoId() == idEquipo && item.getNumero() == numeroCamiseta) {
                return true;
            }
        }
        return false;
    }

    public void insertar(Jugador nuevoJugador) {
        listaJugadores.add(nuevoJugador);
    }

    public boolean registrarJugador(Jugador nuevoJugador) {
        boolean yaExiste = listaJugadores.stream()
                .anyMatch(j -> j.getEquipoId() == nuevoJugador.getEquipoId() && j.getNumero() == nuevoJugador.getNumero());

        if (yaExiste) {
            return false;
        }

        nuevoJugador.setId(idSecuencial++);
        listaJugadores.add(nuevoJugador);
        return true;
    }

    public List<Jugador> listarJugadores() {
        return listaJugadores;
    }

    public Optional<Jugador> buscarJugadorPorId(int identificador) {
        return listaJugadores.stream().filter(j -> j.getId() == identificador).findFirst();
    }

}
