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

    @Query(value = "select t from Tag t where t.category.id = :categoryId")
        // nativeQuery时必须返回所有列§3.10.16.1 "Returning Managed Entities from Native Queries" of the JPA spec says:
        // @Query(value = "select * from M_TAG t where t.t_category = :categoryId", nativeQuery = true)
    List<Tag> findByCategoryId(@Param("categoryId") Long categoryId);

}
