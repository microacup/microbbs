package me.micro.bbs.markdown;

/**
 * Abstraction representing a service capable of translating markdown to HTML.
 */
public interface MarkdownService {
    String renderToHtml(String markdownSource);
}
