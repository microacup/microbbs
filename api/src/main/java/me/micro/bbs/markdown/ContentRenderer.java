package me.micro.bbs.markdown;

import me.micro.bbs.user.User;
import me.micro.bbs.user.support.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ContentRenderer {
    private MarkdownService markdownService;

    @Autowired
    private UserRepository userRepository;

    @Value("${server.contextPath}")
    private String contextPath;

    @Autowired
    public ContentRenderer(@Qualifier("pegdown") MarkdownService markdownService) {
        this.markdownService = markdownService;
    }

    /**
     * 渲染HTML
     *
     * @param content
     * @return
     */
    public String render(final String content) {
        String markdownText = content;
        markdownText = replaceAt(markdownText);
        String html = markdownService.renderToHtml(markdownText);
        return renderCallouts(decode(html));
    }

    private String replaceAt(String markdownText) {
        Set<String> nickNames = getUserNames(markdownText);
        for (final String nickName : nickNames) {
            User user = userRepository.findByNick(nickName);
            markdownText = markdownText.replace('@' + nickName, "@<a href='" + contextPath
                    + "/users/" + user.getId() + "/profile'>" + nickName + "</a>");
        }
        return markdownText;
    }

    private String decode(String html) {
        Matcher matcher = Pattern.compile("<pre>!(.*)</pre>").matcher(html);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).replace('{', '<').replace('}', '>'));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private String renderCallouts(String html) {
        Pattern calloutPattern = Pattern.compile("\\[callout title=([^\\]]+)\\]([^\\[]*)\\[\\/callout\\]");
        Matcher matcher = calloutPattern.matcher(html);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb,
                    String.format("<div class=\"callout\">\n" + "<div class=\"callout-title\">%s</div>\n" + "%s\n"
                            + "</div>", matcher.group(1), matcher.group(2)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * Gets user names from the specified text.
     *
     * <p>
     * A user name is between &#64; and a punctuation, a blank or a line break (\n). For example, the specified text is
     * <pre>&#64;88250 It is a nice day. &#64;Vanessa, we are on the way.</pre> There are two user names in the text,
     * 88250 and Vanessa.
     * </p>
     *
     * @param text the specified text
     * @return user names, returns an empty set if not found
     * @throws ServiceException service exception
     */
    public static  Set<String> getUserNames(final String text) throws ServiceException {
        final Set<String> ret = new HashSet<>();
        int idx = text.indexOf('@');

        if (-1 == idx) {
            return ret;
        }

        String copy = text.trim();
        copy = copy.replaceAll("```.+?```",  " "); // pre 单行
        copy = copy.replaceAll("^```[\\s\\S]+?^```", " ");  // ``` 里面的是 pre 标签内容
        copy = copy.replaceAll("`[\\s\\S]+?`", " "); // 同一行中，`some code` 中内容也不该被解析
        copy = copy.replaceAll("/^    .*", " "); // 4个空格也是 pre 标签，在这里 . 不会匹配换行
        copy = copy.replaceAll("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?", " "); // email
        copy = copy.replaceAll("\\[@.+?\\]\\(\\/.+?\\)", " ");// 已经被 link 的 username
        String[] uNames = StringUtils.substringsBetween(copy, "@", " ");
        String tail = StringUtils.substringAfterLast(copy, "@");

        if (tail.contains(" ")) {
            tail = null;
        }

        if (null != tail) {
            if (null == uNames) {
                uNames = new String[1];
                uNames[0] = tail;
            } else {
                uNames = Arrays.copyOf(uNames, uNames.length + 1);
                uNames[uNames.length - 1] = tail;
            }
        }

        if (null == uNames) {
            return ret;
        }

        for (int i = 0; i < uNames.length; i++) {
            final String maybeUserName = uNames[i];
            ret.add(maybeUserName);
            copy = copy.replaceFirst("@" + maybeUserName, "");
            idx = copy.indexOf('@');
            if (-1 == idx) {
                return ret;
            }
        }

        return ret;
    }

    public static void main(String[] args) {
        String str = "abc@abc.com @小明 @张小黑  hahhahs";

        Set<String> userNames = getUserNames(str);

        System.out.println(userNames);
    }

}
