Feature: ValidateUserPostComments
  To perform the validations for the comments for the post made by a specific user

  Scenario: To perform the validations for the comments for the post made by a specific user
    Given users from "/users" with given username "Delphine"
    And Use the details fetched to make a search for the "/posts" written by the user
    And For each post, fetch the comments from path "/comments"
    Then validate if the emails in comment section are in the proper format
