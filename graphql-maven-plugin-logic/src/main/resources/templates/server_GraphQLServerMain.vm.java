package ${packageUtilName};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.graphql_java_generator.GraphqlUtils;

/**
 * @author generated by graphql-java-generator
 * @see <a href="https://github.com/graphql-java-generator/graphql-java-generator">https://github.com/graphql-java-generator/graphql-java-generator</a>
 */
@SpringBootApplication(scanBasePackages = { "${pluginConfiguration.packageName}", "com.graphql_java_generator" })
@EnableConfigurationProperties
public class GraphQLServerMain#if(${pluginConfiguration.packaging}=="war") extends SpringBootServletInitializer#end {

#if($packaging=="war")
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GraphQLServerMain.class);
	}
#end

	public static void main(String[] args) {
		SpringApplication.run(GraphQLServerMain.class, args);
	}

}
