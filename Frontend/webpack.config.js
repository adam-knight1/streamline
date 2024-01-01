const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyPlugin = require("copy-webpack-plugin");
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

module.exports = {
  optimization: {
    usedExports: true
  },
  entry: {
    examplePage: path.resolve(__dirname, 'src', 'pages', 'examplePage.js'),
    userPage: path.resolve(__dirname, 'src', 'pages', 'UserPage.js'),
    userLoginPage: path.resolve(__dirname, 'src', 'pages', 'loginPage.js'),
    userHomePage: path.resolve(__dirname, 'src', 'pages', 'userHomePage.js')
  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].js',
  },
  devServer: {
    https: false,
    port: 8080,
    open: true,
    proxy: [
      {
        context: [
          '/example',
          '/user',
          '/login'
          '/userHome'
        ],
        target: 'http://localhost:5001'
      }
    ]
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './src/index.html',
      filename: 'index.html',
      inject: false
    }),

    new HtmlWebpackPlugin({
                  template: './src/user.html',
                  filename: 'user.html',
                  inject: false
                }),
                new HtmlWebpackPlugin({
                                  template: './src/login.html',
                                  filename: 'login.html',
                                  inject: false
                                }),
      template: './src/user.html',
      filename: 'user.html',
      inject: false
    }),

    new HtmlWebpackPlugin({
      template: './src/userHome.html',
      filename: 'userHome.html',
      inject: false
    }),
    new CopyPlugin({
      patterns: [
        {
          from: path.resolve('src/css'),
          to: path.resolve("dist/css")
        }
      ]
    }),
    new CleanWebpackPlugin()
  ]
}
