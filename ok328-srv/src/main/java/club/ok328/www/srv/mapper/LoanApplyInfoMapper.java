package club.ok328.www.srv.mapper;


import club.ok328.www.srv.entity.LoanApplyInfo;

import java.util.List;

public interface LoanApplyInfoMapper {
    int deleteByPrimaryKey(String applyNo);

    int insert(LoanApplyInfo record);

    int insertSelective(LoanApplyInfo record);

    LoanApplyInfo selectByPrimaryKey(String applyNo);

    List<LoanApplyInfo> selectByCond();

    int updateByPrimaryKeySelective(LoanApplyInfo record);

    int updateByPrimaryKey(LoanApplyInfo record);
}