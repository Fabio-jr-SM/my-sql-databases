
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DisplayAuthors {

    /*Declara uma constante de String para URL de banco de dados. Isso identifica o 
    nome do BD ao qual se conectar, bem como informações sobre o protocolo utilizado pelo driver JDBC*/
    
    static final String DATABASE_URL = "jdbc:mysql://localhost/books";

    public static void main(String args[]) {
        Connection connection = null; // gerencia a conexão
        Statement statement = null; // instrução de consulta
        ResultSet resultSet = null; // gerencia resultados

        try {
            
            /*Cria um obj Connection referenciado por connection.
            Os Objetos Connection permitem ao programas criar instruções de SQL que acessem o Banco de dados*/
            
            /*o metodo getConnection da classe DriverManager que tenta conectar ao banco de dados especificado 
            na URL da linha 14*/
            
            /*O método getConnection aceita 3 argumentos: 
            1. uma STRING que especifica o URL do banco de dados;
            2. O nome de usuario do banco de dados MySql; e
            3. A senha do banco de dados MySql*/
            
            /*Caso o driveManager não se conectar ao banco de dados o metodo getConnection lança uma SQLException*/
            
            connection = DriverManager.getConnection(
                    DATABASE_URL, "root", "ifmt@123"); //permitir ao programas criar instruções de SQL que acessem o Banco de dados

            /*Nessa linha o invoca o metodo Connection createStatement para obter um objeto que implementara 
            interface Statement que utiliza o objeto Statement para enviar instruções de SQL ao BD*/
            
            statement = connection.createStatement(); // enviar instruções de SQL ao BD

            /*o metodo executeQuery do objeto Statement cria uma consulta que seleciona todas as informações de autor da tabela Authors
            Esse metodo retorna um objeto que implementa a interface ResultSet (permite que o programa manipule o resultado da consulta)
            e contem os resultados da consulta*/
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
