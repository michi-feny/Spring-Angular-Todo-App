# Stage 1: Build Phase
FROM node:lts-alpine as builder
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
# Führen Sie den Produktions-Build durch
RUN npm run build -- --output-path=./dist

# Stage 2: Export der Artefakte
FROM scratch
# Speichern Sie die gebauten Dateien im Volume, das in docker-compose definiert ist
CMD cp -r /app/dist /angular-dist