#android-http
Performing basic http requests in Android requires at least a handlful of code.
This is annoying when almost every component initiates some network activity.

##Installation
To use this library your Android project must be configured to use the JCenter or Maven Central repositories.

Add the following to your package dependencies and sync gradle.
```
compile 'org.unfoldingword.tools:http:1.0.0'
```

##Usage

```
GetRequest request = GetRequest.newInstance(someurl);
String response = request.submit();
int responseCode = request.getResponseCode();
```

Or if you need authentication
```
GetRequest request = GetRequest.newInstance(someurl);
request.setAuthentication(username, password);
// or using a token: request.setAuthentication(sometoken);
String response = request.submit();
int responseCode = request.getResponseCode();
```