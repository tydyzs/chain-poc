package org.git.modules.clm.batch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.git.modules.clm.batch.mapper.BatchDataRecordMapper;
import org.git.modules.clm.batch.service.IBatDataRecordService;
import org.git.modules.clm.batch.vo.TbBatDataRecord;
import org.springframework.stereotype.Service;

@Service
public class BatDataRecordServiceImpl extends ServiceImpl<BatchDataRecordMapper, TbBatDataRecord> implements IBatDataRecordService {

}
