FROM httpd:2.4-alpine

COPY hanoi-frontend/docker/httpd/httpd-default.conf /usr/local/apache2/conf/extra/httpd-default.conf
COPY hanoi-frontend/docker/httpd/httpd.conf /usr/local/apache2/conf/httpd.conf
# COPY solawi-management-web-app/docker/httpd/htaccess_file /usr/local/apache2/htdocs/.htaccess
RUN chmod 644 /usr/local/apache2/conf/extra/httpd-default.conf
RUN chmod 644 /usr/local/apache2/conf/httpd.conf

COPY hanoi-frontend/build/dist/js/productionExecutable/hanoi-frontend.js /usr/local/apache2/htdocs/hanoi-frontend.js
COPY hanoi-frontend/build/dist/js/productionExecutable/hanoi-frontend.js.map /usr/local/apache2/htdocs/hanoi-frontend.js.map
COPY hanoi-frontend/build/dist/js/productionExecutable/index.html /usr/local/apache2/htdocs/index.html
COPY hanoi-frontend/build/dist/js/productionExecutable/css/ /usr/local/apache2/htdocs/css/
COPY hanoi-frontend/build/dist/js/productionExecutable/js/ /usr/local/apache2/htdocs/js/
COPY hanoi-frontend/build/dist/js/productionExecutable/img/ /usr/local/apache2/htdocs/img/
