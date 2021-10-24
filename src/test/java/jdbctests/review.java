package jdbctests;
import org.testng.annotations.Test;
import utilities.DBUtils;

import java.sql.*;
import java.util.*;

public class review {
    String dbUrl = "jdbc:oracle:thin:@54.90.49.91:1521:xe";
    String dbUsername = "hr";
    String dbPassword = "hr";


@Test
    public void test()throws SQLException{

    Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
    //create statement object
    Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    //run query and get the result in resultset object
    ResultSet resultSet = statement.executeQuery("select * from departments");

    resultSet.last();
    System.out.println("resultSet.getRow() = " + resultSet.getRow());

    resultSet.beforeFirst();
    System.out.println("resultSet.getRow() = " + resultSet.getRow());

    DatabaseMetaData databaseMetaData = connection.getMetaData();

    System.out.println("databaseMetaData.getDatabaseProductName() = " + databaseMetaData.getDatabaseProductName());
    System.out.println("databaseMetaData.getDatabaseProductVersion() = " + databaseMetaData.getDatabaseProductVersion());
    System.out.println("databaseMetaData.getDriverName() = " + databaseMetaData.getDriverName());
    System.out.println("databaseMetaData.getDriverVersion() = " + databaseMetaData.getDriverVersion());

    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
    System.out.println("resultSetMetaData.getColumnName(0) = " + resultSetMetaData.getColumnName(1));
    System.out.println("resultSetMetaData.getColumnName(1) = " + resultSetMetaData.getColumnName(2));

    for (int i = 1; i <=resultSetMetaData.getColumnCount(); i++) {
        System.out.println("resultSetMetaData.getColumnName(i) = " + resultSetMetaData.getColumnName(i));

    }



}

@Test
    public void test1() throws SQLException {

    Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
    Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    ResultSet resultSet = statement.executeQuery("select * from regions");

    List<Map<String,Object> > mapList =new ArrayList<>();


    Map<String,Object> row1 = new LinkedHashMap<>();
    row1.put("first_name","Steven");
    row1.put("last_name","King");
    row1.put("salary",24000);
    row1.put("job_id","AD_PRES");

    Map<String,Object> row2 = new LinkedHashMap<>();
    row2.put("first_name","Neena");
    row2.put("last_name","Kochhar");
    row2.put("salary",17000);
    row2.put("job_id","AD_VP");


    mapList.add(row1);
    mapList.add(row2);

    System.out.println("mapList.toString() = " + mapList.toString());

}
@Test
    public void test2() throws SQLException {
    Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
    Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    ResultSet resultSet= statement.executeQuery("select * from countries");
    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

    List<Map<String,Object>> mapList =new ArrayList<>();


    System.out.println("resultSetMetaData.getColumnCount() = " + resultSetMetaData.getColumnCount());
    System.out.println("resultSet.last() = " + resultSet.last());
    System.out.println("resultSet.getRow() = " + resultSet.getRow());

        resultSet.beforeFirst();
    while(resultSet.next()){
        Map<String,Object> map = new LinkedHashMap<>();
        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {

          map.put(resultSetMetaData.getColumnName(i), resultSet.getString(i));

        }
        mapList.add(map);
    }

    System.out.println(mapList.toString());


}
@Test
    public void test3(){
    DBUtils.createConnection();


}
}

