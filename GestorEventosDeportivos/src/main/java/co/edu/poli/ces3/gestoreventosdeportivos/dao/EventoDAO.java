package co.edu.poli.ces3.gestoreventosdeportivos.dao;

import co.edu.poli.ces3.gestoreventosdeportivos.models.Evento;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventoDAO {
    private static List<Evento> listaEventos = new ArrayList<>();
    private static int idGenerador = 1;

    public List<Evento> listarTodos() {
        return listaEventos;
    }

    public void insertar(Evento nuevoEvento) {
        listaEventos.add(nuevoEvento);
    }

    public boolean registrarEvento(Evento nuevoEvento) {
        if (nuevoEvento.getEquiposParticipantes().size() < 2) {
            return false;
        }
        nuevoEvento.setId(idGenerador++);
        listaEventos.add(nuevoEvento);
        return true;
    }

    public List<Evento> listarEventos() {
        return listaEventos;
    }

    public Optional<Evento> buscarEventoPorId(int identificador) {
        return listaEventos.stream().filter(ev -> ev.getId() == identificador).findFirst();
    }

    public List<Evento> buscarPorFiltros(String deporteFiltro, String estadoFiltro, String inicio, String fin) {
        return listaEventos.stream()
                .filter(ev -> (deporteFiltro == null || ev.getDeporte().equalsIgnoreCase(deporteFiltro)))
                .filter(ev -> (estadoFiltro == null || ev.getEstado().equalsIgnoreCase(estadoFiltro)))
                .filter(ev -> (inicio == null || ev.getFecha().compareTo(inicio) >= 0))
                .filter(ev -> (fin == null || ev.getFecha().compareTo(fin) <= 0))
                .collect(Collectors.toList());
    }

}
