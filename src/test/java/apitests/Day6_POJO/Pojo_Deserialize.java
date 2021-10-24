package apitests.Day6_POJO;

import com.google.gson.Gson;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class Pojo_Deserialize {


    @Test
    public void oneSpartanPojo(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .and().auth().basic("admin", "admin")
                .when().get("http://54.90.49.91:8000/api/spartans/{id}");

        assertEquals(response.statusCode(),200);

        //JSON to my custom class(POJO)
        //deserialization to POJO
        Spartan spartan15 = response.body().as(Spartan.class);

        System.out.println(spartan15.getId());
        System.out.println(spartan15.getName());
        System.out.println(spartan15.getGender());
        System.out.println(spartan15.getPhone());

        System.out.println(spartan15.toString());

    }

    @Test
    public void regionWithPojo() {

        Response response = when().get("http://54.90.49.91:1000/ords/hr/regions");

        assertEquals(response.statusCode(), 200);

        //Convert JSON response to Pojo,(Custom class)

        Regions regions = response.body().as(Regions.class);
        System.out.println("regions.getLimit() = " + regions.getLimit());

        System.out.println(regions.getCount());
        System.out.println(regions.getHasMore());

        List<Item> items = regions.getItems();
        System.out.println(items.size());
        System.out.println(items.get(3).getRegionName());
    }
    @Test
    public void linkWithPojo(){

        Response response = get("http://54.90.49.91:1000/ords/hr/regions");
        Link link = response.body().as(Link.class);

        System.out.println("link.getHref() = " + link.getHref());



    }
    @Test
    public void link_WithPojo(){
        Response response = when().get("http://54.90.49.91:1000/ords/hr/regions");
        Link_ link_ = response.body().as(Link_.class);
        System.out.println("link_.getHref() = " + link_.getHref());
    }


    @Test
    public void gson_example(){

        Gson gson = new Gson(); // this is just showing that what is happening back

        //JSON to Java collections/POJO --> De-serialization  // we use for deserialization as method

        String myJson = "{\n" +
                "    \"id\": 15,\n" +
                "    \"name\": \"Meta\",\n" +
                "    \"gender\": \"Female\",\n" +
                "    \"phone\": 1938695106\n" +
                "}";

        Spartan spartan = gson.fromJson(myJson, Spartan.class); // gson is a library  it make De-serialization (JSON to Java collections/POJO)
                                                          // and serializaition JAVA Collections/Pojo to JSON
        System.out.println("spartan.toString() = " + spartan.toString());
        Map<String,Object> map = gson.fromJson(myJson, Map.class);

        System.out.println(spartan.toString());

        Gson gson2 = new Gson();
        Spartan spartan2 = gson2.fromJson(myJson,Spartan.class);
        System.out.println("spartan2.toString() = " + spartan2.toString());
        Map<String,Object>map2 = gson2.fromJson(myJson,Map.class);
        System.out.println("map2 = " + map2);


        //-------------------SERIALIZATION----------------
        //JAVA Collections/Pojo to JSON
        Spartan spartanEU = new Spartan(200,"Mike","Male",123123123);
        String jsonSpartanEU = gson.toJson(spartanEU);
        System.out.println(jsonSpartanEU);

        Spartan spar = new Spartan(100,"Joe","Male",54545454L);
        String json = gson.toJson(spar);
        System.out.println("json = " + json);


    }

}