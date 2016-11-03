package me.micro.bbs.tag.support;

import me.micro.bbs.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * TagRepository
 *
 * Created by microacup on 2016/11/3.
 */
public interface TagRepository extends JpaRepository<Tag, Long>{

    @Query("select t from Tag t where t.category.id = :categoryId")
    List<Tag> findByCategoryId(@Param("categoryId") Long categoryId);

}
