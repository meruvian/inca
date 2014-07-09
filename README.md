Inca S2REST Plugins for Struts2
===

The Inca S2Rest Plugin enables REST architectural style to build on top of your Struts2 application by providing http method handling and serialization/deserialization feature to transform your action models into REST resource-model, such as json, xml, atom, etc. It also provides basic functionality of Struts2 framework such as action, result, interceptors, and exception mappings using annotation-based programming model to makes your code more elegant with XML-free configuration.

Features
---
* Request method (“POST”, “GET”, “PUT”, etc) handling to enable separation of action using `@Action` method-level annotation.
* Built-in resource transformer for JSON and XML.
* Extendable resource transformer, just implement ResourceTransformer interface and write your custom transformer.
* XML configuration overrides using annotation.
* Result handling using `ActionResult` for single result without `@Result` mapping.

Setup
---
This plugin can be installed by copying jar file into `/WEB-INF/lib/` directory of your application. Or if you’re using Maven just add dependency to your project’s POM file.
```xml
<dependency>
    <groupId>org.meruvian.inca.s2restplugins</groupId>
    <artifactId>inca-s2rest-plugin</artifactId>
    <version>1.0</version>
</dependency>
```

Writing Your First Action
---
Let’s start with very simple example. To define an action, you have to add `@Action` on top of your action class.
```java
@Action(name = "/hello")
public class HelloAction {
    public ActionResult execute() {
        return new ActionResult("/WEB-INF/view/hello.jsp");
    }
}
```

By default plugin will invoke `execute()` method if there is no method annotated by `@Action` are qualified with its request uri pattern. Allowed types of action method are `Result` (not recommended), `ActionResult` and `String`.
Now let’s make an action that handle result with different approach.

```java
@Action(name = "/hello")
@Results({ @Result(name = "success", location = "/WEB-INF/view/hello.jsp") })
public class HelloAction {
    public String execute() {
        return "success";
    }
}
```

Pretty easy, right? More about result handling will be explained in Result Mapping section.

Annotation Reference
---
There are number of annotations provided by the Inca S2Rest Plugin that are overrides the default configuration to map actions to request URIs.
### Defining an Action with `@Action`
`@Action` can be used both on class-level and method-level. Typically `name` property at class-level annotation equals to namespace attribute. While method-level annotation equals to action name with specific HTTP request method.
```java
@Action(name = "/profile")
public class ProfileAction {
    @Action(name = "/list", method = HttpMethod.GET)
    public Object findAll() {
        return new ActionResult("/WEB-INF/view/profile/list.vm").setType("velocity");
    }

    public String execute() {
        return null;
    }
}
```

In the example, the `@Action` on the class-level indicates that all action (method-level) in this action class are relatives to the `/profile` path. Although `/profile` are namespace, technically `execute()` still invoked when request for that URI appear. So if you don’t want to implement that action just return `null` to render a blank page. The `findAll()` combines both definition of path and method into one, so this method will handle for `GET /profile/list` request.

### Access Request Parameters with `@ActionParam`
> This is experimental feature! Since 1.0.2 this feature was deprecated and will be removed in the next major release.

The `@ActionParam` can be used to bind request parameter to a field. They will be injected with object that qualified with name in `ValueStack`. It also support RESTful url parameter pattern to be bound to the action parameters. Take a look at the code examples below.
```java
@Action(name = "/profile")
public class ProfileAction {
    //...
    
    @ActionParam("id")
    private String id;

    @Action(name = "/{id}", method = HttpMethod.GET)
    public ActionResult get() {
        profile = profileService.get(id);
        
        return new ActionResult("/profile").setType("redirect");
    }
    
    //...
}
```

### Result Mappings
Use `@Results` to define result for an action. The `@Results` can be specified at the class-level and method-level. Class-level definition means that the results are global and will be shared across all actions defined within the action class. Method-level definition means that the results apply only to the action method they are defined on.

```java
@Action(name = "/profile")
@Results({ @Result(name = "backToIndex", type = "redirect", location = "/index") })
public class ProfileAction {
    // ...

    @ActionParam("id")
    private String id;

    @Action(name = "/{id}", method = HttpMethod.GET)
    @Results({ @Result(name = "success", location = "/WEB-INF/view/details.jsp") })
    public String get() {
        profile = profileService.get(id);

        if (profile == null) {
            return "backToIndex";
        }
        
        return "success";
    }
    // ...
}
</pre>

== Interceptor Mappings ==
<pre name="java">
// ...

@Action(name = "/details", method = HttpMethod.GET)
@Interceptors({ @InterceptorRef(name = "defaultStack") })
public ActionResult get() {
    return new ActionResult("/WEB-INF/view/details.jsp");
}

// ...
```

### Exception Mappings
```java
// ...

@Results({ @Result(name = "backToIndex", type = "redirect", location = "/index") })
@ExceptionMappings({
    @ExceptionMapping(exception = "java.lang.Exception", result = "backToIndex") 
})
public ActionResult get() {
    return new ActionResult("/WEB-INF/view/details.jsp");
}

// ...
```

Use Wildcard Mapping to Access Request Parameter
---

By default, the Inca S2RestPlugin use Regex Pattern Matcher for its Pattern Matcher. The default pattern matcher can be changed by set the following constants:

```xml
<constant name="struts.patternMatcher" value="struts" />
```

Metadata Request Handling
---

The Inca S2RestPlugin provides metadata processor to transform your POJO into multiple content types. By default, the content types will be automatically transformed by the processor in accordance with URL extension. You can also set the plugins to transform the content type only if the Accept header matches with one of these processors.
```xml
<constant name="struts.inca.handleRequestByHeader" value="true" />
```

### Extension-based request handling
`TODO`

### Http Headers-based request handling

`TODO`

Define Custom Metadata Processors
---
`TODO`

Configuration Reference`
---
`TODO`
