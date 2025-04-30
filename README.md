1. 프로필 작성 기능
유저가 자기소개, 관심사, 사진 등을 입력할 수 있도록
테이블: UserProfile, UserInterest, Interest 등 활용
2. 프로필 탐색 / 소개팅 추천
랜덤 매칭 or 추천 알고리즘 (ex. 관심사 기반) 도입
다른 유저의 프로필 일부(이름, 관심사, 사진) 열람 가능
3. 좋아요 / 매칭 시스템
LikeRepository 통해 좋아요 기능 구현
서로 좋아요 → MatchRepository에 매칭 정보 저장
4. 매칭된 사용자와 채팅
ChatRoomRepository, ChatMessageRepository 사용
매칭된 사용자만 채팅방 생성 가능
5. 프론트엔드 정리 및 연결
로그인 후 토큰 기반으로 프로필 수정 / 좋아요 / 채팅 흐름 연결
로그인 상태 유지: 토큰 localStorage 저장
