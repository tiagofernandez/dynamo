package dynamo.modules.sampleapp.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import dynamo.modules.sampleapp.entity.MyRole;
import dynamo.modules.sampleapp.entity.MyUser;

/**
 * Defines the MyUser repository.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
@SuppressWarnings("unchecked")
public class MyUserRepository
			extends MyUserRepositoryBase {

	/**
	 * Constructor.
	 * 
	 * @param em the entity manager
	 */
	public MyUserRepository(EntityManager em) {
		super(em);
	}

	/**
	 * Lists users by role.
	 * 
	 * @return the users list
	 */
	public List<MyUser> listByRole(MyRole role) {
		Query q = em.createQuery(getListByRoleQuery().toString());
		q.setParameter("role", role);
		return (List<MyUser>) getResultList(q);
	}

	/**
	 * @see dynamo.modules.sampleapp.repository.MyUserRepositoryBase#getListQuery()
	 */
	@Override
	protected StringBuilder getListQuery() {
		StringBuilder query = super.getListQuery();
		query.append(" order by x.name");
		return query;
	}

	/**
	 * Gets the query used for listing MyUser by role.
	 * 
	 * @return the MyUser by username query string
	 */
	protected StringBuilder getListByRoleQuery() {
		StringBuilder query = new StringBuilder("select x from MyUser x");
		query.append(" where x.role=:role");
		query.append(" order by x.name");
		return query;
	}

}