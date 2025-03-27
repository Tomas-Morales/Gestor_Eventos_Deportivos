package co.edu.poli.ces3.gestoreventosdeportivos.servlets;

import co.edu.poli.ces3.gestoreventosdeportivos.dao.EquipoDAO;
import co.edu.poli.ces3.gestoreventosdeportivos.models.Equipo;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/equipos")
public class EquipoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final EquipoDAO gestorEquipos = new EquipoDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        BufferedReader lector = request.getReader();
        Equipo nuevo = gson.fromJson(lector, Equipo.class);

        boolean fueAgregado = gestorEquipos.registrarEquipo(nuevo);

        if (fueAgregado) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(gson.toJson(nuevo));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"El equipo ya existe o los datos son inválidos\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int pagina = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int cantidad = request.getParameter("size") != null ? Integer.parseInt(request.getParameter("size")) : 5;

        List<Equipo> resultados = gestorEquipos.listarEquiposPaginados(pagina, cantidad);
        response.getWriter().write(gson.toJson(resultados));
    }
}
