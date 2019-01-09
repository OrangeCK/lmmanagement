package com.ck.lmmanagement.mapper;

import com.ck.lmmanagement.domain.Image;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 01378803
 * @date 2019/1/7 14:03
 * Description  :
 */
@Mapper
@Repository
public interface ImageMapper extends BaseMapper<Image> {
}
