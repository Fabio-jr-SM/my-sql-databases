
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;

public class ResultSetTableModel extends AbstractTableModel {

    private Connection connection;
    private Statement statement;
    private ResultSet resultset;
    private ResultSetMetaData metaData;
    private int numberOfRows;
    private boolean connectedToDatabase = false;

    public ResultSetTableModel(String url, String username,
            String password, String query) throws SQLException {

            connection = DriverManager.getConnection(url, username, password, query);

        statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );

        connectedToDatabase = true;

        setQuery(query);
    }

    public Class getColumnClass(int column) throws IllegalStateException {
        if (!connectedToDatabase) {

            throw new IllegalStateException("Not Connectd to Database");

            try{
                String className = metaData.getColumnClassName(column + 1);

                return Class.forName(className);

            } catch (Exception exception) {
                exception.printStackTrace();
            }

            return Object.class;
        }
    }

    public int getColumnCount() throws IllegalStateException {
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
            try {
                return metaData.getColumnCount();
            }
            catch (SQLException sqlException) {
                sqlException.printStackTrace();
            } 

            return 0; // se ocorrerem os problemas acima, retorna 0 para o número de colunas
        } // fim do método getColumnCount

    }// obtém o nome de uma coluna particular em ResultSet[
    
    public String getColumnName(int column) throws IllegalStateException {
        // assegura que o banco de dados conexão está disponível
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }
        // determina o nome de coluna
        try {
            return metaData.getColumnName(column + 1);
        } // fim do try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // fim do catch

        return ""; // se ocorrerem problemas, retorna string vazia para nome de coluna
    } // fim do método getColumnName

    // retorna o número de linhas em ResultSet
    public int getRowCount() throws IllegalStateException {
        // assegura que o banco de dados conexão está disponível
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        return numberOfRows;
    } // fim do método getRowCount
    // obtém o valor na linha e na coluna especificadas

    public Object getValueAt(int row, int column)
            throws IllegalStateException {
        // assegura que o banco de dados conexão está disponível
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        // obtém o valor na linha e na coluna especificadas do ResultSet
        try {
            resultSet.absolute(row + 1);
            return resultSet.getObject(column + 1);
        } // fim do try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // fim do catch

        return ""; // se ocorrerem problemas, retorna objeto string vazio
    } // fim do método getValueAt

    // configura a nova string de consulta de banco de dados
    public void setQuery(String query)
            throws SQLException, IllegalStateException {
        // assegura que o banco de dados conexão está disponível
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }
        // especifica a consulta e a executa
        resultSet = statement.executeQuery(query);
        // obtém os metadados para o ResultSet
        metaData = resultSet.getMetaData();

        // determina o número de linhas em ResultSet
        resultSet.last(); // move para a última linha
        numberOfRows = resultSet.getRow(); // obtém o número de linha
        // notifica a JTable de que modelo foi alterado
        fireTableStructureChanged();
    } // fim do método setQuery

    public void disconnectFromDatabase() {
        if (connectedToDatabase) {
            // fecha Statement e Connection
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } // fim do try
            catch (SQLException sqlException) {
                sqlException.printStackTrace();
            } // fim do catch
            finally // atualiza status de conexão de banco de dados
            {
                connectedToDatabase = false;
            } // fim de finally
        } // fim do if
    } // fim do método disconnectFromDatabase
}
