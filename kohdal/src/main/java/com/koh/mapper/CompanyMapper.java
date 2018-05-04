/**
 * @Author: qianyuhang
 * @Date: 2018-4-20
 */
package com.koh.mapper;

import com.koh.entity.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author qianyuhang
 * @date 2018-4-20
 */
@Repository
public interface CompanyMapper extends CrudRepository<Company, Integer> {

}