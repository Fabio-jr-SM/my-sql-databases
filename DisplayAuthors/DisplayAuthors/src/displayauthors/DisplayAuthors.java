
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DisplayAuthors {

    static final String DATABASE_URL = "jdbc:mysql://localhost/books";

    public static void main(String args[]) {
        Connection connection = null; // gerencia a conexão
        Statement statement = null; // instrução de consulta
        ResultSet resultSet = null; // gerencia resultados

        try {
            
            connection = DriverManager.getConnection(
                    DATABASE_URL, "root", "ifmt@123");

            statement = connection.createStatement();

            resultSet = statement.executeQuery(
                    "SELECT AuthorID, FirstName, LastName FROM Authors");

            
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            System.out.println("Authors Table of Books Database:\n");
            for (int i = 1; i <= numberOfColumns; i++) {
                System.out.printf("%-8s\t", metaData.getColumnName(i));
            }
            System.out.println();

            while (resultSet.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    System.out.printf("%-8s\t", resultSet.getObject(i));
                }
                System.out.println();
            } // fim do while
        } // fim do try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // fim do catch
        finally // assegura que o resultSet, a instrução e a conexão estão fechados
        {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } // fim do try
            catch (Exception exception) {
                exception.printStackTrace();
            } // fim do catch
        } // fim de finally
    } // fim de main
}
