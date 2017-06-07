package com.example.rest.repository;

import java.util.List;
import java.util.Optional;

import com.example.rest.entity.User;

public interface UserRepository {

	public List<User> findAll();

	public Optional<User> findOne(String id);

	public Optional<User> findByEmail(String email);

	public User create(User user);

	public User update(User user);

	public void delete(User user);
}
