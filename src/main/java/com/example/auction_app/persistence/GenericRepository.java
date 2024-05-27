package com.example.auction_app.persistence;
import java.sql.SQLException;


/** generic contract for repositories. minimum set of methods that all repositories should have */
public interface GenericRepository<T> {

    public void add(T entity) throws SQLException;;

    public T get(int id);

    public void update(T entity);

    public void delete(T entity);

    public int getSize();

}