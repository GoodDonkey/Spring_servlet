<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hi.servlet.domain.member.Member" %>
<%@ page import="hi.servlet.domain.member.MemberRepository" %>
<%
    // request, response 그대로 사용 가능(문법 약속)
    MemberRepository memberRepository = MemberRepository.getInstance();

    System.out.println("MemberSaveServlet.service");

    // request 정보로 회원 가입하기
    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));

    Member member = new Member(username, age);
    memberRepository.save(member);
%>

<html>
<head>
    <meta charset="UTF-8">
</head>
<body>

성공
<ul>
    <li>id=<%=member.getId()%></li>
    <li>username=<%=member.getUsername()%></li>
    <li>age=<%=member.getAge()%></li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>