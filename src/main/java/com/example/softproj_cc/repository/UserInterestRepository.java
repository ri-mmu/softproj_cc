package com.example.softproj_cc.repository;

import com.example.softproj_cc.entity.UserInterest;
import com.example.softproj_cc.entity.UserInterestId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInterestRepository extends JpaRepository<UserInterest, UserInterestId> {
}
