package com.alice.project.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.alice.project.domain.Member;
import com.alice.project.repository.NotificationRepository;
import com.alice.project.web.MemberAccount;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationInterceptor implements HandlerInterceptor {

	private final NotificationRepository notificationRepository;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (modelAndView != null && !isRedirectView(modelAndView) && authentication != null
				&& authentication.getPrincipal() instanceof MemberAccount) {
			Member member = ((MemberAccount) authentication.getPrincipal()).getMember();
			long count = notificationRepository.countByMemberAndChecked(member, false);
			modelAndView.addObject("hasNotification", count > 0);
		}
	}

	private boolean isRedirectView(ModelAndView modelAndView) {
		return modelAndView.getViewName().startsWith("redirect:") || modelAndView.getView() instanceof RedirectView;
	}
}
