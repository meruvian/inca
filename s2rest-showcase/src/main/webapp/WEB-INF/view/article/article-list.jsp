<%@taglib prefix="s" uri="/struts-tags"%>

<table class="table table-bordered">
	<tr>
		<th>Request URI</th>
		<td><%= request.getMethod().toUpperCase() + " " + request.getAttribute("struts.request_uri") %></td>
	</tr>
	<tr>
		<th>Url Pattern</th>
		<td>${url}</td>
	</tr>
	<tr>
		<td colspan="2">
			<a class="btn btn-primary" data-toggle="modal" href="#source">Source</a>
		</td>
	</tr>
</table>

<div class="modal hide fade" id="source">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">&times;</button>
		<h3>Source</h3>
	</div>
	<div class="modal-body">
		<pre class="brush: java">
@Action(name = "/articles")
@Results({ @Result(name = "list", location = "/WEB-INF/view/article/article-list.jsp") })
public class ArticleAction implements ModelDriven&lt;ArticleActionModel&gt; {
	...
	
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
	
	...
}
		</pre>
	</div>
</div>

<s:url var="a" value="/articles/find" />
<div>
	<s:iterator value="articles.entityList">
	<h2>
		<s:url value="/articles/read" var="url" />
		<a href="${url}/${id}">
			${title}
		</a>
	</h2>
	<p><i class="icon-pencil"></i> <a href="${a}/${publishYear}">${publishYear}</a> / <a href="${a}/${publishYear}/${publishMonth}">${publishMonth}</a> / <a href="${a}/${publishYear}/${publishMonth}/${publishDate}">${publishDate}</a></p>
	<div style="text-align: justify;">
		<s:set name="c" value="%{content.split(\"\n\")}" />
		<s:iterator var="ct" value="%{c}">
		<p>${ct}</p>
		</s:iterator>
	</div>
	</s:iterator>
</div>