var cssmin = require('gulp-minify-css'),
    gulp = require('gulp'),
    uglify = require('gulp-uglify');

var paths = {
    js: ['js*/**'],
    css: {
        files: ['stylesheets/*.css']
    },
    images:{
        files: ['images*/**']
    },
    assets: ['lib*/**'],
    dest: '../resources/static/'
};

// concat and minify CSS files
gulp.task('minify-css', function () {
    return gulp.src(paths.css.files)
        //.pipe(cssmin({}))
        .pipe(gulp.dest(paths.dest + 'css'));
});

// copy images
gulp.task('copy-images', function () {
    return gulp.src(paths.images.files)
        .pipe(gulp.dest(paths.dest));
});

// copy assets
gulp.task('copy-assets', function () {
    return gulp.src(paths.assets)
        .pipe(gulp.dest(paths.dest));
});

gulp.task('copy-lib', function () {
    gulp.src('node_modules/ionicons*/**').pipe(gulp.dest(paths.dest + 'lib'));
    gulp.src('node_modules/font-awesome*/**').pipe(gulp.dest(paths.dest + 'lib'));
    gulp.src('node_modules/jquery/dist*/**').pipe(gulp.dest(paths.dest + 'lib/jquery'));
    gulp.src('node_modules/bootstrap/dist*/**').pipe(gulp.dest(paths.dest + 'lib/bootstrap'));
    gulp.src('node_modules/bootstrap-table/dist*/**').pipe(gulp.dest(paths.dest + 'lib/bootstrap-table'));
    return gulp.src('node_modules/admin-lte/dist*/**').pipe(gulp.dest(paths.dest + 'lib/admin-lte'));
});

gulp.task('compress', function() {
    return gulp.src(paths.js)
        .pipe(uglify())
        .pipe(gulp.dest(paths.dest));
});

//
//gulp.task('concatjs', function() {
//	  return gulp.src('public/dist/js/*.js')
//	    .pipe(concat('app.min.js'))
//	    .pipe(gulp.dest('public/javascripts/dist/'));
//});

gulp.task('build', ['minify-css', 'copy-images', 'copy-assets','copy-lib', 'compress'], function () {});