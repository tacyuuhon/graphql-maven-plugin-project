package org.allGraphQLCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.allGraphQLCases.client.AnotherMutationType;
import org.allGraphQLCases.client.Character;
import org.allGraphQLCases.client.Episode;
import org.allGraphQLCases.client.Human;
import org.allGraphQLCases.client.HumanInput;
import org.allGraphQLCases.client.MyQueryType;
import org.allGraphQLCases.client.util.AnotherMutationTypeExecutor;
import org.allGraphQLCases.client.util.GraphQLRequest;
import org.allGraphQLCases.client.util.MyQueryTypeExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.graphql_java_generator.exception.GraphQLRequestExecutionException;
import com.graphql_java_generator.exception.GraphQLRequestPreparationException;

@Execution(ExecutionMode.CONCURRENT)
class FullQueriesIT {

	ApplicationContext ctx;

	MyQueryTypeExecutor myQuery;
	AnotherMutationTypeExecutor mutationType;

	GraphQLRequest mutationWithDirectiveRequest;
	GraphQLRequest mutationWithoutDirectiveRequest;
	GraphQLRequest withDirectiveTwoParametersRequest;
	GraphQLRequest multipleQueriesRequest;

	@BeforeEach
	void setup() throws GraphQLRequestPreparationException {
		ctx = new AnnotationConfigApplicationContext(SpringTestConfig.class);
		myQuery = ctx.getBean(MyQueryTypeExecutor.class);
		assertNotNull(myQuery);
		mutationType = ctx.getBean(AnotherMutationTypeExecutor.class);
		assertNotNull(mutationType);

		// The response preparation should be somewhere in the application initialization code.
		mutationWithDirectiveRequest = mutationType.getGraphQLRequest(//
				"mutation{createHuman (human: &humanInput) @testDirective(value:&value, anotherValue:?anotherValue)   "//
						+ "{id name appearsIn friends {id name}}}"//
		);

		mutationWithoutDirectiveRequest = mutationType.getGraphQLRequest(//
				"mutation{createHuman (human: &humanInput) {id name appearsIn friends {id name}}}"//
		);

		withDirectiveTwoParametersRequest = mutationType.getGraphQLRequest(
				"query{directiveOnQuery (uppercase: false) @testDirective(value:&value, anotherValue:?anotherValue)}");

		multipleQueriesRequest = myQuery.getGraphQLRequest("{"//
				+ " directiveOnQuery (uppercase: false) @testDirective(value:&value, anotherValue:?anotherValue)"//
				+ " withOneOptionalParam {id name appearsIn friends {id name}}"//
				+ " withoutParameters {appearsIn @skip(if: &skipAppearsIn) name @skip(if: &skipName) }"//
				+ "}");

	}

	@Execution(ExecutionMode.CONCURRENT)
	@Test
	void noDirective() throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {

		// Go, go, go
		MyQueryType resp = myQuery.exec("{directiveOnQuery}"); // Direct queries should be used only for very
																// simple cases

		// Verifications
		assertNotNull(resp);
		List<String> ret = resp.getDirectiveOnQuery();
		assertNotNull(ret);
		assertEquals(0, ret.size());
	}

	@Execution(ExecutionMode.CONCURRENT)
	@Test
	void withDirectiveOneParameter() throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {

		// Go, go, go

		// Direct queries should be used only for very simple cases, but you can do what you want... :)
		MyQueryType resp = myQuery.exec("{directiveOnQuery  (uppercase: true) @testDirective(value:&value)}", //
				"value", "the value", "skip", Boolean.FALSE);

		// Verifications
		assertNotNull(resp);
		List<String> ret = resp.getDirectiveOnQuery();
		assertNotNull(ret);
		assertEquals(1, ret.size());
		//
		assertEquals("THE VALUE", ret.get(0));
	}

