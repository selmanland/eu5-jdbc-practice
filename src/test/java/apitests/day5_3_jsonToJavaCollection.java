package apitests ;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class day5_3_jsonToJavaCollection { //day5.3

    @BeforeClass
    public void beforeclass() {
        baseURI = ConfigurationReader.get("spartan_api_url");
    }

    @Test
    public void SpartanToMap() {

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 15)
                .when().get("/api/spartans/{id}");

        assertEquals(response.statusCode(), 200);

        //we will convert json response to java map
        Map<String, Object> jsonDataMap = response.body().as(Map.class);
        System.out.println("jsonDataMap = " + jsonDataMap);

        String name = (String) jsonDataMap.get("name");
        assertEquals(name, "Meta");

        BigDecimal phone = new BigDecimal(String.valueOf(jsonDataMap.get("phone")));

        System.out.println("phone = " + phone);
    }

    @Test
    public void allSpartansToListOfMap() {

        Response response = given().accept(ContentType.JSON)
                .when().get("/api/spartans");

        assertEquals(response.statusCode(), 200);

        //we need to de-serialize JSON response to List of Maps
        List<Map<String, Object>> allSpartanList = response.body().as(List.class);

        System.out.println(allSpartanList);

        //print second spartan first name
        System.out.println(allSpartanList.get(1).get("name"));
        //save spartan 3 in a map
        Map<String, Object> spartan3 = allSpartanList.get(2);

        System.out.println(spartan3);
    }
    @Test
    public void test9(){
        Response response = given().accept(ContentType.JSON).when().get("/api/spartans");

        List <Map<String,Object>> as = response.body().as(List.class);
        System.out.println("as = " + as);

        System.out.println("as.get(0).get(\"name\") = " + as.get(0).get("name"));
        System.out.println("as.get(3).get(\"name\") = " + as.get(3).get("name"));

        Map<String,Object> map1=as.get(0);
        System.out.println("map1 = " + map1);
    }

    @Test
    public void regionToMap() {

        Response response = when().get("http://54.90.49.91:1000/ords/hr/regions");

        assertEquals(response.statusCode(), 200);

        //we de-serialize JSON response to Map
        Map<String, Object> regionMap = response.body().as(Map.class);
        System.out.println("regionMap = " + regionMap);

        System.out.println(regionMap.get("count"));

        System.out.println(regionMap.get("hasMore"));

        System.out.println(regionMap.get("items"));

        List<Map<String, Object>> itemsList = (List<Map<String, Object>>) regionMap.get("items");

        //print first region name
        System.out.println(itemsList.get(0).get("region_name"));

    }
    @Test
    public void allItems(){

        Response response = when().get("http://54.90.49.91:1000/ords/hr/regions");
        JsonPath js = response.jsonPath();
        // json has a map
        Map<String,Object> firstMap= response.body().as(Map.class);
        //Map<String,Object> firstMap2= js.getMap("response");
        System.out.println("firsMap = " + firstMap);
      //  System.out.println("firstMap2 = " + firstMap2);

        //first element of the map has list map
        List<Map<String,Object>> itemList = (List<Map<String, Object>>) firstMap.get("items");
        System.out.println("itemList = " + itemList);

        //first map of the list map
        Map<String, Object> itemListFirsMap= itemList.get(0);
        System.out.println("itemListFirsMap = " + itemListFirsMap);

        System.out.println("itemListFirsMap.get(\"links\") = " + itemListFirsMap.get("links"));
        // ?????????
        List<Map<String,Object>> linkList = (List<Map<String, Object>>) itemListFirsMap.get("links");
        System.out.println("linkList.toString() = " + linkList);
        System.out.println("linkList.size() = " + linkList.size());

        Map<String, Object> stringObjectMap = linkList.get(0);
        System.out.println("stringObjectMap.get(\"rel\") = " + stringObjectMap.get("rel"));


    }


}














