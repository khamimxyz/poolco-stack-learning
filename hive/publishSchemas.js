const fs = require('fs');
const { execSync } = require('child_process');
const hiveConfig = require('./hive.config.js');

const services = [];

Object.values(hiveConfig.projects.federatedService.services).forEach(service => {
  services.push(service);
});

services.forEach(service => {
  try {
    execSync(`npx hive schema:publish --url ${service.url} --service ${service.name} --registry.accessToken ${hiveConfig.token} ${service.schema}`, { stdio: 'inherit' });
    console.log(`Published schema: ${service.schema}`);
  } catch (error) {
    console.error(`Error publishing schema: ${service.schema}`, error);
  }
});
