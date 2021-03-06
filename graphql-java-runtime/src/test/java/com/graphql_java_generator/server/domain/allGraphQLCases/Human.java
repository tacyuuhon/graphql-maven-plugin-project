/** Generated by the default template from graphql-java-generator */
package com.graphql_java_generator.server.domain.allGraphQLCases;

import com.graphql_java_generator.GraphQLField;
import com.graphql_java_generator.annotation.GraphQLInputParameters;
import com.graphql_java_generator.annotation.GraphQLNonScalar;
import com.graphql_java_generator.annotation.GraphQLObjectType;
import com.graphql_java_generator.annotation.GraphQLScalar;
import java.util.List;
import java.util.UUID;

/**
 * @author generated by graphql-java-generator
 * @see <a href="https://github.com/graphql-java-generator/graphql-java-generator">https://github.com/graphql-java-generator/graphql-java-generator</a>
 */
@GraphQLObjectType("Human")
public class Human implements Character, Commented, WithID, AnyCharacter {

	public Human(){
		// No action
	}

	@GraphQLScalar(fieldName = "id", graphQLTypeSimpleName = "ID",  javaClass = UUID.class)
	UUID id;


	@GraphQLScalar(fieldName = "name", graphQLTypeSimpleName = "String",  javaClass = String.class)
	String name;


	@GraphQLNonScalar(fieldName = "bestFriend", graphQLTypeSimpleName = "Character",  javaClass = Character.class)
	Character bestFriend;


	@GraphQLNonScalar(fieldName = "friends", graphQLTypeSimpleName = "Character",  javaClass = Character.class)
	List<Character> friends;


	@GraphQLScalar(fieldName = "nbComments", graphQLTypeSimpleName = "Int",  javaClass = Integer.class)
	Integer nbComments;


	@GraphQLScalar(fieldName = "comments", graphQLTypeSimpleName = "String",  javaClass = String.class)
	List<String> comments;


	@GraphQLScalar(fieldName = "appearsIn", graphQLTypeSimpleName = "Episode",  javaClass = Episode.class)
	List<Episode> appearsIn;


	@GraphQLScalar(fieldName = "homePlanet", graphQLTypeSimpleName = "String",  javaClass = String.class)
	String homePlanet;



	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setBestFriend(Character bestFriend) {
		this.bestFriend = bestFriend;
	}

	public Character getBestFriend() {
		return bestFriend;
	}

	public void setFriends(List<Character> friends) {
		this.friends = friends;
	}

	public List<Character> getFriends() {
		return friends;
	}

	public void setNbComments(Integer nbComments) {
		this.nbComments = nbComments;
	}

	public Integer getNbComments() {
		return nbComments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setAppearsIn(List<Episode> appearsIn) {
		this.appearsIn = appearsIn;
	}

	public List<Episode> getAppearsIn() {
		return appearsIn;
	}

	public void setHomePlanet(String homePlanet) {
		this.homePlanet = homePlanet;
	}

	public String getHomePlanet() {
		return homePlanet;
	}

    public String toString() {
        return "Human {"
				+ "id: " + id
				+ ", "
				+ "name: " + name
				+ ", "
				+ "bestFriend: " + bestFriend
				+ ", "
				+ "friends: " + friends
				+ ", "
				+ "nbComments: " + nbComments
				+ ", "
				+ "comments: " + comments
				+ ", "
				+ "appearsIn: " + appearsIn
				+ ", "
				+ "homePlanet: " + homePlanet
        		+ "}";
    }

    /**
	 * Enum of field names
	 */
	 public static enum Field implements GraphQLField {
		Id("id"),
		Name("name"),
		BestFriend("bestFriend"),
		Friends("friends"),
		NbComments("nbComments"),
		Comments("comments"),
		AppearsIn("appearsIn"),
		HomePlanet("homePlanet");

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
		private UUID id;
		private String name;
		private Character bestFriend;
		private List<Character> friends;
		private Integer nbComments;
		private List<String> comments;
		private List<Episode> appearsIn;
		private String homePlanet;


		public Builder withId(UUID id) {
			this.id = id;
			return this;
		}
		public Builder withName(String name) {
			this.name = name;
			return this;
		}
		public Builder withBestFriend(Character bestFriend) {
			this.bestFriend = bestFriend;
			return this;
		}
		public Builder withFriends(List<Character> friends) {
			this.friends = friends;
			return this;
		}
		public Builder withNbComments(Integer nbComments) {
			this.nbComments = nbComments;
			return this;
		}
		public Builder withComments(List<String> comments) {
			this.comments = comments;
			return this;
		}
		public Builder withAppearsIn(List<Episode> appearsIn) {
			this.appearsIn = appearsIn;
			return this;
		}
		public Builder withHomePlanet(String homePlanet) {
			this.homePlanet = homePlanet;
			return this;
		}

		public Human build() {
			Human _object = new Human();
			_object.setId(id);
			_object.setName(name);
			_object.setBestFriend(bestFriend);
			_object.setFriends(friends);
			_object.setNbComments(nbComments);
			_object.setComments(comments);
			_object.setAppearsIn(appearsIn);
			_object.setHomePlanet(homePlanet);
			return _object;
		}
	}
}
