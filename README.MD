Affiliate Referer Server
========================

A server for debugging the Referer [SIC] header in HTTP requests and for blanking referers. The server is implemented using Play Framework and Scala.

An example use case for blanking referers is to keep affiliate campaigns and landing pages secret from affiliate managers who might steal them to use for their own gain. 

Usage
=====

Examples for when running the server locally. A handy way to test these is using a service such as tinyurl.

##### Show Referer header:

Link to http://localhost:9000 from a web page, follow the link and you will be shown the contents of the Referer header.

##### Remove Referer header and redirect to the provided URL:

http://localhost:9000/filter?url=http://www.google.com

##### Show all headers:

Link to http://localhost:9000/meta from a web page, follow the link and you will be shown meta information, including the contents of all headers.
