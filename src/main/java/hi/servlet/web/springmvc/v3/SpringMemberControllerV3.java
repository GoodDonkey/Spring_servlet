package hi.servlet.web.springmvc.v3;

import hi.servlet.domain.member.Member;
import hi.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @GetMapping(value = "/new-form")
    public String newForm() {
        return "new-form"; // prefix, suffix 는 application.properties에 따른다.
    }


    @GetMapping
    public String findMembers(Model model) {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "members";
    }

    @PostMapping("/save")
    public String saveMember(@RequestParam("username") String username,
                             @RequestParam("age") int age,
                             Model model) {

        // 파라미터를 그냥 사용하면됨
        Member member = new Member(username, age);
        memberRepository.save(member);

        // ModelAndView 를 반환할 필요 없이 받은 모델을 그대로 사용한다.
        model.addAttribute("member", member);
        return "save-result";
    }
}
