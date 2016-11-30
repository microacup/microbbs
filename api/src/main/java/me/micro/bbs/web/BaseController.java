package me.micro.bbs.web;

import org.springframework.ui.Model;

/**
 *
 *
 * Created by microacup on 2016/11/30.
 */
public abstract class BaseController {
    protected static final String MODE_NORMAL = "normal";
    protected static final String MODE_SIMPLE = "simple";

    /**
     * 注入运行模式
     *
     * @param model
     */
    protected void injectMode(Model model) {
        model.addAttribute("mode", getMode());
    }

    /**
     * 运行模式
     *
     * @return
     */
    protected abstract String getMode();
}
