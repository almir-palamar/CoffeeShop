FROM php:8.2-cli

RUN apt-get update && apt-get install -y
RUN docker-php-ext-install pdo pdo_mysql pcntl
RUN curl -sS https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin --filename=composer

COPY . .
RUN composer install
WORKDIR /var/www/html
EXPOSE 8000
