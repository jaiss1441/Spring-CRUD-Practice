package com.example.Spring_CRUD_Practice.repo;


import com.example.Spring_CRUD_Practice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
}
