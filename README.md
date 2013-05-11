# Misha Framework

Misha Framework is a light & fast MVC framework for the Servlet based web application.

Structure of the framework is simple and very understandable. 

## Features
* Using a [_Intercepting Filter pattern_](http://www.oracle.com/technetwork/java/interceptingfilter-142169.html) .
* Minimal Template Engine
* Simple routing logic like Ruby on Rails.
* Useful but minimal helpers are available: Logging, Sanityzing, …etc 
* Browser testing (Selenium+Firefox) codes bundled. You can start a test even if you don't have no knowledge for testing.
* Easy to debug. Because it's minimal so you can follow the codes line by line. 

## Prerequisites

* Tomcat 6.x    
* **Eclipse**
    * Web Tools Platform (WTP)
    * Sysdeo Eclipse Tomcat Launcher plugin
* External-Libraries
	* jstl.jar	* standard.jar	* log4j-1.2.9.jar	* selenium-java-2.32.0.jar	* selenium-server-standalone-2.32.0.jar	* testng-6.8.jar

## Installation

1. Clone or donwload .zip and extract it.
2. Open **Eclipse** and import this.
3. Have fun ;)


## Documentation

### Routing (routes.xml)

CONCEPT: `An URL <==> Controller#action`

There is a following routing table, you can easily to convert it.

| Base URL                       | Pattern (path) | Controller        | Action    |
| ------------------------------ | -------------- | ----------------- | --------- |
| http://foo.bar/MishaFramework/ | welcome/a      | WelcomeController | alpha     |
| http://foo.bar/MishaFramework/ | welcome/b      | WelcomeController | beta      |
| http://foo.bar/MishaFramework/ | welcome/*.8    | WelcomeController | index     |

__routes.xml(example):__

```xml
<?xml version="1.0" encoding="UTF-8"?>
<routeinfo controllerBasePath="jp.recruit.bootcamp.controller" urlPrefix="/MishaFramework">
    <route pattern="/welcome/a" controller="WelcomeController" action="alpha" />
    <route pattern="/welcome/b" controller="WelcomeController" action="beta" />
    <route pattern="/welcome/*.*" controller="WelcomeController" action="index" />
</routeinfo>
```


### Logging (DebugHelper)

* Log4J based logging. It will be enabled only if you use `DebugHelper#*`.
* `DebugHelper.out` is almost equivalent to `System.out.println` but it's more useful. 
* DebugHelper output it contetns to _console_ and _file_ (`/WEB-INF/log/app.log`).
	* Daily log rotation is enabled by default.
	* You can configure it from `/WEB-INF/conf/mf-log4j.properties`.
		* You can use `${Log4jSavePath.MF}` variable in `mf-log4j.properties`
		* `${Log4jSavePath.MF} = /path/to/WEB-INF/log/`


### View

View generation process is in three parts: _Layout-Template_, _Layout-Body_ and _Controller#action_ .

#### 1. Write Layout-template
__/WEB-INF/view/layout/basic-layout.jsp(example):__

```html
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${_page.title}</title>
</head>
<body>
<c:import url="${_page.jsp}" />
</body>
</html>
```

#### 2. Setting up the Controller#action

In Controller#action, you can use following variables no need for preparation.   

| Variable     | Type(Interface)     | Scope                   |
| ------------ | -------------       | ------------            |
| page         | setPageAttribute    | Action + View           |
| errors       | LinkedList          | Action + View + Session |
| requestURI   | String              | Action                  |
| session      | HttpSession         | Action                  |
| out          | PrintWriter         | Action                  |  

Especially `page` is very important attribute because it has 2 roles.
The first, `page` can control _layout-template_ and  _layout-body_ or _page title_ using setPageAttribute.
Second, if you set it in action using like this : `setPageAttribute("foo", OBJECT)`, then you can use it in EL like `${_page.foo}` in _layout-body_.

__WelcomeController#index(example):__

The following code uses all variables above, it'll be helpful.

```java
public String index(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    DebugHelper.out("Hello, This is Welcome Controller.");

    setPageAttribute(LAYOUT, "basic-layout.jsp");
    setPageAttribute(TITLE, "WELCOME!!");
    setPageAttribute(JSP, "welcome.jsp");        

	// Output JSON Example:
    // setPageAttribute(LAYOUT, null);
	// response.setContentType("application/json; charset=UTF-8;");
	// out.print(String.format("{\"request-uri\": \"%s\", \"key\":\"日本語\"}", requestURI));

    setPageAttribute("foo", "abc");

    errors.add("ERR1");
    errors.add("ERR2");

    return null;
}
```

#### 3. Write Layout-Body

__/WEB-INF/view/welcome.jsp(example):__

Following code will be extract `${_page.jsp}` in _layout-template_ .   
No need for define scriptlet like `<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>`. They are defined in `/WEB-INF/views/common.jsp`, you can edit it by yourself.

```jsp
<c:forEach var="error" items="${_errors}">
  ${error}<br>
</c:forEach>

<pre>
PAGE = <c:out value="${_page.jsp}"/>
foo  = <c:out value="${_page.foo}"/>
foo  = <c:out value="${_page.bar}"/>
</pre>
```




## Planned Features

* Analyze and Sanityzing request parameters and make them available in layout.
* Audit filter for CSRF. And a tag for it using taglibs.
* Minimal O/R mapper

