package com.example.rest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rest.entity.User;
import com.example.rest.exception.BadRequestException;
import com.example.rest.exception.NotFoundException;
import com.example.rest.repository.UserRepository;

@Service
public class UserServiceImp implements UserService {

	private UserRepository repository;

	public UserServiceImp(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return this.repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public User findOne(String id) {
		return this.repository.findOne(id)
				.orElseThrow(() -> new NotFoundException("User with id " + id + " does not exist"));
	}

	@Override
	@Transactional
	public User create(User user) {
		Optional<User> existing = this.repository.findByEmail(user.getEmail());
		if (existing.isPresent()) {
			throw new BadRequestException("User with email " + user.getEmail() + " already exists");
		}
		return this.repository.create(user);
	}

	@Override
	@Transactional
	public User update(String id, User user) {
		this.repository.findOne(id).orElseThrow(() -> new NotFoundException("User with id " + id + " does not exist"));
		return this.repository.update(user);
	}

	@Override
	@Transactional
	public void delete(String id) {
		User existing = this.repository.findOne(id)
				.orElseThrow(() -> new NotFoundException("User with id " + id + " does not exist"));
		this.repository.delete(existing);
	}

}
