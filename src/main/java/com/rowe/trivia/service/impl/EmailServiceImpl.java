package com.rowe.trivia.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.rowe.trivia.domain.User;
import com.rowe.trivia.domain.UserQuestion;
import com.rowe.trivia.service.EmailService;

import freemarker.cache.URLTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class EmailServiceImpl implements EmailService{
	private static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	private Configuration configuration;
	private JavaMailSender mailSender;
	private String applicationURL;
	private InternetAddress fromAddress;
	
	public EmailServiceImpl(String applicationURL) {
		super();
		this.mailSender = new GaeJavaMailSender();
		this.applicationURL = applicationURL;
		setupConfiguration();
	}
	
	@Override
	public void notifyUserOfNewQuestionIfNeeded(final UserQuestion userQuestion) {
		final User contestant = userQuestion.getContestant();
		if(!contestant.isEmailNotificationEnabled()){
			// or the user has been notified recently.	
			return;
		}
		if(StringUtils.isBlank(contestant.getEmail())){
			logger.warn("No email address so email not sent: {}", contestant);
			return;
		}
		logger.info("Notifying user of available question {}", userQuestion);
		mailSender.send(new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage message) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
				if(fromAddress != null){
					helper.setFrom(fromAddress);
				}
				helper.addTo(contestant.getEmail(), contestant.getDisplayName());
				helper.setSubject(contestant.getDisplayName() + ", you have a question waiting!");
				helper.setText(buildQuestionAskedBody(userQuestion), true);
			}
		});
	}
	
	@Override
	public void notifyUserOfWinningQuestion(UserQuestion userQuestion) {
		User contestant = userQuestion.getContestant();
		if(!contestant.isEmailNotificationEnabled()){
			return;
		}
		logger.info("Notifying user of winning {}", userQuestion);
		//TODO: implement me
	}
	
	private void questionAsked(UserQuestion userQuestion) {
		final User user = userQuestion.getContestant();
		if(StringUtils.isBlank(user.getEmail())){
			logger.warn("No email address so email not sent: {}", user);
			return;
		}
		final String body = buildQuestionAskedBody(userQuestion);
		logger.info("Using body {}", body);
		//TODO: clean up this mess
		mailSender.send(new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
				if(fromAddress != null){
					helper.setFrom(fromAddress);
				}
				helper.setTo(new InternetAddress(user.getEmail(), user.getDisplayName()));
				helper.setSubject(user.getDisplayName() + ", you have a question waiting!");
				helper.setText(body, true);
			}
		});
		logger.info("Sent questionAskedEmail to {}", user);
	}

	String buildQuestionAskedBody(UserQuestion userQuestion){
		final Map<String, Object> model = new HashMap<String, Object>();
		model.put("userQuestion", userQuestion);
		model.put("applicationURL", applicationURL);
		return generateToString("/com/rowe/trivia/service/questionAskedEmail.ftl", model);
	}
	
	private String generateToString(String templateName, Object model) {
		StringWriter writer = new StringWriter();
		generate(templateName, model, writer);
		return writer.toString();
	}
	
	private void generate(String name, Object model, Writer writer) {
		try {
			final Template template = configuration.getTemplate(name);
			template.process(model, writer);
		}catch(IOException e){
			throw new RuntimeException(e);
		} catch (TemplateException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void setupConfiguration()  {
		configuration = new Configuration();
		configuration.setTemplateLoader(new ClasspathTemplateLoader());
	}
	
	public void setFromAddress(String address) {
		try {
			this.fromAddress = new InternetAddress(address, "PrizeMat");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		} 
	}
	
	private static class ClasspathTemplateLoader extends URLTemplateLoader {
		@Override
		protected URL getURL(String name) {
			try {
				Resource res = new ClassPathResource(name);
				if(!res.isReadable()){
					return null;
				}
				return res.getURL();
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}
	
	private static class GaeJavaMailSender extends JavaMailSenderImpl {

	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    public Session getSession() {
	        return Session.getDefaultInstance(new Properties(), null);
	    }

	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    protected Transport getTransport(Session session) throws NoSuchProviderException {
	        return new Transport(session, null) {
	            @Override
	            public void connect(String host, int port, String username, String password) throws MessagingException {
	                // No need to connect for GAE's mail implementation
	            }

	            @Override
	            public void close() throws MessagingException {
	                // No need to close a connection for GAE's mail implementation
	            }

	            @Override
	            public void sendMessage(Message message, Address[] recipients) throws MessagingException {
	                Transport.send(message, recipients);
	            }
	        };
	    }
	}
}
