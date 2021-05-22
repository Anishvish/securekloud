package com.project.securecloud.repository;

import java.util.List;

import com.project.securecloud.entity.PersonEntity;

public interface FileRepository {

	void saveData(PersonEntity personDto);

	List<PersonEntity> getDataBasedOnFilter(String q);
}
