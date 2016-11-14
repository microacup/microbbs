package me.micro.bbs.category.support;

import me.micro.bbs.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CategoryRepository
 *
 * Created by microacup on 2016/11/3.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    


}
