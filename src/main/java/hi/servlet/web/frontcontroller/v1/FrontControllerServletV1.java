package hi.servlet.web.frontcontroller.v1;

import hi.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hi.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hi.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                          IOException {
        System.out.println("FrontControllerServletV1.service");

        String requestURI = request.getRequestURI(); // URI String 을 얻는다.

        ControllerV1 controller = controllerMap.get(requestURI); // Map에서 구현체를 찾아온다.

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // Map에 없을 시 404 에러
            return;
        }

        controller.process(request, response); // 구현체에서 process 메서드를 이용한다.

    }
}
