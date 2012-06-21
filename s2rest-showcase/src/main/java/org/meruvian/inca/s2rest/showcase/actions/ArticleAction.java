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
package org.meruvian.inca.s2rest.showcase.actions;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.HashMap;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.meruvian.inca.s2rest.showcase.entity.Articles;
import org.meruvian.inca.s2rest.showcase.service.ArticleService;
import org.meruvian.inca.struts2.rest.ActionResult;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.ActionParam;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;

/**
 * @author Dian Aditya
 * 
 */
@Action(name = "/articles")
@Results({ @Result(name = "list", location = "/WEB-INF/view/article/article-list.jsp") })
public class ArticleAction {

	@ActionParam("year")
	private int year;

	@ActionParam("month")
	private int month;

	@ActionParam("date")
	private int date;

	@ActionParam("article")
	private Articles articles = new Articles();

	@ActionParam("model")
	protected Object model = new HashMap<String, Object>();

	@ActionParam("url")
	protected String url;

	@Inject
	private ArticleService service;
	
	public String execute() {
		model = service.articles(-1, -1, -1);
		url = "/articles";

		return "list";
	}
	
	@Action(name = "/find/{year}/{month}/{date}")
	public String articlesByDate() {
		model = service.articles(year, month, date);
		url = "/articles/{year}/{month}/{date}";

		return "list";
	}

	@Action(name = "/find/{year}/{month}")
	public String articlesByMonth() {
		model = service.articles(year, month, -1);
		url = "/articles/{year}/{month}";

		return "list";
	}

	@Action(name = "/find/{year}")
	public String articlesByYear() {
		model = service.articles(year, -1, -1);
		url = "/articles/{year}";

		return "list";
	}

	@Action(name = "/read/{article.id}")
	public ActionResult read() {
		articles = service.findById(articles.getId());
		url = "/articles/read/{article.id}";

		return new ActionResult("/WEB-INF/view/article/article-detail.jsp");
	}

	@Action(name = "/form", method = HttpMethod.GET)
	public ActionResult form() {
		url = "/articles/form";

		return new ActionResult("/WEB-INF/view/article/article-form.jsp");
	}

	@Action(name = "/form", method = HttpMethod.POST)
	public ActionResult submit() {
		service.save(articles);
		url = "/articles/form";

		return new ActionResult("/WEB-INF/view/article/article-success.jsp");
	}

	@Inject
	private DataSource dataSource;

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
}
