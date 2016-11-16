package me.micro.bbs.tag;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * TagForm
 *
 * Created by microacup on 2016/11/16.
 */
@Getter
@Setter
public class TagForm {
    @NotEmpty
    private String code;

    @NotEmpty
    private String title;

    @NotNull
    private Long categoryId;
}
