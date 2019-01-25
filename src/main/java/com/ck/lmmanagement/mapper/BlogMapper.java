package com.ck.lmmanagement.mapper;

import com.ck.lmmanagement.domain.BlogQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 01378803
 * @date 2019/1/25 16:02
 * Description  :
 */
@Mapper
@Repository
public interface BlogMapper extends BaseMapper<BlogQuery> {
}
