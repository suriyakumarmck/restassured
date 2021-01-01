package common;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class RestAssuredHelper {

    private Logger log = MyLogger.log;
    private RequestSpecBuilder builder = new RequestSpecBuilder();
    private String method;
    private String url;

    public RestAssuredHelper(String uri, String method) {
        //Formulate the API url
        builder.setBaseUri("https://jsonplaceholder.typicode.com/");
        this.url =  uri;
        this.method = method;
    }

    private ResponseOptions<Response> ExecuteAPI() throws URISyntaxException {


        RequestSpecification requestSpec = builder.build();
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.spec(requestSpec);
        if (this.method.equalsIgnoreCase(APIConstant.ApiMethods.POST)) {
            return request.post(new URI(this.url));
        } else if (this.method.equalsIgnoreCase(APIConstant.ApiMethods.DELETE)) {
            return request.delete(new URI(this.url));
        } else if (this.method.equalsIgnoreCase(APIConstant.ApiMethods.GET)) {
            return request.get(new URI(this.url));
        }
        return null;
    }

    public ResponseOptions<Response> ExecuteWithBody(Map<String, String> pathParams) throws URISyntaxException {
        builder.addPathParams(pathParams);
        return ExecuteAPI();
    }

    public ResponseOptions<Response> ExecuteWithQueryParams(Map<String, String> queryPath) throws URISyntaxException {
        builder.addQueryParams(queryPath);
        return ExecuteAPI();
    }

    public ResponseOptions<Response> ExecuteWithPathParams(Map<String, String> queryPath) throws URISyntaxException {
        builder.addPathParams(queryPath);
        return ExecuteAPI();
    }

}
