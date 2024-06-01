FROM node:18
LABEL org.opencontainers.image.source="https://github.com/KsaweryZietara/cargoflow"
RUN mkdir -p /app
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
EXPOSE 3000
CMD ["npm", "run", "dev", "--", "--host"]
