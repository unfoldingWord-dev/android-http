#android-http
Performing basic http requests in Android requires at least a handlful of code.
This is annoying when almost every component initiates some network activity.

##Installation
To use this library your Android project must be configured to use the JCenter or Maven Central repositories.

Add the following to your package dependencies and sync gradle.
```
compile 'org.unfoldingword.tools:http:2.0.0'
```

##Usage

```
GetRequest request = new GetRequest(someurl);
String response = request.read();
int responseCode = request.getResponseCode();
```

we now support downloading to a file

```
GetRequest request = new GetRequest(someurl);
request.download(somefile);
int responseCode = request.getResponseCode();
```

###Exceptions
If an exception occurs durring your request you can still retrieve the status code and message.
```
GetRequest request = new GetRequest(someurl);
try {
  request.download(somefile);
} catch (IOException e) {
  int responseCode = request.getResponseCode();
  String errorMessage = request.getErrorMessage();
}
```

###Authentication
```
GetRequest request = new GetRequest(someurl);
request.setAuthentication(username, password);
// or using a token: request.setAuthentication(sometoken);
String response = request.read();
int responseCode = request.getResponseCode();
```



##Extending
Requests are built around an abstract `Request` class. So you can easily create your own requests if the ones provided are incomplete or insufficient.