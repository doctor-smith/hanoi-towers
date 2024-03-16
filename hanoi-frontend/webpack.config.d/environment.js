var webpack = require("webpack");
var path = require('path');
var dotenv = require('dotenv').config({ path: path.resolve(__dirname, '../../../../hanoi-frontend/.env') });
var definePlugin = new webpack.DefinePlugin(
   {
      "PROCESS_ENV": JSON.stringify(dotenv.parsed)
   }
)

config.plugins.push(definePlugin)