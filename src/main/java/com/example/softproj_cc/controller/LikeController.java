package com.example.softproj_cc.controller;

import com.example.softproj_cc.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 좋아요 응답하기 API
    @PostMapping("/respond")
    public ResponseEntity<String> respondLike(
            @RequestParam Integer senderId,
            @RequestParam Integer receiverId,
            @RequestParam String response) {
        likeService.respondLike(senderId, receiverId, response);
        return ResponseEntity.ok("좋아요 응답 성공");
    }
}
