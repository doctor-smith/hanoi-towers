config.devServer = {
  ...config.devServer, // Merge with other devServer settings
  /*
  headers: {
    "X-Content-Type-Options": "nosniff",
    "X-MyHeader": "empty"
  },
  */
   "historyApiFallback": true
   /*
   {
       rewrites: [
         { from: "$/hanoi-towers.js", to: 'hanoi-towers.js' },

       ],
     },
     */
};
