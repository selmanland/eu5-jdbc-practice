package apitests.Day6_POJO;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import javax.annotation.concurrent.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class Day7_1_PostRequestDemoReview {

    @BeforeClass
    public void beforeclass() {
        baseURI = ConfigurationReader.get("spartan_api_url");
    }

    /*
    Given accept type and Content type is JSON
    And request json body is:
    {
      "gender":"Male",
      "name":"MikeEU",
      "phone":8877445596
   }
    When user sends POST request to '/api/spartans'
    Then status code 201
    And content type should be application/json
    And json payload/response/body should contain:
    "A Spartan is Born!" message
    and same data what is posted
 */
    @Test
    public void test() {
        String jsonBody = "{\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"name\": \"Mike\",\n" +
                "  \"phone\": 12345678965\n" +
                "}";
        Response response = given()
                .accept(ContentType.JSON).contentType(ContentType.JSON).and()
                .body(jsonBody)
                .when().post("/api/spartans");


        assertEquals(response.statusCode(), 201);
        assertEquals(response.contentType(), "application/json");
        assertEquals(response.path("success"), "A Spartan is Born!");
        assertEquals(response.path("data.name"), "Mike");

    }

    @Test
    public void test2() {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("name", "Mike");
        requestMap.put("gender", "Male");
        requestMap.put("phone", 8877445596l);

        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestMap).log().all().
                when().post("/api/spartans").
                then().statusCode(201)
                .contentType("apllication/json")
                .body("succes", is("A Spartan is Born!"), "data.name",
                 is("Mike"));

    }
  @Test
  public void test3(){
    Gson gson = new Gson();
    Spartan spar = new Spartan();
    spar.setName("Mike");
    spar.setGender("Male");
    spar.setPhone(12345678965L);

    String jsonBody = gson.toJson(spar);

    given().log().all().accept(ContentType.JSON).
            contentType(ContentType.JSON).
            and()
            .body(jsonBody).
    when().post("/api/spartans").
    then()
            .statusCode(201)
            .contentType("application/json")
            .body("success", is("A Spartan is Born!"),
              "data.name",equalTo("Mike"),
              "data.gender",equalTo("Male"),
              "data.phone",equalTo(12345678965L));


  }
  @Test
public void test4(){
    String jsonBody = "{\n" +
              "  \"gender\": \"Male\",\n" +
              "  \"name\": \"Mike\",\n" +
              "  \"phone\": 12345678965\n" +
              "}";
      Response response = given().accept(ContentType.JSON).contentType(ContentType.JSON).
              and().body(jsonBody).
              when().post("/api/spartans");// post request

      int idPost = response.path("data.id");

      given().accept(ContentType.JSON).
              pathParam("id",idPost).
      when().get("/api/spartans/{id}").
      then().statusCode(200).log().all();



  }

}
