package stepDefinitions;

import Resources.Utils;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.testng.Assert;
import Reporting.Report;

import java.io.IOException;
import static io.restassured.RestAssured.given;

public class stepDefinitions extends Utils {

    RequestSpecification request;
    Response response;


    @When("User execute GET users API")
    public void user_execute_get_users_api() throws IOException {
         Report.createStep("User execute GET users API");
         Report.stepMessage.set("GET - https://gorest.co.in/public/v2/users");
         response = given().contentType("application/json")
                .when().get("https://gorest.co.in/public/v2/users")
                .thenReturn();

    }

    @Then("User verify response code {int}")
    public void userVerifyResponseCode ( int arg0){
        Report.createStep("User verify response code "+arg0);
        Report.stepMessage.set("Response - "+response.asPrettyString());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(200,statusCode,"Invalid Status Code");
    }

    @Then("User verify response contains {string}")
    public void userVerifyResponseContains ( String arg0){
        Report.createStep("User verify response contains "+arg0);
        String body = response.getBody().asString();
        if (!body.contains(arg0)){

            throw new AssertionError("Name is not found");
        };
    }

    @When("User execute GET Comments API")
    public void userExecuteGETCommentsAPI() {
        Report.createStep("User execute GET Comments API");
        Report.stepMessage.set("GET - https://gorest.co.in/public/v2/comments");
        response = given().contentType("application/json")
                .when().get("https://gorest.co.in/public/v2/comments")
                .thenReturn();
    }
}
