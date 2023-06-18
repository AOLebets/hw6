package com.goit.crud.repository;

import com.goit.crud.datasource.Datasource;
import com.goit.crud.entity.Client;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;

import java.util.List;


@RequiredArgsConstructor
public abstract class JDBCRepository<T> {
    @Language("SQL")
    private final String findByIdQuery = "SELECT * FROM %s WHERE ?=?;";
    @Language("SQL")
    private final String findAllQuery = "SELECT * FROM %s;";
    @Language("SQL")
    private final String deleteByIdQuery = "DELETE FROM %s WHERE ?=?;";

    public String getFindByIdQuery(String tableName) {
        return String.format(findByIdQuery, tableName);
    }

    public String getFindAllQuery(String tableName) {
        return String.format(findAllQuery, tableName);

    }

    public String getDeleteByIdQuery(String tableName) {
        return String.format(deleteByIdQuery, tableName);
    }

    protected final Datasource datasource;

    public abstract long create( String name );

    public abstract String getById( Long id );

    public abstract void deleteById( Long id );

    public abstract List<T> listAll();
}
