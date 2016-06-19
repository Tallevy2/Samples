# reporting-sdk-sample
Sample project with usage examples of Perfecto's Reporting SDK.

The project uses the canonical [TodoMVC](http://todomvc.com/) as the application under test. 

Detailed instructions on running the examples can be found by framework:

- [TestNG sample](testng-sample)
- [JUnit sample](junit-sample)
- [Main sample](main-sample)

# Running locally during test authoring
Test authoring phase is performed using a local FirefoxDriver instance. During this phase the reporting client outputs its messages to the command line.

The choice of WebDriver to create is controlled by the _is-ocal-driver_ environment variable, whose default value is <code>true</code>.

In order to run the tests using a remote Selenium grid and Perfecto's Reporting solution set the variable's value to <code>false</code>

# Navigating to the generated report
A link to the generated Perfecto report can be retrieved in code:
```java
System.out.println("Report URL - " + reportiumClient.getReportUrl());
```

# Jenkins integration

## Passing job details to Reporting


## Adding a direct link to the report to the build page
You can add a dynamically generated link to the report for a given Jenkins job by using the [GRoovy Postbuild Plugin](https://wiki.jenkins-ci.org/display/JENKINS/Groovy+Postbuild+Plugin).
 
The script should include the following code:

```groovy
jobName = manager.build.getProject().getName()
buildNumber = manager.build.getNumber()
 
summary = manager.createSummary("graph.gif")
summary.appendText("<a href=\"https://reporting.perfectomobile.com/?TENANTID=10000001&jobName=${jobName}&jobNumber=${buildNumber}\">Perfecto Test Report</a>", false)
```


