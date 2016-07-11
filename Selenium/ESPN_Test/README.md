## ESPN Test - Selenium code sample

This code sample shows how to create a multiply tests sessions on multiply devices parallel.
It includes testNG testing framework, Maven and Reportium. 

**TODO:**
- Import the project to Eclipse or Intellij as Maven project.
- Set your Perfecto lab user , password and host at testng.xml file.
- Set your device capabilities at testng.xml file, DO NOT CHANGE THE DEVICE LOCATION (The scenario designed for espn global site).
- Set your ESPN email and password (Register at www.espn.go.com).
- Execute from testng.xml as TestNG test.

You may want to change the number of times the test will run, do this by changing the following test anotation parameter : 
invocationCount at Test_Main.java file.

```java
	
    @Test(invocationCount = 1)
    public void testGroup(){
        test();
        test2();
    }
```
