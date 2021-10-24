package apitests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.baseURI;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
public class day4_1_hrApiWithPath {
    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("hr_api_url");
    }

    @Test
    public void getCountriesWithPath(){                       // q is filter colums but it is not working every webpage
        Response response = given().accept(ContentType.JSON)  // it works according to api document
                .and().queryParam("q", "{\"region_id\":2}")
                .when().get("/countries");

        assertEquals(response.statusCode(),200);

        //print limit value
        System.out.println("response.path(\"limit\") = " + response.path("limit"));

        System.out.println("response.path(\"hasMore\").toString() = " + response.path("hasMore").toString());

        String firstCountryId = response.path("items.country_id[0]");
        System.out.println("firstCountryId = " + firstCountryId);

        String secondCountryName = response.path("items.country_name[-1]");
        System.out.println("secondCountryName = " + secondCountryName);

        JsonPath js = response.jsonPath();
        List<Object> list = js.getList("items.country_name");
        System.out.println("list = " + list);

        System.out.println("js.getList(\"items\") = " + js.getMap("items[2]"));


        response.path("items.coutry_id");


        String link2 =response.path("items.links[2].href[0]");
        System.out.println("link2 = " + link2);

        //get all countries
        List<String> countryNames = response.path("items.country_name");
        System.out.println("countryNames = " + countryNames);

        //assert that all regions'ids are equal to 2
        List<Integer> regionIDs = response.path("items.region_id");

        for (int regionID : regionIDs) {
            System.out.println(regionID);
            assertEquals(regionID,2);

        }
        Object coutry_id = response.path("items.country_id[-1]");
        System.out.println("coutry_idm = " + coutry_id);

        List<Object> links = response.path("items.links");
        System.out.println("links.toString() = " + links.toString());


    }

    @Test
    public void test2(){
        Response response = given().accept(ContentType.JSON)                     // q is filter colums but it is not working every webpage
                .and().queryParam("q","{\"job_id\":\"IT_PROG\"}")               // it works according to api document
                .when().get("/employees");


        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        assertTrue(response.body().asString().contains("IT_PROG"));

        //makes sure we have only IT_PROG as a job_id
        List<String> jobIDs = response.path("items.job_id");

        for (String jobID : jobIDs) {
            System.out.println("jobID = " + jobID);
            assertEquals(jobID,"IT_PROG");
        }

    }



}