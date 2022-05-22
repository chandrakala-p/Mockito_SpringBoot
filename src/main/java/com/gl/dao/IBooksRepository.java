package com.gl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.beans.Books;

@Repository
public interface IBooksRepository extends JpaRepository<Books, Integer> {

}
