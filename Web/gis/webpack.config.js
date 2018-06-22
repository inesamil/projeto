const path = require('path')

module.exports = {
  entry: './js/index.js',
  output: {
    filename: 'main.js',
    path: path.resolve(__dirname, 'dist')
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /(node_modules)/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['env', 'react', 'stage-3']
          }
        }
      }
    ]
  },
  devServer: {
    port: 9000,
    historyApiFallback: {
      disableDotRule: true
    }
  }
}
