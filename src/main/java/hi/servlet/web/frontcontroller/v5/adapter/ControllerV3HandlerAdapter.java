package hi.servlet.web.frontcontroller.v5.adapter;

import hi.servlet.web.frontcontroller.ModelView;
import hi.servlet.web.frontcontroller.v3.ControllerV3;
import hi.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
                                                                                                      ServletException,
                                                                                                      IOException {
        ControllerV3 controller = (ControllerV3) handler; // support 하는지 확인한 후 호출할 예정

        Map<String, String> paramMap = createParamMap(request);
        return controller.process(paramMap); // ModelView 반환함
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames()
               .asIterator()
               .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }

}
