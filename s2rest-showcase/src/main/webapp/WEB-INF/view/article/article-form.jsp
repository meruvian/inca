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
	
	@Action(name = "/form", method = HttpMethod.GET)
	public ActionResult form() {
		url = "/articles/form";

		return new ActionResult("/WEB-INF/view/article/article-form.jsp");
	}
	
	...
}
		</pre>
	</div>
</div>

<div>
	<s:form cssClass="form-horizontal" method="post">
		<div class="control-group">
			<label class="control-label" for="title">Title</label>
			<div class="controls">
				<input name="article.title" type="text" class="span4" id="title" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="year">Publish Date</label>
			<div class="controls">
				<select name="article.publishYear" id="year" style="width: 150px;">
					<s:iterator var="i" begin="2012" end="2050">
					<option value="${i}">${i}</option>
					</s:iterator>
				</select>
				<select name="article.publishMonth" id="month" style="width: 100px;">
					<s:iterator var="i" begin="1" end="12">
					<option value="${i}">${i}</option>
					</s:iterator>
				</select>
				<select name="article.publishDate" id="date" style="width: 100px;">
					<s:iterator var="i" begin="1" end="31">
					<option value="${i}">${i}</option>
					</s:iterator>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="content">Content</label>
			<div class="controls">
				<textarea name="article.content" rows="10" cols="100" class="span10"></textarea>
			</div>
		</div>
		<div class="form-actions">
			<input type="submit" value="Save" class="btn btn-primary" style="width: 100px;" />
			<input type="reset" value="Reset" class="btn" style="width: 100px;" />
		</div>
	</s:form>
</div>