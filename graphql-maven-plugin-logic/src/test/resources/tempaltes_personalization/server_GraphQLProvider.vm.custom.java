/** This template is custom **/
package ${pluginConfiguration.packageName};

/**
 * @author generated by graphql-java-generator
 * @see <a href="https://github.com/graphql-java-generator/graphql-java-generator">https://github.com/graphql-java-generator/graphql-java-generator</a>
 */
import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import graphql.GraphQL;
import graphql.TypeResolutionEnvironment;
import graphql.language.FieldDefinition;
import graphql.language.InterfaceTypeDefinition;
import graphql.language.ObjectTypeDefinition;
import graphql.language.Type;
import graphql.language.TypeName;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.TypeResolver;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

#foreach($import in $imports)
import $import;
#end

/**
 * This class is responsible for providing all the GraphQL Beans to the graphql-java Spring Boot integration.
 * <BR/><BR/>
 * Based on the https://www.graphql-java.com/tutorials/getting-started-with-spring-boot/ tutorial
 * 
 * @author EtienneSF
 */
@Component
public class GraphQLProvider {

	/** The logger for this instance */
	protected Logger logger = LoggerFactory.getLogger(GraphQLProvider.class);

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	GraphQLDataFetchers graphQLDataFetchers;

	private GraphQL graphQL;

	@Bean
	public GraphQL graphQL() {
		return graphQL;
	}

	/**
	 * The {@link DataLoaderRegistry} will be autowired by Spring in the GraphQL Java Spring Boot framework. It will
	 * then be wired for each request execution, as specified in this page:
	 * <A HREF="https://www.graphql-java.com/documentation/master/batching/">graphql-java batching</A>
	 * 
	 * @return
	 */
	@Bean
	public DataLoaderRegistry dataLoaderRegistry() {
		logger.debug("Creating DataLoader registry");
		DataLoaderRegistry registry = new DataLoaderRegistry();
		DataLoader<Object, Object> dl;

		for (BatchLoaderDelegate<?, ?> batchLoaderDelegate : applicationContext
				.getBeansOfType(BatchLoaderDelegate.class).values()) {
			// Let's check that we didn't already register a BatchLoaderDelegate with this name
			if ((dl = registry.getDataLoader(batchLoaderDelegate.getName())) != null) {
				throw new RuntimeException(
						"Only one BatchLoaderDelegate with a given name is allows, but two have been found: "
								+ dl.getClass().getName() + " and " + batchLoaderDelegate.getClass().getName());
			}
			// Ok, let's register this new one.
			registry.register(batchLoaderDelegate.getName(), DataLoader.newDataLoader(batchLoaderDelegate));
		}

		return registry;
	}
	
	@PostConstruct
	public void init() throws IOException {
		Resource res;
		StringBuffer sdl = new StringBuffer();
#foreach ($schemaFile in $schemaFiles)
		res = new ClassPathResource("/${schemaFile}");
		Reader reader = new InputStreamReader(res.getInputStream(), Charset.forName("UTF8"));
		sdl.append(FileCopyUtils.copyToString(reader));
#end
		this.graphQL = GraphQL.newGraphQL(buildSchema(sdl.toString())).build();
	}

	private GraphQLSchema buildSchema(String sdl) {
		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);

		RuntimeWiring runtimeWiring = buildWiring();
		SchemaGenerator schemaGenerator = new SchemaGenerator();
		return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
	}

	private RuntimeWiring buildWiring() {
		// Thanks to this thread :
		// https:// stackoverflow.com/questions/54251935/graphql-no-resolver-definied-for-interface-union-java
		//
		// Also see sample :
		// https://github.com/graphql-java/graphql-java-examples/tree/master/http-example
		return RuntimeWiring.newRuntimeWiring()
#foreach ($customScalar in $customScalars)
#if (${customScalar.graphQLScalarTypeClass})
			.scalar(new ${customScalar.graphQLScalarTypeClass}())
#elseif (${customScalar.graphQLScalarTypeStaticField})
			.scalar(${customScalar.graphQLScalarTypeStaticField})
#elseif (${customScalar.graphQLScalarTypeGetter})
			.scalar(${customScalar.graphQLScalarTypeGetter})
#else
		.scalar(): ${customScalar.javaName} : you must define one of graphQLScalarTypeClass, graphQLScalarTypeStaticField or graphQLScalarTypeGetter (in the POM parameters for CustomScalars)
#end
#end
#foreach ($dataFetchersDelegate in $dataFetchersDelegates)
			// Data fetchers for ${dataFetchersDelegate.name}
#foreach ($dataFetcher in $dataFetchersDelegate.dataFetchers)
			.type(newTypeWiring("${dataFetcher.field.owningType.javaName}").dataFetcher("${dataFetcher.field.javaName}", graphQLDataFetchers.${dataFetchersDelegate.camelCaseName}${dataFetcher.pascalCaseName}()))
#if ($dataFetcher.field.owningType.class.simpleName == "InterfaceType")
			.type(newTypeWiring("${dataFetcher.field.owningType.concreteClassSimpleName}").dataFetcher("${dataFetcher.field.javaName}", graphQLDataFetchers.${dataFetchersDelegate.camelCaseName}${dataFetcher.pascalCaseName}()))
#end
#end
#end
#if ($interfaces.size() > 0)
			//
			// Let's link the interface types to the concrete types
#end
#foreach ($interface in $interfaces)
			.type("${interface.javaName}", typeWiring -> typeWiring.typeResolver(get${interface.javaName}Resolver()))
#end
#if ($unions.size() > 0)
			//
			// Let's link the interface types to the concrete types
#end
#foreach ($union in $unions)
			.type("${union.javaName}", typeWiring -> typeWiring.typeResolver(get${union.javaName}Resolver()))
#end
			.build();
	}

#foreach ($interface in $interfaces)
	private TypeResolver get${interface.javaName}Resolver() {
		return new TypeResolver() {
			@Override
			public GraphQLObjectType getType(TypeResolutionEnvironment env) {
				Object javaObject = env.getObject();
				String ret = null;

#foreach ($implementingType in ${interface.implementingTypes})
				if (javaObject instanceof ${implementingType.javaName}) {
					ret = "${implementingType.javaName}";
				} else
#end
				{
					throw new RuntimeException("Can't resolve javaObject " + javaObject.getClass().getName());
				}
				logger.trace("Resolved type for javaObject {} is {}", javaObject.getClass().getName());
				return env.getSchema().getObjectType(ret);
			}
		};
	}

#end
#foreach ($union in $unions)
private TypeResolver get${union.javaName}Resolver() {
	return new TypeResolver() {
		@Override
		public GraphQLObjectType getType(TypeResolutionEnvironment env) {
			Object javaObject = env.getObject();
			String ret = null;

#foreach ($implementingType in ${union.implementingTypes})
			if (javaObject instanceof ${implementingType.javaName}) {
				ret = "${implementingType.javaName}";
			} else
#end
			{
				throw new RuntimeException("Can't resolve javaObject " + javaObject.getClass().getName());
			}
			logger.trace("Resolved type for javaObject {} is {}", javaObject.getClass().getName());
			return env.getSchema().getObjectType(ret);
		}
	};
}

#end
}
