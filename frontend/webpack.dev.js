var path = require('path');
var webpack = require('webpack');

module.exports = {
  entry: './src/main.js',
  output: {
    path: path.resolve(__dirname, '../src/main/resources/static/assets'),
    publicPath: 'http://localhost:8000/assets/',
    filename: 'build.js'
  },
  module: {
    rules: [
      {
        test: /\.css$/,
        use: [
          'vue-style-loader',
          'css-loader'
        ],
      },
      {
        test: /\.scss$/,
        use: [
          'vue-style-loader',
          'css-loader',
          'sass-loader'
        ],
      },
      {
        test: /\.sass$/,
        use: [
          'vue-style-loader',
          'css-loader',
          'sass-loader?indentedSyntax'
        ],
      },
      {
        test: /\.vue$/,
        loader: 'vue-loader',
        options: {
          loaders: {
            // Since sass-loader (weirdly) has SCSS as its default parse mode, we map
            // the "scss" and "sass" values for the lang attribute to the right configs here.
            // other preprocessors should work out of the box, no loader config like this necessary.
            'scss': [
              'vue-style-loader',
              'css-loader',
              'sass-loader'
            ],
            'sass': [
              'vue-style-loader',
              'css-loader',
              'sass-loader?indentedSyntax'
            ]
          }
          // other vue-loader options go here
        }
      },
      {
        test: /\.js$/,
        loader: 'babel-loader',
        exclude: /node_modules/
      },
      {
        test: /\.(png|jpg|gif|svg)$/,
        loader: 'file-loader',
        options: {
          name: 'images/[name].[ext]?[hash]'
        }
      },
        {
            test: /\.(woff2?|eot|ttf|otf)$/,
            loader: 'url-loader',
            options: {
                name: '/fonts/[name].[ext]',
                //name: utils.assetsPath('fonts/[name].[hash:7].[ext]'),
                // workaround for vuejs-templates webpack issue 1266
                //publicPath: '/'
                //publicPath: path.resolve(__dirname, '../src/main/resources/static/assets/fonts/')
            }
        }
    ]
  },
  resolve: {
    alias: {
      'vue$': 'vue/dist/vue.esm.js'
    },
    extensions: ['*', '.js', '.vue', '.json']
  },
  devServer: {
      headers: { "Access-Control-Allow-Origin": "*" },
      historyApiFallback: true,
      noInfo: true,
      overlay: true,
      port: 8000,
      watchOptions: {
          poll: true
      }
  },
  performance: {
    hints: false
  },
  devtool: '#eval-source-map'
};
