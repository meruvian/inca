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
	
	@Action(name = "/form", method = HttpMethod.POST)
	public ActionResult submit() {
		service.save(articles);
		url = "/articles/form";

		return new ActionResult("/WEB-INF/view/article/article-success.jsp");
	}
	
	...
}
		</pre>
	</div>
</div>