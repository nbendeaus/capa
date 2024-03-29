const webpack = require('webpack');
const CommonsChunkPlugin = require('webpack/lib/optimize/CommonsChunkPlugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const StringReplacePlugin = require('string-replace-webpack-plugin');
const AddAssetHtmlPlugin = require('add-asset-html-webpack-plugin');
const WebpackNotifierPlugin = require('webpack-notifier');
const MergeJsonWebpackPlugin = require("merge-jsons-webpack-plugin")
const path = require('path');

const ExtractTextPlugin = require('extract-text-webpack-plugin');

module.exports = function (options) {
    const DATAS = {
        VERSION: JSON.stringify(require("../package.json").version),
        DEBUG_INFO_ENABLED: options.env === 'dev'
    };
    return {
        entry: {
            'polyfills': './src/main/webapp/app/polyfills',
            'global': './src/main/webapp/content/scss/style.scss',
            'main': './src/main/webapp/app/app.main'
        },
        resolve: {
            extensions: ['.ts', '.js'],
            modules: ['node_modules']
        },
        module: {
            rules: [
                {
                    test: /\.html$/,
                    loader: 'html-loader',
                    options: {
                        minimize: true,
                        caseSensitive: true,
                        removeAttributeQuotes:false,
                        minifyJS:false,
                        minifyCSS:false
                    },
                    exclude: ['./src/main/webapp/index.html']
                },
                {
                    test: /\.scss$/,
                    loader: ExtractTextPlugin.extract({
                        fallback: 'style-loader',
                        loader: [
                            { loader: 'css-loader', query: { modules: true, sourceMaps: true } },
                            'postcss-loader'
                        ]
                    }),
                    exclude: /(style\.scss|vendor\.scss|global\.scss)/
                },
                {
                    test: /(style\.scss|vendor\.scss|global\.scss)/,
                    use: [
                        'style-loader',
                        { loader: 'css-loader', options: {modules: true, importLoaders: 1 } },
                        'sass-loader'
                    ],
                },
                {
                    test: /\.css$/,
                    use: [
                        'to-string-loader',
                        { loader: 'css-loader', options: { modules: true, importLoaders: 1 } }
                    ],
                    exclude: /(style\.css|vendor\.css|global\.css)/
                },
                {
                    test: /(style\.css|vendor\.css|global\.css)/,
                    use: [
                        'style-loader',
                        { loader: 'css-loader', options: { modules: true, importLoaders: 1 } }
                    ],
                },
                {
                    test: /\.(jpe?g|png|gif|svg|woff2?|ttf|eot)$/i,
                    loaders: ['file-loader?hash=sha512&digest=hex&name=[hash].[ext]']
                },
                {
                    test: /app.constants.ts$/,
                    loader: StringReplacePlugin.replace({
                        replacements: [{
                            pattern: /\/\* @toreplace (\w*?) \*\//ig,
                            replacement: function (match, p1, offset, string) {
                                return `_${p1} = ${DATAS[p1]};`;
                            }
                        }]
                    })
                }
            ]
        },
        plugins: [
            new CommonsChunkPlugin({
                names: ['manifest', 'polyfills'].reverse()
            }),
            new webpack.DllReferencePlugin({
                context: './',
                manifest: require(path.resolve('./target/www/vendor.json'))
            }),
            new CopyWebpackPlugin([
                { from: './node_modules/core-js/client/shim.min.js', to: 'core-js-shim.min.js' },
                { from: './node_modules/swagger-ui/dist', to: 'swagger-ui/dist' },
                { from: './src/main/webapp/swagger-ui/', to: 'swagger-ui' },
                { from: './src/main/webapp/favicon.ico', to: 'favicon.ico' },
                { from: './src/main/webapp/robots.txt', to: 'robots.txt' },
                { from: './src/main/webapp/i18n', to: 'i18n' }
            ]),
            new webpack.ProvidePlugin({
                $: "jquery",
                jQuery: "jquery"
            }),
            new MergeJsonWebpackPlugin({
                output: {
                    groupBy: [
                        { pattern: "./src/main/webapp/i18n/de/*.json", fileName: "./target/www/i18n/de/all.json" },
                        { pattern: "./src/main/webapp/i18n/en/*.json", fileName: "./target/www/i18n/en/all.json" }
                        // jhipster-needle-i18n-language-webpack - JHipster will add/remove languages in this array
                 ]
                }
            }),
            new HtmlWebpackPlugin({
                template: './src/main/webapp/index.html',
                chunksSortMode: 'dependency',
                inject: 'body'
            }),
            new AddAssetHtmlPlugin([
                { filepath: path.resolve('./target/www/vendor.dll.js'), includeSourcemap: false }
            ]),
            new StringReplacePlugin(),
            new WebpackNotifierPlugin({
                title: 'Capa',
                contentImage: path.join(__dirname, 'capa_logo.png')
            }),
        ]
    };
};
