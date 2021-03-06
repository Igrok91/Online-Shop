<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Online Shop</title>
	<link href="bootstrap/css/shop-homepae.css" rel="stylesheet" />
	<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
	<link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet" />
	<script src="bootstrap/scripts/jquery-1.7.1.min.js"></script>
	<script src="bootstrap/js/bootstrap.js"></script>
	<style>
		#cartitem {
			display: inline;
		}

		#welcome {
			display: inline;
			height: 20px;
		}

		#Login {
			display: inline;
			height: 20px;
		}
	</style>
</head>
<body>
<!--Header-->
<nav class="navbar navbar-inverse" role="navigation">
	<div class="navbar-inner">
		<c:url var="home" value="/home.jsp" />
		<a class="brand" href="${home}">Online Shop</a>
		<div class="nav-collapse collapse">
			<ul class="nav">
				<c:forEach var="categories" items="${categories}">
					<li class="dropdown"><a class="dropdown-toggle"
											data-toggle="dropdown" href="#"> <c:out value="${categories.key}" />
					</a>
						<ul class="dropdown-menu">
							<c:forEach var="subcategories" items="${categories.value}">
								<c:url var="url" value="/category">
									<c:param name="subcat" value="${subcategories.categoryid}" />
									<c:param name="categ" value="${categories.key}" />
								</c:url>
								<li><a tabindex="-1" href="${url}">
									<c:out value="${subcategories.productCategory}" /></a></li>
							</c:forEach>
						</ul></li>
				</c:forEach>
			</ul>
			<ul class="nav pull-right">
				<li class="dropdown"><a id="cartitem" class="dropdown-toggle"
										data-toggle="dropdown" href="#"><span id="itemcount"
																			  class="btn btn-default btn-sm"><i
						class="icon-shopping-cart icon-red"></i> <c:set var="cartItems"
																		scope="session" value="${cart.numberOfItems}" /> <span
						class="headerCartItemsCount">
					<c:choose>
					<c:when test="${empty cartItems}">0
					</c:when>
					<c:otherwise>
						<c:out value="${cartItems}" />
					</c:otherwise>
					</c:choose>
						</span> <span class="headerCartItemsCountWord"><c:out
						value="${cartItems==1?'item':'items'}" /></span> <b class="caret"></b></span></a>
					<ul class="dropdown-menu">
						<c:url var="cart" value="/cart" />
						<li><a tabindex="-1" href="${cart}">View Cart</a></li>
						<li><a tabindex="-1" href="checkout_unreg.jsp">Checkout
							Cart</a></li>
					</ul></li>
				<li class="dropdown"><c:choose>
					<c:when test="${empty email}">
						<a href="login-register.jsp"><b>Login/Register</b></a>
					</c:when>
					<c:otherwise>
						<a class="dropdown-toggle" data-toggle="dropdown" href="#"><span
								id="welcome" class="btn btn-default btn-sm"><c:out
								value="Hello ${email}" /><b class="caret"></b></span></a>
						<ul class="dropdown-menu">
							<li><a tabindex="-1" href="/">Account</a></li>
							<li><a tabindex="-1" href="/">Orders</a></li>
							<c:url var="logout" value="/logout" />
							<li><a tabindex="-1" href="${logout}">Sign out</a></li>
						</ul>
					</c:otherwise>
				</c:choose></li>
			</ul>
		</div>
	</div>
</nav>

<!-- Body -->

	<div class="container">
		<div class="row">
			<div class="span12">
				<c:forEach var="products" items="${productsList}">
					<c:url var="url" value="/product">
						<c:param name="productId" value="${products.productId}" />
					</c:url>
					<div class="span3">
						 <a href="${url}">${products.productName}</a>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>
</body>
</html>
