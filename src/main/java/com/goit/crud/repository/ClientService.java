package com.goit.crud.repository;

import com.goit.crud.datasource.Datasource;
import com.goit.crud.entity.Client;
import com.goit.crud.exception.DatasourceException;
import com.goit.crud.exception.NameLengthException;
import org.intellij.lang.annotations.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//@Slf4j
//DAO - DATA ACCESS OBJECT
public class ClientService extends JDBCRepository<Client> {

    private static final String TABLE_NAME = "client";
    private static final Logger LOG = LoggerFactory.getLogger(ClientService.class);

    @Language("SQL")
    private static final String INSERT_CLIENT = """
            INSERT INTO client (ID, NAME) VALUES (DEFAULT, ?)
            """;

    @Language("SQL")
    private static final String UPDATE_CLIENT = """
            UPDATE client SET NAME=? WHERE ID=?;
            """;

    public ClientService(Datasource datasource) {
        super(datasource);
        LOG.info("Created ClientServiceRepositoryIml");
    }

    @Override
    public long create(String name) {

        if (name.length()>1021) {
            String message = "Name too long, please contact the technical support"
                    + "if yours name is this long.";
            throw new NameLengthException(message);
        }
        
        try {
                PreparedStatement preparedStatement = datasource.preparedStatement(INSERT_CLIENT, true);
                final int nameIndex = 1;

                preparedStatement.setString(nameIndex, name);
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                generatedKeys.first();
                long clientId = generatedKeys.getLong("ID");
                datasource.close();
                return clientId;

        } catch (SQLException e) {
            String message = "create";
            LOG.error(message, e);
            throw new DatasourceException(message, e);
        }
    }
    
    @Override
    public String getById(Long id) {
        try {
            PreparedStatement preparedStatement = datasource.preparedStatement(getFindByIdQuery(TABLE_NAME), true);
            preparedStatement.setString(1, "ID");
            preparedStatement.setLong(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean next = resultSet.next();
            if (!next) {
                return null;
            }
            String clientId = resultSet.getString(TABLE_NAME);
            datasource.close();
            return clientId;
        } catch (SQLException e) {
            String message = "findById";
            LOG.error(message, e);
            throw new DatasourceException(message, e);
        }
    }
    
    void setName(long id, String name) {
        
        if (name.length()>1021) {
            String message = "Name too long, please contact the technical support"
                    + "if yours name is this long.";
            throw new NameLengthException(message);
        }
        
        try {
            Client client = getClientById(id);
            assert client != null;

            PreparedStatement preparedStatement = datasource.preparedStatement(UPDATE_CLIENT, true);
            final int nameIndex = 1;
            final int idIndex = 2;

            preparedStatement.setString(nameIndex, name);
            preparedStatement.setLong(idIndex, client.getClientId());
            preparedStatement.executeUpdate();
            datasource.close();
        } catch (SQLException e) {
            String message = "set name";
            LOG.error(message, e);
            throw new DatasourceException(message, e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            PreparedStatement preparedStatement = datasource.preparedStatement(getDeleteByIdQuery(TABLE_NAME));
            final int clientIdIndex = 1;
            final int idIndex = 2;
            preparedStatement.setString(clientIdIndex, "ID");
            preparedStatement.setLong(idIndex, id);
            preparedStatement.executeUpdate();
            datasource.close();
        } catch (SQLException e) {
            String message = "delete";
            LOG.error(message, e);
            throw new DatasourceException(message, e);
        }
    }
    
    @Override
    public List<Client> listAll() {
        LOG.info("listing all clients");
        try {
            PreparedStatement preparedStatement = datasource.preparedStatement(getFindAllQuery(TABLE_NAME), true);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Client> customerEntities = new ArrayList<>();
            while (resultSet.next()) {
                Client client = parseClients(resultSet);
                customerEntities.add(client);
            }
            datasource.close();
            LOG.info("all customers listed");
            return customerEntities;
        } catch (SQLException e) {
            String message = "listAll";
            LOG.error(message, e);
            throw new DatasourceException(message, e);
        }
    }

    // service methods lower

    private Client parseClients(ResultSet resultSet) throws SQLException {
        Long clientId = resultSet.getLong("ID");
        String clientName = resultSet.getString("NAME");
        return Client.of(clientId, clientName);
    }

    private Client getClientById(Long id) throws SQLException {
        try {
            PreparedStatement preparedStatement = datasource.preparedStatement(getFindByIdQuery(TABLE_NAME), true);
            preparedStatement.setString(1, "ID");
            preparedStatement.setLong(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean next = resultSet.next();
            if (!next) {
                return null;
            }
            Client client = parseClients(resultSet);
            datasource.close();
            return client;
        } catch (SQLException e) {
            String message = "findById";
            LOG.error(message, e);
            throw new DatasourceException(message, e);
        }
    }

}
