package me.micro.bbs.category.support;

import me.micro.bbs.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * CategoryRepository
 *
 * Created by microacup on 2016/11/3.
 */
@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCode(String code);
}
