package me.micro.bbs.message.support;

import me.micro.bbs.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 *
 * Created by microacup on 2016/11/21.
 */
@Repository
@Transactional
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByTargetUserIdAndHasReadFalseOrderByCreatedTimeDesc(Long targetUserId);

    @Modifying
    @Query("update Message m set m.hasRead = true where m.id=:id")
    int readed(@Param("id") Long id);
}
