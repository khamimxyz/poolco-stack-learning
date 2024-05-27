const path = require('path');

const baseDir = 'C:/Workspace/slash/github/poolco-stack-learning/simple-ecommerce';

module.exports = {
  token: 'c845fb71b2d12a9ea3d94ed396b4d75a',
  service: {
    name: 'simple-ecommerce-hive',
  },
  projects: {
    federatedService: {
      federate: true,
      services: [
        {
          name: 'authService',
          schema: path.join(baseDir, 'auth/src/main/resources/schema/auth.graphql'),
          url: 'http://localhost:9011/graphql',
        },
        {
          name: 'productService',
          schema: path.join(baseDir, 'product/src/main/resources/schema/product.graphql'),
          url: 'http://localhost:9012/graphql',
        },
        {
          name: 'orderService',
          schema: path.join(baseDir, 'order/src/main/resources/schema/order.graphql'),
          url: 'http://localhost:9013/graphql',
        },
      ],
    },
  },
};
