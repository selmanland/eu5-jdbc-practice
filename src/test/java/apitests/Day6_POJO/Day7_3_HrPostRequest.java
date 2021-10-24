package apitests.Day6_POJO;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


public class Day7_3_HrPostRequest {
    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("hr_api_url");
    }

    @Test
    public void PostRegion1(){
        Day7_2_RegionPost regionPost = new Day7_2_RegionPost(10,"Cybertek Germany");

        given().log().all()
                .and()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .body(regionPost)
                .when().post("/regions/")
                .then().log().all()
                .statusCode(201)
                .body("region_id",is(10));






    }

}
