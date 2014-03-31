/*
 

                           # #     ###   
                             #       ##  
 #####  ##### #####   #### # #####    ## 
#   #  #      #    # #     # #         ##
#  #    ####  #    # #     # #        ## 
#           # #    # #     # #       ##  
 ##### #####   # ##  #     #  #### ###   
              #                          



 */


package ds;

import helpers.XmlHelper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ConnectionFactory {
    private DataSource dataSource; 
    private Connection connection;
    
    
    private ConnectionFactory(){
        
    }
    private static ConnectionFactory instance;
    public static ConnectionFactory getInstance() {
        if(instance == null)
            instance = new ConnectionFactory();
        return instance;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public Connection createConnection(String dataSourceXml) {
        dataSource = XmlHelper.getInstance().convertXmlToDataSource(dataSourceXml);
        if(connection == null){/*(4)*/ 
            try {
                connection = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUser(), dataSource.getPassword());
                System.out.println(connectionInfo(connection));
            } catch (SQLException ex) {
            }
        }
        return connection;
    }
    
    
    public  String connectionInfo(Connection connection) {
        StringBuilder sb = new StringBuilder(100);
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();        
                
                sb.append("Connected as : ").append(databaseMetaData.getUserName()).append("\n")
                  .append("          to : ").append(databaseMetaData.getDatabaseProductName())
                                             .append(" ")
                                             .append(databaseMetaData.getDatabaseProductVersion())
                                             .append("\n")
                  .append("       using : ")
                  .append(databaseMetaData.getDriverName())
                  .append(" ")
                  .append(databaseMetaData.getDriverMajorVersion())
                  .append(".")
                  .append(databaseMetaData.getDriverMinorVersion())
                  .append(" type ")
                  .append(databaseMetaData.getJDBCMajorVersion())
                  .append(".")
                  .append(databaseMetaData.getJDBCMinorVersion())
                  .append("\n");
             
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
         return sb.toString();
    }
    
    
    
    
    
}
