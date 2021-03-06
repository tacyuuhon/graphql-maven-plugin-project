# Introduction

This page is more an internal howto, to remind of the step to:
* Release the Maven plugin
* Release the Gradle plugin
* Publish the project web site, as publishing a site is really complex, from the maven-site-plugin, to the github configuration to use a specific domain, through the issue of the multi-module stuff.

And of course, if it can be useful for anyone to publish a site, then, it's nice! :)

# Before publishing:

* Check that the user has access on the owner of the git repository, as the last step works only if connected on githib with the owner of the repository.

* Check the TODO list: some done work may still be on the TODO list.

* Update the CHANGELOG.md:
    * Check the version for the updates being released
    * Check that all the done job is described 


# HowTo release the Maven Plugin

Execute to execute the release on GitHub:

```
mvn release:clean release:prepare
```

Take care to:
* Give the proper version for the release
* Let the default value for the tag (so that all tags look the same)
* Give __local-SNAPSHOT__ as the next version. This allows very easy comparison between the commits in git

Then, in order to publish the release toward the maven central repository:

```
mvn release:perform
```

Once this command succeeds, the maven plugin should be stored in the OSSRH repository, waiting to be replicated into the central repository. This can be checked by browsing into it: [https://repo1.maven.org/maven2/com/graphql-java-generator/graphql-maven-plugin/](https://repo1.maven.org/maven2/com/graphql-java-generator/graphql-maven-plugin/).

At the end of the process, the plugin should be available [https://mvnrepository.com/artifact/com.graphql-java-generator/graphql-maven-plugin](https://mvnrepository.com/artifact/com.graphql-java-generator/graphql-maven-plugin). This last step can be long (up to several hours).

# How to release the Gradle Plugin

1) You'll first have to change the plugin's version:
* Open the _graphql-gradle-plugin/common_conf.gradle_ file
* Change the project version to the plugin's version to publish. The __Maven plugin__ of this version __must be available__ in maven repository (either local or central)

2) Adapt the plugin code, if things have changed due to changed in the plugin-logic module

3) Copy the code and resources for the samples, from the maven project:

From the root of the Gradle project, execute:

```
gradlew copySamplesFromMavenPlugin
```

4) [not always] Adapt the dependencies version(see the _graphql-gradle-plugin/common_conf.gradle_ file)

5) [not always] Adapt the _build.gradle_ files, for instance if some plugin options have changed in the samples

6) Build the whole project 

From the root of the Gradle project, execute:

```
gradlew clean
```

Then

```
gradlew build
```

(the _gradlew clean build_ doesn't seem to work)

7) Create and push the _git_ tag

You can copy/paste what's changed in the tag comment

8) Publish the plugin

From the _graphql-gradle-plugin_ folder, execute:

```
gradlew publishPlugins
```


# HowTo publish the site


## Configuring the domain name

The standard way of publishing a web site on github, is to push the site on the gh-pages branch of the project. Then, check the settings of the project. At this stage, the project site is available at this URL: [https://graphql-java-generator.github.io/graphql-maven-plugin-project/](https://graphql-java-generator.github.io/graphql-maven-plugin-project/).
 
It's possible to define a custom domain in the settings.

On the DNS configuration for the graphql-java-generator.com domain, let's add this line:

```
graphql-maven-plugin-project 1800 IN CNAME graphql-java-generator.github.io.
```

And in the settings, define the Custom domain to be: graphql-maven-plugin-project.graphql-java-generator.com

Then, the project site is available at [https://graphql-maven-plugin-project.graphql-java-generator.com](graphql-maven-plugin-project.graphql-java-generator.com) with the site published on the gh-pages branch of the project.


## Generating the site

The use of [maven-site-plugin](https://maven.apache.org/plugins/maven-site-plugin/) is complex, but well described. 

Here are the steps to execute:

* cd target/checkout
    * This allows to go to the just performed release, and get all the code in the relevant version
* publish_site    which is a Windows command file, that wraps:
    * mvn antrun:run -Prelease 
    * git push

This script asks for the release version. But it seems that this entry is not taken into account. Only the project version is used.

Thanks to the _CNAME_ file being preserved, in the ant file, the custom domain configuration should not disappear... But it may be worth a check, to get to [the settings](https://github.com/graphql-java-generator/graphql-maven-plugin-project/settings) of the project, to restore the custom domain, which is graphql-maven-plugin-project.graphql-java-generator.com