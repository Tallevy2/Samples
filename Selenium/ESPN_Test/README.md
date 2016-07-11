## ESPN Test - Selenium code sample

This code sample shows how to create a multiply tests sessions on multiply devices parallel. 
It includes testNG testing framework, Maven and Reportium. 

**TODO:**
- Import the project to Eclipse or Intellij as Maven project.
- Set your Perfecto lab user , password and host at testng.xml file.
- Set your device capabilities at testng.xml file , DO NOT CHANGE THE DEVICE LOCATION (The scenario works only for espn global site).
<<<<<<< HEAD
- Set your ESPN email and password (Register at www.espn.go.com).
- Set the number of times the test should run by changing invocationCount parameter.

```java
	
    @Test(invocationCount = 1)
    public void testGroup(){
        test();
        test2();
    }
```

=======
- Set your ESPN email and password at testng.xml file.
- Set the number of times you would like to run the scenario at Test_Main.java file.
>>>>>>> 327f12c51efdd00a3f14c5da2f8eaabdc800c459
- Run from testng.xml as TestNG test.


