package dynamo.core.repository;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * Defines an abstract repository, providing infra-structure methods.
 * 
 * @author Tiago Fernandez
 * @since 0.3
 */
public abstract class AbstractRepository {

	private static Log logger = LogFactory.getLog(AbstractRepository.class);

	protected EntityManager em;

	/**
	 * Constructor.
	 * 
	 * @param em the entity manager
	 */
	public AbstractRepository(EntityManager em) {
		Assert.notNull(em, "EntityManager must not be null");
		this.em = em;
	}

	/**
	 * Finds an entity by ID.
	 * 
	 * @param id the reference
	 * @return the entity
	 */
	public Object findEntityById(Long id) {
		Assert.notNull(getTargetEntity(), "Entity class must not be null");
		Assert.notNull(id, "Entity ID must not be null");
		return em.find(getTargetEntity(), id);
	}

	/**
	 * Gets an unique entity, regarding search parameters.
	 * 
	 * @param namedQuery defined in the Entity
	 * @param params for fetching (name x value)
	 * @return the single result
	 */
	public Object getSingleResult(String namedQuery, Map<String, Object> params) {		
		Assert.hasLength(namedQuery, "Named query must not be blank");
		Object entity = null;		
		try {			
			// The named query is usually defined in the entity class with @NamedQuery annotation
			Query query = em.createNamedQuery(namedQuery);
			setQueryParams(query, params);
			entity = query.getSingleResult();
		}
		catch (NoSuchElementException ex) {
			logger.debug("No such element while running '" + namedQuery + "'");
		}
		catch (NoResultException ex) {
			logger.debug("No results while running '" + namedQuery + "'");
		}
		return entity;
	}
	
	/**
	 * Gets an unique entity, regarding search parameters.
	 * 
	 * @param query the bound query
	 * @return the single result
	 */
	public Object getSingleResult(Query query) {		
		Assert.notNull(query, "Query must not be null");
		Object entity = null;		
		try {			
			entity = query.getSingleResult();
		}
		catch (NoSuchElementException ex) {
			logger.debug("No such element while running '" + query.toString() + "'");
		}
		catch (NoResultException ex) {
			logger.debug("No results while running '" + query.toString() + "'");
		}
		return entity;
	}

	/**
	 * Gets a list of objects, regarding search parameters.
	 * 
	 * @param namedQuery defined in the Entity
	 * @param params for fetching (name x value)
	 * @return the result list
	 */
	public List<?> getResultList(String namedQuery, Map<String, Object> params) {
		Assert.hasLength(namedQuery, "Named query must not be blank");
		Query query = em.createNamedQuery(namedQuery);
		setQueryParams(query, params);
		return query.getResultList();
	}

	/**
	 * Gets a list of objects, regarding search parameters.
	 * 
	 * @param query the bound query
	 * @return the result list
	 */
	public List<?> getResultList(Query query) {
		Assert.notNull(query, "Query must not be null");
		return query.getResultList();
	}
	
	/**
	 * Merges an Entity.
	 * 
	 * @param entity to merge
	 */
	public void mergeEntity(Object entity) {
		Assert.notNull(entity, "Entity object must not be null");
		em.merge(entity);
	}

	/**
	 * Persists an Entity.
	 * 
	 * @param entity to persist
	 */
	public void persistEntity(Object entity) {
		Assert.notNull(entity, "Entity object must not be null");
		em.persist(entity);
	}

	/**
	 * Removes an entity by ID.
	 * 
	 * @param id the reference
	 */
	public void removeEntity(Long id) {
		Assert.notNull(getTargetEntity(), "Entity class must not be null");
		Assert.notNull(id, "Entity ID must not be null");
		Object entity = findEntityById(id);
		em.remove(entity);
	}

	/**
	 * Sets parameters to a Query.
	 * 
	 * @param query the query
	 * @param params the parameters to set
	 */
	protected void setQueryParams(Query query, Map<String, Object> params) {
		Assert.notNull(query, "Query must not be null");
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
	}
	
	/**
	 * Gets the repository's target entity.
	 * 
	 * @return the target entity
	 */
	protected abstract Class<?> getTargetEntity();

}
