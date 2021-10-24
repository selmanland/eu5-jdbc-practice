package apitests.Day8;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import static io.restassured.RestAssured.*;

public class PutDeleteReview {
    @BeforeClass

    public void beforeclass() {
        baseURI = ConfigurationReader.get("spartan_api_url");
    }

    @Test
    public void putTest() {
        Map<String, Object> putParam = new HashMap<>();
        putParam.put("name", "Putname");
        putParam.put("gender", "Male");
        putParam.put("phone", 12365489756L);

        given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("id", 90)
                .body(putParam).
                when().put("/api/spartans/{id}").
                then().assertThat().statusCode(204)
                .log().all();
    }

    @Test
    public void patchTest() {
        Map<String, Object> patchParam = new HashMap<>();
        patchParam.put("gender", "Female");

        given().contentType(ContentType.JSON)
                .pathParam("id", 90)
                .body(patchParam).
                when().patch("/api/spartans/{id}").
                then().log().all().assertThat().statusCode(204);

    }

    @Test
    public void deleteTest() {
        Random ran = new Random();
        int idToDelete = ran.nextInt(200) + 1;

        given() .contentType(ContentType.JSON)
                .pathParam("id", idToDelete).
        when()  .delete("/api/spartans/{id}").
        then()  .log().all().statusCode(204);


    }

}










