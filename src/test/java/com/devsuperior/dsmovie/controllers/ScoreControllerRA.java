package com.devsuperior.dsmovie.controllers;

import static io.restassured.RestAssured.baseURI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsuperior.dsmovie.tests.TokenUtil;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.simple.JSONObject;

public class ScoreControllerRA {

	private String clientUsername, clientPassword, adminUsername, adminPassword;
	private String clientToken, adminToken;
	private Long existingMovieId, nonExistingMovieId;

	private Map<String, Object> postScoreInstance;

	@BeforeEach
	public void setUp() throws JSONException {

		baseURI = "http://localhost:8080";

		clientUsername = "alex@gmail.com";
		clientPassword = "123456";
		adminUsername = "maria@gmail.com";
		adminPassword = "123456";

		clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
		adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);

		existingMovieId = 1l;
		nonExistingMovieId = 200L;

		postScoreInstance = new HashMap<>();
		postScoreInstance.put("movieID", existingMovieId);
		postScoreInstance.put("score", 5.0);

	}

	@Test
	public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {

		postScoreInstance.put("movieId", nonExistingMovieId);

		JSONObject newScore = new JSONObject(postScoreInstance);

		given().header("Content_type", "application/json").header("Authorization", "Bearer " + clientToken)
				.body(newScore).contentType(ContentType.JSON).accept(ContentType.JSON).when().put("/scores").then()
				.statusCode(404)

		;

	}

	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() throws Exception {
	}

	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {
	}
}
