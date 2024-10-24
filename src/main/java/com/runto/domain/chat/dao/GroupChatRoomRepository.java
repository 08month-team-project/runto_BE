package com.runto.domain.chat.dao;

import com.runto.domain.chat.domain.GroupChatRoom;
import com.runto.domain.gathering.domain.Gathering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupChatRoomRepository extends JpaRepository<GroupChatRoom,Long> {
    boolean existsByGathering(Gathering gathering);

    GroupChatRoom findByGathering(Gathering gathering);
}
