'use strict';

// 上下文地址
var _ctx = $("meta[name='ctx']").attr("content");
var ms = {
    Urls: {
        posts: _ctx + '/api/admin/posts',
        replies:  _ctx + '/api/admin/posts/{postId}/replies',
        categories: _ctx + '/api/admin/categories',
        categories_edit: _ctx + '/api/admin/categories/{id}',
        tags: _ctx + '/api/admin/tags',
        tags_edit: _ctx + '/api/admin/tags/{id}'
    }
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                     格式化字符串，给字符串加上 format 函数                                         //
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * 扩展了 String 类型，给其添加格式化的功能，替换字符串中 {placeholder} 或者 {0}, {1} 等模式部分为参数中传入的字符串
 * 使用方法:
 *     'I can speak {language} since I was {age}'.format({language: 'Javascript', age: 10})
 *     'I can speak {0} since I was {1}'.format('Javascript', 10)
 * 输出都为:
 *     I can speak Javascript since I was 10
 *
 * @param replacements 用来替换 placeholder 的 JSON 对象或者数组
 * @return 格式化后的字符串
 */
String.prototype.format = function(replacements) {
    replacements = (typeof replacements === 'object') ? replacements : Array.prototype.slice.call(arguments, 0);
    return formatString(this, replacements);
};

/**
 * 替换字符串中 {placeholder} 或者 {0}, {1} 等模式部分为参数中传入的字符串
 * 使用方法:
 *     formatString('I can speak {language} since I was {age}', {language: 'Javascript', age: 10})
 *     formatString('I can speak {0} since I was {1}', 'Javascript', 10)
 * 输出都为:
 *     I can speak Javascript since I was 10
 *
 * @param str 带有 placeholder 的字符串
 * @param replacements 用来替换 placeholder 的 JSON 对象或者数组
 * @return 格式化后的字符串
 */
var formatString = function (str, replacements) {
    replacements = (typeof replacements === 'object') ? replacements : Array.prototype.slice.call(arguments, 1);

    return str.replace(/\{\{|\}\}|\{(\w+)\}/g, function(m, n) {
        if (m == '{{') { return '{'; }
        if (m == '}}') { return '}'; }
        return replacements[n];
    });
};


// 格式化时间
function timeFormatter(value) {
    if (!value) return '-';
    return moment(value).format('YYYY-MM-DD HH:mm');
}

function error(msg) {
    console.log(msg);
}