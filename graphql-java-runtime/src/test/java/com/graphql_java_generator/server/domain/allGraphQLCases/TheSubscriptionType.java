/** Generated by the default template from graphql-java-generator */
package com.graphql_java_generator.server.domain.allGraphQLCases;

import com.graphql_java_generator.GraphQLField;
import com.graphql_java_generator.annotation.GraphQLInputParameters;
import com.graphql_java_generator.annotation.GraphQLNonScalar;
import com.graphql_java_generator.annotation.GraphQLObjectType;

/**
 * @author generated by graphql-java-generator
 * @see <a href="https://github.com/graphql-java-generator/graphql-java-generator">https://github.com/graphql-java-generator/graphql-java-generator</a>
 */
@GraphQLObjectType("TheSubscriptionType")
public class TheSubscriptionType  {

	public TheSubscriptionType(){
		// No action
	}

	@GraphQLNonScalar(fieldName = "subscribeNewHumanForEpisode", graphQLTypeSimpleName = "Human",  javaClass = Human.class)
	Human subscribeNewHumanForEpisode;



	public void setSubscribeNewHumanForEpisode(Human subscribeNewHumanForEpisode) {
		this.subscribeNewHumanForEpisode = subscribeNewHumanForEpisode;
	}

	public Human getSubscribeNewHumanForEpisode() {
		return subscribeNewHumanForEpisode;
	}

    public String toString() {
        return "TheSubscriptionType {"
				+ "subscribeNewHumanForEpisode: " + subscribeNewHumanForEpisode
        		+ "}";
    }

    /**
	 * Enum of field names
	 */
	 public static enum Field implements GraphQLField {
		SubscribeNewHumanForEpisode("subscribeNewHumanForEpisode");

		private String fieldName;

		Field(String fieldName) {
			this.fieldName = fieldName;
		}

		public String getFieldName() {
			return fieldName;
		}

		public Class<?> getGraphQLType() {
			return this.getClass().getDeclaringClass();
		}

	}

	public static Builder builder() {
			return new Builder();
		}



	/**
	 * Builder
	 */
	public static class Builder {
		private Human subscribeNewHumanForEpisode;


		public Builder withSubscribeNewHumanForEpisode(Human subscribeNewHumanForEpisode) {
			this.subscribeNewHumanForEpisode = subscribeNewHumanForEpisode;
			return this;
		}

		public TheSubscriptionType build() {
			TheSubscriptionType _object = new TheSubscriptionType();
			_object.setSubscribeNewHumanForEpisode(subscribeNewHumanForEpisode);
			return _object;
		}
	}
}
