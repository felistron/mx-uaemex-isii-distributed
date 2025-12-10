FROM nginx:alpine

# Copiar archivos del frontend
COPY ./src /usr/share/nginx/html

# Copiar configuración de Nginx
COPY nginx.conf /etc/nginx/templates/default.conf.template

# Exponer el puerto
EXPOSE ${PORT}

# Nginx con soporte para variables de entorno
CMD ["nginx", "-g", "daemon off;"]
