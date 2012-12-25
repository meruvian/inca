<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags" %>		

<s:url var="js" value="/scripts" forceAddSchemeHostAndPort="true" />
<s:url var="css" value="/styles" forceAddSchemeHostAndPort="true" />

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title><s:text name="application.title" /></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="Dian Aditya">

		<!-- Le styles -->
		<link href="${css}/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="${css}/shCore.css" rel="stylesheet" type="text/css" />
		<link href="${css}/shThemeDefault.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			body {
				padding-top: 60px;
				padding-bottom: 40px;
			}
			.sidebar-nav {
				padding: 9px 0;
			}
		</style>
		<%-- <link href="../assets/css/bootstrap-responsive.css" rel="stylesheet"> --%>

		<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
		<!--[if lt IE 9]>
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		
		<script type="text/javascript" src="${js}/jquery-min.js"></script>
		<script type="text/javascript" src="${js}/jquery.address.min.js"></script>
		<script type="text/javascript" src="${js}/bootstrap.min.js"></script>
		<script type="text/javascript" src="${js}/shCore.js"></script>
		<script type="text/javascript" src="${js}/shBrushJava.js"></script>
		<script type="text/javascript" src="${js}/inca.ext.js"></script>
		<script type="text/javascript">
		var ajax = {};
		ajax.fullContextPath = '<s:url value="/" forceAddSchemeHostAndPort="true" />';
		ajax.contextPath = '<s:url value="/" />';
		ajax.load = function(url) {
			jQuery.ajax({
				type : "GET",
				url : this.fullContextPath + url,
				success : function(data) {
					data = data.replace(/<script.*>.*<\/script>/ig,""); // Remove script tags
					data = data.replace(/<\/?link.*>/ig,""); //Remove link tags
					data = data.replace(/<\/?html.*>/ig,""); //Remove html tag
					data = data.replace(/<\/?body.*>/ig,""); //Remove body tag
					data = data.replace(/<\/?head.*>/ig,""); //Remove head tag
					data = data.replace(/<\/?!doctype.*>/ig,""); //Remove doctype
					data = data.replace(/<title.*>.*<\/title>/ig,""); // Remove title tags
					
					$('#content').empty().html(data);
					
					window.inca.init();
				},
				error : function(jqXHR, textStatus, errorThrown) {
					$('#content').empty();
					$('#content').append('<h2>HTTP Status ' + jqXHR.status +' - ' + textStatus +'</h2>');
					$('#content').append(errorThrown);
				}
			});
		};
		
		$(function() {
			$.address.externalChange(function() {
				if(location.hash == "" || location.hash == "#") {
					$('#content').empty();
					$('#welcome').clone().show().appendTo('#content');
				} else {
					ajax.load(location.hash.substring(2));
				}
			});
			
			$('#loading').overflow().hide();
			$('#loading').css("position", "fixed");
			$('#loading').width($(window).width());
			$('#loading').height($(window).height());
			$('#loading-dialog').css('z-index', 1001);
			$('#loading-dialog').center();
			
			$(document).ajaxStart(function() {
				$('#loading').hide();
				$('#loading').fadeIn();
			});

			$(document).ajaxStop(function() {
				$('#loading').show();
				$('#loading').hide();
			});
		});
		</script>
	</head>
	<body>
		<div class="hero-unit" style="display: none;" id="welcome">
			<h1><s:text name="application.title" /></h1>
			<p style="text-align: justify;"><s:text name="project.description" /></p>
			<p><a href="<s:text name="project.wiki.url" />" class="btn btn-primary btn-large" target="_blank">Learn more &raquo;</a></p>
		</div>
		<div id="loading">
			<div id="loading-dialog"><img src="<s:url value="/images/loading.gif" />" /></div>
		</div>
		<div class="navbar navbar-inverse navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container-fluid">
					<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</a>
					<a class="brand" href="#"><s:text name="application.name" /></a>
					<div class="nav-collapse">
						<ul class="nav">
							<li class="active"><a href="#">Home</a></li>
							<li><a href="http://java.net/projects/s2restplugins" target="_blank">Wiki</a></li>
						</ul>
					</div><!--/.nav-collapse -->
				</div>
			</div>
		</div>

		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span3">
					<div class="well sidebar-nav span3" data-spy="affix" data-offset-top="0">
						<ul class="nav nav-list">
							<li class="nav-header"><s:text name="sidebar.title" /></li>
							<li><a href="#/simple">Simple Action</a></li>
							<li><a href="#/articles">Url Params</a></li>
							<li><a href="#/articles/form">Method Handling</a></li>
							<li><a>JSON Output (TODO)</a></li>
							<li><a>Contribute!</a></li>
						</ul>
					</div>
				</div>
				<div class="span9" id="content">
				</div><!--/span-->
			</div><!--/row-->

			<br />
			<footer class="footer modal-footer">
				<p>Inca S2Rest v1.0.2</p>
				<p>&copy; 2012 - <strong><a href="http://www.meruvian.org" target="_blank">Meruvian</a></strong></p>
			</footer>

		</div><!--/.fluid-container-->
	</body>
</html>
