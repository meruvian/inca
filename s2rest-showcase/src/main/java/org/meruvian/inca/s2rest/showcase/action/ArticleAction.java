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
package org.meruvian.inca.s2rest.showcase.action;

import java.sql.CallableStatement;
import java.sql.Connection;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.meruvian.inca.s2rest.showcase.action.model.ArticleActionModel;
import org.meruvian.inca.s2rest.showcase.service.ArticleService;
import org.meruvian.inca.struts2.rest.ActionResult;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;

/**
 * @author Dian Aditya
 * 
 */
@Action(name = "/articles")
@Results({ @Result(name = "list", location = "/WEB-INF/view/article/article-list.jsp") })
public class ArticleAction implements ModelDriven<ArticleActionModel> {
	@Inject
	private ArticleService service;

	@Inject
	private DataSource dataSource;

	private ArticleActionModel model = new ArticleActionModel();

	@Action
	public String index() {
		model.setArticles(service.articles(-1, -1, -1));
		model.setUrl("/articles");

		return "list";
	}

	@Action(name = "/find/{year}/{month}/{date}")
	public String articlesByDate() {
		model.setArticles(service.articles(model.getYear(), model.getMonth(),
				model.getDate()));
		model.setUrl("/articles/{year}/{month}/{date}");

		return "list";
	}

	@Action(name = "/find/{year}/{month}")
	public String articlesByMonth() {
		model.setArticles(service.articles(model.getYear(), model.getMonth(),
				-1));
		model.setUrl("/articles/{year}/{month}");

		return "list";
	}

	@Action(name = "/find/{year}")
	public String articlesByYear() {
		model.setArticles(service.articles(model.getYear(), -1, -1));
		model.setUrl("/articles/{year}");

		return "list";
	}

	@Action(name = "/read/{article.id}")
	public ActionResult read() {
		model.setArticle(service.findById(model.getArticle().getId()));
		model.setUrl("/articles/read/{article.id}");

		return new ActionResult("/WEB-INF/view/article/article-detail.jsp");
	}

	@Action(name = "/form", method = HttpMethod.GET)
	public ActionResult form() {
		model.setUrl("/articles/form");

		return new ActionResult("/WEB-INF/view/article/article-form.jsp");
	}

	@Action(name = "/form", method = HttpMethod.POST)
	public ActionResult submit() {
		service.save(model.getArticle());
		model.setUrl("/articles/form");

		return new ActionResult("/WEB-INF/view/article/article-success.jsp");
	}

	@Action(name = "/backup")
	public String backup() throws Exception {
		Connection connection = dataSource.getConnection();
		String backupdirectory = "/home/dian/backup.sql";
		CallableStatement cs = connection
				.prepareCall("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)");
		cs.setString(1, backupdirectory);
		cs.execute();
		cs.close();

		return null;
	}

	public ArticleActionModel getModel() {
		return model;
	}
}
