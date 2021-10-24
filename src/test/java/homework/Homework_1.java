package homework;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static org.testng.Assert.*;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Homework_1 {

    @BeforeClass
    public void beforeclass(){
      baseURI=ConfigurationReader.get("hr_api_url");
    }
   @Test
    public void test1(){
//        Given accept type is Json
//        Path param value- US
//        When users sends request to /countries
//        Then status code is 200 - And Content - Type is Json
//        And country_id is US
//        And Country_name is United States of America
//        And Region_id is
       Response response = given().accept(ContentType.JSON).and().pathParam("country_id", "US").when().get("/countries/{country_id}");

       assertEquals(response.statusCode(),200);
       assertEquals(response.contentType(),"application/json");
       assertEquals(response.path("country_id"),"US");
       assertEquals(response.path("country_name"),"United States of America");

       int id = response.path("region_id");
       assertEquals(id,2);
   }
    @Test
   public void test2() {
    /*- Given accept type is Json
- Query param value - q={"department_id":80}
- When users sends request to /employees
- Then status code is 200
- And Content - Type is Json
- And all job_ids start with 'SA'
- And all department_ids are 80
- Count is 25 */

        Response response = given().accept(ContentType.JSON).and().queryParam("q", "{\"department_id\":80}")
                .and().queryParam("departments_id", 80).when().get("/employees");

        int count = response.path("count");
        assertEquals(count, 25);

    }

    @Test
    public void test3(){
        /*- Given accept type is Json
-Query param value q= region_id 3
- When users sends request to /countries
- Then status code is 200
- And all regions_id is 3
- And count is 6
- And hasMore is false
- And Country_name are;
Australia,China,India,Japan,Malaysia,Singapore*/

        Response response = given().accept(ContentType.JSON).and().queryParam("q", "{\"region_id\":3}")
                .when().get("/countries");

        JsonPath js = response.jsonPath();

        assertEquals(response.statusCode(),200);
        int count = response.path("count");
        List<Integer> regionIds= js.getList("items.region_id");
        for (Integer regionId : regionIds) {
            regionId=3;
        }
        boolean hasMore = js.getBoolean("hasMore");

        assertEquals(count,6);
        assertEquals(hasMore,false);


    }
}