var path = require('path');
var ROOT = path.resolve(__dirname, 'src/main/resources');
var SRC  = path.resolve(ROOT, 'jsx');
var DEST = path.resolve(ROOT, 'js');

module.exports = {
    entry: SRC,
    resolve: {
        extensions: ['', '.js', '.jsx' ]
    },
    output: {
        path: DEST,
        filename: 'app.js',
        publicPath: '/js/',
        library: "BookApp"
    },
    module: {
        loaders: [
            {
                test: /\.jsx?$/,
                loaders: ['babel'],
                include: SRC
            }
        ]
    }
};