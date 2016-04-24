package com.zouzoutingting.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.Feedback;
import com.zouzoutingting.service.IFeedbackService;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年4月24日
 */
@Service("feedbackService")
public class FeedbackServiceImpl implements IFeedbackService {

	@Autowired
	private IDao<Feedback> feedbackDao = null;
	
	@Override
	public void add(String message) {
		Feedback feedback = new Feedback();
		feedback.setMessage(message);
		feedback.setAddtime(new Date());
		feedbackDao.save(feedback);
	}
}
