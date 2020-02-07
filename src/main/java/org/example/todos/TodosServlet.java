package org.example.todos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Todos", urlPatterns = "/api/todos/*")
public class TodosServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(TodosServlet.class);
    private ObjectMapper mapper;
    private TodosRepository repository;

    /**
     * Servlet container needs it.
     */
    public TodosServlet() {
        this(new TodosRepository(), new ObjectMapper());
    }

    public TodosServlet(TodosRepository repository, ObjectMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Got request with parameters " + req.getParameterMap());
        resp.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(resp.getOutputStream(), repository.findAll());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Got post" + req.getParameterMap());
        resp.setContentType("application/json;charset=UTF-8");
        var newTodo = mapper.readValue(req.getInputStream(), Todos.class);
        mapper.writeValue(resp.getOutputStream(), repository.addTodo(newTodo));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Get put" + req.getParameterMap());
        var pathInfo = req.getPathInfo();
        try {
            var todoId = Integer.valueOf(pathInfo.substring(1));
            var todo = repository.toggleTodo(todoId);
            resp.setContentType("application/json;charset=UTF-8");
            mapper.writeValue(resp.getOutputStream(), todo);
        } catch (NumberFormatException e) {
            logger.warn("Wrong path used: " + pathInfo);
        }
    }
}
