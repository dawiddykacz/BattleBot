# Use official Node.js LTS image
FROM node:20-alpine

# Set working directory
WORKDIR /app

# Copy only package files first (for caching)
COPY package*.json ./

# Install dependencies inside container
RUN npm ci

# Copy the rest of the application code
COPY . .

CMD ["npm", "run", "start"]
