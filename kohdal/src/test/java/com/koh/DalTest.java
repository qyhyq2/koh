package com.koh;

import com.koh.entity.Company;
import com.koh.mapper.CompanyMapper;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

public class DalTest extends AbstractServiceTest {

    @Resource
    private CompanyMapper companyMapper;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void test() throws Exception {
        companyMapper.save(Company.builder().address("123").age(12).name("321").salary(100.1f).build());
    }

}