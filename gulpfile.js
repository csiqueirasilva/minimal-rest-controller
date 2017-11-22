var gulp = require('gulp');
var debug = require('gulp-debug');
var order = require('gulp-order');
var concat = require('gulp-concat');
var concatVendor = require('gulp-concat-vendor');
var uglify = require('gulp-uglify-es').default;
var minifyCss = require('gulp-minify-css');
var minifyJs = require('gulp-minify');
var mainBowerFiles = require('main-bower-files');
var inject = require('gulp-inject');
var runSequence = require('gulp-run-sequence');
var gzip = require('gulp-gzip');
var clone = require('gulp-clone');
var series = require('stream-series');
var merge = require('merge-stream');
var streamqueue = require('streamqueue');
var babel = require('gulp-babel');
var clean = require('gulp-clean');
var flatten = require('gulp-flatten');

var outputDir = 'dist';

var vendorJs;
var vendorCss;

var rootPath = 'src/main/resources/static/';

var bowerJsFiles = ['**/*.js'];
var bowerCssFiles = ['**/*.css'];

var jsFiles = ['js/lib/*.js', 'js/main.js', 'js/*.js', 'js/service/*.js', 'js/module/*.js', 'js/directive/*.js', 'js/controller/**/*.js'];
var cssFiles = ['css/**/*.css', 'css/*.css', 'main.css'];

var bowerFilesConfig = {
	paths: {
		bowerDirectory: 'node_modules',
		bowerJson: 'package.json'
	},
	debugging: true,
	overrides: {
		"bootstrap": {
			main: ['dist/js/bootstrap.js', 'dist/css/bootstrap.css']
		},
		"angular-ui-bootstrap": {
			main: ['dist/ui-bootstrap-tpls.js', 'dist/ui-bootstrap-csp.css']
		}
	}
};

function getPath(txt) {
	return rootPath + txt;
}

gulp.task('lib-js-files', function () {
	for (var i = 0; i < jsFiles.length; i++) {
		jsFiles[i] = getPath(jsFiles[i]);
	}

	vendorJs = 
		streamqueue({objectMode: true}, 
			gulp.src(mainBowerFiles(bowerJsFiles, bowerFilesConfig), {base: 'node_modules'}),
			gulp.src(jsFiles)
		)
		.pipe(debug())
		.pipe(concatVendor('clinica.min.js'))
//		.pipe(uglify({mangle: false}).on('error', console.log))
		.pipe(gulp.dest(getPath(outputDir)));
});

gulp.task('lib-css-files', function () {
	for (var i = 0; i < cssFiles.length; i++) {
		cssFiles[i] = getPath(cssFiles[i]);
	}

	vendorCss = 
		streamqueue({objectMode: true},
			gulp.src(mainBowerFiles(bowerCssFiles, bowerFilesConfig), {base: 'node_modules'}),
			gulp.src(cssFiles)
		)
		.pipe(debug())
		.pipe(concatVendor('clinica.min.css'))
//		.pipe(minifyCss())
		.pipe(gulp.dest(getPath(outputDir)));
});

gulp.task('copyFonts', function () {
	gulp.src('node_modules/**/*.{ttf,woff,woff2,eof,svg}')
		.pipe(debug())
		.pipe(flatten())
		.pipe(gulp.dest(getPath('fonts')));
});