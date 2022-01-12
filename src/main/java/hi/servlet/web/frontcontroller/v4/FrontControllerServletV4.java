package hi.servlet.web.frontcontroller.v4;

import hi.servlet.web.frontcontroller.MyView;
import hi.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hi.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hi.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                            IOException {
        String requestURI = request.getRequestURI(); // URI String 을 얻는다.
        System.out.println("requestURI = " + requestURI);

        ControllerV4 controller = controllerMap.get(requestURI); // Map에서 구현체를 찾아온다.

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // Map에 없을 시 404 에러
            return;
        }

        // 여기서 paramMap 을 만들어 process 해야 한다.
        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>(); // 이 빈 모델은 controller로 넘겨져 데이터가 저장된다.
        String viewName = controller.process(paramMap, model); // 논리 viewName을 반환함.

        // viewName 을 이용해 실제 뷰 경로를 가진 MyView 객체를 얻는다.
        MyView view = viewResolver(viewName);

        // view 를 render 하여 jsp 로 보낸다.
        view.render(model, request, response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames()
               .asIterator()
               .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
