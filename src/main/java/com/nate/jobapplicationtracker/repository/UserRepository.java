package com.nate.jobapplicationtracker.repository;

import com.nate.jobapplicationtracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
