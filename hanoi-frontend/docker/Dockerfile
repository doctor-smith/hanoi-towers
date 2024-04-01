FROM httpd:2.4-alpine

# Copy apache config files
COPY hanoi-frontend/docker/httpd/httpd.conf /usr/local/apache2/conf/httpd.conf
COPY hanoi-frontend/docker/httpd/extra/httpd-default.conf /usr/local/apache2/conf/extra/httpd-default.conf
COPY hanoi-frontend/docker/httpd/extra/httpd-vhosts.conf /usr/local/apache2/conf/extra/httpd-vhosts.conf

# Copy htaccess file
# COPY hanoi-frontend/docker/httpd/htaccess_file /usr/local/apache2/htdocs/.htaccess

# Set access rights
RUN chmod 644 /usr/local/apache2/conf/extra/httpd-default.conf
RUN chmod 644 /usr/local/apache2/conf/extra/httpd-vhosts.conf
RUN chmod 644 /usr/local/apache2/conf/httpd.conf

# Copy website related stuff
COPY hanoi-frontend/build/dist/js/productionExecutable/ /usr/local/apache2/htdocs/hanoi
#COPY hanoi-frontend/build/dist/js/productionExecutable/hanoi-frontend.js /usr/local/apache2/htdocs/hanoi-frontend.js
#COPY hanoi-frontend/build/dist/js/productionExecutable/hanoi-frontend.js.map /usr/local/apache2/htdocs/hanoi-frontend.js.map
#COPY hanoi-frontend/build/dist/js/productionExecutable/index.html /usr/local/apache2/htdocs/index.html
#COPY hanoi-frontend/build/dist/js/productionExecutable/css/ /usr/local/apache2/htdocs/css/
#COPY hanoi-frontend/build/dist/js/productionExecutable/js/ /usr/local/apache2/htdocs/js/
#COPY hanoi-frontend/build/dist/js/productionExecutable/img/ /usr/local/apache2/htdocs/img/
