package co.edu.poli.ces3.gestoreventosdeportivos.servlets;

import co.edu.poli.ces3.gestoreventosdeportivos.dao.EventoDAO;
import co.edu.poli.ces3.gestoreventosdeportivos.models.Evento;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/eventos")
public class EventoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final EventoDAO eventoDAO = new EventoDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Evento> listaEventos = eventoDAO.listarTodos();
        String json = gson.toJson(listaEventos);
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        BufferedReader lector = request.getReader();
        Evento nuevo = gson.fromJson(lector, Evento.class);

        eventoDAO.insertar(nuevo);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().write("{\"mensaje\": \"Evento registrado con éxito\"}");
    }
}
