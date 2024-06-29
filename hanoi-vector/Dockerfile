FROM ubuntu

RUN apt update
RUN apt install -y curl sudo sed

ARG BETTERSTACK_TOKEN
RUN curl -sSL https://logs.betterstack.com/setup-vector/apache/$BETTERSTACK_TOKEN -o /tmp/setup-vector.sh && bash /tmp/setup-vector.sh
RUN sed -i 's|http://127.0.0.1/server-status?auto|http://hanoi_frontend/server-status?auto|g' /etc/vector/vector.yaml

CMD ["/usr/bin/vector"]


