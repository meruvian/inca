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

<div class="modal hide" id="source">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">x</button>
		<h3>Source</h3>
	</div>
	<div class="modal-body">
		<pre class="brush: java">
@Action(name = "/articles")
@Results({ @Result(name = "list", location = "/WEB-INF/view/article/article-list.jsp") })
public class ArticleAction {
	...
	
	@ActionParam("article")
	private Articles articles = new Articles();
	
	@Action(name = "/read/{article.id}")
	public ActionResult read() {
		articles = service.findById(articles.getId());
		url = "/articles/read/{article.id}";

		return new ActionResult("/WEB-INF/view/article/article-detail.jsp");
	}
	
	...
}
		</pre>
	</div>
</div>

<s:url var="a" value="/articles/find" />
<div>
	<h2>
		<s:url value="/articles/read" var="url" />
		<a href="${url}/${article.id}">
			${article.title}
		</a>
	</h2>
	<p><i class="icon-pencil"></i> <a href="${a}/${article.publishYear}">${article.publishYear}</a> / <a href="${a}/${article.publishYear}/${article.publishMonth}">${article.publishMonth}</a> / <a href="${a}/${article.publishYear}/${article.publishMonth}/${article.publishDate}">${article.publishDate}</a></p>
	<p style="text-align: justify;">${article.content}</p>
</div>