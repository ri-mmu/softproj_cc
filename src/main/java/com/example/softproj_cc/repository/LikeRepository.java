package com.example.softproj_cc.repository;

import com.example.softproj_cc.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    List<Like> findBySenderId(Integer senderId);
    List<Like> findByReceiverId(Integer receiverId);

    // senderId와 receiverId를 기반으로 Like 객체를 찾는 메서드 정의
    Optional<Like> findBySenderIdAndReceiverId(Integer senderId, Integer receiverId);
}