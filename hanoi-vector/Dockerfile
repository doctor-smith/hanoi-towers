FROM ubuntu

RUN apt update
RUN apt install -y curl sudo sed

ARG BETTERSTACK_TOKEN
COPY /hanoi-vector/install-vector.sh /install-vector.sh
RUN chmod +x /install-vector.sh
RUN /install-vector.sh $BETTERSTACK_TOKEN
RUN sed -i 's|http://127.0.0.1/server-status?auto|http://hanoi_frontend/server-status?auto|g' /etc/vector/vector.yaml

CMD ["/usr/bin/vector"]


