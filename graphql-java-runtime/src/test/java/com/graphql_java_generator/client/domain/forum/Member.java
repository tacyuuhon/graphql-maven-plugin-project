package com.graphql_java_generator.client.domain.forum;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.graphql_java_generator.GraphQLField;
import com.graphql_java_generator.annotation.GraphQLInputParameters;
import com.graphql_java_generator.annotation.GraphQLInputType;
import com.graphql_java_generator.annotation.GraphQLNonScalar;
import com.graphql_java_generator.annotation.GraphQLObjectType;
import com.graphql_java_generator.annotation.GraphQLScalar;

import java.util.Date;

/**
 * @author generated by graphql-java-generator
 * @see <a href="https://github.com/graphql-java-generator/graphql-java-generator">https://github.com/graphql-java-generator/graphql-java-generator</a>
 */
@GraphQLObjectType("Member")
public class Member  {

	@JsonProperty("id")
	@GraphQLScalar( fieldName = "id", graphQLTypeSimpleName = "ID", javaClass = String.class)
	String id;


	@GraphQLInputParameters(names = {"uppercase"}, types = {"Boolean"})
	@JsonProperty("name")
	@GraphQLScalar( fieldName = "name", graphQLTypeSimpleName = "String", javaClass = String.class)
	String name;


	@JsonProperty("alias")
	@GraphQLScalar( fieldName = "alias", graphQLTypeSimpleName = "String", javaClass = String.class)
	String alias;


	@JsonProperty("email")
	@GraphQLScalar( fieldName = "email", graphQLTypeSimpleName = "String", javaClass = String.class)
	String email;


	@JsonProperty("type")
	@GraphQLScalar( fieldName = "type", graphQLTypeSimpleName = "MemberType", javaClass = MemberType.class)
	MemberType type;


	@JsonProperty("__typename")
	@GraphQLScalar( fieldName = "__typename", graphQLTypeSimpleName = "String", javaClass = String.class)
	String __typename;



	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setType(MemberType type) {
		this.type = type;
	}

	public MemberType getType() {
		return type;
	}

	public void set__typename(String __typename) {
		this.__typename = __typename;
	}

	public String get__typename() {
		return __typename;
	}

    public String toString() {
        return "Member {"
				+ "id: " + id
				+ ", "
				+ "name: " + name
				+ ", "
				+ "alias: " + alias
				+ ", "
				+ "email: " + email
				+ ", "
				+ "type: " + type
				+ ", "
				+ "__typename: " + __typename
        		+ "}";
    }

    /**
	 * Enum of field names
	 */
	 public static enum Field implements GraphQLField {
		Id("id"),
		Name("name"),
		Alias("alias"),
		Email("email"),
		Type("type"),
		__typename("__typename");

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
		private String id;
		private String name;
		private String alias;
		private String email;
		private MemberType type;


		public Builder withId(String id) {
			this.id = id;
			return this;
		}
		public Builder withName(String name) {
			this.name = name;
			return this;
		}
		public Builder withAlias(String alias) {
			this.alias = alias;
			return this;
		}
		public Builder withEmail(String email) {
			this.email = email;
			return this;
		}
		public Builder withType(MemberType type) {
			this.type = type;
			return this;
		}

		public Member build() {
			Member object = new Member();
			object.setId(id);
			object.setName(name);
			object.setAlias(alias);
			object.setEmail(email);
			object.setType(type);
			object.set__typename("Member");
			return object;
		}
	}
}
