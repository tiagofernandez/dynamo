package dynamo.modules.sampleapp.repository;

import java.util.*;

import javax.persistence.*;

import dynamo.modules.sampleapp.entity.*;
import dynamo.core.repository.AbstractRepository;

/**
 * Defines the MyGroup's repository.
 */
@SuppressWarnings("unchecked")
public class MyGroupRepositoryBase
			extends AbstractRepository {

	/**
	 * Constructor.
	 * 
	 * @param em the entity manager
	 */
	public MyGroupRepositoryBase(EntityManager em) {
		super(em);
	}

	/**
	 * Gets MyGroup by id.
	 * 
	 * @param id the id
	 * @return the MyGroup
	 */
	public MyGroup getById(Long id) {
		Query q = em.createQuery(getByIdQuery().toString());
		q.setParameter("id", id);
		return (MyGroup) getSingleResult(q);
	}

	/**
	 * Gets MyGroup by name.
	 * 
	 * @param name the name
	 * @return the MyGroup
	 */
	public MyGroup getByName(String name) {
		Query q = em.createQuery(getByNameQuery().toString());
		q.setParameter("name", name);
		return (MyGroup) getSingleResult(q);
	}

	/**
	 * Lists MyGroup.
	 * 
	 * @return the MyGroup list
	 */
	public List<MyGroup> list() {
		Query q = em.createQuery(getListQuery().toString());
		return (List<MyGroup>) getResultList(q);
	}
	
	/**
	 * Gets the query used for getting MyGroup by id.
	 * 
	 * @return the MyGroup by id query string
	 */
	protected StringBuilder getByIdQuery() {
		StringBuilder query = new StringBuilder("select x from MyGroup x");
		query.append(" where x.id=:id");
		return query;
	}

	/**
	 * Gets the query used for getting MyGroup by name.
	 * 
	 * @return the MyGroup by name query string
	 */
	protected StringBuilder getByNameQuery() {
		StringBuilder query = new StringBuilder("select x from MyGroup x");
		query.append(" where x.name=:name");
		return query;
	}

	/**
	 * Gets the query used for listing MyGroup.
	 * 
	 * @return the MyGroup query string
	 */
	protected StringBuilder getListQuery() {
		return new StringBuilder("select x from MyGroup x");
	}

	/**
	 * @see dynamo.core.repository.AbstractRepository#getTargetEntity()
	 */
	@Override
	protected Class<?> getTargetEntity() {
		return MyGroup.class;
	}

}