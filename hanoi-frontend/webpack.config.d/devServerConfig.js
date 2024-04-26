config.devServer = {
  ...config.devServer, // Merge with other devServer settings

  headers: {
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE, PATCH, OPTIONS",
      "Access-Control-Allow-Headers": "X-Requested-With, content-type, Authorization"
  //  "X-Content-Type-Options": "nosniff",
  //  "X-MyHeader": "empty"
  },
   "historyApiFallback": true
   /*
   {
       rewrites: [
         { from: "$/hanoi-towers.js", to: 'hanoi-towers.js' },

       ],
     },
     */
};
