package com.example.rest.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.example.rest.entity.User;

@Repository
public class UserRepositoryImp implements UserRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<User> findAll() {
		TypedQuery<User> query = this.em.createNamedQuery("User.findAll", User.class);
		return query.getResultList();
	}

	@Override
	public Optional<User> findOne(String id) {
		return Optional.ofNullable(this.em.find(User.class, id));
	}

	@Override
	public Optional<User> findByEmail(String email) {
		TypedQuery<User> query = this.em.createNamedQuery("User.findByEmail", User.class);
		query.setParameter("pEmail", email);
		List<User> matches = query.getResultList();
		return matches.isEmpty() ? Optional.empty() : Optional.of(matches.get(0));
	}

	@Override
	public User create(User user) {
		this.em.persist(user);
		return user;
	}

	@Override
	public User update(User user) {
		return this.em.merge(user);
	}

	@Override
	public void delete(User user) {
		this.em.remove(user);
	}

}
