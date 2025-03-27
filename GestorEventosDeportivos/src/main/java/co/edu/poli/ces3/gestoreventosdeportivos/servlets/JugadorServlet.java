package co.edu.poli.ces3.gestoreventosdeportivos.servlets;

import co.edu.poli.ces3.gestoreventosdeportivos.dao.EquipoDAO;
import co.edu.poli.ces3.gestoreventosdeportivos.dao.JugadorDAO;
import co.edu.poli.ces3.gestoreventosdeportivos.models.Equipo;
import co.edu.poli.ces3.gestoreventosdeportivos.models.Jugador;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/jugadores")
public class JugadorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final JugadorDAO jugadorDAO = new JugadorDAO();
    private final EquipoDAO equipoDAO = new EquipoDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Jugador> listado = jugadorDAO.listarTodos();
        String json = gson.toJson(listado);
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        BufferedReader lector = request.getReader();
        Jugador nuevo = gson.fromJson(lector, Jugador.class);

        Equipo equipoExistente = equipoDAO.buscarPorId(nuevo.getEquipoId());
        if (equipoExistente == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\": \"El equipo no existe\"}");
            return;
        }

        boolean estaEnUso = jugadorDAO.numeroAsignado(nuevo.getNumero(), nuevo.getEquipoId());
        if (estaEnUso) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().write("{\"mensaje\": \"El número ya está en uso en este equipo\"}");
            return;
        }

        jugadorDAO.insertar(nuevo);
        equipoExistente.agregarJugador(nuevo.getId());

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().write("{\"mensaje\": \"Jugador registrado con éxito\"}");
    }
}
