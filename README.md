# url-shortener
Shortens links using Spring Boot  + ReactJS

# Installation

```
git clone git://github.com/ybutrameev/url-shortener.git
cd url-shortener
mvn clean install
```
You will also need [redis-server](https://github.com/antirez/redis) to use this project  

Then look up to your `target` folder to experience new born `lv-1.0-SNAPSHOT.jar`  
You can simply run `java -jar lv-1.0-SNAPSHOT.jar` at that point  
Web interface will be available at `localhost:8080`

# REST API endpoints
## shorten `POST`
`http://localhost:8080/api/shorten`

Consumes a link and optional custom id. Id's max size is 12 symbols. 
```json
{
  "url": "https://github.com",
  "customId": null
}
```
Returns:
```json
{
  "shortUrl": "http://localhost:8080/r/baTo"
}
```
## get `GET`
`http://localhost:8080/api/get/{id}`

Returns a long link consuming id as a path variable:
```json
{
  "longUrl": "http://github.com"
}
```
## count `GET`
`http://localhost:8080/api/count`

Returns a number of already shortened links:
```json
{
  "urlCount": "29381"
}
```
## Error case
API error format:
```json
{
  "timestamp": "2018-12-12T15:55:55.059+0000",
  "status": 400,
  "error": "Bad Request",
  "message": "Custom Id is already used",
  "path": "/api/shorten"
}
```

# Tests
This project uses JUnit4 to test REST API endpoints.  
Tests are located at `src/test/java`

# Front-end screenshot  
![Alt text](http://i68.tinypic.com/35ar987.png)  
Links counter is animated ^_^ (trust me)
