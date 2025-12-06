FROM nginx:latest

COPY ./src /usr/share/nginx/html

EXPOSE ${PORT}

ENTRYPOINT ["nginx", "-g", "daemon off;"]
