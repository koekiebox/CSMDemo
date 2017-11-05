---
![Junit logo](https://avatars3.githubusercontent.com/u/874086?s=400&v=4)
---

# Camel Spring Maven Backbase Demo (MSM Demo)

## Unit Test

Follow the steps below in order to run the test cases.

1. Open `com.backbase.csmdemo.ABaseTestCase` to configure the API endpoint.
> The default endpoint is `http://localhost:8080/csmdemo/api/`

2. Deploy the `csmdemo.war` inside a valid J2EE web application server.
> A local instance may be started using Maven Jetty Plugin. Command is;
> `mvn clean install package jetty:run`

**The validated release has been tested using Tomcat 7.**