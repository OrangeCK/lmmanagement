package com.ck.lmmanagement.service.impl;

import com.ck.lmmanagement.domain.BlogQuery;
import com.ck.lmmanagement.mapper.BlogMapper;
import com.ck.lmmanagement.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 01378803
 * @date 2019/1/25 16:01
 * Description  :
 */
@Service
public class BlogServiceImpl extends BaseServiceImpl<BlogQuery> implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
}
