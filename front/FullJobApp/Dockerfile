FROM node:20 AS build
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build --output-path=dist --configuration=production
