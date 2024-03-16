FROM httpd:2.4-alpine

COPY docker/httpd/httpd-default.conf /usr/local/apache2/conf/extra/
COPY docker/httpd/httpd.conf /usr/local/apache2/conf/
# COPY solawi-management-web-app/docker/httpd/htaccess_file /usr/local/apache2/htdocs/.htaccess
RUN chmod 644 /usr/local/apache2/conf/extra/httpd-default.conf
RUN chmod 644 /usr/local/apache2/conf/httpd.conf

COPY ./build/distributions/ /usr/local/apache2/htdocs/