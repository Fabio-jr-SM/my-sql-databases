import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DIsplayAuthors {

    static final String DATABESE_URL = "jdbc:mysql:://localhost/books";
    public static void main(String[] args) {
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection(DATABESE_URL, "deitel ", "ifmt@123");

            statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT AuthorID, FirstName, LastName, FROM Authors");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfcolumns = metaData.getColumnCount();
            System.out.println("Authors Table of Books Database\n");

            for(int i = 1; i <= numberOfcolumns; i++){
                System.out.printf("%-8st", metaData.getColumnName(i));
                System.out.println();

                while (resultSet.next()) {
                    for(int j = 1; j <= numberOfcolumns; j++){
                        System.out.printf("%-8st", resultSet.getObject(j));
                        System.out.println();
                    }
                }
            }
        }

        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        finally{
            try{
                resultSet.close();
                statement.close();
                connection.close();
            }
            catch(Exception exception){
                exception.printStackTrace();
            }
        }
    }
}