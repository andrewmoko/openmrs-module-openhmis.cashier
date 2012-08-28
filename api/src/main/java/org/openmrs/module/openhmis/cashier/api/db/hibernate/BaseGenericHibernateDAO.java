package org.openmrs.module.openhmis.cashier.api.db.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.APIException;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseGenericHibernateDAO<T> implements IGenericHibernateDAO<T> {

	SessionFactory sessionFactory;
	private final Class<T> type;
	
	@Autowired
	public BaseGenericHibernateDAO(Class<T> type, SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.type = type; 
	}	
	
	@Override
	public Criteria createCriteria() {
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(type);
	}	

	@Override
	public T save(T entity) throws APIException {
		Session session = sessionFactory.getCurrentSession();
		Integer listId = null;
		try {
			listId = (Integer) session.save(entity);
		} catch (Exception ex) {
			throw new RuntimeException("An exception occurred while attempting to add a " + entity.getClass().getSimpleName() + " entity.", ex);
		}
		return entity;
	}

	@Override
	public void delete(T entity) throws APIException {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.delete(entity);
		} catch (Exception ex) {
			throw new RuntimeException("An exception occurred while attempting to delete a " + entity.getClass().getSimpleName() + " entity.", ex);
		}
	}

	@Override
	public T selectSingle(Serializable id) throws APIException {
		Session session = sessionFactory.getCurrentSession();
		Criteria search;
		try {
			search = session.createCriteria(type)
				.add(Restrictions.eq("id", id));
		}
		catch (Exception ex) {
			throw new RuntimeException("An exception occurred while attempting to select a single " + type.getSimpleName() + " entity with ID " + id.toString() + ".", ex);			
		}
		return selectSingle(search);
	}

	@Override
	public T selectSingle(Criteria criteria) throws APIException {
		Session session = sessionFactory.getCurrentSession();
		T result = null;
		try {
			List<T> results = criteria.list();
			if (results.size() < 1)
				throw new RuntimeException("No entity found for criteria.");
			if (results.size() > 1)
				throw new RuntimeException("Multiple entities found for criteria.");
			result = results.get(0);
		}
		catch (Exception ex) {
			throw new RuntimeException("An exception occurred while attempting to select a single " + type.getSimpleName() + " entity.", ex);
		}
		return result;		
	}

	@Override
	public List<T> select() throws APIException {
		Session session = sessionFactory.getCurrentSession();

		try {
			Criteria search = session.createCriteria(type);
			return search.list();
		} catch (Exception ex) {
			throw new RuntimeException("An exception occurred while attempting to get " + type.getSimpleName() + " entities.", ex);
		} finally {
			//session.close();
		}
	}

	@Override
	public List<T> select(Criteria criteria) throws APIException {
		Session session = sessionFactory.getCurrentSession();
		List<T> results = null;
		try {
			results = criteria.list();
		}
		catch (Exception ex) {
			throw new RuntimeException("An exception occurred while attempting to select " + type.getSimpleName() + " entities.", ex);
		}
		return results;		
	}
}