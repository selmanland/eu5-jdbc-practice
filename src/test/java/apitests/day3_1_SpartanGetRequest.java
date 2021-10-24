package apitests;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
public class day3_1_SpartanGetRequest {

    String spartanurl = "http://54.90.49.91:8000";

    @Test
    public void  test1(){
        Response response = when().get(spartanurl+"/api/spartans");

        System.out.println(response.statusCode());

        response.prettyPrint();


    }

    /* TASK
        When users sends a get request to /api/spartans/3
        Then status code should be 200
        And content type should be application/json;charset=UTF-8
        and json body should contain Fidole
     */
  @Test
    public void test2(){
        Response response = when().get(spartanurl + "/api/spartans/3");

        //verify status code
        Assert.assertEquals(response.statusCode(),200);

        //verify content type
        Assert.assertEquals(response.contentType(),"application/json");  // application/json;charset=UTF-8 this url was wrong i changed correct one which is application/json

        //verify Fidole
        Assert.assertTrue(response.body().asString().contains("Fidole"));
    }

    /*
        Given no headers provided
        When Users sends GET request to /api/hello
        Then response status code should be 200
        And Content type header should be “text/plain;charset=UTF-8”
        And header should contain date
        And Content-Length should be 17
        And body should be “Hello from Sparta"
        */

    @Test
    public void helloTest(){
        //request
        Response response = when().get(spartanurl + "/api/hello");

        //verify status code
        Assert.assertEquals(response.statusCode(),200);
        //verify content-type
        Assert.assertEquals(response.contentType(),"text/plain;charset=UTF-8");

        //verify we have headers named date
        Assert.assertTrue(response.headers().hasHeaderWithName("Date")); //turns boolean it checks if Date is available. it checks only keys not value
        //to get any header passing as a key
        System.out.println(response.header("Content-Length"));   // header and headers
        System.out.println(response.header("Date"));

        //verify content length is 17
        Assert.assertEquals(response.header("Content-Length"),"17"); // (key, value) Content-Length = 17

        //verify hello from sparta
        Assert.assertEquals(response.getBody().asString(),"Hello from Sparta"); //response.asString() also can be wihout getBody()
    }

    // headers is only to check whether given header key is available
    // header, write the key inside header method get value


}