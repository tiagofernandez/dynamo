package dynamo.modules.sampleapp.repository;

import javax.persistence.EntityManager;

/**
 * Defines the MyGroup repository.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class MyGroupRepository
			extends MyGroupRepositoryBase {

	/**
	 * Constructor.
	 * 
	 * @param em the entity manager
	 */
	public MyGroupRepository(EntityManager em) {
		super(em);
	}

	/**
	 * @see dynamo.modules.sampleapp.repository.MyGroupRepositoryBase#getByIdQuery()
	 */
	@Override
	protected StringBuilder getByIdQuery() {
		StringBuilder query = new StringBuilder("select x from MyGroup x");
		query.append(" left join fetch x.users");
		query.append(" where x.id=:id");
		query.append(" order by x.name");
		return query;
	}

	/**
	 * @see dynamo.modules.sampleapp.repository.MyGroupRepositoryBase#getByNameQuery()
	 */
	@Override
	protected StringBuilder getByNameQuery() {
		StringBuilder query = new StringBuilder("select x from MyGroup x");
		query.append(" left join fetch x.users");
		query.append(" where x.name=:name");
		query.append(" order by x.name");
		return query;
	}
	
	/**
	 * @see dynamo.modules.sampleapp.repository.MyGroupRepositoryBase#getListQuery()
	 */
	@Override
	protected StringBuilder getListQuery() {
		StringBuilder query = super.getListQuery();
		query.append(" order by x.name");
		return query;
	}

}