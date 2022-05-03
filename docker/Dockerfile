FROM httpd:2.4-alpine

COPY docker/httpd/httpd-default.conf /usr/local/apache2/conf/extra/
COPY docker/httpd/httpd.conf /usr/local/apache2/conf/
# COPY solawi-management-web-app/docker/httpd/htaccess_file /usr/local/apache2/htdocs/.htaccess
RUN chmod 644 /usr/local/apache2/conf/extra/httpd-default.conf
RUN chmod 644 /usr/local/apache2/conf/httpd.conf

COPY ./build/distributions/ /usr/local/apache2/htdocs/
#COPY ./build/distributions/solawi-management-web-app.js /usr/local/apache2/htdocs/js/server.js
#COPY ./solawi-management-web-app/build/distributions/solawi-management-web-app.js.map /usr/local/apache2/htdocs/js/server.js.map
#COPY ./solawi-management-web-app/build/distributions/index.html /usr/local/apache2/htdocs/index.html
#COPY ./solawi-management-web-app/build/distributions/css/ /usr/local/apache2/htdocs/css/
#COPY ./solawi-management-web-app/build/distributions/js/ /usr/local/apache2/htdocs/js/
#COPY ./solawi-management-web-app/build/distributions/img/ /usr/local/apache2/htdocs/img/