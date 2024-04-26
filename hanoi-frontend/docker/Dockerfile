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
