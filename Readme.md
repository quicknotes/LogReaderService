## LogReader Service

Service can retrieve logs from "\var\log" directory in reverse chronological order with the following parameters:

- "filename" - the file name [required]
- "searchword" - the word to filter for in every line
- "limit" - the maximum number of lines to return, default is 100. Set as 0 if all content is intended. Warning: service might return 500 if all content is requested for a large file.

## Server dependencies:

- Spring boot frame work with *Maven*. You can follow the official website to install spring boot [link](https://docs.spring.io/spring-boot/installing.html)


## How to build the service:
In the source directory, run
- `./mvnw package` to build the service
- `./mvnw test` to run unit-tests
- `./mvnw spring-boot:run` to start the service, service will be started on localhost:8080.


## How to test the service using curl
Some example commands are show below:
#### show last 100(default) lines from /var/log/install.log file
`curl -sS 'http://localhost:8080/getlog?filename=install.log'`

#### show last 100(default) lines from /var/log/install.log file containing the word 'install'
`curl -sS 'http://localhost:8080/getlog?filename=install.log&searchword=install'`

#### show last 5(default) lines from /var/log/install.log file containing the word 'install'
`curl -sS 'http://localhost:8080/getlog?limit=5&filename=install.log&searchword=install'`

#### show all lines from /var/log/install.log file containing the word 'install'
`curl -sS 'http://localhost:8080/getlog?limit=0&filename=install.log&searchword=install'`



