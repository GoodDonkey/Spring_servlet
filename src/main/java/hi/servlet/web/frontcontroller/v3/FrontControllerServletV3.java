package hi.servlet.web.frontcontroller.v3;

import hi.servlet.web.frontcontroller.MyView;
import hi.servlet.web.frontcontroller.ModelView;
import hi.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hi.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hi.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                            IOException {
        String requestURI = request.getRequestURI(); // URI String 을 얻는다.
        System.out.println("requestURI = " + requestURI);

        ControllerV3 controller = controllerMap.get(requestURI); // Map에서 구현체를 찾아온다.

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // Map에 없을 시 404 에러
            return;
        }

        // 여기서 paramMap 을 만들어 process 해야 한다.
        Map<String, String> paramMap = createParamMap(request);

        ModelView mv = controller.process(paramMap);// ModelView 객체를 반환함.

        // mv 가 주는 view의 논리이름에서 실제 뷰를 얻어야 한다.
        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        // view 를 render 하여 jsp 로 보낸다.
        view.render(mv.getModel(), request, response);
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
