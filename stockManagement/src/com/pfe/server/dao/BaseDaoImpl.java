package com.pfe.server.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

/**
 * Default implementation DAO class. Any new DAO class should extend this class.
 * 
 * @param <I>
 *            Key class
 * @param <E>
 *            Entity class
 */
public abstract class BaseDaoImpl<I extends Serializable, E> extends HibernateDaoSupport implements IBaseDao<I, E> {
	
	private Class<E> entityClass;

	/**
	 * init constructor
	 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		Type type = parameterizedType.getActualTypeArguments()[1];
		if (type instanceof Class) {
			this.entityClass = (Class<E>)type;
		}
	}
	
	/**
	 * @see com.pfe.server.dao.IBaseDao#get(java.io.Serializable)
	 */
	@Override
	public E get(I id) {
		return (E) getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * @see com.pfe.server.dao.IBaseDao#persist(java.lang.Object)
	 */
	@Override
	public E persist(E entity) {
		getHibernateTemplate().persist(entity);
		return entity;
		//getHibernateTemplate().saveOrUpdate(entity);
		//return entity;
		//return getHibernateTemplate().merge(entity);
	}
	
	@Override
	public E merge(E entity) {
		return getHibernateTemplate().merge(entity);
	}

	/**
	 * @see com.pfe.server.dao.IBaseDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(E entity) {
		getHibernateTemplate().delete(entity);
	}
		
	/**
	 * @see com.pfe.server.dao.IBaseDao#findAll()
	 */
	@Override
	public List<E> findAll() {
		return findByCriteria();
	}
		
	/**
	 * Return a list of items by a given criteria
	 * @param crit
	 * @return
	 */
	protected List<E> findByCriteria(Criterion... criterions) {
		return findByCriteria(-1, -1, null, null, criterions);
	}
	
	/**
	 * Return a list of items by a given criteria applying the specified ordering
	 * @param orders
	 * @param criterions
	 * @return
	 */
	protected List<E> findByCriteria(List<OrderAlias> sortAliases, List<Order> orders, Criterion... criterions) {
		return findByCriteria(-1, -1, sortAliases, orders, criterions);
	}
	
	/**
	 * Return a list of items by a given criteria applying limit of results
	 * @param firstResult
	 * @param maxResults
	 * @param criterions
	 * @return
	 */
	protected List<E> findByCriteria(int firstResult, int maxResults, Criterion... criterions) {
		return findByCriteria(firstResult, maxResults, null, null, criterions);
	}
	
	/**
	 * Return a list of items by a given criteria applying the specified ordering and limit of results
	 * @param firstResult
	 * @param maxResults
	 * @param orders
	 * @param criterions
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<E> findByCriteria(int firstResult, int maxResults, List<OrderAlias> orderAliases, List<Order> orders, Criterion... criterions) {
		DetachedCriteria crit = DetachedCriteria.forClass(getEntityClass());		
		if(criterions != null) {
			for (Criterion c : criterions) {
				if(c != null) {
					crit.add(c);
				}
			}
		}
		if(orderAliases != null && orderAliases.size() > 0) {
			for(OrderAlias orderAlias : orderAliases) {
				if(orderAlias != null 
						&& StringUtils.isNotEmpty(orderAlias.getAssociationPath())
						&& StringUtils.isNotEmpty(orderAlias.getName())) {
					crit.createAlias(orderAlias.getAssociationPath(), orderAlias.getName(), orderAlias.getType());
				}
			}
		}
		if(orders != null && orders.size() > 0) {
			for(Order order : orders) {
				if(order != null) {
					crit.addOrder(order);
				}
			}
		}
		return getHibernateTemplate().findByCriteria(crit, firstResult, maxResults);
	}

	/**
	 * @return the entityClass
	 */
	protected Class<E> getEntityClass() {
		return entityClass;
	}
	

	/**
	 * @see com.pfe.server.dao.IBaseDao#count()
	 */
	@Override
	public long count() throws DataAccessException {
		return countByCriteria(DetachedCriteria.forClass(entityClass));
	}
	
	protected long countByCriteria(List<OrderAlias> aliases, Criterion... criterions) {
		DetachedCriteria crit = DetachedCriteria.forClass(getEntityClass());		
		if(criterions != null) {
			for (Criterion c : criterions) {
				if(c != null) {
					crit.add(c);
				}
			}
		}
		if(aliases != null && aliases.size() > 0) {
			for(OrderAlias orderAlias : aliases) {
				if(orderAlias != null 
						&& StringUtils.isNotEmpty(orderAlias.getAssociationPath())
						&& StringUtils.isNotEmpty(orderAlias.getName())) {
					crit.createAlias(orderAlias.getAssociationPath(), orderAlias.getName(), orderAlias.getType());
				}
			}
		}
		return countByCriteria(crit);
	}
	
	/**
	 * Return a list of items by a given criteria
	 * @param crit
	 * @return
	 */
	protected long countByCriteria(Criterion... criterion) throws DataAccessException {
		DetachedCriteria crit = DetachedCriteria.forClass(getEntityClass());
		if(criterion != null) {
			for (Criterion c : criterion) {
				if(c != null) {
					crit.add(c);
				}
			}
		}
		return countByCriteria(crit);
	}

	/**
	 * Count the number of instances of an entity in the database based on a given Hibernate criteria object.
	 * @param criteria the detached Hibernate criteria object.
	 * @return the number of instances
	 * @throws DataAccessException
	 */
	protected long countByCriteria(final DetachedCriteria criteria) throws DataAccessException {
		Assert.notNull(criteria, "DetachedCriteria must not be null");
		Long count = (Long)getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Long>() {
			@Override
			public Long doInHibernate(Session session)
					throws HibernateException {
				Criteria executableCriteria = criteria
						.getExecutableCriteria(session);
				executableCriteria.setProjection(Projections.rowCount());
				for (Object result : executableCriteria.list()) {
					if (result instanceof Long) {
						return (Long)result;
					} else if (result instanceof Integer) {
						return Long.valueOf((Integer)result);
					}
				}
				return -1L;
			}
		});

		return count.longValue();
	}

