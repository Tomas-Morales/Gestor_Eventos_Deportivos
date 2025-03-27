package co.edu.poli.ces3.gestoreventosdeportivos.dao;

import co.edu.poli.ces3.gestoreventosdeportivos.models.Equipo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquipoDAO {
    private static List<Equipo> listaEquipos = new ArrayList<>();
    private static int idActual = 1;

    public Equipo buscarPorId(int identificador) {
        for (Equipo item : listaEquipos) {
            if (item.getId() == identificador) {
                return item;
            }
        }
        return null;
    }

    public boolean registrarEquipo(Equipo nuevoEquipo) {
        boolean yaExiste = listaEquipos.stream()
                .anyMatch(e -> e.getNombre().equalsIgnoreCase(nuevoEquipo.getNombre())
                        && e.getDeporte().equalsIgnoreCase(nuevoEquipo.getDeporte()));

        if (yaExiste) {
            return false;
        }

        nuevoEquipo.setId(idActual++);
        listaEquipos.add(nuevoEquipo);
        return true;
    }

    public List<Equipo> listarEquiposPaginados(int pagina, int cantidad) {
        int desde = (pagina - 1) * cantidad;
        int hasta = Math.min(desde + cantidad, listaEquipos.size());

        if (desde >= listaEquipos.size()) {
            return new ArrayList<>();
        }

        return listaEquipos.subList(desde, hasta);
    }

    public Optional<Equipo> buscarEquipoPorId(int identificador) {
        return listaEquipos.stream().filter(e -> e.getId() == identificador).findFirst();
    }

    public boolean asignarJugador(int idEquipo, int idJugador) {
        Optional<Equipo> equipoEncontrado = buscarEquipoPorId(idEquipo);
        if (equipoEncontrado.isPresent()) {
            equipoEncontrado.get().agregarJugador(idJugador);
            return true;
        }
        return false;
    }

    public List<Equipo> obtenerListadoEquipos() {
        return listaEquipos;
    }
}
