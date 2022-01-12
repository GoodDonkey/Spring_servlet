package hi.servlet.web.springmvc.v2;

import hi.servlet.domain.member.Member;
import hi.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerV2 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    /** 메서드 레벨에 있는 여러 매핑정보들을 하나의 클래스에 모은다.
     * */
    @RequestMapping("/new-form")
    public ModelAndView newForm() {
        return new ModelAndView("new-form"); // prefix, suffix 는 application.properties에 따른다.
    }


    @RequestMapping("")
    public ModelAndView findMembers() {
        List<Member> members = memberRepository.findAll();

        ModelAndView mv = new ModelAndView("members");
        mv.addObject("members", members);
        return mv;
    }

    @RequestMapping("/save")
    public ModelAndView saveMember(HttpServletRequest request, HttpServletResponse response) {

        // 필요한 파라미터가 주어진다.
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        ModelAndView mv = new ModelAndView("save-result");
        mv.addObject("member", member); // model에 데이터를 넣는다. model은 LinkedHashMap 의 일종
        // mv.getModel().put()과 같음.
        return mv;
    }
}
