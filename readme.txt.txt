mvn clean install



mvn tomcat7:run



http://localhost:8080/profiler/hello

REST Endpoint	HTTP Method	Description
/customers	GET	Returns the list of customers
/customers/{id}	GET	Returns customer detail for given customer {id}
/customers	POST	Creates new customer from the post data
/customers/{id}	PUT	Replace the details for given customer {id}
/customers/{id}	DELETE	Delete the customer for given customer {id}
