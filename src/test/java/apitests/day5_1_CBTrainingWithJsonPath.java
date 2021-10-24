package apitests;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import utilities.ConfigurationReader;

public class day5_1_CBTrainingWithJsonPath {  //day 5.1

    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("cbt_api_url");
    }

    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 24112) //17982
                .when().get("/student/{id}");

        //verify status code
        assertEquals(response.statusCode(),200);

        //assign response to jsonpath
        JsonPath jsonPath = response.jsonPath();

        //get values from jsonpath
        String firstName = jsonPath.getString("students.firstName[0]");
        System.out.println("firstName = " + firstName);

        String lastName = jsonPath.get("students.lastName[0]");
        System.out.println("lastName = " + lastName);

        String phone = jsonPath.getString("students.contact[0].phone");
        System.out.println("phone = " + phone);

        //get me city and zipcode, do assertion
        String city = jsonPath.getString("students.company[0].address.city");
        System.out.println("city = " + city);
        assertEquals(city,"string");

        String zipCode = jsonPath.getString("students.company[0].address.zipCode");
        System.out.println("zipCode = " + zipCode);
        assertEquals(zipCode,0);

        String firstname2 = jsonPath.getString("students.firstName");
        System.out.println("firstname2 = " + firstname2);

//        String firstname3 =response.path("students.firstName");
//        System.out.println("firstname3 = " + firstname3);

        String zipCode2= response.path("students.company[0].address.zipCode");
        System.out.println("zipCode2 = " + zipCode2);

//try to arraylist and integer with string response path

    }

    @Test
    public void test3(){ //self study
        Response response = given().accept(ContentType.JSON).and().pathParam("id",24112)
                .when().get("/student/{id}");
        JsonPath jsonPath=response.jsonPath();

        System.out.println("response.statusCode() = " + response.statusCode());

        int studentId0 = jsonPath.getInt("students.studentId[0]");
        System.out.println("d = " + studentId0);

        System.out.println("response.path(\"student.studentId[0]\") = " + response.path("students.studentId[0]"));

        Response response2 = given().accept(ContentType.JSON)
                .and().pathParam("id", 24112)
                .when().get("/student/{id}");
        System.out.println("response.path(\"student.studentId[0]\") = " + response2.path("students.studentId[0]"));


//        String firstname3 =response.path("students.firstName"); // it complains , you will get error. ArrayList cannot cast to String
//        System.out.println("firstname3 = " + firstname3);

        String fistName2 = jsonPath.getString("students.firstName");//but jasonPath can not complain you can get a result inside [Mira]
        System.out.println("fistName2 = " + fistName2);

        int zipCode =jsonPath.getInt("students.company[0].address.zipCode");
        System.out.println("zipCode = " + zipCode);
        assertEquals(zipCode,0);

        String zipCode1 =jsonPath.getString("students.company[0].address.zipCode");// you can convert directyl integer value to String
        System.out.println("zipCode = " + zipCode1);
        assertEquals(zipCode,"0");

        String zipCode2 =response.path("students.company[0].address.zipCode"); // cannot be cast to String
        System.out.println("zipCode = " + zipCode2);                             //response.path you can not int to String but jasonPath possible
        //assertEquals(zipCode,"0");

        System.out.println("response.path(\"students.company[0].address.zipCode\") = " + response.path("students.company[0].address.zipCode"));


    }
}