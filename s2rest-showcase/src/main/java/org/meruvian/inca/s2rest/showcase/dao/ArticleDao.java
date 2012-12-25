/**
 * Copyright 2012 BlueOxygen Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.meruvian.inca.s2rest.showcase.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.meruvian.inca.s2rest.showcase.entity.Article;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.persistence.access.PersistenceDAO;
import org.springframework.stereotype.Repository;

/**
 * @author Dian Aditya
 * 
 */
@Repository
public class ArticleDao extends PersistenceDAO<Article> {

	public EntityListWrapper<Article> articles(int year, int month, int date) {
		EntityListWrapper<Article> articles = new EntityListWrapper<Article>();

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Article> query = cb.createQuery(entityClass);

		Root<Article> root = query.from(entityClass);
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (year > 0) {
			predicates.add(cb.equal(root.get("publishYear"), year));
		}

		if (month > 0 && month <= 12) {
			predicates.add(cb.equal(root.get("publishMonth"), month));
		}

		if (date > 0) {
			predicates.add(cb.equal(root.get("publishDate"), date));
		}

		CriteriaQuery<Article> entities = query.select(root);
		entities.where(predicates.toArray(new Predicate[0]));
		entities.orderBy(cb.asc(root.get("publishYear")),
				cb.asc(root.get("publishMonth")),
				cb.asc(root.get("publishDate")));

		articles.setEntityList(entityManager.createQuery(entities)
				.getResultList());
		articles.setRowCount(articles.getEntityList().size());
		articles.setTotalPage(1);
		articles.setCurrentPage(1);
		articles.setLimit(0);

		return articles;
	}
}
