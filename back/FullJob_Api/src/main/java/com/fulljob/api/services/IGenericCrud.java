package com.fulljob.api.services;

import java.util.List;

public interface IGenericCrud <E,ID>{

	List<E>	findAll ();
	E findById (ID id);
	E insertOne (E entity);
	E updatetOne (E entity);
	void deleteOne (ID id);
}
