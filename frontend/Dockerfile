# Dockerfile for frontend (Vue.js)

# Step 1: Build the Vue.js application
FROM node:18 AS builder

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json to install dependencies
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application source code
COPY . .

# Build the frontend
RUN npm run build

# Step 2: Serve the built frontend using Nginx
FROM nginx:alpine

# Copy the built Vue.js files to Nginx's serving directory
COPY --from=builder /app/dist /usr/share/nginx/html

# Expose the default Nginx port
EXPOSE 80

# Command to run Nginx
CMD ["nginx", "-g", "daemon off;"]