	@Execution(ExecutionMode.CONCURRENT)
	@Test
	void withDirectiveTwoParameters() throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {

		// Go, go, go
		MyQueryType resp = withDirectiveTwoParametersRequest.execQuery( //
				"value", "the value", "anotherValue", "the other value", "skip", Boolean.TRUE);

		// Verifications
		assertNotNull(resp);
		List<String> ret = resp.getDirectiveOnQuery();
		assertNotNull(ret);
		assertEquals(2, ret.size());
		//
		assertEquals("the value", ret.get(0));
		assertEquals("the other value", ret.get(1));
	}

	@Execution(ExecutionMode.CONCURRENT)
	@Test
	void mutation() throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		// Preparation
		HumanInput input = new HumanInput();
		input.setName("a new name");
		List<Episode> episodes = new ArrayList<>();
		episodes.add(Episode.JEDI);
		episodes.add(Episode.EMPIRE);
		episodes.add(Episode.NEWHOPE);
		input.setAppearsIn(episodes);

		////////////////////////////////////////////////////////////////////////////////////////////////
		// WITHOUT DIRECTIVE

		// Go, go, go
		AnotherMutationType resp = mutationWithoutDirectiveRequest.execMutation("humanInput", input);

		// Verifications
		assertNotNull(resp);
		Human ret = resp.getCreateHuman();
		assertNotNull(ret);
		assertEquals("a new name", ret.getName());

		////////////////////////////////////////////////////////////////////////////////////////////////
		// WITH DIRECTIVE

		// Go, go, go
		resp = mutationWithDirectiveRequest.execMutation( //
				"humanInput", input, //
				"value", "the mutation value", //
				"anotherValue", "the other mutation value");

		// Verifications
		assertNotNull(resp);
		ret = resp.getCreateHuman();
		assertNotNull(ret);
		assertEquals("the other mutation value", ret.getName());
	}

	/**
	 * Test of this multiple query request :
	 * 
	 * <PRE>
	 * {
	 * directiveOnQuery (uppercase: false) @testDirective(value:&value, anotherValue:?anotherValue)
	 * withOneOptionalParam {id name appearsIn friends {id name}}
	 * withoutParameters {appearsIn @skip(if: &skipAppearsIn) name @skip(if: &skipName) }
	 * }
	 * </PRE>
	 */
	@Execution(ExecutionMode.CONCURRENT)
	@Test
	void multipleQueriesResponse() throws GraphQLRequestExecutionException {
		/*
		 * { directiveOnQuery (uppercase: false) @testDirective(value:&value, anotherValue:?anotherValue)
		 * 
		 * withOneOptionalParam {id name appearsIn friends {id name}}
		 * 
		 * withoutParameters {appearsIn @skip(if: &skipAppearsIn) name @skip(if: &skipName) }}
		 */

		////////////////////////////////////////////////////////////////////////////////////////////
		// Let's skip appearsIn but not name

		// Go, go, go
		MyQueryType resp = multipleQueriesRequest.execQuery( //
				"value", "An expected returned string", //
				"skipAppearsIn", true, //
				"skipName", false);

		// Verification
		assertNotNull(resp);
		//
		List<String> directiveOnQuery = resp.getDirectiveOnQuery();
		assertEquals(1, directiveOnQuery.size());
		assertEquals("An expected returned string", directiveOnQuery.get(0));
		//
		Character withOneOptionalParam = resp.getWithOneOptionalParam();
		assertNotNull(withOneOptionalParam.getFriends());
		//
		List<Character> withoutParameters = resp.getWithoutParameters();
		assertNotNull(withoutParameters);
		assertTrue(withoutParameters.size() > 0);
		assertNull(withoutParameters.get(0).getAppearsIn());
		assertNotNull(withoutParameters.get(0).getName());

		////////////////////////////////////////////////////////////////////////////////////////////
		// Let's skip appearsIn but not name

		// Go, go, go
		resp = multipleQueriesRequest.execQuery( //
				"value", "An expected returned string", //
				"skipAppearsIn", false, //
				"skipName", true);

		// Verification
		withoutParameters = resp.getWithoutParameters();
		assertNotNull(withoutParameters);
		assertTrue(withoutParameters.size() > 0);
		assertNotNull(withoutParameters.get(0).getAppearsIn());
		assertNull(withoutParameters.get(0).getName());
	}
}
