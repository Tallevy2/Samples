## ESPN Test - Selenium code sample

This code sample shows how to create a multiply tests sessions on multiply devices parallel. 
It includes testNG testing framework, Maven and Reportium. 

**TODO:**
- Import the project to Eclipse or Intellij as Maven project.
- Set your Perfecto lab user , password and host at testng.xml file.
- Set your device capabilities at testng.xml file , DO NOT CHANGE THE DEVICE LOCATION (The scenario works only for espn global site).
- Set your ESPN email and password (Register at www.espn.go.com).
- Run from testng.xml as TestNG test.
- Set the number of times the test should run by changing invocationCount parameter.

```java
	
    @Test(invocationCount = 1)
    public void testGroup(){
        test();
        test2();
    }
```
