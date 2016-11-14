package me.micro.bbs.category;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * CategoryForm
 *
 * Created by microacup on 2016/11/14.
 */
@Getter
@Setter
public class CategoryForm {

    @NotBlank
    private String code;

    @NotBlank
    private String title;

}
