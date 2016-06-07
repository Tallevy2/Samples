# reporting-sdk-sample
Sample project with usage examples of Perfecto's Reportium SDK.

The project uses the canonical [TodoMVC](http://todomvc.com/) as the application under test. 

Detailed instructions on running the examples can be found by framework:

- [TestNG sample](testng-sample/README.md)
- [JUnit sample](junit-sample/README.md)

# Running locally during test authoring
Test authoring phase is performed using a local FirefoxDriver instance. During this phase the reportium client outputs its messages to the command line.

The choice of WebDriver to create is controlled by the _is-ocal-driver_ environment variable, whose default value is <code>true</code>.

In order to run the tests using a remote Selenium grid and Perfecto's Reporting solution set the variable's value to <code>false</code>

# Reportium credentials
In order to use the actual Reportium client you need to provide the following environment variables:

- selenium-grid-url - Perfecto's web url, e.g. _https://mycompany.perfectomobile.com/nexperience/perfectomobile/wd/hub_
- selenium-grid-username - User name for accessing Perfecto's web
- selenium-grid-password - User password for accessing Perfecto's web
- reportium-username - User name for accessing Reportium
- reportium-password - User password for accessing Reportium
- reportium-tenant - Reportium tenant associated with the user

The final command line will look like this when executed from the project's root folder:

> mvn clean verify -f testng-sample/pom.xml -Psanity -Dselenium-grid-url=https://mycompany.perfectomobile.com/nexperience/perfectomobile/wd/hub -Dselenium-grid-username=myuser -Dselenium-grid-password=mypassword -Dreportium-username=myuser -Dreportium-password=mypassword -Dreportium-tenant=mytenant -Dis-local-driver=false

# Navigating to the generated report
A link to the generated Perfecto report can be retrieved in code. 
Details will be added later. 

# Jenkins integration
You can add a dynamically generated link to the report for a given Jenkins job by using the [GRoovy Postbuild Plugin](https://wiki.jenkins-ci.org/display/JENKINS/Groovy+Postbuild+Plugin).
 
The script should include the following code:

```groovy
jobName = manager.build.getProject().getName()
buildNumber = manager.build.getNumber()
 
summary = manager.createSummary("graph.gif")
summary.appendText("<a href=\"https://reporting.perfectomobile.com/?TENANTID=10000001&jobName=${jobName}&jobNumber=${buildNumber}\">Perfecto Test Report</a>", false)
```


