package org.example.todos;

import org.example.HibernateUtil;

import java.util.List;

public class TodosRepository {

    List<Todos> findAll() {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        var result = session.createQuery("from Todos", Todos.class).list();
        transaction.commit();
        session.close();
        return result;
    }

    public Todos toggleTodo(Integer id) {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        var todo = session.get(Todos.class, id);
        todo.setDone(!todo.isDone());
        transaction.commit();
        session.close();
        return todo;
    }

    public Todos addTodo(Todos newTodo) {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        session.persist(newTodo);
        transaction.commit();
        session.close();
        return newTodo;
    }
}
