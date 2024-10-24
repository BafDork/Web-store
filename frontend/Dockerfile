# Dockerfile для фронтенда (Vue.js)

# Шаг 1: Сборка приложения Vue.js
FROM node:18 AS builder

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем package.json и package-lock.json для установки зависимостей
COPY package*.json ./

# Устанавливаем зависимости
RUN npm install

# Копируем остальной исходный код приложения
COPY . .

# Собираем фронтенд
RUN npm run build

# Шаг 2: Обслуживание собранного фронтенда с помощью Nginx
FROM nginx:alpine

# Копируем собранные файлы Vue.js в директорию Nginx для обслуживания
COPY --from=builder /app/dist /usr/share/nginx/html

# Копируем конфигурационный файл Nginx
COPY ./nginx.conf /etc/nginx/conf.d/default.conf

# Открываем стандартный порт Nginx
EXPOSE 80

# Команда для запуска Nginx
CMD ["nginx", "-g", "daemon off;"]
