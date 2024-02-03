FROM node:14.18.1

WORKDIR /react

COPY public /react
COPY src /react/
COPY package.json /react/

RUN npm install

EXPOSE 3000

CMD ["npm", "start"]