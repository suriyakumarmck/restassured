package steps;

import common.MyLogger;
import components.CommentComponent;
import components.PostComponent;
import components.UserComponent;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import models.Comment;
import models.Post;
import models.User;
import org.apache.logging.log4j.Logger;
import utils.EmailUtils;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;

public class CommentTestSteps {

    public static ResponseOptions<Response> response;
    public static ArrayList commentsEmailID;
    private Logger log = MyLogger.log;

//    @Given("^users from \"([^\"]*)\" with given username \"([^\"]*)\"$")
//    public void usersFromWithGivenUsername(String url, String username) throws Throwable {
//        Map<String, String> queryParams = new HashMap<>();
//        queryParams.put("username", username);
//        RestAssuredHelper restAssuredHelper = new RestAssuredHelper(url, APIConstant.ApiMethods.GET);
//        response = restAssuredHelper.ExecuteWithQueryParams(queryParams);
//    }
//
//    @And("^For each post, fetch the comments from path \"([^\"]*)\"$")
//    public void forEachPostFetchTheCommentsFromPath(String url) throws Throwable {
//        var posts = response.getBody().as(Post[].class);
//        commentsEmailID = new ArrayList();
//        for(Post post: posts)
//        {
//            Map<String, String> queryParams = new HashMap<String, String>();
//            queryParams.put("postId", post.getId().toString());
//            RestAssuredHelper restAssuredHelper = new RestAssuredHelper(url, APIConstant.ApiMethods.GET);
//            response = restAssuredHelper.ExecuteWithQueryParams(queryParams);
//            var comments = response.getBody().as(Comment[].class);
//            for(Comment comment : comments)
//            {
//                commentsEmailID.add(comment.getEmail());
//            }
//        }
//    }
//
//    @And("^Use the details fetched to make a search for the \"([^\"]*)\" written by the user$")
//    public void useTheDetailsFetchedToMakeASearchForTheWrittenByTheUser(String url) throws Throwable {
//        var user = response.getBody().as(User[].class);
//        Map<String, String> queryParams = new HashMap<String, String>();
//        Integer userId = user[0].getId();
//        queryParams.put("userId", userId.toString());
//        RestAssuredHelper restAssuredHelper = new RestAssuredHelper(url, APIConstant.ApiMethods.GET);
//        response = restAssuredHelper.ExecuteWithQueryParams(queryParams);
//    }
//
//    @Then("^validate if the emails in comment section are in the proper format$")
//    public void validateIfTheEmailsInCommentSectionAreInTheProperFormat() {
//        assertThat("validate that comment section have valid email Ids", EmailUtils.isValidEmailAddress(commentsEmailID));
//    }

    @Given("^users from \"([^\"]*)\" with given username \"([^\"]*)\"$")
    public void usersFromWithGivenUsername(String url, String username) {
        response = UserComponent.getUser(username);
    }

    @And("^For each post, fetch the comments from path \"([^\"]*)\"$")
    public void forEachPostFetchTheCommentsFromPath(String url) {
        var posts = response.getBody().as(Post[].class);
        commentsEmailID = new ArrayList();
        for(Post post: posts)
        {
            response = CommentComponent.getCommentsOnPost(post.getId());
            var comments = response.getBody().as(Comment[].class);
            for(Comment comment : comments)
            {
                commentsEmailID.add(comment.getEmail());
            }
        }
    }

    @And("^Use the details fetched to make a search for the \"([^\"]*)\" written by the user$")
    public void useTheDetailsFetchedToMakeASearchForTheWrittenByTheUser(String url) {
        var user = response.getBody().as(User[].class);
        response = PostComponent.getPosts(user[0].getId());
    }

    @Then("^validate if the emails in comment section are in the proper format$")
    public void validateIfTheEmailsInCommentSectionAreInTheProperFormat() {
        log.info("validate that comment section have valid email Ids");
        assertThat("validate that comment section have valid email Ids", EmailUtils.isValidEmailAddress(commentsEmailID));
    }
}
