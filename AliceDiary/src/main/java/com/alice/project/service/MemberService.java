package com.alice.project.service;


import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alice.project.domain.Member;
import com.alice.project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
//@Transactional //로직을 처리하다가 에러 발생 시 변경된 데이터를 로직 수행 이전 상태로 콜백
@RequiredArgsConstructor //final 필드 생성자 생성해줌
public class MemberService implements UserDetailsService{ //MemberService가 UserDetailService를 구현

	private final MemberRepository memberRepository;
	
	public Member saveMember(Member member) {
//		validateDuplicateMember(member);
		return memberRepository.save(member); //insert
	}
	
	
	//id 중복테스트
	public int checkIdDuplicate(String id) {
		boolean check = memberRepository.existsById(id);
		if(check) {
			return 1; //아이디 중복이면 1
		}else {
			return 0; //사용 가능 아이디면 0
		}
	}
	
	
	//email 인증
	 @Autowired
	    JavaMailSender emailSender;
	 
	    public static final String ePw = createKey(); //인증번호
	 
	    private MimeMessage createMessage(String to)throws Exception{
	        System.out.println("보내는 대상 : "+ to);
	        System.out.println("인증 번호 : "+ePw);
	        MimeMessage  message = emailSender.createMimeMessage();
	 
	        message.addRecipients(RecipientType.TO, to);//보내는 대상
	        message.setSubject("Alice 회원가입 이메일 인증");//제목
	 
	        String msgg="";
	        msgg+= "<div style='margin:100px;'>";
	        msgg+= "<h1> 안녕하세요 Alice입니다. </h1>";
	        msgg+= "<br>";
	        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
	        msgg+= "<br>";
	        msgg+= "<p>감사합니다!<p>";
	        msgg+= "<br>";
	        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
	        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
	        msgg+= "<div style='font-size:130%'>";
	        msgg+= "CODE : <strong>";
	        msgg+= ePw+"</strong><div><br/> ";
	        msgg+= "</div>";
	        message.setText(msgg, "utf-8", "html");//내용
	        message.setFrom(new InternetAddress("aliceproject0803@gmail.com","Alice"));//보내는 사람
	 
	        return message;
	    }
	 
	    public static String createKey() {
	        StringBuffer key = new StringBuffer();
	        Random rnd = new Random();
	 
	        for (int i = 0; i < 8; i++) { // 인증코드 8자리
	            int index = rnd.nextInt(3); // 0~2 까지 랜덤
	 
	            switch (index) {
	                case 0:
	                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
	                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
	                    break;
	                case 1:
	                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
	                    //  A~Z
	                    break;
	                case 2:
	                    key.append((rnd.nextInt(10)));
	                    // 0~9
	                    break;
	            }
	        }
	 
	        return key.toString();
	    }
	    
	    //인증 번호 반납
	    public String sendSimpleMessage(String to)throws Exception {
	        // TODO Auto-generated method stub
	        MimeMessage message = createMessage(to);
	        try{//예외처리
	            emailSender.send(message);
	        }catch(MailException es){
	            es.printStackTrace();
	            throw new IllegalArgumentException();
	        }
	        return ePw;
	    }


		@Override
		public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException { //로그인 할 유저의 id를 파라미터로 전달받음
			Member member = memberRepository.findById(id);
			
			if(member == null) {
				throw new UsernameNotFoundException(id);
			}
			/*UserDetail을 구현하고 있는 User 객체 반환
			  User객체를 생성하기 위해 생성자로 회원의 아이디, 비밀번호, status를 파라미터로 넘겨 줌
			*/
			return User.builder()
					.username(member.getId())
					.password(member.getPwd())
					.roles(member.getStatus().toString())
					.build();
		}
	    
	    
	    
	
 }
