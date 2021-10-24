package apitests;

import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class day5_2_HamcrestMatchersApiTest { //day5.2
    /*
      given accept type is Json
      And path param id is 15
      When user sends a get request to spartans/{id}
      Then status code is 200
      And content type is Json
      And json data has following
          "id": 15,
          "name": "Meta",
          "gender": "Female",
          "phone": 1938695106
       */
    @Test
    public void OneSpartanWithHamcrest() {
        given().accept(ContentType.JSON)                                              // our
                .and().pathParam("id", 15).                                           // request
                when().get("http://54.90.49.91:8000/api/spartans/{id}")             // part====================================
                .then().statusCode(200)                                                      // response part
                .and().assertThat().contentType(equalTo("application/json"))   //
                .and().assertThat().body("id", equalTo(15),
                "name", equalTo("Meta"),
                "gender", equalTo("Female"),     //no need to use assertThat
                "phone", equalTo(1938695106));

    }

    @Test
    public void teacherData() {
        given().accept(ContentType.JSON)
                .and().pathParam("id", 10774) //8261
                .when().log().all().get("http://api.cybertektraining.com/teacher/{id}")  //request log().all()
                .then().assertThat().statusCode(200)
                .and().contentType(equalTo("application/json;charset=UTF-8"))
                .and().header("Content-Length", equalTo("295"))  //240
                .and().header("Connection", equalTo("Keep-Alive"))
                .and().header("Date", notNullValue())
                .and().assertThat().body("teachers.firstName[0]", equalTo("Florine"),
                "teachers.lastName[0]", equalTo("Hickle"),
                "teachers.gender[0]", equalTo("Male"))
                .log().all()  //response log().all()
        ;

    }

    @Test
    public void teachersWithDepartments() {

        given().accept(ContentType.JSON)
                .and().pathParam("name", "Computer")
                .when().log().all().get("http://api.cybertektraining.com/teacher/department/{name}")
                .then().statusCode(200).and()
                .contentType(equalTo("application/json;charset=UTF-8")).and()
                .body("teachers.firstName", hasItems("Alexander", "Marteen")); // whether "Alexander","Marteen" are inside the teachers.firstName


        given().accept(ContentType.JSON).and().pathParam("name", "Computer").when().log().all().get("http://api.cybertektraining.com/teacher/department/{name}")
                .then().assertThat().statusCode(200).and().contentType("application/json;charset=UTF-8").and().assertThat()
                .body("teachers.section", hasItems("IT"), "teachers.salary[0]", equalTo(3000)).and().assertThat()
                .body("teachers.batch[0]", equalTo(5)).log().all();
    }

    // and().header("Content-Length",equalTo("295"))                  not accept integer   ,  i do not know the reason
    // and().body("teachers.batch[0]",equalTo(5)).log().all();        accept integer


}