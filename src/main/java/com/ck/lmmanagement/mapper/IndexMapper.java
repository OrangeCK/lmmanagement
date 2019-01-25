package com.ck.lmmanagement.mapper;

import com.ck.lmmanagement.domain.IndexQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 01378803
 * @date 2019/1/25 9:57
 * Description  :
 */
@Mapper
@Repository
public interface IndexMapper extends BaseMapper<IndexQuery> {
}
