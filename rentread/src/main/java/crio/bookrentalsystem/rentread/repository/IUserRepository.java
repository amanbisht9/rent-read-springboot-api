package crio.bookrentalsystem.rentread.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crio.bookrentalsystem.rentread.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, String>{
    
}
