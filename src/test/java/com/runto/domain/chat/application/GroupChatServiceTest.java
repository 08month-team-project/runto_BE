package com.runto.domain.chat.application;

import com.runto.domain.chat.dao.GroupChatRoomRepository;
import com.runto.domain.chat.dao.GroupChatRoomUserRepository;
import com.runto.domain.chat.domain.GroupChatRoom;
import com.runto.domain.chat.domain.GroupChatRoomUser;
import com.runto.domain.gathering.domain.Coordinates;
import com.runto.domain.gathering.domain.Gathering;
import com.runto.domain.gathering.domain.Location;
import com.runto.domain.gathering.type.GoalDistance;
import com.runto.domain.gathering.type.RunningConcept;
import com.runto.domain.user.domain.User;
import com.runto.domain.user.type.Gender;
import com.runto.domain.user.type.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GroupChatServiceTest {

    @Mock
    private GroupChatRoomRepository groupChatRoomRepository;

    @Mock
    private GroupChatRoomUserRepository groupChatRoomUserRepository;

    @InjectMocks
    private GroupChatService groupChatService;

    private Gathering gathering;

    @BeforeEach
    void setUp(){
        User user1 = User.builder()
                .id(1L)
                .email("email@gmail.com")
                .name("홍길동")
                .gender(Gender.MAN)
                .nickname("길동이")
                .phoneNumber("010-1234-1234")
                .profileImageUrl("xxx.png")
                .role(UserRole.USER)
                .build();



        gathering = Gathering.builder()
                .id(1L)
                .host(user1)
                .title("개모임")
                .description("개들의 모임")
                .appointedAt(LocalDateTime.now())
                .deadline(LocalDateTime.MAX)
                .concept(RunningConcept.MARATHON)
                .goalDistance(GoalDistance.HALF_MARATHON)
                .thumbnailUrl("testImage.png")
                .location(Location.builder()
                        .addressName("서울시 중랑구")
                        .coordinates(Coordinates.builder()
                                .x(123.456)
                                .y(15.123).build())
                        .build())
                .maxNumber(10)
                .build();
    }

    @DisplayName("그룹 채팅방 생성 성공 테스트")
    @Test
    void createGroupChatRoom_success() {
        //given

        //그룹 채팅방 객체를 생성하고 Mock에서 사용하기위해 저장
        GroupChatRoom expectedRoom = GroupChatRoom.createRoom(gathering);
        //when
        //만드려고하는 모임의 채팅방은 존재하지 않는다고 가정함
        when(groupChatRoomRepository.existsByGathering(any(Gathering.class))).thenReturn(false);
        when(groupChatRoomRepository.save(any(GroupChatRoom.class))).thenReturn(expectedRoom);
        when(groupChatRoomRepository.findByGathering(gathering)).thenReturn(expectedRoom);


        //태스트할 서비스의 메소드 실행
        groupChatService.createGroupChatRoom(gathering);

        //then 호출검증
        verify(groupChatRoomRepository).existsByGathering(any(Gathering.class));
        verify(groupChatRoomRepository).save(any(GroupChatRoom.class));

        //상태 검증
        GroupChatRoom savedRoom = groupChatRoomRepository.findByGathering(gathering);
        assertNotNull(savedRoom);
        assertEquals(gathering,savedRoom.getGathering());
    }


    @DisplayName("그룹 채팅방 생성 실패 테스트 - 이미 존재하는 채팅방")
    @Test
    void createGroupChatRoom_fail_alreadyExist() {
        //TODO
    }

    @DisplayName("그룹 채팅방 참여 성공 테스트")
    @Test
    void joinGroupChatRoom_success() {
        //given
        User user2 = User.builder()
                .id(2L)
                .email("email2@gmail.com")
                .name("장보고")
                .gender(Gender.MAN)
                .nickname("해신")
                .phoneNumber("010-1235-1235")
                .profileImageUrl("xxx123.png")
                .role(UserRole.USER)
                .build();
        Long roomId = 1L;

        GroupChatRoom groupChatRoom = GroupChatRoom.createRoom(gathering);
        GroupChatRoomUser groupChatRoomUser = GroupChatRoomUser.createGroupChatRoomUser(groupChatRoom,user2);

        //when
        when(groupChatRoomRepository.findById(1L)).thenReturn(Optional.of(groupChatRoom));
        when(groupChatRoomUserRepository.existsByGroupChatRoomAndUser(any(GroupChatRoom.class),any(User.class))).thenReturn(false);
        when(groupChatRoomUserRepository.findById(any(Long.class))).thenReturn(Optional.of(groupChatRoomUser));

        groupChatService.joinGroupChatRoom(user2,roomId);

        //then
        verify(groupChatRoomRepository).findById(any(Long.class));
        verify(groupChatRoomUserRepository).existsByGroupChatRoomAndUser(any(GroupChatRoom.class), any(User.class));
        Optional<GroupChatRoomUser> savedGroupChatUser = groupChatRoomUserRepository.findById(user2.getId());
        assertTrue(savedGroupChatUser.isPresent());
        assertEquals(groupChatRoomUser.getId(),savedGroupChatUser.get().getId());
    }
    //존재 x , 이미 참여중인 채팅방, 채팅방 인원 초과
    @DisplayName("그룹 채팅방 참여 실패 테스트 - 존재하지 않는 채팅방")
    @Test
    void joinGroupChatRoom_fail_notExist() {
        //TODO
    }

    @DisplayName("그룹 채팅방 참여 실패 테스트 - 이미 참여중인 채팅방")
    @Test
    void joinGroupChatRoom_fail_alreadyJoin() {
        //TODO
    }

    @DisplayName("그룹 채팅방 참여 실패 테스트 - 채팅방 인원 초과")
    @Test
    void joinGroupChatRoom_overUsers() {
        //TODO
    }
}