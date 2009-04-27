package dynamo.modules.sampleapp.repository;

import java.util.*;

import javax.persistence.*;

import dynamo.modules.sampleapp.entity.*;
import dynamo.core.repository.AbstractRepository;

/**
 * Defines the MyUser's repository.
 */
@SuppressWarnings("unchecked")
public class MyUserRepositoryBase
			extends AbstractRepository {

	/**
	 * Constructor.
	 * 
	 * @param em the entity manager
	 */
	public MyUserRepositoryBase(EntityManager em) {
		super(em);
	}

	/**
	 * Gets MyUser by id.
	 * 
	 * @param id the id
	 * @return the MyUser
	 */
	public MyUser getById(Long id) {
		Query q = em.createQuery(getByIdQuery().toString());
		q.setParameter("id", id);
		return (MyUser) getSingleResult(q);
	}

	/**
	 * Gets MyUser by username.
	 * 
	 * @param username the username
	 * @return the MyUser
	 */
	public MyUser getByUsername(String username) {
		Query q = em.createQuery(getByUsernameQuery().toString());
		q.setParameter("username", username);
		return (MyUser) getSingleResult(q);
	}

	/**
	 * Gets MyUser by email.
	 * 
	 * @param email the email
	 * @return the MyUser
	 */
	public MyUser getByEmail(String email) {
		Query q = em.createQuery(getByEmailQuery().toString());
		q.setParameter("email", email);
		return (MyUser) getSingleResult(q);
	}

	/**
	 * Lists MyUser.
	 * 
	 * @return the MyUser list
	 */
	public List<MyUser> list() {
		Query q = em.createQuery(getListQuery().toString());
		return (List<MyUser>) getResultList(q);
	}
	
	/**
	 * Gets the query used for getting MyUser by id.
	 * 
	 * @return the MyUser by id query string
	 */
	protected StringBuilder getByIdQuery() {
		StringBuilder query = new StringBuilder("select x from MyUser x");
		query.append(" where x.id=:id");
		return query;
	}

	/**
	 * Gets the query used for getting MyUser by username.
	 * 
	 * @return the MyUser by username query string
	 */
	protected StringBuilder getByUsernameQuery() {
		StringBuilder query = new StringBuilder("select x from MyUser x");
		query.append(" where x.username=:username");
		return query;
	}

	/**
	 * Gets the query used for getting MyUser by email.
	 * 
	 * @return the MyUser by email query string
	 */
	protected StringBuilder getByEmailQuery() {
		StringBuilder query = new StringBuilder("select x from MyUser x");
		query.append(" where x.email=:email");
		return query;
	}

	/**
	 * Gets the query used for listing MyUser.
	 * 
	 * @return the MyUser query string
	 */
	protected StringBuilder getListQuery() {
		return new StringBuilder("select x from MyUser x");
	}

	/**
	 * @see dynamo.core.repository.AbstractRepository#getTargetEntity()
	 */
	@Override
	protected Class<?> getTargetEntity() {
		return MyUser.class;
	}

}