package br.com.lopes.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;
 




import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;

import br.com.lopes.util.JpaUtils;
 
/**
 * @author gabriel
 *
 * Oct 17, 2013
 */
 
@SuppressWarnings("unchecked")
public class GenericDAO<PK, T> {
    private EntityManager entityManager;    
 
    private Logger log = Logger.getLogger(getClass());
    
    public GenericDAO() {
        entityManager = JpaUtils.getEntityManager();
    }
 
    public T getById(PK pk) {
		entityManager = JpaUtils.getEntityManager();
        return (T) entityManager.find(getTypeClass(), pk);
    }
 
    public void insert(T entity) {
    	try{
    		log.info("insert "+entity.getClass().getName() +' ' +entity);
    		entityManager = JpaUtils.getEntityManager();
    		entityManager.getTransaction().begin();
    		entityManager.persist(entity);
	        entityManager.getTransaction().commit();
	        log.info("insert sucessful");
    	} catch (Exception e) {
			
			log.error("Falha ao inserir Class/Entity "+entity.getClass().getName() +'/'+entity + " Error:"+ e.getMessage());
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			
			throw e;
		}
    }
 
    public void update(T entity) {
    	try{
    		log.info("update "+entity.getClass().getName() +' ' +entity);
			entityManager = JpaUtils.getEntityManager();
			entityManager.getTransaction().begin();
			entityManager.merge(entity);
			entityManager.getTransaction().commit();
			log.info("update sucessful");
		} catch (Exception e) {
			log.error("Falha ao gravar Class/Entity "+entity.getClass().getName() +'/'+entity + " Error:" + e.getMessage());
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw e;
		}

    }
 
    public void delete(T entity) {
    	try{
    		log.info("delete "+entity.getClass().getName() +' ' +entity);
			entityManager = JpaUtils.getEntityManager();
			entityManager.getTransaction().begin();			
			entityManager.remove(entity);
			entityManager.getTransaction().commit();
			log.info("delete successful");
		} catch (Exception e) {
			
			log.error("Falha ao deletar Class/Entity "+entity.getClass().getName() +'/'+entity + " Error:"+ e.getMessage());
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			
			throw e;
		}
    }
 
    public List<T> findAll() {
		entityManager = JpaUtils.getEntityManager();
        return entityManager.createQuery(("FROM " + getTypeClass().getName()))
                .getResultList();
    }
 
    private Class<?> getTypeClass() {
        Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        return clazz;
    }
}
