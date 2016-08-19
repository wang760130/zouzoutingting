package com.zouzoutingting.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.Feedback;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年4月24日
 */
@Transactional
@Repository(value="feedbackDao")
public class FeedbackDaoImpl extends DaoImpl<Feedback> implements IDao<Feedback> {}
