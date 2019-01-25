package com.ck.lmmanagement.service.impl;

import com.ck.lmmanagement.domain.IndexQuery;
import com.ck.lmmanagement.mapper.IndexMapper;
import com.ck.lmmanagement.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 01378803
 * @date 2019/1/25 9:56
 * Description  :
 */
@Service
public class IndexServiceImpl extends BaseServiceImpl<IndexQuery> implements IndexService {
    @Autowired
    private IndexMapper indexMapper;
}
