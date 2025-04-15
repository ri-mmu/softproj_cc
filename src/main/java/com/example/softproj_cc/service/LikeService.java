package com.example.softproj_cc.service;

import com.example.softproj_cc.entity.Like;
import com.example.softproj_cc.entity.Match;
import com.example.softproj_cc.entity.User;
import com.example.softproj_cc.entity.enums.LikeStatus;
import com.example.softproj_cc.repository.LikeRepository;
import com.example.softproj_cc.repository.MatchRepository;
import com.example.softproj_cc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;  // UserRepository 추가

    // 좋아요 응답하기 (응답: "성공", "거절")
    public void respondLike(Integer senderId, Integer receiverId, String response) {
        Like like = likeRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 좋아요입니다."));

        // response 값에 따라 상태 변경
        LikeStatus status;
        switch (response) {
            case "성공":
                status = LikeStatus.LIKED;
                break;
            case "거절":
                status = LikeStatus.REJECTED;
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 응답입니다.");
        }

        like.setStatus(status);  // 상태 설정

        // 매칭이 성사된 경우
        if (LikeStatus.LIKED.equals(status)) {
            // 매칭된 유저들 조회
            User sender = userRepository.findById(senderId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
            User receiver = userRepository.findById(receiverId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

            // 매칭 생성
            Match match = new Match();
            match.setUser1(sender);
            match.setUser2(receiver);
            matchRepository.save(match);
        }

        likeRepository.save(like);  // 좋아요 상태 저장
    }


}
