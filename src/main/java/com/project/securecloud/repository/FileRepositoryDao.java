package com.project.securecloud.repository;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.securecloud.entity.PersonEntity;

@Repository
public class FileRepositoryDao implements FileRepository {

	private static Logger logger = LoggerFactory.getLogger(FileRepositoryDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public void saveData(PersonEntity personDto) {

		try (Session session = this.sessionFactory.openSession()) {

			logger.info("FileRepositoryDao -> saveData :: is invoked ");
			session.save(personDto);

		} catch (Exception e) {
			logger.info("Exception occured = {}", e.getMessage());
			throw new RuntimeException("Exception = {}" + e.getMessage());
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	@Override
	@Transactional
	public List<PersonEntity> getDataBasedOnFilter(String q) {

		try (Session session = this.sessionFactory.openSession()) {
			String sql = "select * from person_data where name like '%" + q + "%' or address like '%" + q + "%'"
					+ " or pension like '%" + q + "%' or phonenumber like '%" + q + "%' or salary like '%" + q + "%'";

			return session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).getResultList();
		} catch (Exception e) {
			logger.info("Exception occured = {}", e.getMessage());
			return new ArrayList();
		}
	}

}
