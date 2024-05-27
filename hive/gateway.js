const { ApolloServer } = require('@apollo/server');
const { ApolloGateway } = require('@apollo/gateway');
const { startStandaloneServer } = require('@apollo/server/standalone');

const gateway = new ApolloGateway({
  serviceList: [
    { name: 'authService', url: 'http://localhost:9011/graphql' },
    { name: 'productService', url: 'http://localhost:9012/graphql' },
    { name: 'orderService', url: 'http://localhost:9013/graphql' },
  ],
});

const server = new ApolloServer({
  gateway,
  subscriptions: false,
});

const { url } = startStandaloneServer(server, {
    listen: { port: 4000 },
});
  
console.log(`🚀  Server ready at: ${url}`);  