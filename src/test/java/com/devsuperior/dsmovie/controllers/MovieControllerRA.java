package com.devsuperior.dsmovie.controllers;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsuperior.dsmovie.tests.TokenUtil;

import io.restassured.http.ContentType;

public class MovieControllerRA {

	private String clientUsername, clientPassword, adminUsername, adminPassword;
	private String clientToken, adminToken, invalidToken;
	private Long existingMovieId, nonExistingMovieId;
	private String movieTitle;

	private Map<String, Object> postMovieInstance;

	@BeforeEach
	public void setUp() throws JSONException {

		baseURI = "http://localhost:8080";

		clientUsername = "alex@gmail.com";
		clientPassword = "123456";
		adminUsername = "maria@gmail.com";
		adminPassword = "123456";

		adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
		clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
		invalidToken = adminToken + "xpto";

		movieTitle = "Matrix";
		existingMovieId = 1L;
		nonExistingMovieId = 200L;

		postMovieInstance = new HashMap<>();
		postMovieInstance.put("title", "Creed: Nascido para Lutar");
		postMovieInstance.put("score", 9.0);
		postMovieInstance.put("count", 2);
		postMovieInstance.put("image", "https://www.themoviedb.org/creed");

	}

	@Test
	public void findAllShouldReturnOkWhenMovieNoArgumentsGiven() {

		given().get("/movies").then().statusCode(200).body("content.id[0]", is(1))
				.body("content.title[0]", equalTo("The Witcher")).body("content.score[0]", is(4.5F))
				.body("content.count[0]", is(2)).body("content.image[0]",
						equalTo("https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jBJWaqoSCiARWtfV0GlqHrcdidd.jpg"));

	}

	@Test
	public void findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty() {

		given().get("/movies?title={movieTitle}", movieTitle).then().statusCode(200).body("content.id[0]", is(4))
				.body("content.title[0]", equalTo("Matrix Resurrections")).body("content.score[0]", is(0.0F))
				.body("content.count[0]", is(0)).body("content.image[0]",
						equalTo("https://www.themoviedb.org/t/p/w533_and_h300_bestv2/hv7o3VgfsairBoQFAawgaQ4cR1m.jpg"))

		;

	}

	@Test
	public void findByIdShouldReturnMovieWhenIdExists() {

		given().get("/movies/{existingMovieId}", existingMovieId).then().statusCode(200).body("id", is(1))
				.body("title", equalTo("The Witcher")).body("score", is(4.5F)).body("count", is(2)).body("image",
						equalTo("https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jBJWaqoSCiARWtfV0GlqHrcdidd.jpg"));

	}

	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() {

		given().get("/movies/{existingMovieId}", nonExistingMovieId).then().statusCode(404);

	}

	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle() throws JSONException {

		postMovieInstance.put("title", null);

		JSONObject newMovie = new JSONObject(postMovieInstance);

		given().header("Content_type", "application/json").header("Authorization", "Bearer " + adminToken)
				.body(newMovie).contentType(ContentType.JSON).accept(ContentType.JSON).when().post("/movies").then()
				.statusCode(422).body("errors.message[0]", equalTo("Campo requerido"))

		;
	}

	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {

		JSONObject newMovie = new JSONObject(postMovieInstance);

		given().header("Content_type", "application/json").header("Authorization", "Bearer " + clientToken)
				.body(newMovie).contentType(ContentType.JSON).accept(ContentType.JSON).when().post("/movies").then()
				.statusCode(403)

		;

	}

	@Test
	public void insertShouldReturnUnauthorizedWhenInvalidToken() throws Exception {
		
		JSONObject newMovie = new JSONObject(postMovieInstance);

		given().header("Content_type", "application/json").header("Authorization", "Bearer " + invalidToken)
				.body(newMovie).contentType(ContentType.JSON).accept(ContentType.JSON).when().post("/movies").then()
				.statusCode(401);
		
	}
}
