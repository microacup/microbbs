

// 功能开始
var _ctx = $("meta[name='ctx']").attr("content");


var ms = {
    Urls: {
        posts_related_top5: _ctx + '/api/posts/{postId}/related/top5',
        posts_now_top5: _ctx + '/api/posts/now/top5',
        replies: _ctx + '/api/posts/{postId}/replies',
        do_reply: _ctx + '/api/replies',
        tags_hot: _ctx + '/api/tags/hot',
        messages: _ctx + '/api/messages',
        profile: _ctx + '/api/profile',
        profile_id: _ctx + '/api/users/{userId}/profile'
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

$(function () {
    // 提取主色
    $.adaptiveBackground.run({
        parent: '.avatar-bg',
        normalizeTextColor: true
    });
});

function error(msg) {
    alert(msg);
    console.log(msg);
}

// 加载消息
var navBarApp = new Vue({
    el: '#nav-bar',
    data: {
        messages: []
    },
    created: function () {
        this.loadMessages();
    },
    methods: {
        loadMessages: function() {
            this.$http.get(ms.Urls.messages).then(function (response) {
                if(response.status == 200) {
                    this.messages =  response.body.data || [];
                } else {
                    error(response.body.msg);
                }
            }, function (response) {
                error('拉取消息失败');
            });
        },
        markReaded: function () {
            if(this.messages.length == 0) return;

            var msgIds = [];
            for(var i = this.messages.length -1;i>=0;i--) {
                if(!this.messages[i].hasRead) {
                    msgIds.push(this.messages[i].id);
                }
            }

            if(msgIds.length > 0) {
                var _csrf = $('#messages-form input[name="_csrf"]').val();
                var ids = '?ids=' + msgIds.join(',') + '&_csrf=' + _csrf;
                this.$http.put(ms.Urls.messages + ids).then(function (response) {
                    if(response.status == 200) {
                        var $badge = $('#badge-messages');
                        $badge.text("0");
                        $badge.hide();
                        navBarApp.messages.map(function(msg){msg.hasRead=true});
                    } else {
                        error(response.body.msg);
                    }
                }, function (response) {
                    console.log(response);
                    //error('标记消息失败');
                });

            }
        }
    }
});