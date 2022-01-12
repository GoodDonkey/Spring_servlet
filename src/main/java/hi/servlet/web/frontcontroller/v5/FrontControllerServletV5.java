package hi.servlet.web.frontcontroller.v5;

import hi.servlet.web.frontcontroller.ModelView;
import hi.servlet.web.frontcontroller.MyView;
import hi.servlet.web.frontcontroller.v3.ControllerV3;
import hi.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hi.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hi.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hi.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    // 어떤 Controller 든 받을 수 있다.
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                          IOException {
        /** V3 컨트롤러를 사용해 처리하는 과정
         * V3 컨트롤러(handler)를 직접 사용하는 것이 아니라 Adapter를 통해 handler를 사용한다.
         * */
        Object handler = getHandler(request); // handler 호출: ControllerV3 구현체 중 하나 반환됨.

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // Map에 없을 시 404 에러
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        ModelView mv = adapter.handle(request, response, handler);

        // mv 가 주는 view의 논리이름에서 실제 뷰를 얻어야 한다.
        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        // view 를 render 하여 jsp 로 보낸다.
        view.render(mv.getModel(), request, response);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다." + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI(); // URI String 을 얻는다.
        System.out.println("requestURI = " + requestURI);

        return handlerMappingMap.get(requestURI); //

    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

}
