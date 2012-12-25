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
@Action(name = "simple")
public class SimpleAction {
	@Action
	public ActionResult index() {
		return new ActionResult("/WEB-INF/simple/simple-index.jsp");
	}
}
		</pre>
	</div>
</div>

<s:url var="a" value="/simple" />
<div>
	<h2>Welcome to the very simple Action!</h2>
</div>