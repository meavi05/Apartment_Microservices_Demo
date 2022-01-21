/**
 * 
 */
package com.application.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.application.entity.UserEntity;
import com.application.exception.CustomException;

/*
 * 
 */
/**
 * @author avi08
 *
 */
@Repository
public class AuthRepository {
	
	@Autowired
	ModelMapper mapper;
	@PersistenceContext
	EntityManager em;

	private static final Logger Logger = LogManager.getLogger(AuthRepository.class);

	@Transactional
	public UserEntity register(UserEntity user) throws CustomException {
		Logger.debug("Inside addUser method: Start", AuthRepository.class);
		em.persist(user);
		Logger.debug("Inside addUser method: End", AuthRepository.class);
		return user;
	}

	public UserEntity getUser(String email) {
		Logger.debug("Inside getUserData method: Start", AuthRepository.class);
		UserEntity userEntity = null;
		TypedQuery<UserEntity> userDataQuery = em.createNamedQuery("UserEntity.findByEmail", UserEntity.class);
		userDataQuery.setParameter("email", email);
		try {
			userEntity = userDataQuery.getSingleResult();
		} catch (NoResultException e) {
			Logger.debug("No user return for the given user name", AuthRepository.class);
			return null;
		}
		Logger.debug("Inside getUserData method: End", AuthRepository.class);
		return userEntity;
	}

	

	


}
