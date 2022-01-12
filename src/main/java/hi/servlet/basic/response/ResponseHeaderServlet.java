package hi.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                          IOException {
        // [status-line]
        response.setStatus(HttpServletResponse.SC_OK); // 숫자로 적는 것보단 의미있는 값으로 적는게 좋다.

        // [response-headers]
        response.setHeader("Content-Type", "text/plain;charset=utf-8");

        // 캐시 없애기
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");

        // 내가 원하는 임의의 헤더 만들기
        response.setHeader("my-header", "hello");

        // 헤더 편의 메서드
        content(response);
        cookie(response);
        redirect(response);

        // ok 반응 보내기
        PrintWriter writer = response.getWriter();
        writer.println("성공하였습니다.");
    }

    private void content(HttpServletResponse response) {
        //Content-Type: text/plain;charset=utf-8
        //Content-Length: 2
        //response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain"); // key value로 주지 않아도 된다.
        response.setCharacterEncoding("utf-8");
        //response.setContentLength(2); //(생략시 자동 생성)
    }

    private void cookie(HttpServletResponse response) {
        //Set-Cookie: myCookie=good; Max-Age=600;
        // response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600"); // 원래 이렇게 하면 된다.
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException {
        //Status Code 302
        //Location: /basic/hello-form.html
        //response.setStatus(HttpServletResponse.SC_FOUND); //302
        //response.setHeader("Location", "/basic/hello-form.html");
        response.sendRedirect("/basic/hello-form.html");
    }
}