	/**
	 * Convert a list of SortField into a list of Order keeping only the ones that applies to the current entity. 
	 * @param sorts a list of SortField
	 * @return a list of Order, null in case of an empty result.
	 */
	protected List<Order> getOrders(List<SortField> sorts) {
		if(sorts == null || sorts.size() == 0) {
			return null;
		}
		List<Order> result = null;
		for(SortField sort : sorts) {
			if(sort != null && getSortType() != null && getSortType().isInstance(sort)) {
					if(result == null) {
						result = new ArrayList<Order>(2);
					}
					result.add(sort.isAscending() ? Order.asc(sort.getField()) : Order.desc(sort.getField()));
			}
		}
		return result;
	}
	
	/**
	 * Convert a list of SortField into a list of Order keeping only the ones that applies to the current entity. 
	 * @param sorts a list of SortField
	 * @return a list of Order, null in case of an empty result.
	 */
	protected List<OrderAlias> getOrderAliases(List<SortField> sorts) {
		if(sorts == null || sorts.size() == 0) {
			return null;
		}
		List<OrderAlias> result = null;
		for(SortField sort : sorts) {
			if(sort != null && getSortType() != null && getSortType().isInstance(sort) && sort.getOrderAlias() != null) {
					if(result == null) {
						result = new ArrayList<OrderAlias>(2);
					}
					result.add(sort.getOrderAlias());
			}
		}
		return result;
	}
	
	/**
	 * Return the type of the parent sort associated to this entity.
	 * This method must be overloaded in the base classes when sorting functionality is required.
	 * See in DomainDaoImpl for an example.
	 * @return
	 */
	protected Class<? extends SortField> getSortType() {
		return null;			
	}

	@Override
	public void deleteAll() {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		hibernateTemplate.deleteAll(hibernateTemplate.loadAll(getEntityClass()));
	}
	
	@Override
	public void evict(E o) {
		getHibernateTemplate().evict(o);
	}
	
}
