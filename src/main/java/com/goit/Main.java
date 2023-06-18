package com.goit;

import com.goit.config.FlywayConfig;
import com.goit.config.LogConfig;
import com.goit.crud.datasource.Datasource;
import com.goit.crud.entity.Client;
import com.goit.crud.repository.ClientService;
import com.goit.crud.repository.JDBCRepository;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        new LogConfig();
        new FlywayConfig().setup().migrate();
        JDBCRepository<Client> repository = new ClientService(new Datasource());
        List<Client> all = repository.listAll();
        System.out.println(all);
    }
}