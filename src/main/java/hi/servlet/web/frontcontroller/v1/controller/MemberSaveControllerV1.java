package hi.servlet.web.frontcontroller.v1.controller;

import hi.servlet.domain.member.Member;
import hi.servlet.domain.member.MemberRepository;
import hi.servlet.web.frontcontroller.v1.ControllerV1;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberSaveControllerV1 implements ControllerV1 {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // request 정보로 회원 가입하기
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        // model 에 데이터를 보관한다.
        request.setAttribute("member", member); // jsp 에서 member 라는 key로 사용할 수 있다.

        // 데이터를 저장한 request 객체를 이용하여 jsp로 forward
        String viewPath = "/WEB-INF/views/save-result.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);

    }
}
