package me.micro.bbs.message.support;

import me.micro.bbs.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
