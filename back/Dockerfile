FROM php:8.0.2-fpm

RUN apt-get update && apt-get install -y
RUN docker-php-ext-install pdo pdo_mysql
RUN curl -sS https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin --filename=composer

COPY . .
RUN composer install
WORKDIR /var/www/html
EXPOSE 8000
