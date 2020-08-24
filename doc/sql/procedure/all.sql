CREATE FUNCTION FNC_GET_UUID(IN IN_PARAMETER VARCHAR(4))
    RETURNS VARCHAR(32)
SPECIFIC SQL191212104147452
BEGIN
    --声明变量
    DECLARE SEQ_SIX_NUM VARCHAR(6);
    DECLARE SRETURN VARCHAR(28) DEFAULT '';
    DECLARE SEQ_NUM INTEGER;
    --设置变量值
    SET SEQ_NUM = (seq_six_num.nextval);
    SET SEQ_SIX_NUM = (SELECT (right(digits(SEQ_NUM), 6)) FROM SYSIBM.SYSDUMMY1);
    SET SRETURN = (select VARCHAR_FORMAT(current timestamp,'yyyyMMddHHmissSSSSS')||SEQ_SIX_NUM from sysibm.sysdummy1);
    --返回值
    RETURN IN_PARAMETER || SRETURN;
END
;

CREATE FUNCTION FNC_GET_BAT_DATE ()
    RETURNS VARCHAR (20)
    LANGUAGE SQL
    RETURN SELECT t.BATCH_DATE
           FROM TB_SYS_DATE t;

CREATE FUNCTION CLM.FNC_GET_TRAN_SEQ ( )
  RETURNS VARCHAR(20)
  SPECIFIC SQL191220161504539
  NOT SECURED
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  READS SQL DATA
  INHERIT SPECIAL REGISTERS
BEGIN
/*
	该函数的作用是产生一个流水号，流水号产生规则是：
	获取授信流水表的最大流水号、用信流水表的最大流水号，通过比较两个流水号的最大值，取最大的那个值+1作为新的流水号
*/
    --声明变量
    DECLARE N_TRAN_SEQ_SN	VARCHAR(20);---产生的新交易流水号
    DECLARE GRANT_SEQ_SN	BIGINT;--授信流水号最大值
	DECLARE USE_SEQ_SN		BIGINT;--用信流水号最大值
    DECLARE TRAN_SEQ_NUM	BIGINT;--最大流水号值
	DECLARE V_TRAN_SYSTEM	VARCHAR(20);--交易系统
	--设定值
	  SET V_TRAN_SYSTEM = '0010';--资金系统的系统号
    SET GRANT_SEQ_SN = (SELECT MAX(BIGINT((SUBSTR(T.TRAN_SEQ_SN,8,LENGTH(T.TRAN_SEQ_SN)-7))))FROM TB_CRD_GRANTING_SERIAL T );--WHERE T.TRAN_SYSTEM = V_TRAN_SYSTEM
    SET USE_SEQ_SN = (SELECT MAX(BIGINT((SUBSTR(T1.TRAN_SEQ_SN,8,LENGTH(T1.TRAN_SEQ_SN)-7))))FROM TB_CRD_APPLY_SERIAL T1);-- WHERE T1.TRAN_SYSTEM = V_TRAN_SYSTEM);
	IF GRANT_SEQ_SN > USE_SEQ_SN
	THEN
		SET TRAN_SEQ_NUM = (GRANT_SEQ_SN+1);
	ELSE
		SET TRAN_SEQ_NUM = (USE_SEQ_SN+1);
    END IF;
    --返回值
    RETURN 'CR00001' || TO_CHAR(TRAN_SEQ_NUM);
END;

CREATE FUNCTION FNC_GET_BIZ_NUM(IN IN_PARAMETER VARCHAR(4))
    RETURNS VARCHAR(20)
    SPECIFIC SQL191104181901290
    LANGUAGE SQL
    NOT DETERMINISTIC
    EXTERNAL ACTION
    READS SQL DATA
    INHERIT SPECIAL REGISTERS
BEGIN
    --声明变量
    DECLARE SEQ_SIX_NUM VARCHAR(6);
    DECLARE SRETURN VARCHAR(20) DEFAULT '';
    DECLARE SEQ_NUM INTEGER;
    --设置变量值
    SET SEQ_NUM = (seq_six_num.nextval);
    SET SEQ_SIX_NUM = (SELECT (right(digits(SEQ_NUM), 6)) FROM SYSIBM.SYSDUMMY1);
    SET SRETURN = (SELECT replace(t.WORK_DATE,'-','')|| SEQ_SIX_NUM from TB_SYS_DATE t);
    --返回值
    RETURN IN_PARAMETER || SRETURN;
END;

CREATE FUNCTION FNC_GET_BUSI_time()
    RETURNS timestamp

    --获取当前营业时间，带时分秒
    RETURN SELECT to_date(
                              t.WORK_DATE
                              || to_char(CURRENT_TIMESTAMP, 'hh24:mi:ss'),
                              'yyyy-mm-dd hh24:mi:ss')
           FROM TB_SYS_DATE t;

CREATE PROCEDURE PRC_CREDIT_STATIS()
BEGIN

    /**
      统计实时额度信息
     */
    DECLARE V_WORK_TIME TIMESTAMP; --营业时间
    SET V_WORK_TIME = CLM.FNC_GET_BUSI_TIME();

    --额度统计表-额度品种（历史+实时）
    delete from TB_CRD_STATIS_CRDPT t where t.DATA_TYPE = '1';--删除实时数据，再重新插入
    insert into TB_CRD_STATIS_CRDPT(STATIS_ID,
                                    APPROVE_COUNT, APPROVE_EXP_AMOUNT, CREDIT_EXP_BALANCE, LOAN_EXP_BALANCE,
                                    LIMIT_CREDIT, LIMIT_AVI, LIMIT_USED, EXP_USED, EXP_AVI, CRD_DETAIL_PRD,
                                    ORG_NUM, DATA_TYPE, CREATE_TIME, UPDATE_TIME)
    select CLM.FNC_GET_UUID('TJ')    STATIS_ID,
           sum(t.APPROVE_COUNT)      APPROVE_COUNT,--批复数量
           sum(t.APPROVE_EXP_AMOUNT) APPROVE_EXP_AMOUNT,--批复敞口金额
           sum(t.APPROVE_EXP_AMOUNT) CREDIT_EXP_BALANCE,--授信敞口余额
           sum(t.LOAN_EXP_BALANCE)   LOAN_EXP_BALANCE,--贷款敞口余额
           sum(t.LIMIT_CREDIT)       LIMIT_CREDIT,--授信额度
           sum(t.LIMIT_AVI)          LIMIT_AVI,--授信额度可用
           sum(t.LIMIT_USED)         LIMIT_USED,--授信额度已用
           sum(t.EXP_USED)           EXP_USED,--敞口已用
           sum(t.EXP_AVI)            EXP_AVI,--敞口可用
           t.CRD_DETAIL_PRD,
           t.ORG_NUM,
           '1',--实时数据
           V_WORK_TIME               CREATE_TIME,
           V_WORK_TIME               UPDATE_TIME
    from TB_CRD_STATIS t
    group by t.ORG_NUM, t.CRD_DETAIL_PRD;


    --额度统计表-企业规模（历史+实时）
    delete from TB_CRD_STATIS_USCALE t where t.DATA_TYPE = '1';--删除实时数据，再重新插入
    insert into TB_CRD_STATIS_USCALE(STATIS_ID,
                                     APPROVE_COUNT, APPROVE_EXP_AMOUNT, CREDIT_EXP_BALANCE, LOAN_EXP_BALANCE,
                                     LIMIT_CREDIT, LIMIT_AVI, LIMIT_USED, EXP_USED, EXP_AVI, UNIT_SCALE,
                                     ORG_NUM, DATA_TYPE, CREATE_TIME, UPDATE_TIME)
    select CLM.FNC_GET_UUID('TJ')    STATIS_ID,
           sum(t.APPROVE_COUNT)      APPROVE_COUNT,--批复数量
           sum(t.APPROVE_EXP_AMOUNT) APPROVE_EXP_AMOUNT,--批复敞口金额
           sum(t.APPROVE_EXP_AMOUNT) CREDIT_EXP_BALANCE,--授信敞口余额
           sum(t.LOAN_EXP_BALANCE)   LOAN_EXP_BALANCE,--贷款敞口余额
           sum(t.LIMIT_CREDIT)       LIMIT_CREDIT,--授信额度
           sum(t.LIMIT_AVI)          LIMIT_AVI,--授信额度可用
           sum(t.LIMIT_USED)         LIMIT_USED,--授信额度已用
           sum(t.EXP_USED)           EXP_USED,--敞口已用
           sum(t.EXP_AVI)            EXP_AVI,--敞口可用
           t.UNIT_SCALE,--企业规模
           t.ORG_NUM,
           '1',--实时数据
           V_WORK_TIME               CREATE_TIME,
           V_WORK_TIME               UPDATE_TIME
    from TB_CRD_STATIS t
    where t.UNIT_SCALE is not null
    group by t.ORG_NUM, t.UNIT_SCALE;

    --额度统计表-客户类型（历史+实时）
    delete from tb_crd_statis_custype t where t.DATA_TYPE = '1';--删除实时数据，再重新插入
    insert into tb_crd_statis_custype(STATIS_ID,
                                      APPROVE_COUNT, APPROVE_EXP_AMOUNT, CREDIT_EXP_BALANCE, LOAN_EXP_BALANCE,
                                      LIMIT_CREDIT, LIMIT_AVI, LIMIT_USED, EXP_USED, EXP_AVI, CUSTOMER_TYPE,
                                      ORG_NUM, DATA_TYPE, CREATE_TIME, UPDATE_TIME)
    select CLM.FNC_GET_UUID('TJ')    STATIS_ID,
           sum(t.APPROVE_COUNT)      APPROVE_COUNT,--批复数量
           sum(t.APPROVE_EXP_AMOUNT) APPROVE_EXP_AMOUNT,--批复敞口金额
           sum(t.APPROVE_EXP_AMOUNT) CREDIT_EXP_BALANCE,--授信敞口余额
           sum(t.LOAN_EXP_BALANCE)   LOAN_EXP_BALANCE,--贷款敞口余额
           sum(t.LIMIT_CREDIT)       LIMIT_CREDIT,--授信额度
           sum(t.LIMIT_AVI)          LIMIT_AVI,--授信额度可用
           sum(t.LIMIT_USED)         LIMIT_USED,--授信额度已用
           sum(t.EXP_USED)           EXP_USED,--敞口已用
           sum(t.EXP_AVI)            EXP_AVI,--敞口可用
           t.CUSTOMER_TYPE,--客户类型
           t.ORG_NUM,
           '1',--实时数据
           V_WORK_TIME               CREATE_TIME,
           V_WORK_TIME               UPDATE_TIME
    from TB_CRD_STATIS t
    group by t.ORG_NUM, t.CUSTOMER_TYPE;

    --额度统计表-集团客户类型（历史+实时）
    insert into tb_crd_statis_custype(STATIS_ID,
                                      APPROVE_COUNT, APPROVE_EXP_AMOUNT, CREDIT_EXP_BALANCE, LOAN_EXP_BALANCE,
                                      LIMIT_CREDIT, LIMIT_AVI, LIMIT_USED, EXP_USED, EXP_AVI, CUSTOMER_TYPE,
                                      ORG_NUM, DATA_TYPE, CREATE_TIME, UPDATE_TIME)
    select CLM.FNC_GET_UUID('TJ')    STATIS_ID,
           sum(t.APPROVE_COUNT)      APPROVE_COUNT,--批复数量
           sum(t.APPROVE_EXP_AMOUNT) APPROVE_EXP_AMOUNT,--批复敞口金额
           sum(t.APPROVE_EXP_AMOUNT) CREDIT_EXP_BALANCE,--授信敞口余额
           sum(t.LOAN_EXP_BALANCE)   LOAN_EXP_BALANCE,--贷款敞口余额
           sum(t.LIMIT_CREDIT)       LIMIT_CREDIT,--授信额度
           sum(t.LIMIT_AVI)          LIMIT_AVI,--授信额度可用
           sum(t.LIMIT_USED)         LIMIT_USED,--授信额度已用
           sum(t.EXP_USED)           EXP_USED,--敞口已用
           sum(t.EXP_AVI)            EXP_AVI,--敞口可用
           '4',--客户类型
           t.ORG_NUM,
           '1',--实时数据
           V_WORK_TIME               CREATE_TIME,
           V_WORK_TIME               UPDATE_TIME
    from TB_CRD_STATIS t,
         TB_CSM_GROUP_MEMBER a
    where a.MEMBER_CUSTOMER_NUM = t.CUSTOMER_NUM
    group by t.ORG_NUM, a.CUSTOMER_NUM;

    --额度统计表-担保方式（历史+实时）
    delete from TB_CRD_STATIS_GTYPE t where t.DATA_TYPE = '1';--删除实时数据，再重新插入
    insert into TB_CRD_STATIS_GTYPE(STATIS_ID,
                                    APPROVE_COUNT, APPROVE_EXP_AMOUNT, CREDIT_EXP_BALANCE, LOAN_EXP_BALANCE,
                                    LIMIT_CREDIT, LIMIT_AVI, LIMIT_USED, EXP_USED, EXP_AVI, GUARANTEE_TYPE,
                                    ORG_NUM, DATA_TYPE, CREATE_TIME, UPDATE_TIME)
    select CLM.FNC_GET_UUID('TJ')    STATIS_ID,
           sum(t.APPROVE_COUNT)      APPROVE_COUNT,--批复数量
           sum(t.APPROVE_EXP_AMOUNT) APPROVE_EXP_AMOUNT,--批复敞口金额
           sum(t.APPROVE_EXP_AMOUNT) CREDIT_EXP_BALANCE,--授信敞口余额
           sum(t.LOAN_EXP_BALANCE)   LOAN_EXP_BALANCE,--贷款敞口余额
           sum(t.LIMIT_CREDIT)       LIMIT_CREDIT,--授信额度
           sum(t.LIMIT_AVI)          LIMIT_AVI,--授信额度可用
           sum(t.LIMIT_USED)         LIMIT_USED,--授信额度已用
           sum(t.EXP_USED)           EXP_USED,--敞口已用
           sum(t.EXP_AVI)            EXP_AVI,--敞口可用
           t.GUARANTEE_TYPE,--担保方式
           t.ORG_NUM,
           '1',--实时数据
           V_WORK_TIME               CREATE_TIME,
           V_WORK_TIME               UPDATE_TIME
    from TB_CRD_STATIS t
    group by t.ORG_NUM, t.GUARANTEE_TYPE;


    --额度统计表-行业（历史+实时）
    delete from TB_CRD_STATIS_INDUSTRY t where t.DATA_TYPE = '1';--删除实时数据，再重新插入
    insert into TB_CRD_STATIS_INDUSTRY(STATIS_ID,
                                       APPROVE_COUNT, APPROVE_EXP_AMOUNT, CREDIT_EXP_BALANCE, LOAN_EXP_BALANCE,
                                       LIMIT_CREDIT, LIMIT_AVI, LIMIT_USED, EXP_USED, EXP_AVI, INDUSTRY,
                                       ORG_NUM, DATA_TYPE, CREATE_TIME, UPDATE_TIME)
    select CLM.FNC_GET_UUID('TJ')    STATIS_ID,
           sum(t.APPROVE_COUNT)      APPROVE_COUNT,--批复数量
           sum(t.APPROVE_EXP_AMOUNT) APPROVE_EXP_AMOUNT,--批复敞口金额
           sum(t.APPROVE_EXP_AMOUNT) CREDIT_EXP_BALANCE,--授信敞口余额
           sum(t.LOAN_EXP_BALANCE)   LOAN_EXP_BALANCE,--贷款敞口余额
           sum(t.LIMIT_CREDIT)       LIMIT_CREDIT,--授信额度
           sum(t.LIMIT_AVI)          LIMIT_AVI,--授信额度可用
           sum(t.LIMIT_USED)         LIMIT_USED,--授信额度已用
           sum(t.EXP_USED)           EXP_USED,--敞口已用
           sum(t.EXP_AVI)            EXP_AVI,--敞口可用
           t.INDUSTRY_MAIN           INDUSTRY,--行业
           t.ORG_NUM,
           '1',--实时数据
           V_WORK_TIME               CREATE_TIME,
           V_WORK_TIME               UPDATE_TIME
    from (
             select a.*, substr(a.INDUSTRY, 1, 1) INDUSTRY_MAIN from TB_CRD_STATIS a
         ) t
    group by t.ORG_NUM, t.INDUSTRY_MAIN;


    --额度统计表-业务品种（历史+实时）
    delete from TB_CRD_STATIS_PRODUCT t where t.DATA_TYPE = '1';--删除实时数据，再重新插入
    insert into TB_CRD_STATIS_PRODUCT(STATIS_ID,
                                      APPROVE_COUNT, APPROVE_EXP_AMOUNT, CREDIT_EXP_BALANCE, LOAN_EXP_BALANCE,
                                      LIMIT_CREDIT, LIMIT_AVI, LIMIT_USED, EXP_USED, EXP_AVI, PRODUCT_NUM,
                                      ORG_NUM, DATA_TYPE, CREATE_TIME, UPDATE_TIME)
    select CLM.FNC_GET_UUID('TJ')    STATIS_ID,
           sum(t.APPROVE_COUNT)      APPROVE_COUNT,--批复数量
           sum(t.APPROVE_EXP_AMOUNT) APPROVE_EXP_AMOUNT,--批复敞口金额
           sum(t.APPROVE_EXP_AMOUNT) CREDIT_EXP_BALANCE,--授信敞口余额
           sum(t.LOAN_EXP_BALANCE)   LOAN_EXP_BALANCE,--贷款敞口余额
           sum(t.LIMIT_CREDIT)       LIMIT_CREDIT,--授信额度
           sum(t.LIMIT_AVI)          LIMIT_AVI,--授信额度可用
           sum(t.LIMIT_USED)         LIMIT_USED,--授信额度已用
           sum(t.EXP_USED)           EXP_USED,--敞口已用
           sum(t.EXP_AVI)            EXP_AVI,--敞口可用
           t.PRODUCT_NUM,--业务品种
           t.ORG_NUM,
           '1',--实时数据
           V_WORK_TIME               CREATE_TIME,
           V_WORK_TIME               UPDATE_TIME
    from TB_CRD_STATIS t
    group by t.ORG_NUM, t.PRODUCT_NUM;

    --额度统计表-机构（历史+实时）
    delete from TB_CRD_STATIS_ORG t where t.DATA_TYPE = '1';--删除实时数据，再重新插入
    insert into TB_CRD_STATIS_ORG(STATIS_ID, APPROVE_EXP_AMOUNT, CREDIT_EXP_BALANCE, LOAN_EXP_BALANCE,
                                  LIMIT_CREDIT, LIMIT_AVI, LIMIT_USED, EXP_USED, EXP_AVI, ORG_NUM,
                                  corporation_count, individual_count, bank_count, DATA_TYPE, CREATE_TIME, UPDATE_TIME)
    select CLM.FNC_GET_UUID('TJ')    STATIS_ID,
           sum(t.APPROVE_EXP_AMOUNT) APPROVE_EXP_AMOUNT,--批复敞口金额
           sum(t.APPROVE_EXP_AMOUNT) CREDIT_EXP_BALANCE,--授信敞口余额
           sum(t.LOAN_EXP_BALANCE)   LOAN_EXP_BALANCE,--贷款敞口余额
           sum(t.LIMIT_CREDIT)       LIMIT_CREDIT,--授信额度
           sum(t.LIMIT_AVI)          LIMIT_AVI,--授信额度可用
           sum(t.LIMIT_USED)         LIMIT_USED,--授信额度已用
           sum(t.EXP_USED)           EXP_USED,--敞口已用
           sum(t.EXP_AVI)            EXP_AVI,--敞口可用
           t.ORG_NUM,
           0                         corporation_count,
           0                         individual_count,
           0                         bank_count,
           '1',--实时数据
           V_WORK_TIME               CREATE_TIME,
           V_WORK_TIME               UPDATE_TIME
    from TB_CRD_STATIS t
    where t.ORG_NUM is not null
    group by t.ORG_NUM;

    for STATIS_ORG as (select b.ORG_NUM, a.CUSTOMER_TYPE, count(1) customer_count
                       from tb_csm_party a,
                            (
                                select t.ORG_NUM, t.CUSTOMER_NUM from TB_CRD_STATIS t group by t.ORG_NUM, t.CUSTOMER_NUM
                            ) b
                       where a.CUSTOMER_NUM = b.CUSTOMER_NUM
                       group by a.CUSTOMER_TYPE, b.ORG_NUM)
        do
            if (STATIS_ORG.CUSTOMER_TYPE = '1') then--个人客户
                update TB_CRD_STATIS_ORG t
                set t.INDIVIDUAL_COUNT=STATIS_ORG.customer_count
                where t.ORG_NUM = STATIS_ORG.ORG_NUM;
            elseif
                (STATIS_ORG.CUSTOMER_TYPE = '2') then--公司客户
                update TB_CRD_STATIS_ORG t
                set t.CORPORATION_COUNT=STATIS_ORG.customer_count
                where t.ORG_NUM = STATIS_ORG.ORG_NUM;
            elseif
                (STATIS_ORG.CUSTOMER_TYPE = '3') then--同业客户
                update TB_CRD_STATIS_ORG t
                set t.BANK_COUNT=STATIS_ORG.customer_count
                where t.ORG_NUM = STATIS_ORG.ORG_NUM;
            end if;
        end for;


END;

CREATE FUNCTION FNC_GET_BUSI_DATE ()
    RETURNS VARCHAR (20)
    LANGUAGE SQL
    RETURN SELECT t.WORK_DATE
           FROM TB_SYS_DATE t;

CREATE FUNCTION FNC_GET_HS_DATE(IN HS_TYPE VARCHAR(1), IN STYLE VARCHAR(8))
    RETURNS VARCHAR(10)
    LANGUAGE SQL
BEGIN
    /**
      参数1日期时段：当前0、同期1、年初2、上期3 的日期
      参数2日期风格：Y年份、M月份、D日，为空则返回6位日期，不区分大小写
     */
    --声明变量

    DECLARE V_HS_DATE VARCHAR(10);--返回的日期
    DECLARE V_BUSI_TIME timestamp(10);--当前系统日期
    DECLARE V_STYLE VARCHAR(10);--日期风格


    set V_BUSI_TIME = CLM.FNC_GET_BUSI_TIME();

    if STYLE is not null and TRIM(STYLE) <> '' then
        if UPPER(STYLE) = 'Y' then
            set V_STYLE = 'yyyy';
        elseif UPPER(STYLE) = 'M' then
            set V_STYLE = 'MM';
        elseif UPPER(STYLE) = 'D' then
            set V_STYLE = 'dd';
        else
            set V_STYLE = STYLE;
        end if;
    else
        set V_STYLE = 'yyyyMM';
    end if;

    if (HS_TYPE = 0) then--当前
        set V_HS_DATE = to_char(V_BUSI_TIME, V_STYLE) ;
    elseif (HS_TYPE = 1) then--同期（指去年同月份）
        set V_HS_DATE = to_char(V_BUSI_TIME - 12 month, V_STYLE) ;
    elseif (HS_TYPE = 2) then--年初（今年一月份）
        set V_HS_DATE = to_char(to_date(to_char(year(V_BUSI_TIME) - 1) || '1231', 'yyyyMMdd') , V_STYLE);
    elseif (HS_TYPE = 3) then--上期（上个月）
        set V_HS_DATE = to_char(V_BUSI_TIME - 1 month, V_STYLE) ;
    end if;

    --返回值
    RETURN V_HS_DATE;
END;

CREATE PROCEDURE CLM.PRC_CREDIT_STATIS_HS ( )
  SPECIFIC SQL191226184108454
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN

    /**
      统计历史额度信息，月末时，将当前统计数据，记录一份在表中
      CHENCHUAN 2019-12-03
     */
    DECLARE V_LAST_DATE VARCHAR(10);--月末最后一天
    DECLARE V_WORK_DATE VARCHAR(10);--当前系统日期
    DECLARE V_WORK_TIME TIMESTAMP;--当前系统日期
    DECLARE V_BAT_DATE VARCHAR(10);--当前批量日期

    DECLARE V_YEAR VARCHAR(6); --年
    DECLARE V_MONTH VARCHAR(2); --月
    DECLARE V_DATE VARCHAR(2);


    SET V_WORK_TIME = CLM.FNC_GET_BUSI_TIME(); --当前营业时间
    set V_WORK_DATE = CLM.FNC_GET_BUSI_DATE();
    SET V_BAT_DATE = CLM.FNC_GET_BAT_DATE();--当前批量日期
    SET V_YEAR = SUBSTR(V_BAT_DATE, 1, 4);
    SET V_MONTH = SUBSTR(V_BAT_DATE, 6, 2);
    SET V_DATE = SUBSTR(V_BAT_DATE, 9, 2);
    --获取本月最后一天的日期
    SET V_LAST_DATE = TO_CHAR(LAST_DAY(V_WORK_TIME), 'YYYY-MM-DD');


    --如果当前营业日期不是月末，跳过处理
    IF V_LAST_DATE != V_BAT_DATE THEN
        RETURN;
    END IF;


    --额度明细历史表
    DELETE FROM TB_CRD_DETAIL_HS T WHERE T.YEAR = V_YEAR AND T.MONTH = V_MONTH;

    INSERT INTO TB_CRD_DETAIL_HS(STATIS_ID, CRD_DETAIL_NUM, CRD_MAIN_NUM, CRD_DETAIL_PRD,CRD_PRODUCT_TYPE,
                                 CRD_GRANT_ORG_NUM, CUSTOMER_NUM, CRD_ADMIT_FLAG, CURRENCY_CD,
                                 EXCHANGE_RATE, BEGIN_DATE, END_DATE, LIMIT_CREDIT, LIMIT_AVI,
                                 LIMIT_USED, EXP_CREDIT, EXP_USED, EXP_AVI, LIMIT_PRE, EXP_PRE,
                                 LIMIT_EARMARK, earmark_begin_date, earmark_end_date,
                                 LIMIT_FROZEN, EXP_FROZEN, IS_CYCLE, IS_MIX, MIXREMARK,
                                 CLOSE_DATE, CLOSE_REASON, IS_CONTINUE,
                                 TRAN_SYSTEM, USER_NUM, ORG_NUM, YEAR, MONTH, DATE, CREATE_TIME, UPDATE_TIME)
    SELECT CLM.FNC_GET_UUID('TJ') STATIS_ID,
           T.CRD_DETAIL_NUM,
           T.CRD_MAIN_NUM,
           T.CRD_DETAIL_PRD,
           t.CRD_PRODUCT_TYPE,
           T.CRD_GRANT_ORG_NUM,
           T.CUSTOMER_NUM,
           T.CRD_ADMIT_FLAG,
           T.CURRENCY_CD,
           T.EXCHANGE_RATE,
           T.BEGIN_DATE,
           T.END_DATE,
           T.LIMIT_CREDIT,
           T.LIMIT_AVI,
           T.LIMIT_USED,
           T.EXP_CREDIT,
           T.EXP_USED,
           T.EXP_AVI,
           T.LIMIT_PRE,
           T.EXP_PRE,
           T.LIMIT_EARMARK,
           T.earmark_begin_date,
           T.earmark_end_date,
           T.LIMIT_FROZEN,
           T.EXP_FROZEN,
           T.IS_CYCLE,
           T.IS_MIX,
           T.MIXREMARK,
           T.CLOSE_DATE,
           T.CLOSE_REASON,
           T.IS_CONTINUE,
           T.TRAN_SYSTEM,
           T.USER_NUM,
           T.ORG_NUM,
           V_YEAR  AS         YEAR,
           V_MONTH AS         MONTH,
           V_DATE  AS         DATE,
           V_WORK_TIME        CREATE_TIME,
           V_WORK_TIME        UPDATE_TIME
    FROM TB_CRD_DETAIL T;

    --额度主表历史
    DELETE FROM TB_CRD_MAIN_HS T WHERE T.YEAR = V_YEAR AND T.MONTH = V_MONTH;
    INSERT INTO TB_CRD_MAIN_HS(STATIS_ID, CRD_MAIN_NUM, CRD_MAIN_PRD, CRD_PRODUCT_TYPE,CRD_GRANT_ORG_NUM,
                               CUSTOMER_NUM, CURRENCY_CD, EXCHANGE_RATE, CREDIT_STATUS, LIMIT_CREDIT,
                               LIMIT_USED, LIMIT_AVI, EXP_CREDIT, EXP_USED, EXP_AVI, LIMIT_PRE, EXP_PRE,
                               LIMIT_FROZEN, EXP_FROZEN, BEGIN_DATE, END_DATE, FROZEN_DATE, OVER_DATE,
                               TRAN_SYSTEM, ORG_NUM, USER_NUM, YEAR, MONTH, DATE, CREATE_TIME, UPDATE_TIME)
    SELECT CLM.FNC_GET_UUID('TJ') STATIS_ID,
           t.CRD_MAIN_NUM,
           t.CRD_MAIN_PRD,
           t.CRD_GRANT_ORG_NUM,
           t.CRD_PRODUCT_TYPE,
           t.CUSTOMER_NUM,
           t.CURRENCY_CD,
           t.EXCHANGE_RATE,
           t.CREDIT_STATUS,
           t.LIMIT_CREDIT,
           t.LIMIT_USED,
           t.LIMIT_AVI,
           t.EXP_CREDIT,
           t.EXP_USED,
           t.EXP_AVI,
           t.LIMIT_PRE,
           t.EXP_PRE,
           t.LIMIT_FROZEN,
           t.EXP_FROZEN,
           t.BEGIN_DATE,
           t.END_DATE,
           t.FROZEN_DATE,
           t.OVER_DATE,
           t.TRAN_SYSTEM,
           t.ORG_NUM,
           t.USER_NUM,
           V_YEAR      AS     YEAR,
           V_MONTH     AS     MONTH,
           V_DATE      AS     DATE,
           V_WORK_TIME AS     CREATE_TIME,
           V_WORK_TIME AS     UPDATE_TIME
    FROM TB_CRD_MAIN T;


    --客户额度历史
    --额度统计表-额度品种（历史+实时）
    DELETE FROM TB_CRD_STATIS_CSM_HS T WHERE  T.YEAR = V_YEAR AND T.MONTH = V_MONTH;--删除历史数据，再重新插入
    INSERT INTO TB_CRD_STATIS_CSM_HS(STATIS_ID,
                                    APPROVE_COUNT, APPROVE_EXP_AMOUNT, CREDIT_EXP_BALANCE, LOAN_EXP_BALANCE,
                                    LIMIT_CREDIT, LIMIT_AVI, LIMIT_USED, EXP_USED, EXP_AVI,customer_num,
                                    ORG_NUM, YEAR, MONTH, DATE, CREATE_TIME, UPDATE_TIME)
    SELECT CLM.FNC_GET_UUID('TJ') STATIS_ID,
           APPROVE_COUNT,
           APPROVE_EXP_AMOUNT,
           CREDIT_EXP_BALANCE,
           LOAN_EXP_BALANCE,
           LIMIT_CREDIT,
           LIMIT_AVI,
           LIMIT_USED,
           EXP_USED,
           EXP_AVI,
           CUSTOMER_NUM,
           ORG_NUM,
           V_YEAR  AS         YEAR,
           V_MONTH AS         MONTH,
           V_DATE  AS         DATE,
           V_WORK_TIME        CREATE_TIME,
           V_WORK_TIME        UPDATE_TIME
    FROM TB_CRD_STATIS_CSM T; --实时数据


    --额度统计表-额度品种（历史+实时）
    DELETE FROM TB_CRD_STATIS_CRDPT T WHERE T.DATA_TYPE = '2' AND T.YEAR = V_YEAR AND T.MONTH = V_MONTH;--删除历史数据，再重新插入

    INSERT INTO TB_CRD_STATIS_CRDPT(STATIS_ID,
                                    APPROVE_COUNT, APPROVE_EXP_AMOUNT, CREDIT_EXP_BALANCE, LOAN_EXP_BALANCE,
                                    LIMIT_CREDIT, LIMIT_AVI, LIMIT_USED, EXP_USED, EXP_AVI, CRD_DETAIL_PRD,
                                    ORG_NUM, DATA_TYPE, YEAR, MONTH, DATE, CREATE_TIME, UPDATE_TIME)
    SELECT CLM.FNC_GET_UUID('TJ') STATIS_ID,
           APPROVE_COUNT,
           APPROVE_EXP_AMOUNT,
           CREDIT_EXP_BALANCE,
           LOAN_EXP_BALANCE,
           LIMIT_CREDIT,
           LIMIT_AVI,
           LIMIT_USED,
           EXP_USED,
           EXP_AVI,
           CRD_DETAIL_PRD,
           ORG_NUM,
           '2'                DATA_TYPE,
           V_YEAR  AS         YEAR,
           V_MONTH AS         MONTH,
           V_DATE  AS         DATE,
           V_WORK_TIME        CREATE_TIME,
           V_WORK_TIME        UPDATE_TIME
    FROM TB_CRD_STATIS_CRDPT T --实时数据
    WHERE T.DATA_TYPE = '1';


    --额度统计表-企业规模（历史+实时）
    DELETE FROM TB_CRD_STATIS_USCALE T WHERE T.DATA_TYPE = '2' AND T.YEAR = V_YEAR AND T.MONTH = V_MONTH;--删除历史数据，再重新插入

    INSERT INTO TB_CRD_STATIS_USCALE(STATIS_ID,
                                     APPROVE_COUNT, APPROVE_EXP_AMOUNT, CREDIT_EXP_BALANCE, LOAN_EXP_BALANCE,
                                     LIMIT_CREDIT, LIMIT_AVI, LIMIT_USED, EXP_USED, EXP_AVI, UNIT_SCALE,
                                     ORG_NUM, DATA_TYPE, YEAR, MONTH, DATE, CREATE_TIME, UPDATE_TIME)
    SELECT CLM.FNC_GET_UUID('TJ') STATIS_ID,
           APPROVE_COUNT,
           APPROVE_EXP_AMOUNT,
           CREDIT_EXP_BALANCE,
           LOAN_EXP_BALANCE,
           LIMIT_CREDIT,
           LIMIT_AVI,
           LIMIT_USED,
           EXP_USED,
           EXP_AVI,
           UNIT_SCALE,
           ORG_NUM,
           '2'                DATA_TYPE,
           V_YEAR  AS         YEAR,
           V_MONTH AS         MONTH,
           V_DATE  AS         DATE,
           V_WORK_TIME        CREATE_TIME,
           V_WORK_TIME        UPDATE_TIME
    FROM TB_CRD_STATIS_USCALE T --实时数据
    WHERE T.DATA_TYPE = '1';


    --额度统计表-客户类型（历史+实时）
    DELETE FROM TB_CRD_STATIS_CUSTYPE T WHERE T.DATA_TYPE = '2' AND T.YEAR = V_YEAR AND T.MONTH = V_MONTH;--删除历史数据，再重新插入

    INSERT INTO TB_CRD_STATIS_CUSTYPE(STATIS_ID,
                                      APPROVE_COUNT, APPROVE_EXP_AMOUNT, CREDIT_EXP_BALANCE, LOAN_EXP_BALANCE,
                                      LIMIT_CREDIT, LIMIT_AVI, LIMIT_USED, EXP_USED, EXP_AVI, CUSTOMER_TYPE,
                                      ORG_NUM, DATA_TYPE, YEAR, MONTH, DATE, CREATE_TIME, UPDATE_TIME)
    SELECT CLM.FNC_GET_UUID('TJ') STATIS_ID,
           APPROVE_COUNT,
           APPROVE_EXP_AMOUNT,
           CREDIT_EXP_BALANCE,
           LOAN_EXP_BALANCE,
           LIMIT_CREDIT,
           LIMIT_AVI,
           LIMIT_USED,
           EXP_USED,
           EXP_AVI,
           CUSTOMER_TYPE,
           ORG_NUM,
           '2'                DATA_TYPE,
           V_YEAR  AS         YEAR,
           V_MONTH AS         MONTH,
           V_DATE  AS         DATE,
           V_WORK_TIME        CREATE_TIME,
           V_WORK_TIME        UPDATE_TIME
    FROM TB_CRD_STATIS_CUSTYPE T --实时数据
    WHERE T.DATA_TYPE = '1';

    --额度统计表-担保方式（历史+实时）
    DELETE FROM TB_CRD_STATIS_GTYPE T WHERE T.DATA_TYPE = '2' AND T.YEAR = V_YEAR AND T.MONTH = V_MONTH;--删除历史数据，再重新插入

    INSERT INTO TB_CRD_STATIS_GTYPE(STATIS_ID,
                                    APPROVE_COUNT, APPROVE_EXP_AMOUNT, CREDIT_EXP_BALANCE, LOAN_EXP_BALANCE,
                                    LIMIT_CREDIT, LIMIT_AVI, LIMIT_USED, EXP_USED, EXP_AVI, GUARANTEE_TYPE,
                                    ORG_NUM, DATA_TYPE, YEAR, MONTH, DATE, CREATE_TIME, UPDATE_TIME)
    SELECT CLM.FNC_GET_UUID('TJ') STATIS_ID,
           APPROVE_COUNT,
           APPROVE_EXP_AMOUNT,
           CREDIT_EXP_BALANCE,
           LOAN_EXP_BALANCE,
           LIMIT_CREDIT,
           LIMIT_AVI,
           LIMIT_USED,
           EXP_USED,
           EXP_AVI,
           GUARANTEE_TYPE,
           ORG_NUM,
           '2'                DATA_TYPE,
           V_YEAR  AS         YEAR,
           V_MONTH AS         MONTH,
           V_DATE  AS         DATE,
           V_WORK_TIME        CREATE_TIME,
           V_WORK_TIME        UPDATE_TIME
    FROM TB_CRD_STATIS_GTYPE T --实时数据
    WHERE T.DATA_TYPE = '1';


    --额度统计表-行业（历史+实时）
    DELETE FROM TB_CRD_STATIS_INDUSTRY T WHERE T.DATA_TYPE = '2' AND T.YEAR = V_YEAR AND T.MONTH = V_MONTH;--删除历史数据，再重新插入

    INSERT INTO TB_CRD_STATIS_INDUSTRY(STATIS_ID,
                                       APPROVE_COUNT, APPROVE_EXP_AMOUNT, CREDIT_EXP_BALANCE, LOAN_EXP_BALANCE,
                                       LIMIT_CREDIT, LIMIT_AVI, LIMIT_USED, EXP_USED, EXP_AVI, INDUSTRY,
                                       ORG_NUM, DATA_TYPE, YEAR, MONTH, DATE, CREATE_TIME, UPDATE_TIME)
    SELECT CLM.FNC_GET_UUID('TJ') STATIS_ID,
           APPROVE_COUNT,
           APPROVE_EXP_AMOUNT,
           CREDIT_EXP_BALANCE,
           LOAN_EXP_BALANCE,
           LIMIT_CREDIT,
           LIMIT_AVI,
           LIMIT_USED,
           EXP_USED,
           EXP_AVI,
           INDUSTRY,
           ORG_NUM,
           '2'                DATA_TYPE,
           V_YEAR  AS         YEAR,
           V_MONTH AS         MONTH,
           V_DATE  AS         DATE,
           V_WORK_TIME        CREATE_TIME,
           V_WORK_TIME        UPDATE_TIME
    FROM TB_CRD_STATIS_INDUSTRY T --实时数据
    WHERE T.DATA_TYPE = '1';


    --额度统计表-业务品种（历史+实时）
    DELETE FROM TB_CRD_STATIS_PRODUCT T WHERE T.DATA_TYPE = '2' AND T.YEAR = V_YEAR AND T.MONTH = V_MONTH;--删除历史数据，再重新插入

    INSERT INTO TB_CRD_STATIS_PRODUCT(STATIS_ID,
                                      APPROVE_COUNT, APPROVE_EXP_AMOUNT, CREDIT_EXP_BALANCE, LOAN_EXP_BALANCE,
                                      LIMIT_CREDIT, LIMIT_AVI, LIMIT_USED, EXP_USED, EXP_AVI, PRODUCT_NUM,
                                      ORG_NUM, DATA_TYPE, YEAR, MONTH, DATE, CREATE_TIME, UPDATE_TIME)
    SELECT CLM.FNC_GET_UUID('TJ') STATIS_ID,
           APPROVE_COUNT,
           APPROVE_EXP_AMOUNT,
           CREDIT_EXP_BALANCE,
           LOAN_EXP_BALANCE,
           LIMIT_CREDIT,
           LIMIT_AVI,
           LIMIT_USED,
           EXP_USED,
           EXP_AVI,
           PRODUCT_NUM,
           ORG_NUM,
           '2'                DATA_TYPE,
           V_YEAR  AS         YEAR,
           V_MONTH AS         MONTH,
           V_DATE  AS         DATE,
           V_WORK_TIME        CREATE_TIME,
           V_WORK_TIME        UPDATE_TIME
    FROM TB_CRD_STATIS_PRODUCT T --实时数据
    WHERE T.DATA_TYPE = '1';

    --额度统计表-机构（历史+实时）
    DELETE FROM TB_CRD_STATIS_ORG T WHERE T.DATA_TYPE = '2' AND T.YEAR = V_YEAR AND T.MONTH = V_MONTH;--删除历史数据，再重新插入

    INSERT INTO TB_CRD_STATIS_ORG(STATIS_ID,
                                  APPROVE_EXP_AMOUNT, CREDIT_EXP_BALANCE, LOAN_EXP_BALANCE,
                                  LIMIT_CREDIT, LIMIT_AVI, LIMIT_USED, EXP_USED, EXP_AVI, INDIVIDUAL_COUNT,
                                  CORPORATION_COUNT, BANK_COUNT, ORG_NUM, DATA_TYPE, YEAR, MONTH, DATE, CREATE_TIME,
                                  UPDATE_TIME)
    SELECT CLM.FNC_GET_UUID('TJ') STATIS_ID,
           t.APPROVE_EXP_AMOUNT,
           t.CREDIT_EXP_BALANCE,
           t.LOAN_EXP_BALANCE,
           t.LIMIT_CREDIT,
           t.LIMIT_AVI,
           t.LIMIT_USED,
           t.EXP_USED,
           t.EXP_AVI,
           t.INDIVIDUAL_COUNT,
           t.CORPORATION_COUNT,
           t.BANK_COUNT,
           ORG_NUM,
           '2'                DATA_TYPE,
           V_YEAR  AS         YEAR,
           V_MONTH AS         MONTH,
           V_DATE  AS         DATE,
           V_WORK_TIME        CREATE_TIME,
           V_WORK_TIME        UPDATE_TIME
    FROM TB_CRD_STATIS_ORG T --实时数据
    WHERE T.DATA_TYPE = '1';


END;

comment on procedure PRC_CREDIT_STATIS_HS() is '额度历史统计';

CREATE FUNCTION FNC_GET_division(IN fz decimal(24, 2), IN fm decimal(24, 2))
    RETURNS decimal(24, 2)
BEGIN
    --获取增幅（(分子-分母)/分母）（120-100）/100
    --返回值
    RETURN (nvl(fz, 0) - nvl(fm, 0)) / decode(fm, 0, 1, fm)*100;
END;

CREATE PROCEDURE CLM.PRC_CRD_BATCH_LOG (
    IN SCHEMA_TYPE	VARCHAR(20),
    IN STEP_NAME	VARCHAR(100),
    IN STATUS	VARCHAR(1),
    IN EFFECT_COUNT	INTEGER,
    IN SQL_CODE	INTEGER,
    IN REMARK	VARCHAR(200) )
  SPECIFIC PRC_CRD_BATCH_LOG
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
--错误码
BEGIN
/*
  该过程主要用于存储过程执行的信息的保存
*/
  INSERT INTO
   TB_PROCEDURE_EXEC_LOG(
     PROC_EXEC_ID,
     SCHEMA_TYPE,
     PROC_NAME,
     STATUS,
     EXEC_DATE,
     EXEC_TIME,
     EFFECT_COUNT,
     SQL_CODE,
     REMARK)
  VALUES
   (
     FNC_GET_CLM_UUID(),
     SCHEMA_TYPE,
     STEP_NAME,
     STATUS,
     CURRENT_DATE,
     CURRENT_TIME,
     EFFECT_COUNT,
     SQL_CODE,
     REMARK);
END;

CREATE PROCEDURE CLM.PRC_BILL_EVENT_DEAL (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_BILL_EVENT_DEAL
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
    DECLARE EFFECT_COUNT   INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE       INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE    VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS         VARCHAR(20); --执行情况
  DECLARE PROC_NAME      VARCHAR(50) DEFAULT 'PRC_BILL_EVENT_DEAL'; --存储过程
  DECLARE REMARK         VARCHAR(200);--报错信息
  DECLARE SQLSTATE CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE INTEGER;
  DECLARE V_ERR_MSG VARCHAR(100);--错误信息
  DECLARE TRUNCATE_BILL_EVENT_MAIN VARCHAR(200);--清空表数据
  DECLARE TRUNCATE_BILL_EVENT_DETAIL VARCHAR(200);--清空表数据
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR  SQLWARNING,SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK = SQLSTATE;
    SET STATUS = '0';
    SET RES_NUM = 0;
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    --ROLLBACK;
    RETURN;
  END ;
  SET TRUNCATE_BILL_EVENT_MAIN= 'load from /dev/null of del replace into TB_BILL_EVENT_MAIN_MIDDLE';
  CALL SYSPROC.ADMIN_CMD(TRUNCATE_BILL_EVENT_MAIN);
  SET TRUNCATE_BILL_EVENT_DETAIL= 'load from /dev/null of del replace into TB_BILL_EVENT_DETAIL_MIDDLE';
  CALL SYSPROC.ADMIN_CMD(TRUNCATE_BILL_EVENT_DETAIL);

  INSERT INTO TB_BILL_EVENT_MAIN_MIDDLE(TRAN_SEQ_SN,TRAN_DATE,BUSI_DEAL_NUM,TRAN_TYPE_CD,CRD_APPLY_AMT,CRD_CURRENCY_CD)
  SELECT TRAN_SEQ_SN,TRAN_DATE,BUSI_DEAL_NUM, TRAN_TYPE_CD, CRD_APPLY_AMT, CRD_CURRENCY_CD  FROM TT_BILL_CRD_TRAN_BUS_SOURCE;

  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME = 'PRC_BILL_EVENT_DEAL.1';
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '向票据用信主表中间层插入信息';
  --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);

  INSERT INTO TB_BILL_EVENT_DETAIL_MIDDLE(
TRAN_SEQ_SN,
TRAN_DATE,
BUSI_DEAL_NUM,
BUSI_PRD_NUM,
BUSI_DEAL_DESC,
BUSI_DEAL_ORG_NUM,
BUSI_DEAL_ORG_NAME,
BUSI_OPPT_ORG_NUM,
BUSI_OPPT_ORG_NAME,
BUSI_SUM_AMT,
BUSI_CERT_CNT,
CERT_NUM,
CERT_TYPE_CD,
CERT_PPT_CD,
CERT_INTEREST_PERIOD,
CERT_INTEREST_RATE,
CERT_CURRENCY_CD,
CERT_SEQ_AMT,
CERT_APPLY_AMT,
CERT_APPLY_BALANCE,
CERT_STATUS,
CERT_BEGIN_DATE,
CERT_END_DATE,
CERT_FINISH_DATE,
CERT_DRAWER_CUST_NUM,
CERT_DRAWER_NAME,
CERT_DRAWER_BANK_NUM,
CERT_DRAWER_BANK_NAME,
CERT_GUARANTY_TYPE,
CERT_GUARANTY_PERSON,
CERT_BUSI_REMARK)
   SELECT
   TB.TRAN_SEQ_SN,
          TB.TRAN_DATE,
          TB1.BUSI_DEAL_NUM,
		  TB2.BUSI_PRD_NUM,
		  TB2.BUSI_DEAL_DESC,
          TB2.BUSI_DEAL_ORG_NUM,
          TB2.BUSI_DEAL_ORG_NAME,
          TB2.BUSI_OPPT_ORG_NUM,
          TB2.BUSI_OPPT_ORG_NAME,
		  TB2.BUSI_SUM_AMT,
		  TB2.BUSI_CERT_CNT,
          TB.CERT_NUM,
          TB.CERT_TYPE_CD,
          TB.CERT_PPT_CD,
          TB.CERT_INTEREST_PERIOD,
          TB.CERT_INTEREST_RATE,
          TB.CERT_CURRENCY_CD,
          TB.CERT_SEQ_AMT,
          TB.CERT_APPLY_AMT,
          TB.CERT_APPLY_BALANCE,
          TB.CERT_STATUS,
          TB.CERT_BEGIN_DATE,
          TB.CERT_END_DATE,
          TB.CERT_FINISH_DATE,
          TB.CERT_DRAWER_CUST_NUM,
          TB.CERT_DRAWER_NAME,
          TB.CERT_DRAWER_BANK_NUM,
          TB.CERT_DRAWER_BANK_NAME,
          TB.CERT_GUARANTY_TYPE,
          TB.CERT_GUARANTY_PERSON,
          TB.CERT_BUSI_REMARK
   FROM TT_BILL_OPT_CERT_SOURCE TB
        RIGHT JOIN TT_BILL_CRD_TRAN_BUS_SOURCE TB1
           ON TB.TRAN_SEQ_SN = TB1.TRAN_SEQ_SN
        RIGHT JOIN TT_BILL_CRD_TRAN_OPT_SOURCE TB2
           ON TB1.TRAN_SEQ_SN = TB2.TRAN_SEQ_SN;
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME = 'PRC_BILL_EVENT_DEAL.2';
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '向票据用信详情中间层插入信息';
  --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
  SET RES_NUM = 1;
END;

comment on procedure PRC_BILL_EVENT_DEAL(VARCHAR(10), INTEGER) is '处理票据系统来源数据';

CREATE PROCEDURE CLM.PRC_CRD_EARK_EXPIRE_DUE (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_CRD_EARK_EXPIRE_DUE
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE V_CRD_DETAIL_PRD VARCHAR(10);--二级产品号
  DECLARE V_CRD_DETAIL_NUM VARCHAR(40);--主额度编号
  DECLARE V_M_CRD_GRANT_ORG_NUM VARCHAR(6);--授信机构
  DECLARE V_CUSTOMER_NUM VARCHAR(10);--客户号
  DECLARE V_D_DUE_NUM INTEGER;--圈存额度失效数量
  DECLARE V_FAILURE_STATE VARCHAR(2);--失效状态 01:生效、02：部分冻结、03：全部冻结、04:已完结
  DECLARE V_P_CRD_GRANT_ORG_NUM VARCHAR(6);--授信机构
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_CRD_EARK_EXPIRE_DUE'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;
  SET V_P_CRD_GRANT_ORG_NUM = '01122';--省联社机构号
  /*
                  圈存额度到期处理
    1、圈存额度到期日期等于当前跑批日期EXEC_DATE、当授信机构为省联社时，将该客户该产品的所有的圈存额度，圈存已用都置为0.0
    2、圈存额度到期日等于当前跑批日期EXEC_DATE、当授信机构为联社的法人机构时，则将该机构该产品的圈存额度，圈存已用都置为0.0
  */
  SELECT COUNT(*) INTO V_D_DUE_NUM FROM TB_CRD_DETAIL T1 WHERE T1.EARMARK_END_DATE = EXEC_DATE;
  IF V_D_DUE_NUM >0
  THEN
    FOR CRD_DETAIL_DATAS AS (SELECT T1.CRD_DETAIL_PRD, T1.CRD_DETAIL_NUM, T1.CUSTOMER_NUM, T1.CRD_GRANT_ORG_NUM FROM TB_CRD_DETAIL T1 WHERE T1.EARMARK_END_DATE = EXEC_DATE)
    DO
      SET V_CRD_DETAIL_PRD = CRD_DETAIL_DATAS.CRD_DETAIL_PRD;
      SET V_CRD_DETAIL_NUM = CRD_DETAIL_DATAS.CRD_DETAIL_NUM;
      SET V_M_CRD_GRANT_ORG_NUM = CRD_DETAIL_DATAS.CRD_GRANT_ORG_NUM;
      SET V_CUSTOMER_NUM = CRD_DETAIL_DATAS.CUSTOMER_NUM;
      /*
        授信机构为省联社时
      */
      IF V_M_CRD_GRANT_ORG_NUM = V_P_CRD_GRANT_ORG_NUM
      THEN
        UPDATE TB_CRD_DETAIL T2 SET (T2.LIMIT_EARMARK,T2.LIMIT_EARMARK_USED)=(0.0,0.0) WHERE T2.CUSTOMER_NUM = V_CUSTOMER_NUM AND T2.CRD_DETAIL_PRD = V_CRD_DETAIL_PRD;
      ELSE
        UPDATE TB_CRD_DETAIL T2 SET (T2.LIMIT_EARMARK,T2.LIMIT_EARMARK_USED)=(0.0,0.0) WHERE T2.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM ;
      END IF;
    END FOR;
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '需要处理数量为：'||V_D_DUE_NUM||',处理完成';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,NULL,SQL_CODE,REMARK);
    SET RES_NUM = 1;
  ELSE
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '需要处理数量为0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,NULL,SQL_CODE,REMARK);
    SET RES_NUM = 1;
  END IF;

END;

comment on procedure PRC_CRD_EARK_EXPIRE_DUE(VARCHAR(10), INTEGER) is '同业圈存额度到期处理';

CREATE PROCEDURE CLM.PRC_ECIF_ADDR_INFO_DEAL (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_ECIF_ADDR_INFO_DEAL
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_ECIF_ADDR_INFO_DEAL'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;                 --执行备注
  DECLARE TRUNCATE_ECIF_ADDR_INFO_MIDDLE     VARCHAR (200);                 --清空表数据
    /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;
  SET TRUNCATE_ECIF_ADDR_INFO_MIDDLE = 'load from /dev/null of del replace into TB_ECIF_ADDR_INFO_MIDDLE';
  /*
    清空中间表数据
  */
  CALL SYSPROC.ADMIN_CMD          (TRUNCATE_ECIF_ADDR_INFO_MIDDLE);
  /*
    将对公客户地址信息插入中间表
  */
   INSERT INTO TB_ECIF_ADDR_INFO_MIDDLE (CUSTOMER_NUM,
                                      CONN_TYPE,
                                      COUN_REGI,
                                      PROVINCE,
                                      CITY,
                                      COUNTY,
                                      STREET,
                                      DETAIL_ADDR,
                                      ENG_ADDR,
                                      POST_CODE)
   SELECT T1.CUST_NO,
          T1.CONN_TYPE,
          T1.COUN_REGI,
          T1.PROVINCE,
          T1.CITY,
          T1.COUNTY,
          T1.STREET,
          T1.DETAIL_ADDR,
          T1.ENG_ADDR,
          T1.POST_CODE
   FROM TT_ECIF_CORPORATION_ADDR_INFO T1;
   GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
   SET PROC_NAME = 'PRC_ECIF_ADDR_INFO__DEAL.1';
   SET STATUS = '1';
   SET SQL_CODE = SQLCODE;
   SET REMARK = '向ECIF地址信息中间表插入对公地址信息';
   /*
    将对私客户地址信息插入中间表
   */
   INSERT INTO TB_ECIF_ADDR_INFO_MIDDLE (CUSTOMER_NUM,
                                      CONN_TYPE,
                                      COUN_REGI,
                                      PROVINCE,
                                      CITY,
                                      COUNTY,
                                      STREET,
                                      DETAIL_ADDR,
                                      ENG_ADDR,
                                      POST_CODE)
   SELECT T2.CUST_NO,
          T2.CONN_TYPE,
          T2.COUN_REGI,
          T2.PROVINCE,
          T2.CITY,
          T2.COUNTY,
          T2.STREET,
          T2.DETAIL_ADDR,
          T2.ENG_ADDR,
          T2.POST_CODE
   FROM TT_ECIF_INDIVIDUAL_ADDR_INFO T2;
   GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
   SET PROC_NAME = 'PRC_ECIF_ADDR_INFO__DEAL.2';
   SET STATUS = '1';
   SET SQL_CODE = SQLCODE;
   SET REMARK = '向ECIF地址信息中间表插入对私地址信息';
   --记录插入日志信息
   CALL CLM.PRC_CRD_BATCH_LOG (SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
   SET RES_NUM = 1;
END;

comment on procedure PRC_ECIF_ADDR_INFO_DEAL(VARCHAR(10), INTEGER) is '处理ECIF地址信息落地数据';

CREATE PROCEDURE CLM.PRC_ECIF_BASE_CORPORATION_DEAL (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_ECIF_BASE_CORPORATION_DEAL
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_ECIF_BASE_CORPORATION_DEAL'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  DECLARE TRUNCATE_ECIF_CORPORATION_MIDDLE     VARCHAR (200);                 --清空表数据
   /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;
  SET TRUNCATE_ECIF_CORPORATION_MIDDLE = 'load from /dev/null of del replace into TB_ECIF_CORPORATION_MIDDLE';
  CALL SYSPROC.ADMIN_CMD          (TRUNCATE_ECIF_CORPORATION_MIDDLE);
  INSERT INTO TB_ECIF_CORPORATION_MIDDLE (
   CUST_NO
  ,CUST_TYPE
  ,CUST_STATUS
  ,BANK_CUST_FLAG
  ,CUST_MANAGER_NO
  ,CUST_NAME
  ,ORG_SHORT_NAME
  ,CUST_ENG_NAME
  ,ORG_RNG_SHORT_NAME
  ,NATIONAL_ECONOMY_TYPE
  ,NATIONAL_ECONOMY_DEPART1
  ,NATIONAL_ECONOMY_DEPART2
  ,NATIONAL_ECONOMY_DEPART3
  ,NATIONAL_ECONOMY_DEPART4
  ,FOUND_DATE
  ,REG_CAPITAL
  ,REG_CPTL_CURR
  ,UNIT_SCALE
  ,EMP_NUMBER
  ,COUNTRY_CODE
  ,CREDIT_ORGAN_CODE
  ,BUSIN_SCOPE
  ,BANK_CUST_TYPE1
  ,BANK_CUST_TYPE2
  ,BANK_PAY_SYS_NUM
  ,GROUP_CREDIT_INDICATOR
  ,BANK_INDICATOR
  ,REL_PARTY_IND
  ,SWIFT_CODE
  ,BENE_CUST_TYPE
  ,REMARKS
  ,CREATED_TS
  ,LAST_UPDATED_ORG
  ,LAST_UPDATED_TE
) SELECT
     CUST_NO
  ,CUST_TYPE
  ,CUST_STATUS
  ,BANK_CUST_FLAG
  ,CUST_MANAGER_NO
  ,CUST_NAME
  ,ORG_SHORT_NAME
  ,CUST_ENG_NAME
  ,ORG_RNG_SHORT_NAME
  ,NATIONAL_ECONOMY_TYPE
  ,NATIONAL_ECONOMY_DEPART1
  ,NATIONAL_ECONOMY_DEPART2
  ,NATIONAL_ECONOMY_DEPART3
  ,NATIONAL_ECONOMY_DEPART4
  ,FOUND_DATE
  ,REG_CAPITAL
  ,REG_CPTL_CURR
  ,UNIT_SCALE
  ,EMP_NUMBER
  ,COUNTRY_CODE
  ,CREDIT_ORGAN_CODE
  ,BUSIN_SCOPE
  ,BANK_CUST_TYPE1
  ,BANK_CUST_TYPE2
  ,BANK_PAY_SYS_NUM
  ,GROUP_CREDIT_INDICATOR
  ,BANK_INDICATOR
  ,REL_PARTY_IND
  ,SWIFT_CODE
  ,BENE_CUST_TYPE
  ,REMARKS
  ,CREATED_TS
  ,LAST_UPDATED_ORG
  ,LAST_UPDATED_TE
FROM TT_ECIF_CORPORATION_INFO;
   GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
   SET PROC_NAME = 'PRC_ECIF_BASE_CORPORATION_DEAL.1';
   SET STATUS = '1';
   SET SQL_CODE = SQLCODE;
   SET REMARK = '向ECIF对公客户基本信息中间表插入信息';
   --记录插入日志信息
   CALL PRC_CRD_BATCH_LOG                  (SCHEMA_TYPE,
                           PROC_NAME,
                           STATUS,
                           EFFECT_COUNT,
                           SQL_CODE,
                           REMARK);
  SET RES_NUM = 1;
END;

comment on procedure PRC_ECIF_BASE_CORPORATION_DEAL(VARCHAR(10), INTEGER) is '处理ECIF对公基本信息落地数据';

CREATE PROCEDURE CLM.PRC_ECIF_BASE_CORP_INFO (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_ECIF_BASE_CORP_INFO
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_ECIF_BASE_CORP_INFO'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;
  /*
    根据日终数据文件对应用层的对公客户基本信息数据进行更新，以客户号进行关联
  */
   MERGE INTO TB_CSM_CORPORATION T
   USING TB_ECIF_CORPORATION_MIDDLE M
   ON (M.CUST_NO = T.CUSTOMER_NUM)
   WHEN MATCHED
   THEN
      UPDATE
         SET (T.CUSTOMER_TYPE,
              T.CUST_STATUS,
              T.BANK_CUST_FLAG,
              T.CUST_MANAGER_NO,
              T.CUST_NAME,
              T.ORG_SHORT_NAME,
              T.CUST_ENG_NAME,
              T.ORG_RNG_SHORT_NAME,
              T.NATIONAL_ECONOMY_TYPE,
              T.NATIONAL_ECONOMY_DEPART1,
              T.NATIONAL_ECONOMY_DEPART2,
              T.NATIONAL_ECONOMY_DEPART3,
              T.NATIONAL_ECONOMY_DEPART4,
              T.FOUND_DATE,
              T.REG_CAPITAL,
              T.REG_CPTL_CURR,
              T.UNIT_SCALE,
              T.EMP_NUMBER,
              T.COUNTRY_CODE,
              T.CREDIT_ORGAN_CODE,
              T.BUSIN_SCOPE,
              T.BANK_CUST_TYPE1,
              T.BANK_CUST_TYPE2,
              T.BANK_PAY_SYS_NUM,
              T.GROUP_CREDIT_INDICATOR,
              T.BANK_INDICATOR,
              T.REL_PARTY_IND,
              T.SWIFT_CODE,
              T.BENE_CUST_TYPE,
              T.REMARKS,
              T.CREATED_TS,
              T.LAST_UPDATED_ORG,
              T.LAST_UPDATED_TE,
              T.UPDATE_TIME) =
                (
                   NVL (M.CUST_TYPE, T.CUSTOMER_TYPE),
                   NVL (M.CUST_STATUS, T.CUST_STATUS),
                   NVL (M.BANK_CUST_FLAG, T.BANK_CUST_FLAG),
                   NVL (M.CUST_MANAGER_NO, T.CUST_MANAGER_NO),
                   NVL (M.CUST_NAME, T.CUST_NAME),
                   NVL (M.ORG_SHORT_NAME, T.ORG_SHORT_NAME),
                   NVL (M.CUST_ENG_NAME, T.CUST_ENG_NAME),
                   NVL (M.ORG_RNG_SHORT_NAME, T.ORG_RNG_SHORT_NAME),
                   NVL (M.NATIONAL_ECONOMY_TYPE, T.NATIONAL_ECONOMY_TYPE),
                   NVL (M.NATIONAL_ECONOMY_DEPART1,T.NATIONAL_ECONOMY_DEPART1),
                   NVL (M.NATIONAL_ECONOMY_DEPART2,T.NATIONAL_ECONOMY_DEPART2),
                   NVL (M.NATIONAL_ECONOMY_DEPART3,T.NATIONAL_ECONOMY_DEPART3),
                   NVL (M.NATIONAL_ECONOMY_DEPART4,T.NATIONAL_ECONOMY_DEPART4),
                   NVL (M.FOUND_DATE, T.FOUND_DATE),
                   NVL (M.REG_CAPITAL, T.REG_CAPITAL),
                   NVL (M.REG_CPTL_CURR, T.REG_CPTL_CURR),
                   NVL (M.UNIT_SCALE, T.UNIT_SCALE),
                   NVL (M.EMP_NUMBER, T.EMP_NUMBER),
                   NVL (M.COUNTRY_CODE, T.COUNTRY_CODE),
                   NVL (M.CREDIT_ORGAN_CODE, T.CREDIT_ORGAN_CODE),
                   NVL (M.BUSIN_SCOPE, T.BUSIN_SCOPE),
                   NVL (M.BANK_CUST_TYPE1, T.BANK_CUST_TYPE1),
                   NVL (M.BANK_CUST_TYPE2, T.BANK_CUST_TYPE2),
                   NVL (M.BANK_PAY_SYS_NUM, T.BANK_PAY_SYS_NUM),
                   NVL (M.GROUP_CREDIT_INDICATOR, T.GROUP_CREDIT_INDICATOR),
                   NVL (M.BANK_INDICATOR, T.BANK_INDICATOR),
                   NVL (M.REL_PARTY_IND, T.REL_PARTY_IND),
                   NVL (M.SWIFT_CODE, T.SWIFT_CODE),
                   NVL (M.BENE_CUST_TYPE, T.BENE_CUST_TYPE),
                   NVL (M.REMARKS, T.REMARKS),
                   NVL (M.CREATED_TS, T.CREATED_TS),
                   NVL (M.LAST_UPDATED_ORG, T.LAST_UPDATED_ORG),
                   NVL (M.LAST_UPDATED_TE, T.LAST_UPDATED_TE),
                   CURRENT_TIMESTAMP)
   WHEN NOT MATCHED
   THEN
      INSERT (T.CUSTOMER_NUM,
              T.CUSTOMER_TYPE,
              T.CUST_STATUS,
              T.BANK_CUST_FLAG,
              T.CUST_MANAGER_NO,
              T.CUST_NAME,
              T.ORG_SHORT_NAME,
              T.CUST_ENG_NAME,
              T.ORG_RNG_SHORT_NAME,
              T.NATIONAL_ECONOMY_TYPE,
              T.NATIONAL_ECONOMY_DEPART1,
              T.NATIONAL_ECONOMY_DEPART2,
              T.NATIONAL_ECONOMY_DEPART3,
              T.NATIONAL_ECONOMY_DEPART4,
              T.FOUND_DATE,
              T.REG_CAPITAL,
              T.REG_CPTL_CURR,
              T.UNIT_SCALE,
              T.EMP_NUMBER,
              T.COUNTRY_CODE,
              T.CREDIT_ORGAN_CODE,
              T.BUSIN_SCOPE,
              T.BANK_CUST_TYPE1,
              T.BANK_CUST_TYPE2,
              T.BANK_PAY_SYS_NUM,
              T.GROUP_CREDIT_INDICATOR,
              T.BANK_INDICATOR,
              T.REL_PARTY_IND,
              T.SWIFT_CODE,
              T.BENE_CUST_TYPE,
              T.REMARKS,
              T.CREATED_TS,
              T.LAST_UPDATED_ORG,
              T.LAST_UPDATED_TE,
              T.CREATE_TIME,
              T.UPDATE_TIME)
      VALUES (M.CUST_NO,
              M.CUST_TYPE,
              M.CUST_STATUS,
              M.BANK_CUST_FLAG,
              M.CUST_MANAGER_NO,
              M.CUST_NAME,
              M.ORG_SHORT_NAME,
              M.CUST_ENG_NAME,
              M.ORG_RNG_SHORT_NAME,
              M.NATIONAL_ECONOMY_TYPE,
              M.NATIONAL_ECONOMY_DEPART1,
              M.NATIONAL_ECONOMY_DEPART2,
              M.NATIONAL_ECONOMY_DEPART3,
              M.NATIONAL_ECONOMY_DEPART4,
              M.FOUND_DATE,
              M.REG_CAPITAL,
              M.REG_CPTL_CURR,
              M.UNIT_SCALE,
              M.EMP_NUMBER,
              M.COUNTRY_CODE,
              M.CREDIT_ORGAN_CODE,
              M.BUSIN_SCOPE,
              M.BANK_CUST_TYPE1,
              M.BANK_CUST_TYPE2,
              M.BANK_PAY_SYS_NUM,
              M.GROUP_CREDIT_INDICATOR,
              M.BANK_INDICATOR,
              M.REL_PARTY_IND,
              M.SWIFT_CODE,
              M.BENE_CUST_TYPE,
              M.REMARKS,
              M.CREATED_TS,
              M.LAST_UPDATED_ORG,
              M.LAST_UPDATED_TE,
              CURRENT_TIMESTAMP,
              CURRENT_TIMESTAMP);
              COMMIT;
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME = 'PRC_BASE_CORP_INFO.1';
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '对公客户基本信息表中更新客户信息';
  --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);

END;

comment on procedure PRC_ECIF_BASE_CORP_INFO(VARCHAR(10), INTEGER) is 'ECIF对公客户信息';

CREATE PROCEDURE CLM.PRC_ECIF_BASE_PER_DEAL (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_ECIF_BASE_PER_DEAL
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT   INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE       INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE    VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS         VARCHAR(20); --执行情况
  DECLARE PROC_NAME      VARCHAR(50) DEFAULT 'PRC_BILL_EVENT_DEAL'; --存储过程
  DECLARE REMARK         VARCHAR(200);--报错信息
  DECLARE SQLSTATE CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE INTEGER;
  DECLARE V_ERR_MSG VARCHAR(100);--错误信息
  DECLARE TRUNCATE_ECIF_PER_MIDDLE VARCHAR(200);--清空表数据
  DECLARE EXIT HANDLER FOR  SQLWARNING,SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK = SQLSTATE;
    SET STATUS = '0';
    SET RES_NUM = 0;
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    --ROLLBACK;
    RETURN;
  END ;
   SET TRUNCATE_ECIF_PER_MIDDLE = 'load from /dev/null of del replace into TB_ECIF_BASE_PER_MIDDLE';
   CALL SYSPROC.ADMIN_CMD          (TRUNCATE_ECIF_PER_MIDDLE);
   INSERT INTO TB_ECIF_BASE_PER_MIDDLE (CUST_NO,
                                     CUST_TYPE,
                                     CUST_STATUS,
                                     CUST_NAME,
                                     PINYIN_NAME,
                                     GENDER,
                                     NATION,
                                     RACE,
                                     BIRTH_DATE,
                                     POLI_STATUS,
                                     MARR_STATUS,
                                     EMP_STAT,
                                     HOUSE_CICTS,
                                     HOUSE_TYPE,
                                     HEALTHY_STATUS,
                                     FAMILY_NUM,
                                     EDUCATION,
                                     HIGH_ACADE_DEGREE,
                                     CUST_MANAGER_NO,
                                     WORK_UNIT_NAME,
                                     WORK_INDUSTRY,
                                     UNIT_CHARACTER,
                                     DUTY,
                                     OCCUPATION1,
                                     OCCUPATION2,
                                     OCCUPATION3,
                                     OCCUPATION_EXPLAIN,
                                     TECH_TIYLE_LEVEL,
                                     PAY_CREDIT_FLAG,
                                     IS_BLANK_FLAG,
                                     EMPLOYEE_FLAG,
                                     AGRI_RELATED_IND,
                                     SEIOR_EXECU_IND,
                                     REL_PARTY_IND,
                                     PER_YEAR_INCOME,
                                     TAX_RES_TYPE,
                                     RESID_SITUAT,
                                     CUST_GRADE,
                                     CUST_SATIS,
                                     PER_TOTAL_ASSET,
                                     LIABILITY_BALANCE,
                                     PER_ASSEST_TYPE,
                                     PER_LIAB_TYPE,
                                     RESID_NON_RESID,
                                     CREATED_TS,
                                     LAST_UPDATED_ORG,
                                     LAST_UPDATED_TE)
   SELECT CUST_NO,
          CUST_TYPE,
          CUST_STATUS,
          CUST_NAME,
          PINYIN_NAME,
          GENDER,
          NATION,
          RACE,
          BIRTH_DATE,
          POLI_STATUS,
          MARR_STATUS,
          EMP_STAT,
          HOUSE_CICTS,
          HOUSE_TYPE,
          HEALTHY_STATUS,
          FAMILY_NUM,
          EDUCATION,
          HIGH_ACADE_DEGREE,
          CUST_MANAGER_NO,
          WORK_UNIT_NAME,
          WORK_INDUSTRY,
          UNIT_CHARACTER,
          DUTY,
          OCCUPATION1,
          OCCUPATION2,
          OCCUPATION3,
          OCCUPATION_EXPLAIN,
          TECH_TIYLE_LEVEL,
          PAY_CREDIT_FLAG,
          IS_BLANK_FLAG,
          EMPLOYEE_FLAG,
          AGRI_RELATED_IND,
          SEIOR_EXECU_IND,
          REL_PARTY_IND,
          PER_YEAR_INCOME,
          TAX_RES_TYPE,
          RESID_SITUAT,
          CUST_GRADE,
          CUST_SATIS,
          PER_TOTAL_ASSET,
          LIABILITY_BALANCE,
          PER_ASSEST_TYPE,
          PER_LIAB_TYPE,
          RESID_NON_RESID,
          CREATED_TS,
          LAST_UPDATED_ORG,
          LAST_UPDATED_TE
   FROM TT_ECIF_INDIVIDUAL_INFO;
   GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
   SET PROC_NAME = 'PRC_ECIF_BASE_PER_DEAL.1';
   SET STATUS = '1';
   SET SQL_CODE = SQLCODE;
   SET REMARK = '向ECIF个人客户基本信息中间表插入信息';
   --记录插入日志信息
   CALL PRC_CRD_BATCH_LOG                  (SCHEMA_TYPE,
                           PROC_NAME,
                           STATUS,
                           EFFECT_COUNT,
                           SQL_CODE,
                           REMARK);
  SET RES_NUM = 1;
END;

comment on procedure PRC_ECIF_BASE_PER_DEAL(VARCHAR(10), INTEGER) is '处理ECIF个人基本信息落地数据';

CREATE PROCEDURE CLM.PRC_ECIF_BASE_PER_INFO (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_ECIF_BASE_PER_INFO
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_ECIF_BASE_PER_INFO'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;

  MERGE INTO TB_CSM_INDIVIDUAL  T
   USING TB_ECIF_BASE_PER_MIDDLE M
   ON  (T.CUSTOMER_NUM                                                =M.CUST_NO)
  WHEN MATCHED THEN
    UPDATE
   SET(
   T.CUSTOMER_TYPE,
   T.CUST_STATUS,
   T.CUST_NAME,
   T.PINYIN_NAME,
   T.GENDER,
   T.NATION,
   T.RACE,
   T.BIRTH_DATE,
   T.POLI_STATUS,
   T.MARR_STATUS,
   T.EMP_STAT,
   T.HOUSE_CICTS,
   T.HOUSE_TYPE,
   T.HEALTHY_STATUS,
   T.FAMILY_NUM,
   T.EDUCATION,
   T.HIGH_ACADE_DEGREE,
   T.CUST_MANAGER_NO,
   T.WORK_UNIT_NAME,
   T.WORK_INDUSTRY,
   T.UNIT_CHARACTER,
   T.DUTY,
   T.OCCUPATION1,
   T.OCCUPATION2,
   T.OCCUPATION3,
   T.OCCUPATION_EXPLAIN,
   T.TECH_TIYLE_LEVEL,
   T.PAY_CREDIT_FLAG,
   T.IS_BLANK_FLAG,
   T.EMPLOYEE_FLAG,
   T.AGRI_RELATED_IND,
   T.SEIOR_EXECU_IND,
   T.REL_PARTY_IND,
   T.PER_YEAR_INCOME,
   T.TAX_RES_TYPE,
   T.RESID_SITUAT,
   T.CUST_GRADE,
   T.CUST_SATIS,
   T.PER_TOTAL_ASSET,
   T.LIABILITY_BALANCE,
   T.PER_ASSEST_TYPE,
   T.PER_LIAB_TYPE,
   T.RESID_NON_RESID,
   T.CREATED_TS,
   T.LAST_UPDATED_ORG,
   T.LAST_UPDATED_TE,
   T.UPDATE_TIME) =
   (
   NVL(M.CUST_TYPE,T.CUSTOMER_TYPE),
   NVL(M.CUST_STATUS,T.CUST_STATUS),
   NVL(M.CUST_NAME,T.CUST_NAME),
   NVL(M.PINYIN_NAME,T.PINYIN_NAME),
   NVL(M.GENDER,T.GENDER),
   NVL(M.NATION,T.NATION),
   NVL(M.RACE,T.RACE),
   NVL(M.BIRTH_DATE,T.BIRTH_DATE),
   NVL(M.POLI_STATUS,T.POLI_STATUS),
   NVL(M.MARR_STATUS,T.MARR_STATUS),
   NVL(M.EMP_STAT,T.EMP_STAT),
   NVL(M.HOUSE_CICTS,T.HOUSE_CICTS),
   NVL(M.HOUSE_TYPE,T.HOUSE_TYPE),
   NVL(M.HEALTHY_STATUS,T.HEALTHY_STATUS),
   NVL(M.FAMILY_NUM,T.FAMILY_NUM),
   NVL(M.EDUCATION,T.EDUCATION),
   NVL(M.HIGH_ACADE_DEGREE,T.HIGH_ACADE_DEGREE),
   NVL(M.CUST_MANAGER_NO,T.CUST_MANAGER_NO),
   NVL(M.WORK_UNIT_NAME,T.WORK_UNIT_NAME),
   NVL(M.WORK_INDUSTRY,T.WORK_INDUSTRY),
   NVL(M.UNIT_CHARACTER,T.UNIT_CHARACTER),
   NVL(M.DUTY,T.DUTY),
   NVL(M.OCCUPATION1,T.OCCUPATION1),
   NVL(M.OCCUPATION2,T.OCCUPATION2),
   NVL(M.OCCUPATION3,T.OCCUPATION3),
   NVL(M.OCCUPATION_EXPLAIN,T.OCCUPATION_EXPLAIN),
   NVL(M.TECH_TIYLE_LEVEL,T.TECH_TIYLE_LEVEL),
   NVL(M.PAY_CREDIT_FLAG,T.PAY_CREDIT_FLAG),
   NVL(M.IS_BLANK_FLAG,T.IS_BLANK_FLAG),
   NVL(M.EMPLOYEE_FLAG,T.EMPLOYEE_FLAG),
   NVL(M.AGRI_RELATED_IND,T.AGRI_RELATED_IND),
   NVL(M.SEIOR_EXECU_IND,T.SEIOR_EXECU_IND),
   NVL(M.REL_PARTY_IND,T.REL_PARTY_IND),
   NVL(M.PER_YEAR_INCOME,T.PER_YEAR_INCOME),
   NVL(M.TAX_RES_TYPE,T.TAX_RES_TYPE),
   NVL(M.RESID_SITUAT,T.RESID_SITUAT),
   NVL(M.CUST_GRADE,T.CUST_GRADE),
   NVL(M.CUST_SATIS,T.CUST_SATIS),
   NVL(M.PER_TOTAL_ASSET,T.PER_TOTAL_ASSET),
   NVL(M.LIABILITY_BALANCE,T.LIABILITY_BALANCE),
   NVL(M.PER_ASSEST_TYPE,T.PER_ASSEST_TYPE),
   NVL(M.PER_LIAB_TYPE,T.PER_LIAB_TYPE),
   NVL(M.RESID_NON_RESID,T.RESID_NON_RESID),
   NVL(M.CREATED_TS,T.CREATED_TS),
   NVL(M.LAST_UPDATED_ORG,T.LAST_UPDATED_ORG),
   NVL(M.LAST_UPDATED_TE,T.LAST_UPDATED_TE),
   CURRENT_TIMESTAMP
   )WHEN NOT MATCHED THEN
    INSERT
    (
   T.CUSTOMER_NUM ,
   T.CUSTOMER_TYPE,
   T.CUST_STATUS,
   T.CUST_NAME,
   T.PINYIN_NAME,
   T.GENDER,
   T.NATION,
   T.RACE,
   T.BIRTH_DATE,
   T.POLI_STATUS,
   T.MARR_STATUS,
   T.EMP_STAT,
   T.HOUSE_CICTS,
   T.HOUSE_TYPE,
   T.HEALTHY_STATUS,
   T.FAMILY_NUM,
   T.EDUCATION,
   T.HIGH_ACADE_DEGREE,
   T.CUST_MANAGER_NO,
   T.WORK_UNIT_NAME,
   T.WORK_INDUSTRY,
   T.UNIT_CHARACTER,
   T.DUTY,
   T.OCCUPATION1,
   T.OCCUPATION2,
   T.OCCUPATION3,
   T.OCCUPATION_EXPLAIN,
   T.TECH_TIYLE_LEVEL,
   T.PAY_CREDIT_FLAG,
   T.IS_BLANK_FLAG,
   T.EMPLOYEE_FLAG,
   T.AGRI_RELATED_IND,
   T.SEIOR_EXECU_IND,
   T.REL_PARTY_IND,
   T.PER_YEAR_INCOME,
   T.TAX_RES_TYPE,
   T.RESID_SITUAT,
   T.CUST_GRADE,
   T.CUST_SATIS,
   T.PER_TOTAL_ASSET,
   T.LIABILITY_BALANCE,
   T.PER_ASSEST_TYPE,
   T.PER_LIAB_TYPE,
   T.RESID_NON_RESID,
   T.CREATED_TS,
   T.LAST_UPDATED_ORG,
   T.LAST_UPDATED_TE,
   T.CREATE_TIME,
   T.UPDATE_TIME
    )VALUES(
      M.CUST_NO,
       M.CUST_TYPE,
       M.CUST_STATUS,
       M.CUST_NAME,
       M.PINYIN_NAME,
       M.GENDER,
       M.NATION,
       M.RACE,
       M.BIRTH_DATE,
       M.POLI_STATUS,
       M.MARR_STATUS,
       M.EMP_STAT,
       M.HOUSE_CICTS,
       M.HOUSE_TYPE,
       M.HEALTHY_STATUS,
       M.FAMILY_NUM,
       M.EDUCATION,
       M.HIGH_ACADE_DEGREE,
       M.CUST_MANAGER_NO,
       M.WORK_UNIT_NAME,
       M.WORK_INDUSTRY,
       M.UNIT_CHARACTER,
       M.DUTY,
       M.OCCUPATION1,
       M.OCCUPATION2,
       M.OCCUPATION3,
       M.OCCUPATION_EXPLAIN,
       M.TECH_TIYLE_LEVEL,
       M.PAY_CREDIT_FLAG,
       M.IS_BLANK_FLAG,
       M.EMPLOYEE_FLAG,
       M.AGRI_RELATED_IND,
       M.SEIOR_EXECU_IND,
       M.REL_PARTY_IND,
       M.PER_YEAR_INCOME,
       M.TAX_RES_TYPE,
       M.RESID_SITUAT,
       M.CUST_GRADE,
       M.CUST_SATIS,
       M.PER_TOTAL_ASSET,
       M.LIABILITY_BALANCE,
       M.PER_ASSEST_TYPE,
       M.PER_LIAB_TYPE,
       M.RESID_NON_RESID,
       M.CREATED_TS,
       M.LAST_UPDATED_ORG,
       M.LAST_UPDATED_TE,
       CURRENT_TIMESTAMP,
       CURRENT_TIMESTAMP
       );
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME = 'PRC_BASE_PER_INFO.1';
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '对私客户基本信息表中更新客户信息';
  --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
  SET RES_NUM = 1;

END;

comment on procedure PRC_ECIF_BASE_PER_INFO(VARCHAR(10), INTEGER) is 'ECIF个人客户信息';

CREATE PROCEDURE CLM.PRC_ECIF_CERT_INFO_DEAL (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_ECIF_CERT_INFO_DEAL
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_ECIF_CERT_INFO_DEAL'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  DECLARE TRUNCATE_ECIF_CERT_INFO_MIDDLE   VARCHAR (200);             --清空表数据
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;
  /*
    清空中间表数据
  */
  SET TRUNCATE_ECIF_CERT_INFO_MIDDLE = 'load from /dev/null of del replace into TB_ECIF_CERT_INFO_MIDDLE';
  CALL SYSPROC.ADMIN_CMD (TRUNCATE_ECIF_CERT_INFO_MIDDLE);

  /*
    向证件信息中间表插入个人证件信息
  */
   INSERT INTO TB_ECIF_CERT_INFO_MIDDLE (CUSTOMER_NUM,
                                         CERT_FLAG,
                                         CERT_TYPE,
                                         CERT_NUM,
                                         ISSUED_INST,
                                         CERT_START_DATE,
                                         CERT_END_DATE)
      SELECT T.CUST_NO,
             T.CERT_FLAG,
             T.CERT_TYPE,
             T.CERT_NUM,
             T.ISSUED_INST,
             T.CERT_START_DATE,
             T.CERT_END_DATE
      FROM TT_ECIF_INDIVIDUAL_CERT_INFO T;

   GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
   SET PROC_NAME = 'PRC_ECIF_CERT_INFO_DEAL.1';
   SET STATUS = '1';
   SET SQL_CODE = SQLCODE;
   SET REMARK = '向证件信息中间表插入对私客户证件信息';
   --记录插入日志信息
   CALL CLM.PRC_CRD_BATCH_LOG (SCHEMA_TYPE,
                               PROC_NAME,
                               STATUS,
                               EFFECT_COUNT,
                               SQL_CODE,
                               REMARK);

   /*
     向证件信息中间表插入对公证件信息
   */
   INSERT INTO TB_ECIF_CERT_INFO_MIDDLE (CUSTOMER_NUM,
                                         CERT_FLAG,
                                         CERT_TYPE,
                                         CERT_NUM,
                                         ISSUED_INST,
                                         CERT_START_DATE,
                                         CERT_END_DATE)
      SELECT T1.CUST_NO,
             T1.CERT_FLAG,
             T1.CERT_TYPE,
             T1.CERT_NUM,
             T1.ISSUED_INST,
             T1.CERT_START_DATE,
             T1.CERT_END_DATE
      FROM TT_ECIF_CORPORATION_CERT_INFO T1;

   GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
   SET PROC_NAME = 'PRC_ECIF_CERT_INFO_DEAL.1';
   SET STATUS = '1';
   SET SQL_CODE = SQLCODE;
   SET REMARK = '向证件信息中间表插入对公客户证件信息';
      --记录插入日志信息
   CALL CLM.PRC_CRD_BATCH_LOG (SCHEMA_TYPE,
                               PROC_NAME,
                               STATUS,
                               EFFECT_COUNT,
                               SQL_CODE,
                               REMARK);
   SET RES_NUM = 1;
END;

comment on procedure PRC_ECIF_CERT_INFO_DEAL(VARCHAR(10), INTEGER) is '处理ECIF证件信息落地数据';

CREATE PROCEDURE CLM.PRC_ECIF_CONNECT_INFO_DEAL (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_ECIF_CONNECT_INFO_DEAL
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_ECIF_CONNECT_INFO_DEAL'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  DECLARE TRUNCATE_ECIF_CONNECT_INFO_MIDDLE     VARCHAR (200);--清空表数据
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;
  /*
    清除中间表
  */
  SET TRUNCATE_ECIF_CONNECT_INFO_MIDDLE = 'load from /dev/null of del replace into TB_ECIF_CONNECT_INFO_MIDDLE';
  CALL SYSPROC.ADMIN_CMD(TRUNCATE_ECIF_CONNECT_INFO_MIDDLE);
        INSERT INTO  TB_ECIF_CONNECT_INFO_MIDDLE (
                            CUSTOMER_NUM
                           ,CONN_TYPE
                           ,INTER_CODE
                           ,INLAND_CODE
                           ,TEL_NUMBER
                           ,EXTEN_NUM
                           ,IS_CHECK_FLAG
                                         )
                        SELECT
                            CUST_NO
                            ,CONN_TYPE
                            ,INTER_CODE
                            ,INLAND_CODE
                            ,TEL_NUMBER
                            ,EXTEN_NUM
                            ,IS_CHECK_FLAG
        FROM  TT_ECIF_INDIVIDUAL_PHONE_INFO T1;
        GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
        SET SCHEMA_TYPE = 'CLM';
        SET PROC_NAME = 'PRC_ECIF_CONNECT_INFO_DEAL.1';
        SET STATUS = '1';
        SET SQL_CODE = '';
        SET REMARK = '向ECIF联系信息中间表插入个人联系信息';
        --记录插入日志信息
        CALL CLM.PRC_CRD_BATCH_LOG (SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
        INSERT INTO  TB_ECIF_CONNECT_INFO_MIDDLE (
                            CUSTOMER_NUM
                           ,CONN_TYPE
                           ,INTER_CODE
                           ,INLAND_CODE
                           ,TEL_NUMBER
                           ,EXTEN_NUM
                           ,IS_CHECK_FLAG
                                         )
                        SELECT
                            CUST_NO
                            ,CONN_TYPE
                            ,INTER_CODE
                            ,INLAND_CODE
                            ,TEL_NUMBER
                            ,EXTEN_NUM
                            ,IS_CHECK_FLAG
        FROM  TT_ECIF_CORPORATION_PHONE_INFO T1;
        GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
        SET PROC_NAME = 'PRC_ECIF_CONNECT_INFO_DEAL.2';
        SET STATUS = '1';
        SET SQL_CODE = SQLCODE;
        SET REMARK = '向ECIF联系信息中间表插入对公联系信息';
        --记录插入日志信息
        CALL CLM.PRC_CRD_BATCH_LOG (SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
        SET RES_NUM = 1;
END;

comment on procedure PRC_ECIF_CONNECT_INFO_DEAL(VARCHAR(10), INTEGER) is '处理ECIF联系信息落地数据';

CREATE PROCEDURE CLM.PRC_ECIF_RELATION_INFO (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_ECIF_RELATION_INFO
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_ECIF_RELATION_INFO'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;
  /*
    将ECIF日终文件送来的关系人信息更改到关系人信息表中
  */
  MERGE INTO TB_CSM_RELATION T  USING TB_ECIF_RELATION_INFO_MIDDLE M
  ON
  (M.REL_CUSTOMER_NUM= T.CUSTOMER_NUM AND T.REL_CUSTOMER_NUM = M.REL_CUSTOMER_NUM)
  WHEN MATCHED
  THEN
    UPDATE
      SET
      T.REL_MARK = NVL(M.REL_MARK,T.REL_MARK),
      T.SETTLE_BANK_NUM = NVL(M.SETTLE_BANK_NUM,T.SETTLE_BANK_NUM),
      T.SUP_SETTLE_BANK_NUM = NVL(M.SUP_SETTLE_BANK_NUM,T.SUP_SETTLE_BANK_NUM),
      T.REL_CUSTOMER_TYPE = NVL(M.REL_CUSTOMER_TYPE,T.REL_CUSTOMER_TYPE),
      T.REL_TYPE = NVL(M.REL_TYPE,T.REL_TYPE),
      T.CUST_NAME = NVL(M.CUST_NAME,T.CUST_NAME),
      T.CERT_TYPE = NVL(M.CERT_TYPE,T.CERT_TYPE),
      T.CERT_NUM = NVL(M.CERT_NUM,T.CERT_NUM),
      T.GENDER = NVL(M.GENDER,T.GENDER),
      T.UNIT_SCALE = NVL(M.UNIT_SCALE,T.UNIT_SCALE),
      T.UPDATE_TIME = CURRENT_TIMESTAMP
  WHEN NOT MATCHED
  THEN
    INSERT (
          T.REL_MARK,
          T.SETTLE_BANK_NUM,
          T.SUP_SETTLE_BANK_NUM,
          T.CUSTOMER_NUM,
          T.REL_CUSTOMER_NUM,
          T.REL_CUSTOMER_TYPE,
          T.REL_TYPE,
          T.CUST_NAME,
          T.CERT_TYPE,
          T.CERT_NUM,
          T.GENDER,
          T.UNIT_SCALE,
          T.CREATE_TIME,
          T.UPDATE_TIME
    )VALUES(
           M.REL_MARK,
           M.SETTLE_BANK_NUM,
           M.SUP_SETTLE_BANK_NUM,
           M.CUSTOMER_NUM,
           M.REL_CUSTOMER_NUM,
           M.REL_CUSTOMER_TYPE,
           M.REL_TYPE,
           M.CUST_NAME,
           M.CERT_TYPE,
           M.CERT_NUM,
           M.GENDER,
           M.UNIT_SCALE,
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
    );
  COMMIT;
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME = 'PRC_ECIF_RELATION_INFO.1';
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '更新客户关联信息';
  --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
  SET RES_NUM = 1;

END;

comment on procedure PRC_ECIF_RELATION_INFO(VARCHAR(10), INTEGER) is 'ECIF关联人信息';

CREATE PROCEDURE CLM.PRC_ECIF_RELATION_INFO_DEAL (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_ECIF_RELATION_INFO_DEAL
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_ECIF_RELATION_INFO_DEAL'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  DECLARE TRUNCATE_ECIF_RELATION_INFO_MIDDLE     VARCHAR (200);                 --清空表数据
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
      SET SQL_CODE = SQLCODE;
      SET REMARK   = SQLSTATE;
      SET STATUS   = '0';
      CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
      SET RES_NUM  = 0;
      --ROLLBACK;
      RETURN;
  END;
  SET TRUNCATE_ECIF_RELATION_INFO_MIDDLE = 'load from /dev/null of del replace into TB_ECIF_RELATION_INFO_MIDDLE';
   /*
    清空中间表数据
   */
   CALL SYSPROC.ADMIN_CMD (TRUNCATE_ECIF_RELATION_INFO_MIDDLE);
   INSERT INTO TB_ECIF_RELATION_INFO_MIDDLE (CUSTOMER_NUM,
                                          REL_CUSTOMER_NUM,
                                          REL_TYPE,
                                          CUST_NAME,
                                          CERT_TYPE,
                                          CERT_NUM,
                                          GENDER)
   SELECT T1.CUST_NO,
          T1.REL_CUST_NO,
          T1.REL_TYPE,
          T1.CUST_NAME,
          T1.CERT_TYPE,
          T1.CERT_NUM,
          GENDER
   FROM TT_ECIF_INDIVIDUAL_PEOPLE_INFO T1;

   GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
   SET PROC_NAME = 'PRC_ECIF_RELATION_INFO_DEAL.1';
   SET STATUS = '1';
   SET SQL_CODE = SQLCODE;
   SET REMARK = '向ECIF关联关系中间表插入个人对私关联信息';
   --记录插入日志信息
   CALL CLM.PRC_CRD_BATCH_LOG (SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
   INSERT INTO TB_ECIF_RELATION_INFO_MIDDLE (CUSTOMER_NUM,
                                          REL_CUSTOMER_NUM,
                                          REL_TYPE,
                                          CUST_NAME,
                                          CERT_TYPE,
                                          CERT_NUM,
                                          GENDER)
   SELECT T1.CUST_NO,
          T1.REL_CUST_NO,
          T1.REL_TYPE,
          T1.CUST_NAME,
          T1.CERT_TYPE,
          T1.CERT_NUM,
          GENDER
   FROM TT_ECIF_CORPORATION_PEOPLE_INFO T1;

   GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
   SET PROC_NAME = 'PRC_ECIF_RELATION_INFO_DEAL.2';
   SET STATUS = '1';
   SET SQL_CODE = SQLCODE;
   SET REMARK = '向ECIF关联关系中间表插入对公个人关联信息';
   --记录插入日志信息
   CALL CLM.PRC_CRD_BATCH_LOG (SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
   INSERT INTO TB_ECIF_RELATION_INFO_MIDDLE (
        CUSTOMER_NUM
        ,REL_CUSTOMER_NUM
        ,REL_TYPE
        ,CUST_NAME
        ,CERT_TYPE
        ,CERT_NUM
        ,UNIT_SCALE
                    )
        SELECT T1.CUST_NO,
              T1.REL_CUST_NO,
              T1.REL_TYPE,
              T1.CUST_NAME,
              T1.CERT_TYPE,
              T1.CERT_NUM,
              T1.UNIT_SCALE
        FROM TT_ECIF_CORPORATION_COMPANY_INFO T1;
   GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
   SET PROC_NAME = 'PRC_ECIF_RELATION_INFO_DEAL.3';
   SET STATUS = '1';
   SET SQL_CODE = SQLCODE;
   SET REMARK = '向ECIF关联关系中间表插入对公司客户对公关联信息';
   --记录插入日志信息
   CALL CLM.PRC_CRD_BATCH_LOG (SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
   SET RES_NUM = 1;
END;

comment on procedure PRC_ECIF_RELATION_INFO_DEAL(VARCHAR(10), INTEGER) is '处理ECIF个人基本信息落地数据';

create function FNC_GET_CLM_UUID()
	returns VARCHAR FOR BIT DATA
	language JAVA
	specific FNC_GET_CLM_UUID
	no sql
	external
	name 'UDFUUID!uuid'
	parameter style java
	disallow parallel;

CREATE PROCEDURE CLM.PRC_ECIF_ADDRESS_INFO (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_ECIF_ADDRESS_INFO
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_ECIF_ADDRESS_INFO'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;

--SELECT T.ADDR_ID, T.CUSTOMER_NUM, T.CONN_TYPE, T.COUN_REGI, T.PROVINCE, T.CITY, T.COUNTY, T.STREET, T.DETAIL_ADDR, T.ENG_ADDR, T.POST_CODE, T.CREATE_TIME, T.UPDATE_TIME FROM TB_CSM_ADDRESS_INFO T;
--SELECT  M.CUSTOMER_NUM, M.CONN_TYPE, M.COUN_REGI, M.PROVINCE, M.CITY, M.COUNTY, M.STREET, M.DETAIL_ADDR, M.ENG_ADDR, M.POST_CODE FROM TB_ECIF_ADDR_INFO_MIDDLE M;

  MERGE INTO TB_CSM_ADDRESS_INFO  T
   USING TB_ECIF_ADDR_INFO_MIDDLE M
   ON  (T.CUSTOMER_NUM = M.CUSTOMER_NUM AND T.CONN_TYPE = M.CONN_TYPE)
  WHEN MATCHED THEN
    UPDATE
   SET(
      T.COUN_REGI, T.PROVINCE, T.CITY, T.COUNTY, T.STREET, T.DETAIL_ADDR, T.ENG_ADDR, T.POST_CODE,T.UPDATE_TIME
      )
      =
      (
      NVL(M.COUN_REGI,T.COUN_REGI),
      NVL(M.PROVINCE,T.PROVINCE),
      NVL(M.CITY,T.CITY),
      NVL(M.COUNTY,T.COUNTY),
      NVL(M.STREET,T.STREET),
      NVL(M.DETAIL_ADDR,T.DETAIL_ADDR),
      NVL(M.ENG_ADDR,T.ENG_ADDR),
      NVL(M.POST_CODE,T.POST_CODE),
      CURRENT_TIMESTAMP
   )WHEN NOT MATCHED THEN
    INSERT
    (
    T.ADDR_ID, T.CUSTOMER_NUM, T.CONN_TYPE, T.COUN_REGI, T.PROVINCE, T.CITY, T.COUNTY, T.STREET, T.DETAIL_ADDR, T.ENG_ADDR, T.POST_CODE, T.CREATE_TIME, T.UPDATE_TIME
    )
    VALUES
    (
    FNC_GET_CLM_UUID(),M.CUSTOMER_NUM,M.CONN_TYPE, M.COUN_REGI, M.PROVINCE, M.CITY, M.COUNTY, M.STREET,M.DETAIL_ADDR, M.ENG_ADDR, M.POST_CODE,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP
    );
   COMMIT;
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME = 'PRC_ECIF_ADDRESS_INFO.1';
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '更新客户地址信息';
  --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
  SET RES_NUM = 1;
END;

comment on procedure PRC_ECIF_ADDRESS_INFO(VARCHAR(10), INTEGER) is 'ECIF客户地址信息';

CREATE PROCEDURE CLM.PRC_ECIF_CERT_INFO (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_ECIF_CERT_INFO
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_ECIF_CERT_INFO'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;

  MERGE INTO TB_CSM_CERT_INFO  T
   USING TB_ECIF_CERT_INFO_MIDDLE M
   ON  (T.CUSTOMER_NUM = M.CUSTOMER_NUM AND T.CERT_NUM = M.CERT_NUM AND T.CERT_TYPE = M.CERT_TYPE )
  WHEN MATCHED THEN
    UPDATE
   SET(
      T.CUSTOMER_NUM,
      T.CERT_FLAG,
      T.CERT_TYPE,
      T.CERT_NUM,
      T.ISSUED_INST,
      T.CERT_START_DATE,
      T.CERT_END_DATE,
      T.UPDATE_TIME
      )
      =
      (
      NVL(M.CUSTOMER_NUM,T.CUSTOMER_NUM),
      NVL(M.CERT_FLAG,T.CERT_FLAG),
      NVL(M.CERT_TYPE,T.CERT_TYPE),
      NVL(M.CERT_NUM,T.CERT_NUM),
      NVL(M.ISSUED_INST,T.ISSUED_INST),
      NVL(M.CERT_START_DATE,T.CERT_START_DATE),
      NVL(M.CERT_END_DATE,T.CERT_END_DATE),
      CURRENT_TIMESTAMP
   )WHEN NOT MATCHED THEN
    INSERT
    (
    T.CERT_ID, T.CUSTOMER_NUM, T.CERT_FLAG, T.CERT_TYPE, T.CERT_NUM, T.ISSUED_INST, T.CERT_START_DATE, T.CERT_END_DATE, T.CREATE_TIME, T.UPDATE_TIME
    )
    VALUES
    (
    FNC_GET_CLM_UUID(),M.CUSTOMER_NUM, M.CERT_FLAG, M.CERT_TYPE, M.CERT_NUM, M.ISSUED_INST, M.CERT_START_DATE, M.CERT_END_DATE,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP
    );
   COMMIT;
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME = 'PRC_ECIF_CERT_INFO.1';
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '更新客户证件信息';
  --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);

END;

comment on procedure PRC_ECIF_CERT_INFO(VARCHAR(10), INTEGER) is 'ECIF客户证件信息';

CREATE PROCEDURE CLM.PRC_ECIF_PHONE_INFO (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_ECIF_PHONE_INFO
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_ECIF_PHONE_INFO'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;
  /*
    将ECIF日终文件送来的关系电话信息更改到联系电话信息表中
  */
--SELECT  T.PHONE_ID, T.CUSTOMER_NUM, T.CONN_TYPE, T.INTER_CODE, T.INLAND_CODE, T.TEL_NUMBER, T.EXTEN_NUM, T.IS_CHECK_FLAG, T.CREATE_TIME, T.UPDATE_TIME FROM TB_CSM_PHONE_INFO T ;
--SELECT  M.CUSTOMER_NUM, M.CONN_TYPE, M.INTER_CODE, M.INLAND_CODE, M.TEL_NUMBER, M.EXTEN_NUM, M.IS_CHECK_FLAG FROM TB_ECIF_CONNECT_INFO_MIDDLE M ;
  MERGE INTO TB_CSM_PHONE_INFO T  USING TB_ECIF_CONNECT_INFO_MIDDLE M
  ON
  (M.CUSTOMER_NUM= T.CUSTOMER_NUM AND T.CONN_TYPE = M.CONN_TYPE)
  WHEN MATCHED
  THEN
    UPDATE
      SET
      T.INTER_CODE = NVL(M.INTER_CODE,T.INTER_CODE),
      T.INLAND_CODE = NVL(M.INLAND_CODE,T.INLAND_CODE),
      T.TEL_NUMBER = NVL(M.TEL_NUMBER,T.TEL_NUMBER),
      T.EXTEN_NUM = NVL(M.EXTEN_NUM,T.EXTEN_NUM),
      T.IS_CHECK_FLAG = NVL(M.IS_CHECK_FLAG,T.IS_CHECK_FLAG),
      T.UPDATE_TIME = CURRENT_TIMESTAMP
  WHEN NOT MATCHED
  THEN
    INSERT (
          T.PHONE_ID, T.CUSTOMER_NUM, T.CONN_TYPE, T.INTER_CODE, T.INLAND_CODE, T.TEL_NUMBER, T.EXTEN_NUM, T.IS_CHECK_FLAG, T.CREATE_TIME, T.UPDATE_TIME
    )VALUES(
           FNC_GET_CLM_UUID(),
           M.CUSTOMER_NUM,
           M.CONN_TYPE,
           M.INTER_CODE,
           M.INLAND_CODE,
           M.TEL_NUMBER,
           M.EXTEN_NUM,
           M.IS_CHECK_FLAG,
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
    );
  COMMIT;
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME = 'PRC_ECIF_PHONE_INFO.1';
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '更新客户联系信息';
  --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);

END;

comment on procedure PRC_ECIF_PHONE_INFO(VARCHAR(10), INTEGER) is 'ECIF客户联系信息';

CREATE PROCEDURE CLM.PRC_FUND_CRD_CHECK (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_FUND_CRD_CHECK
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_FUND_CRD_CHECK'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  DECLARE CRD_HAVE       INTEGER; --仅统一授信系统有的数据条数
  DECLARE FUND_HAVE      INTEGER; --仅资金系统有的数据条数
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;


  /*
    将批量日期当天的数据中的对方执行状态(TRAN_ACCT_STATUS)修改为'0'、防止重跑两次文件不一致引起的误判断
  */
  DELETE TB_FUND_GRANT_DETAIL T7 WHERE EXISTS (SELECT 1 FROM TB_FUND_GRANT_MAIN T8 WHERE T8.TRAN_SEQ_SN = T7.TRAN_SEQ_SN AND T8.DATA_SRC = '10' ) AND  T7.TRAN_DATE = EXEC_DATE ;

  DELETE TB_FUND_GRANT_MAIN T9 WHERE T9.TRAN_DATE = EXEC_DATE AND T9.DATA_SRC = '10';

  UPDATE TB_FUND_GRANT_MAIN SET TRAN_ACCT_STATUS='0' WHERE TRAN_DATE=EXEC_DATE AND TRAN_EVENT_STATUS='1' ;

  /*
    主表主要的作用是用来进行对账的，由于日期和流水号可以确定唯一一笔交易，
    所以只要中间主表通过日期和流水号二者与事件主表中的CLM方处理成功的事件
    进行关联进行比对就可以找出：
    1、仅仅CLM系统有的数据条数;2、仅资金系统有的数据条数
  */
  SET CRD_HAVE  = (
                    SELECT
                     count(1)
                    FROM
                     TB_FUND_GRANT_MAIN T1 WHERE NOT EXISTS(
                     SELECT 1 FROM  TB_FUND_GRANT_MAIN_MIDDLE T2
                       WHERE T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN AND
                          T1.TRAN_DATE = T2.TRAN_DATE)
                          AND T1.TRAN_ACCT_STATUS='0'
                          AND T1.TRAN_EVENT_STATUS='1'
                          AND T1.TRAN_DATE = EXEC_DATE
                          );
  SET FUND_HAVE = (
                    SELECT
                     count(1)
                    FROM
                     TB_FUND_GRANT_MAIN_MIDDLE T1
                     WHERE NOT EXISTS (SELECT 1 FROM TB_FUND_GRANT_MAIN T2
                       WHERE T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN AND
                          T1.TRAN_DATE = T2.TRAN_DATE
                          AND T2.TRAN_EVENT_STATUS='1'
                           AND T2.TRAN_ACCT_STATUS='0'
                           AND T2.TRAN_DATE = EXEC_DATE));
------------------------------------STEP1--BIGIN-----------------------------
/*
  如果数据没有异同，则进行更新主表中的对方执行成功标识。
  更新的原则是：
  1、我方执行成功标识(TRAN_EVENT_STATUS)为成功('1')
  2、交易日期为EXEC_DATE
*/

  IF CRD_HAVE = 0 and
     FUND_HAVE = 0
  THEN
    UPDATE
     TB_FUND_GRANT_MAIN T3
    SET
     T3.TRAN_EVENT_INFO  = '系统之间数据无异同',
     T3.TRAN_ACCT_STATUS = '1'
     WHERE  T3.TRAN_EVENT_STATUS='1'  AND T3.TRAN_DATE = EXEC_DATE;

     GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
     SET PROC_NAME = 'PRC_FUND_CRD_CHECK.1';
     SET STATUS = '1';
     SET SQL_CODE = SQLCODE;
     SET REMARK = '更改授信主表对账信息';
     --记录插入日志信息
     CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
  END IF;
  ------------------------------------STEP1--END-----------------------------
  ------------------------------------STEP2--BIGIN---------------------------
  /*
    对于资金系统传来的对账文件中存在的交易而CLM系统不存在的交易需要对调整标识补账标识
  */
  IF FUND_HAVE > 0
  THEN
INSERT INTO
     TB_FUND_GRANT_MAIN(
       EVENT_MAIN_ID,--主键ID
       TRAN_SEQ_SN,--交易流水号
       TRAN_DATE,--交易日期
       BUSI_DEAL_NUM,--业务编号
       TRAN_TYPE_CD,--加以类型
       CRD_APPLY_AMT,--交易金额
       TRAN_EVENT_STATUS,--CLM执行状态
       TRAN_EVENT_INFO,--事件描述信息
       TRAN_ACCT_STATUS,--资金执行情况
       CRD_CURRENCY_CD,--币种
       ADJUST_FLAG,--是否需要调账标识1：需要 0：不需要
       ADJUST_DIRECTION,--调整方向1：需要补账 0：需要冲账
       ADJUST_DESC, TRAN_SYSTEM, USER_NUM )--调账描述
      SELECT
       FNC_GET_CLM_UUID(),
       T1.TRAN_SEQ_SN,
       T1.TRAN_DATE,
       T1.BUSI_DEAL_NUM,
       T1.TRAN_TYPE_CD,
       T1.CRD_APPLY_AMT,
       '0',
       'CRD系统不存在',
       '1',
       T1.CRD_CURRENCY_CD,
       '1',
       '1',
       '需要补账', T1.TRAN_SYSTEM, T1.USER_NUM
      FROM
       TB_FUND_GRANT_MAIN_MIDDLE T1
       WHERE NOT EXISTS (SELECT 1 FROM TB_FUND_GRANT_MAIN T2
         WHERE T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN AND
            T1.TRAN_DATE = T2.TRAN_DATE AND T2.TRAN_DATE = EXEC_DATE AND T2.TRAN_EVENT_STATUS = '1');

    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    SET PROC_NAME = 'PRC_FUND_CRD_CHECK.2';
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '向授信主表插入对账信息';
     --记录插入日志信息
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    /*
      向授信明细事件表中插入信息：满足以下条件
      1、授信主表以交易日期和交易流水号与授信明细中间表做关联
      2、授信主表中CLM系统并未对此数据进行处理(TRAN_EVENT_STATUS = '0')
      3、授信主表的交易日期为EXEC_DATE日期
    */
INSERT INTO TB_FUND_GRANT_DETAIL (
   EVENT_DETAIL_ID
  ,EVENT_MAIN_ID
  ,TRAN_SEQ_SN
  ,TRAN_DATE
  ,CRD_GRANT_ORG_NUM
  ,CUSTOMER_NUM
  ,CRD_MAIN_PRD
  ,CRD_CURRENCY_CD
  ,CRD_SUM_AMT
  ,CRD_BEGIN_DATE
  ,CRD_END_DATE
  ,BUSI_SEGM_AMT
  ,BUSI_SEGM_CNT
  ,CRD_DETAIL_PRD
  ,CRD_DETAIL_AMT
)SELECT
 FNC_GET_CLM_UUID(),
 TF1.EVENT_MAIN_ID,
 TF.TRAN_SEQ_SN,
 TF.TRAN_DATE,
 TF.CRD_GRANT_ORG_NUM,
 TF.CUSTOMER_NUM,
 TF.CRD_MAIN_PRD,
 TF.CRD_CURRENCY_CD,
 TF.CRD_SUM_AMT,
 TF.CRD_BEGIN_DATE,
 TF.CRD_END_DATE,
 TF.BUSI_SEGM_AMT,
 TF.BUSI_SEGM_CNT,
 TF.CRD_DETAIL_PRD,
 TF.CRD_DETAIL_AMT
FROM
 TB_FUND_GRANT_DETAIL_MIDDLE TF
 LEFT JOIN TB_FUND_GRANT_MAIN TF1
   ON TF.TRAN_SEQ_SN = TF1.TRAN_SEQ_SN AND
      TF.TRAN_DATE = TF1.TRAN_DATE AND TF1.TRAN_DATE = EXEC_DATE AND TF1.TRAN_EVENT_STATUS = '0' AND TF1.TRAN_ACCT_STATUS='1';
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME = 'PRC_FUND_CRD_CHECK.3';
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '向授信明细表插入信息';
  --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
  SET RES_NUM = 1;
  END IF;
-----------------------------STEP2--END-----------------------------------
-----------------------------STEP3--BEGIN---------------------------------
  IF CRD_HAVE > 0
  THEN
    UPDATE
     TB_FUND_GRANT_MAIN T3
    SET
     T3.TRAN_EVENT_INFO  = '资金系统不存在该数据',
     T3.TRAN_ACCT_STATUS = '2',
     T3.ADJUST_FLAG = '1',
     T3.ADJUST_DIRECTION = '0'--需要冲账
    WHERE
     NOT EXISTS (SELECT 1 FROM TB_FUND_GRANT_MAIN_MIDDLE T2 WHERE T3.TRAN_SEQ_SN = T2.TRAN_SEQ_SN AND
     T3.TRAN_DATE = T2.TRAN_DATE)AND T3.TRAN_DATE = EXEC_DATE AND T3.TRAN_EVENT_STATUS = '1';
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME = 'PRC_FUND_CRD_CHECK.4';
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '向授信主表中插入对账等标识信息';
  --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
  SET RES_NUM = 1;
  END IF;
  -----------------------------STEP3--BEND---------------------------------
END;

comment on procedure PRC_FUND_CRD_CHECK(VARCHAR(10), INTEGER) is '资金系统授信/切分对账';

CREATE PROCEDURE CLM.PRC_FUND_EARK_CHECK (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_FUND_EARK_CHECK
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_FUND_EARK_CHECK'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  DECLARE CRD_HAVE       INTEGER; --仅统一授信系统有的数据条数
  DECLARE FUND_HAVE      INTEGER; --仅资金系统有的数据条数
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;

  /*删除由于批量插入的数据，防止重跑时插入*/
  DELETE TB_FUND_EARMARK_ALLOT T7 WHERE EXISTS (SELECT 1 FROM TB_FUND_EARMARK_MAIN T8 WHERE T8.TRAN_SEQ_SN = T7.TRAN_SEQ_SN AND T8.DATA_SRC = '10' ) AND  T7.TRAN_DATE = EXEC_DATE ;
  /*删除由于批量插入的数据，防止重跑时插入*/
  DELETE TB_FUND_EARMARK_MAIN T9 WHERE T9.TRAN_DATE = EXEC_DATE AND T9.DATA_SRC = '10';
  /*修改对账成功标识为未跑批状态*/
  UPDATE TB_FUND_EARMARK_MAIN SET TRAN_ACCT_STATUS='0' WHERE TRAN_DATE=EXEC_DATE AND TRAN_EVENT_STATUS='1' ;
  /*
    同一天的数据存在于TB_FUND_EARMARK_MAIN中的CLM已处理(TRAN_EVENT_STATUS='1')且不在资金系统的文件中的数据统计条数
  */
  SET CRD_HAVE          = (
                    SELECT COUNT(1) FROM TB_FUND_EARMARK_MAIN  T1 WHERE
                     NOT EXISTS (SELECT 1 FROM TB_FUND_EARMARK_MAIN_MIDDLE T2 WHERE T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN AND T1.TRAN_DATE = T2.TRAN_DATE ) AND T1.TRAN_EVENT_STATUS='1' AND T1.TRAN_DATE = EXEC_DATE
                    );
  /*
    同一天的数据存在于TB_FUND_EARMARK_MAIN_MIDDLE(资金系统推送的数据)中且不在TB_FUND_EARMARK_MAIN的CLM已处理(TRAN_EVENT_STATUS='1')的数据中的统计条数
  */
  SET FUND_HAVE          = (
                    SELECT COUNT(1) FROM TB_FUND_EARMARK_MAIN_MIDDLE  T1 WHERE
                     NOT EXISTS (SELECT 1 FROM TB_FUND_EARMARK_MAIN T2 WHERE T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN AND T1.TRAN_DATE = T2.TRAN_DATE AND T2.TRAN_DATE = EXEC_DATE AND T2.TRAN_EVENT_STATUS = '1' ));
------------------------------------STEP1--BIGIN-----------------------------
/*
  CLM系统与资金系统文件的比对无差异，将资金系统执行的情况TRAN_ACCT_STATUS置为'1'
*/
  IF CRD_HAVE         = 0 and
     FUND_HAVE = 0
  THEN
    UPDATE
     TB_FUND_EARMARK_MAIN T3
    SET
     T3.TRAN_EVENT_INFO  = '系统之间数据无异同',
     T3.TRAN_ACCT_STATUS = '1'
     WHERE
     EXISTS
     (SELECT 1 FROM TB_FUND_EARMARK_MAIN_MIDDLE T1 WHERE T1.TRAN_SEQ_SN=T3.TRAN_SEQ_SN AND T1.TRAN_DATE=T3.TRAN_DATE)
     AND T3.TRAN_EVENT_STATUS='1'
     AND T3.TRAN_DATE = EXEC_DATE;

    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    SET PROC_NAME = 'PRC_FUND_EARK_CHECK.1';
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '数据无异同,更新标识位';
    --记录插入日志信息
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    SET RES_NUM  = 1;
  END IF;
  ------------------------------------STEP1--END-----------------------------
  ------------------------------------STEP2--BIGIN-----------------------------
  IF FUND_HAVE          > 0
  THEN
  /*
    将同时满足如下条件的额度文件中的数据插入额度圈存事件主表中：
    1、存在于文件中，但是在额度事件主表中的处理成功的事件(TRAN_EVENT_STATUS='1')中没有的，时间是EXEC_DATE
  */
  INSERT INTO TB_FUND_EARMARK_MAIN(
   EVENT_MAIN_ID
  ,TRAN_SEQ_SN
  ,TRAN_DATE
  ,BUSI_DEAL_NUM
  ,TRAN_TYPE_CD
  ,CRD_EARK_AMT
  ,CRD_CURRENCY_CD
  ,TRAN_EVENT_STATUS
  ,TRAN_EVENT_INFO
  ,TRAN_ACCT_STATUS
  ,ADJUST_FLAG
  ,ADJUST_DIRECTION
  ,ADJUST_DESC
  ,DATA_SRC
) SELECT
 FNC_GET_CLM_UUID(),
 T2.TRAN_SEQ_SN,
 T2.TRAN_DATE,
 T2.BUSI_DEAL_NUM,
 T2.TRAN_TYPE_CD,
 T2.CRD_EARK_AMT,
 T2.CRD_CURRENCY_CD,
 '0',
 'CRD系统不存在',
 '1',
 '1',
 '1',
 '需要补账',
 '10'
FROM
 TB_FUND_EARMARK_MAIN_MIDDLE T2 WHERE NOT EXISTS
 (SELECT 1 FROM TB_FUND_EARMARK_MAIN T3
   WHERE T2.TRAN_DATE = T3.TRAN_DATE AND
      T2.TRAN_SEQ_SN = T3.TRAN_SEQ_SN AND
      T2.TRAN_DATE = EXEC_DATE AND T3.TRAN_EVENT_STATUS = '1');
    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    SET PROC_NAME = 'PRC_FUND_EARK_CHECK.2';
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '额度系统数据不存在、进行插入';
    --记录插入日志信息
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
       --插入额度圈存明细
  INSERT INTO     TB_FUND_EARMARK_ALLOT(
              EVENT_DETAIL_ID,
              EVENT_MAIN_ID,
              TRAN_SEQ_SN,
              TRAN_DATE,
              CUSTOMER_NUM,
              CRD_DETAIL_PRD,
              CRD_CURRENCY_CD,
              CRD_ALLOT_ORG_NUM,
              CRD_ALLOC_AMT
)SELECT
 FNC_GET_CLM_UUID(),
 T2.EVENT_MAIN_ID,
 T1.TRAN_SEQ_SN,
 T1.TRAN_DATE,
 T1.CUSTOMER_NUM,
 T1.CRD_DETAIL_PRD,
 T1.CRD_CURRENCY_CD,
 T1.CRD_ALLOT_ORG_NUM,
 T1.CRD_ALLOC_AMT
FROM
 TB_FUND_EARMARK_ALLOT_MIDDLE T1
 LEFT JOIN TB_FUND_EARMARK_MAIN T2
   ON T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN AND
      T1.TRAN_DATE = T2.TRAN_DATE AND
      T1.TRAN_DATE = EXEC_DATE AND T2.TRAN_EVENT_STATUS = '0' AND T2.TRAN_ACCT_STATUS = '1';
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME = 'PRC_FUND_EARK_CHECK.3';
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '额度系统数据不存在、进行插入额度明细信息';
  --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
  SET RES_NUM  = 1;
  END IF;
-----------------------------STEP2--END-----------------------------------
-----------------------------STEP3--BEGIN---------------------------------
  IF CRD_HAVE > 0
  THEN
    UPDATE
     TB_FUND_EARMARK_MAIN T3
    SET
     T3.TRAN_EVENT_INFO                 = '资金系统不存在该数据',
     T3.TRAN_ACCT_STATUS = '2',
     T3.ADJUST_FLAG = '1',
     T3.ADJUST_DIRECTION = '0'--需要冲账
    WHERE
     NOT EXISTS (SELECT 1 FROM TB_FUND_EARMARK_MAIN_MIDDLE T2 WHERE T3.TRAN_SEQ_SN = T2.TRAN_SEQ_SN AND
     T3.TRAN_DATE = T2.TRAN_DATE)AND T3.TRAN_DATE = EXEC_DATE AND T3.TRAN_EVENT_STATUS = '1';
    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    SET PROC_NAME = 'PRC_FUND_EARK_CHECK.4';
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '资金系统不存在该数据';
    --记录插入日志信息
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    SET RES_NUM  = 1;
  END IF;
  -----------------------------STEP3--BEND---------------------------------
END;

comment on procedure PRC_FUND_EARK_CHECK(VARCHAR(10), INTEGER) is '资金系统圈存对账';

CREATE PROCEDURE CLM.PRC_FUND_CUSTOMER_ADMIT_CHECK (
    IN EXEC_DATE	VARCHAR(8),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_FUND_CUSTOMER_ADMIT_CHECK
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_FUND_CUSTOMER_ADMIT_CHECK'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  DECLARE CRD_HAVE       INTEGER; --仅统一授信系统有的数据条数
  DECLARE FUND_HAVE      INTEGER; --仅资金系统有的数据条数
    /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;
  /*删除由于批量插入的数据，防止重跑时插入*/
  DELETE TB_FUND_ADMIT_DETAIL T7 WHERE EXISTS (SELECT 1 FROM TB_FUND_ADMIT_MAIN T8 WHERE T8.TRAN_SEQ_SN = T7.TRAN_SEQ_SN AND T8.DATA_SRC = '10' ) AND  T7.TRAN_DATE = EXEC_DATE ;
  /*删除由于批量插入的数据，防止重跑时插入*/
  DELETE TB_FUND_ADMIT_MAIN T9 WHERE T9.TRAN_DATE = EXEC_DATE AND T9.DATA_SRC = '10';
   /*
    将批量日期当天的数据中的对方执行状态(TRAN_ACCT_STATUS)修改为'0'、防止重跑两次文件不一致引起的误判断
  */
  UPDATE TB_FUND_ADMIT_MAIN                   SET TRAN_ACCT_STATUS='0' WHERE TRAN_DATE=EXEC_DATE AND TRAN_EVENT_STATUS='1';
  SET CRD_HAVE         = ( SELECT count(1)
                     FROM
                        TB_FUND_ADMIT_MAIN  T1
                    WHERE
                        NOT EXISTS (SELECT 1 FROM TB_FUND_ADMIT_MAIN_MIDDLE T2
                    WHERE T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN
                    AND T1.TRAN_DATE = T2.TRAN_DATE)AND T1.TRAN_EVENT_STATUS = '1' AND T1.TRAN_DATE = EXEC_DATE);
  SET FUND_HAVE          = ( SELECT count(1)
                    FROM
                        TB_FUND_ADMIT_MAIN_MIDDLE T1
                     WHERE NOT EXISTS (SELECT 1 FROM TB_FUND_ADMIT_MAIN T2
                       WHERE T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN AND
                          T1.TRAN_DATE = T2.TRAN_DATE AND T2.TRAN_EVENT_STATUS = '1' AND T2.TRAN_DATE = EXEC_DATE));
------------------------------------STEP1--BIGIN--------------------------------------
/*
  如果数据没有异同，则进行更新主表中的对方执行成功标识。
  更新的原则是：
  1、我方执行成功标识(TRAN_EVENT_STATUS)为成功('1')
  2、交易日期为EXEC_DATE
*/
  IF CRD_HAVE                                         = 0 and
     FUND_HAVE = 0
  THEN
    UPDATE
     TB_FUND_ADMIT_MAIN T3
    SET
     T3.TRAN_EVENT_INFO  = '系统之间数据无异同',
     T3.TRAN_ACCT_STATUS = '1' WHERE T3.TRAN_DATE = EXEC_DATE AND TRAN_EVENT_STATUS = '1' ;
     GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
     SET PROC_NAME = 'PRC_FUND_CUSTOMER_ADMIT_CHECK.1';
     SET STATUS = '1';
     SET SQL_CODE = SQLCODE;
     SET REMARK = '数据无异同,更新标识位';
     --记录插入日志信息
     CALL PRC_CRD_BATCH_LOG                 (SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
  END IF;
  ------------------------------------STEP1--END-------------------------------
  ------------------------------------STEP2--BIGIN-----------------------------
  IF FUND_HAVE          > 0
  THEN
   INSERT INTO
 TB_FUND_ADMIT_MAIN(
   EVENT_MAIN_ID,
   TRAN_SEQ_SN,
   TRAN_DATE,
   BUSI_DEAL_NUM,
   TRAN_TYPE_CD,
   TRAN_EVENT_STATUS,
   TRAN_EVENT_INFO,
   TRAN_ACCT_STATUS,
   DATA_SRC)
  SELECT
   FNC_GET_CLM_UUID(),
   T1.TRAN_SEQ_SN,
   T1.TRAN_DATE,
   T1.BUSI_DEAL_NUM,
   T1.TRAN_TYPE_CD,
   '0',
   'CRD系统不存在',
   '1',
   '10'
  FROM
   TB_FUND_ADMIT_MAIN_MIDDLE T1
  WHERE
   NOT EXISTS
     (SELECT
       1
      FROM
       TB_FUND_ADMIT_MAIN T2
      WHERE
       T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN AND
       T1.TRAN_DATE = T2.TRAN_DATE AND T2.TRAN_EVENT_STATUS = '1' AND T2.TRAN_DATE = EXEC_DATE);
    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    SET PROC_NAME = 'PRC_FUND_CUSTOMER_ADMIT_CHECK.2';
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '额度系统数据不存在、进行插入';
    ----记录插入日志信息
    CALL PRC_CRD_BATCH_LOG                 (SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
       --插入客户准入明细
      INSERT INTO     TB_FUND_ADMIT_DETAIL (
   EVENT_DETAIL_ID
  ,EVENT_MAIN_ID
  ,TRAN_SEQ_SN
  ,TRAN_DATE
  ,CUSTOMER_NUM
  ,CRD_STATUS
  ,CRD_DETAIL_PRD
  ,CRD_ADMIT_FLAG
  ,FROZEN_REQ_DATE
  ,FROZEN_BEGIN_DATE
  ,FROZEN_END_DATE
)
SELECT
  FNC_GET_CLM_UUID(),
  T2.EVENT_MAIN_ID,
  T1.TRAN_SEQ_SN,
  T1.TRAN_DATE,
  T1.CUSTOMER_NUM,
  T1.CRD_STATUS,
  T1.CRD_DETAIL_PRD,
  T1.CRD_ADMIT_FLAG,
  T1.FROZEN_REQ_DATE,
  T1.FROZEN_BEGIN_DATE,
  T1.FROZEN_OVER_DATE
  FROM TB_FUND_ADMIT_DETAIL_MIDDLE T1 LEFT JOIN TB_FUND_ADMIT_MAIN T2 ON T1.TRAN_SEQ_SN=T2.TRAN_SEQ_SN AND T1.TRAN_DATE=T2.TRAN_DATE AND T1.TRAN_DATE = EXEC_DATE AND T2.TRAN_EVENT_STATUS = '0' AND T2.TRAN_ACCT_STATUS = '1';
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME = 'PRC_FUND_CUSTOMER_ADMIT_CHECK.3';
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '额度系统数据不存在、进行插入客户准入明细信息';
  CALL PRC_CRD_BATCH_LOG                 (SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
  END IF;
-----------------------------STEP2--END-----------------------------------
-----------------------------STEP3--BEGIN---------------------------------
  IF CRD_HAVE > 0
  THEN
UPDATE
 TB_FUND_ADMIT_MAIN T3
SET
 T3.TRAN_EVENT_INFO  = '资金系统不存在该数据',
 T3.TRAN_ACCT_STATUS = '2',
 T3.ADJUST_FLAG = '1',
 T3.ADJUST_DIRECTION = '0'--需要冲账
WHERE
 NOT    EXISTS
   (SELECT
     1
    FROM
     TB_FUND_ADMIT_MAIN_MIDDLE T2
    WHERE
     T3.TRAN_SEQ_SN = T2.TRAN_SEQ_SN AND
     T3.TRAN_DATE = T2.TRAN_DATE) AND
 T3.TRAN_DATE = EXEC_DATE AND
 T3.TRAN_EVENT_STATUS = '1';
    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    SET PROC_NAME = 'PRC_FUND_CUSTOMER_ADMIT_CHECK.4';
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '资金系统不存在该数据客户准入信息';
  END IF;
  -----------------------------STEP3--BEND---------------------------------
END;

comment on procedure PRC_FUND_CUSTOMER_ADMIT_CHECK(VARCHAR(8), INTEGER) is '资金系统客户准入核对';

CREATE PROCEDURE CLM.PRC_FUND_CRD_TRANSFER_CHECK (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_FUND_CRD_TRANSFER_CHECK
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_FUND_CRD_TRANSFER_CHECK'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  DECLARE CRD_HAVE       INTEGER; --仅统一授信系统有的数据条数
  DECLARE FUND_HAVE      INTEGER; --仅资金系统有的数据条数
    /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;
  /*删除由于批量插入的数据，防止重跑时插入*/
  DELETE TB_FUND_TRANSFER_OUT T7 WHERE EXISTS (SELECT 1 FROM TB_FUND_TRANSFER_MAIN T8 WHERE T8.TRAN_SEQ_SN = T7.TRAN_SEQ_SN AND T8.DATA_SRC = '10' ) AND  T7.TRAN_DATE = EXEC_DATE ;
    /*删除由于批量插入的数据，防止重跑时插入*/
  DELETE TB_FUND_TRANSFER_IN T7 WHERE EXISTS (SELECT 1 FROM TB_FUND_TRANSFER_MAIN T8 WHERE T8.TRAN_SEQ_SN = T7.TRAN_SEQ_SN AND T8.DATA_SRC = '10' ) AND  T7.TRAN_DATE = EXEC_DATE ;
  /*删除由于批量插入的数据，防止重跑时插入*/
  DELETE TB_FUND_TRANSFER_MAIN T9 WHERE T9.TRAN_DATE = EXEC_DATE AND T9.DATA_SRC = '10';
  /*
    防止重跑两次文件不一致造成对账、调整不准确。
  */
  UPDATE TB_FUND_TRANSFER_MAIN SET TRAN_ACCT_STATUS='0' WHERE TRAN_DATE=EXEC_DATE AND TRAN_EVENT_STATUS = '1';
  SET CRD_HAVE  = ( SELECT COUNT (1) FROM  TB_FUND_TRANSFER_MAIN T1 WHERE NOT EXISTS (SELECT 1 FROM TB_FUND_TRANSFER_MAIN_MIDDLE T2 WHERE T2.TRAN_SEQ_SN = T1.TRAN_SEQ_SN AND T2.TRAN_DATE = T1.TRAN_DATE)AND T1.TRAN_DATE = EXEC_DATE AND T1.TRAN_EVENT_STATUS = '1' );
  SET FUND_HAVE = ( SELECT COUNT (1) FROM  TB_FUND_TRANSFER_MAIN_MIDDLE T1 WHERE NOT EXISTS (SELECT 1 FROM TB_FUND_TRANSFER_MAIN T2 WHERE T2.TRAN_SEQ_SN = T1.TRAN_SEQ_SN AND T2.TRAN_DATE = T1.TRAN_DATE AND T2.TRAN_EVENT_STATUS = '1' ));
------------------------------------STEP1--BIGIN-----------------------------

  IF CRD_HAVE         = 0 and
     FUND_HAVE = 0
  THEN
    UPDATE
     TB_FUND_TRANSFER_MAIN T3
    SET
     T3.TRAN_EVENT_INFO = '系统之间数据无异同',
     T3.TRAN_ACCT_STATUS = '1' WHERE T3.TRAN_DATE = EXEC_DATE AND T3.TRAN_EVENT_STATUS = '1';

     GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
     SET PROC_NAME = 'PRC_FUND_CRD_TRANSFER_CHECK.1';
     SET STATUS = '1';
     SET SQL_CODE = SQLCODE;
     SET REMARK = '数据无异同,更新标识位';
     --记录插入日志信息
     CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
     SET RES_NUM = 1;

  END IF;
  ------------------------------------STEP1--END-----------------------------
  ------------------------------------STEP2--BIGIN-----------------------------
  IF FUND_HAVE          > 0
  THEN
  /*
    将不在转让交易主表TB_FUND_TRANSFER_MAIN中，仅在TB_FUND_TRANSFER_MAIN_MIDDLE中的数据插入TB_FUND_TRANSFER_MAIN中
  */
  INSERT INTO TB_FUND_TRANSFER_MAIN  (
                    EVENT_MAIN_ID
                  , TRAN_SEQ_SN
                  , TRAN_DATE
                  , BUSI_DEAL_NUM
                  , TRAN_TYPE_CD
                  , CRD_APPLY_AMT
                  , TRAN_EVENT_STATUS
                  , TRAN_EVENT_INFO
                  , TRAN_ACCT_STATUS
                  , CRD_CURRENCY_CD
                  , ADJUST_FLAG
                  , ADJUST_DIRECTION
                  , ADJUST_DESC
                  ,DATA_SRC
                ) SELECT
                  FNC_GET_CLM_UUID()
                  ,T.TRAN_SEQ_SN
                  ,T.TRAN_DATE
                  ,T.BUSI_DEAL_NUM
                  ,T.TRAN_TYPE_CD
                  ,T.CRD_APPLY_AMT
                  ,'0'
                  ,'CRD系统不存在'
                  ,'1'
                  ,T.CRD_CURRENCY_CD
                  ,'1'
                  ,'1'
                  ,''
                  ,'10'
                FROM TB_FUND_TRANSFER_MAIN_MIDDLE T WHERE NOT EXISTS (SELECT 1 FROM TB_FUND_TRANSFER_MAIN T1 WHERE T1.TRAN_SEQ_SN = T.TRAN_SEQ_SN AND T1.TRAN_DATE = T.TRAN_DATE AND T1.TRAN_EVENT_STATUS = '1');
    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    SET PROC_NAME = 'PRC_FUND_CRD_TRANSFER_CHECK.2';
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '额度系统数据不存在、进行插入';
    --记录插入日志信息
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
       --插入额度转出信息
INSERT INTO TB_FUND_TRANSFER_OUT (TRANSFER_OUT_ID,
                                  EVENT_MAIN_ID,
                                  TRAN_SEQ_SN,
                                  TRAN_DATE,
                                  CRD_OUT_ORG_NUM,
                                  BUSI_SOURCE_REQ_NUM,
                                  CURRENCY_CD,
                                  CRD_APPLY_OUT_AMT)
   SELECT FNC_GET_CLM_UUID(),
          T2.EVENT_MAIN_ID,
          T1.TRAN_SEQ_SN,
          T1.TRAN_DATE,
          T1.CRD_OUT_ORG_NUM,
          T1.BUSI_SOURCE_REQ_NUM,
          T1.CURRENCY_CD,
          T1.CRD_APPLY_OUT_AMT
   FROM TB_FUND_TRANSFER_OUT_MIDDLE T1
        LEFT JOIN TB_FUND_TRANSFER_MAIN T2
           ON     T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN
              AND T1.TRAN_DATE = T2.TRAN_DATE
              AND T2.TRAN_DATE = EXEC_DATE
              AND T2.TRAN_EVENT_STATUS = '0' AND T2.TRAN_ACCT_STATUS = '1';
    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    SET PROC_NAME = 'PRC_FUND_CRD_TRANSFER_CHECK.3';
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '额度系统转出数据不存在、进行插入';
    --记录插入日志信息
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
       --插入额度转入信息
        INSERT INTO TB_FUND_TRANSFER_IN (TRANSFER_IN_ID,
                                  EVENT_MAIN_ID,
                                  TRAN_SEQ_SN,
                                  TRAN_DATE,
                                  CRD_IN_ORG_NUM,
                                  BUSI_NEWL_REQ_NUM,
                                  CURRENCY_CD,
                                  CRD_APPLY_IN_AMT)
   SELECT FNC_GET_CLM_UUID(),
          T2.EVENT_MAIN_ID,
          T1.TRAN_SEQ_SN,
          T1.TRAN_DATE,
          T1.CRD_IN_ORG_NUM,
          T1.BUSI_NEWL_REQ_NUM,
          T1.CURRENCY_CD,
          T1.CRD_APPLY_IN_AMT
   FROM TB_FUND_TRANSFER_IN_MIDDLE T1
        LEFT JOIN TB_FUND_TRANSFER_MAIN T2
           ON     T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN
              AND T1.TRAN_DATE = T2.TRAN_DATE
              AND T2.TRAN_DATE = EXEC_DATE
              AND T2.TRAN_EVENT_STATUS = '0' AND T2.TRAN_ACCT_STATUS = '1';

    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    SET PROC_NAME = 'PRC_FUND_CRD_TRANSFER_CHECK.4';
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '额度系统转入数据不存在、进行插入';
    --记录插入日志信息
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    SET RES_NUM =1;
  END IF;
-----------------------------STEP2--END-----------------------------------
-----------------------------STEP3--BEGIN---------------------------------
/*
  额度系统存在此交易，但是资金系统不存在该数据，需要进行冲账
*/
  IF CRD_HAVE > 0
  THEN
    UPDATE
     TB_FUND_TRANSFER_MAIN T3
    SET
     T3.TRAN_EVENT_INFO = '资金系统不存在该数据',
     T3.TRAN_ACCT_STATUS = '2',
     ADJUST_FLAG = '1',
     ADJUST_DIRECTION = '0'--需要冲账
    WHERE
    NOT EXISTS (SELECT 1 FROM  TB_FUND_TRANSFER_MAIN_MIDDLE T2
                            WHERE T3.TRAN_SEQ_SN = T2.TRAN_SEQ_SN AND
                               T3.TRAN_DATE = T2.TRAN_DATE) AND T3.TRAN_DATE = EXEC_DATE AND T3.TRAN_EVENT_STATUS = '1';
    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    SET PROC_NAME = 'PRC_FUND_CRD_TRANSFER_CHECK.5';
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '资金数据不存在、进行插入';
    --记录插入日志信息
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    SET RES_NUM = 1;
  END IF;
  -----------------------------STEP3--BEND---------------------------------
END;

comment on procedure PRC_FUND_CRD_TRANSFER_CHECK(VARCHAR(10), INTEGER) is '资金系统额度转让对账';

CREATE PROCEDURE CLM.PRC_FUND_CRD_OPT_CHECK (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_FUND_CRD_OPT_CHECK
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
-----需要核对-----------------------------
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_FUND_OPT_CHECK'; --存储过程
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  DECLARE FUND_HAVE      INTEGER; --仅资金系统有的数据条数
  DECLARE CRD_HAVE      INTEGER; --仅资金系统有的数据条数
  DECLARE REMARK         VARCHAR(200);--执行描述
    /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;
      /*删除由于批量插入的数据，防止重跑时插入*/
  DELETE TB_FUND_EVENT_DETAIL T7 WHERE EXISTS (SELECT 1 FROM TB_FUND_EVENT_MAIN T8 WHERE T8.TRAN_SEQ_SN = T7.TRAN_SEQ_SN AND T8.DATA_SRC = '10' ) AND  T7.TRAN_DATE = EXEC_DATE ;
  /*删除由于批量插入的数据，防止重跑时插入*/
  DELETE TB_FUND_EVENT_MAIN T9 WHERE T9.TRAN_DATE = EXEC_DATE AND T9.DATA_SRC = '10';
  /*
 防重跑数据文件不一样
*/
  UPDATE TB_FUND_EVENT_MAIN SET TRAN_ACCT_STATUS='0' WHERE TRAN_DATE=EXEC_DATE AND TRAN_EVENT_STATUS='1';


  SET CRD_HAVE          = ( SELECT COUNT (1) FROM TB_FUND_EVENT_MAIN T1 WHERE NOT EXISTS (SELECT 1 FROM TB_FUND_EVENT_MAIN_MIDDLE T2 WHERE T2.TRAN_SEQ_SN = T1.TRAN_SEQ_SN AND T2.TRAN_DATE = T1.TRAN_DATE )AND T1.TRAN_EVENT_STATUS='1' AND T1.TRAN_DATE = EXEC_DATE
                    );
  SET FUND_HAVE          = (SELECT COUNT(1) FROM TB_FUND_EVENT_MAIN_MIDDLE  T1 WHERE NOT EXISTS (SELECT 1 FROM TB_FUND_EVENT_MAIN T2 WHERE T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN AND T1.TRAN_DATE = T2.TRAN_DATE AND T2.TRAN_EVENT_STATUS='1' AND T2.TRAN_DATE = EXEC_DATE));
------------------------------------STEP1--BIGIN-----------------------------


  IF CRD_HAVE                 = 0 and
     FUND_HAVE = 0
  THEN
    UPDATE
     TB_FUND_EVENT_MAIN T3
    SET
     T3.TRAN_EVENT_INFO = '系统之间数据无异同',
     T3.TRAN_ACCT_STATUS = '1'  WHERE  T3.TRAN_DATE=EXEC_DATE AND T3.TRAN_EVENT_STATUS = '1';

    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    SET PROC_NAME = 'PRC_FUND_CRD_OPT_CHECK.1';
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '数据无异同,更新标识位';
    --记录插入日志信息
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    SET RES_NUM = 1;
  END IF;
  ------------------------------------STEP1--END-----------------------------
  ------------------------------------STEP2--BIGIN-----------------------------
  IF FUND_HAVE                   > 0
  THEN
  INSERT INTO TB_FUND_EVENT_MAIN (EVENT_MAIN_ID,
                                TRAN_SEQ_SN,
                                TRAN_DATE,
                                BUSI_DEAL_NUM,
                                TRAN_TYPE_CD,
                                CRD_APPLY_AMT,
                                TRAN_EVENT_STATUS,
                                TRAN_EVENT_INFO,
                                TRAN_ACCT_STATUS,
                                CRD_CURRENCY_CD,
                                ADJUST_FLAG,
                                ADJUST_DIRECTION,
                                ADJUST_DESC,
                                DATA_SRC)
 SELECT FNC_GET_CLM_UUID(),
       T1.TRAN_SEQ_SN,
       T1.TRAN_DATE,
       T1.BUSI_DEAL_NUM,
       T1.TRAN_TYPE_CD,
       T1.CRD_APPLY_AMT,
       '0',
       'CRD系统不存在',
       '1',
       T1.CRD_CURRENCY_CD,
       '1',
       '1',
       '',
       '10'
FROM TB_FUND_EVENT_MAIN_MIDDLE T1
WHERE NOT EXISTS
         (SELECT 1
          FROM TB_FUND_EVENT_MAIN T3
          WHERE     T1.TRAN_DATE = T3.TRAN_DATE
                AND T1.TRAN_SEQ_SN = T3.TRAN_SEQ_SN
                AND T1.TRAN_DATE = EXEC_DATE AND T3.TRAN_EVENT_STATUS = '1');

    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    SET PROC_NAME = 'PRC_FUND_CRD_OPT_CHECK.2';
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '额度系统用信数据不存在、进行插入,修改补账标识';
    --记录插入日志信息
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
       /*
        根据用信主表关联用信明细中间表向用信明细表插入信息，TRAN_EVENT_STATUS = '0'、TRAN_ACCT_STATUS = '1'

       */
INSERT INTO     TB_FUND_EVENT_DETAIL (
                                  EVENT_DETAILED_ID,
                                  EVENT_MAIN_ID,
                                  TRAN_SEQ_SN,
                                  TRAN_DATE,
                                  CRD_GRANT_ORG_NUM,
                                  CUSTOMER_NUM,
                                  CRD_DETAIL_PRD,
                                  BUSI_DEAL_NUM,
                                  BUSI_PRD_NUM,
                                  BUSI_DEAL_DESC,
                                  BUSI_DEAL_ORG_NUM,
                                  BUSI_DEAL_ORG_NAME,
                                  BUSI_OPPT_ORG_NUM,
                                  BUSI_OPPT_ORG_NAME,
                                  BUSI_SUM_AMT,
                                  BUSI_CERT_CNT,
                                  CERT_NUM,
                                  CERT_TYPE_CD,
                                  CERT_PPT_CD,
                                  CERT_INTEREST_PERIOD,
                                  CERT_INTEREST_RATE,
                                  CERT_CURRENCY_CD,
                                  CERT_SEQ_AMT,
                                  CERT_APPLY_AMT,
                                  CERT_APPLY_BALANCE,
                                  CERT_STATUS,
                                  CERT_BEGIN_DATE,
                                  CERT_END_DATE,
                                  CERT_FINISH_DATE,
                                  CERT_DRAWER_CUST_NUM,
                                  CERT_DRAWER_NAME,
                                  CERT_DRAWER_BANK_NUM,
                                  CERT_DRAWER_BANK_NAME,
                                  CERT_GUARANTY_TYPE,
                                  CERT_GUARANTY_PERSON,
                                  CERT_BUSI_REMARK)
   SELECT
          FNC_GET_CLM_UUID(),
          T2.EVENT_MAIN_ID,
          T1.TRAN_SEQ_SN,
          T1.TRAN_DATE,
          T1.CRD_GRANT_ORG_NUM,
          T1.CUSTOMER_NUM,
          T1.CRD_DETAIL_PRD,
          T1.BUSI_DEAL_NUM,
          T1.BUSI_PRD_NUM,
          T1.BUSI_DEAL_DESC,
          T1.BUSI_DEAL_ORG_NUM,
          T1.BUSI_DEAL_ORG_NAME,
          T1.BUSI_OPPT_ORG_NUM,
          T1.BUSI_OPPT_ORG_NAME,
          T1.BUSI_SUM_AMT,
          T1.BUSI_CERT_CNT,
          T1.CERT_NUM,
          T1.CERT_TYPE_CD,
          T1.CERT_PPT_CD,
          T1.CERT_INTEREST_PERIOD,
          T1.CERT_INTEREST_RATE,
          T1.CERT_CURRENCY_CD,
          T1.CERT_SEQ_AMT,
          T1.CERT_APPLY_AMT,
          T1.CERT_APPLY_BALANCE,
          T1.CERT_STATUS,
          T1.CERT_BEGIN_DATE,
          T1.CERT_END_DATE,
          T1.CERT_FINISH_DATE,
          T1.CERT_DRAWER_CUST_NUM,
          T1.CERT_DRAWER_NAME,
          T1.CERT_DRAWER_BANK_NUM,
          T1.CERT_DRAWER_BANK_NAME,
          T1.CERT_GUARANTY_TYPE,
          T1.CERT_GUARANTY_PERSON,
          T1.CERT_BUSI_REMARK
   FROM TB_FUND_EVENT_DETAIL_MIDDLE T1
        LEFT JOIN TB_FUND_EVENT_MAIN T2
           ON     T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN
              AND T1.TRAN_DATE = T2.TRAN_DATE
              AND T2.TRAN_DATE = EXEC_DATE
              AND TRAN_EVENT_STATUS = '0'
              AND TRAN_ACCT_STATUS = '1';
    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    SET PROC_NAME = 'PRC_FUND_CRD_OPT_CHECK.3';
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '额度系统数据不存在、进行插入额度明细信息';
    --记录插入日志信息
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    SET RES_NUM = 1;
  END IF;
-----------------------------STEP2--END-----------------------------------
-----------------------------STEP3--BEGIN---------------------------------
  IF CRD_HAVE > 0
  THEN
    UPDATE
     TB_FUND_EVENT_MAIN T3
    SET
     T3.TRAN_EVENT_INFO                = '资金系统不存在该数据',
     T3.TRAN_ACCT_STATUS = '2',
     T3.ADJUST_FLAG = '1',
     T3.ADJUST_DIRECTION = '0'
    WHERE NOT EXISTS (SELECT 1 FROM  TB_FUND_EVENT_MAIN_MIDDLE T2
                            WHERE T3.TRAN_SEQ_SN = T2.TRAN_SEQ_SN AND
                               T3.TRAN_DATE = T2.TRAN_DATE) AND T3.TRAN_DATE = EXEC_DATE AND T3.TRAN_EVENT_STATUS = '1';
    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    SET PROC_NAME = 'PRC_FUND_CRD_OPT_CHECK.4';
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '资金系统不存在的用信明细数据，需要冲账';
    --记录插入日志信息
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    SET RES_NUM = 1;
  END IF;
  -----------------------------STEP3--BEND---------------------------------
END;

comment on procedure PRC_FUND_CRD_OPT_CHECK(VARCHAR(10), INTEGER) is '资金系统用信信息对账';

CREATE PROCEDURE CLM.PRC_CRD_EARK_REVERSE (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_CRD_EARK_REVERSE
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE V_CRD_GRANT_ORG_NUM VARCHAR(10); --授信机构
  DECLARE V_CUSTOMER_NUM  VARCHAR(10); --ECIF客户号
  DECLARE V_CRD_MAIN_PRD  VARCHAR(10); --二级额度产品
  DECLARE V_CRD_MAIN_NUM VARCHAR(40); --二级额度编号
  DECLARE V_CRD_DETAIL_PRD VARCHAR(10); --三级额度产品
  DECLARE V_CRD_DETAIL_NUM VARCHAR(10); --三级额度编号
  DECLARE V_D_CRD_DETAIL_NUM VARCHAR(10); --明细表三级额度编号
  DECLARE V_LENGTH_VALUE INTEGER; --字符长度
  DECLARE V_CRD_FLOW_VALUE INTEGER; --额度流水数量
  DECLARE V_D_CRD_FLOW_VALUE INTEGER;--明细流水数量
  DECLARE V_ORG_NUM_PRO VARCHAR(10); ---省联社机构号1000
  DECLARE V_ORG_NUM_DIV VARCHAR(10); ---分行机构号
  DECLARE V_ADJUST_COUNT INTEGER; ---需要冲账的数量
  DECLARE V_TRAN_DATE VARCHAR(10); --交易日期
  DECLARE V_TRAN_SEQ_SN VARCHAR(20); --原流水号
  DECLARE N_TRAN_SEQ_SN VARCHAR(20); --新流水号
  DECLARE V_COM_QUOTA VARCHAR(2);--综合额度产品编号
  DECLARE V_TRAN_SYSTEM VARCHAR(10);---定义来源系统号
  DECLARE V_D_CUSTOMER_NUM  VARCHAR(10);--明细客户号
  DECLARE V_D_CRD_DETAIL_PRD  VARCHAR(10);--明细客户号
  DECLARE V_USER_NUM  VARCHAR(10);---统一授信系统的操作员
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_CRD_EARK_REVERSE'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;
  SET V_ORG_NUM_PRO = '1000';
  SET V_COM_QUOTA = '01';
  SET V_TRAN_SYSTEM = '0100';--根据需要改变
  --SET V_CLM_SYSTEM = '0087';--统一授信系统的系统编号
  SET V_USER_NUM = '0000001';--统一授信系统的操作员


  /*
  对于授信类的交易，如果对账的过程中发现额度系统中存在该笔交易、但是资金系统
  不存在该笔交易、则寻找最近的一笔流水，将额度系统的授信额度按最后一次流水进
  行授信。
  主要包括维护综合额度授信(01)、维护额度切分(02)、额度圈存维护(04)、
  额度冻结(05)、客户准入信息维护(06)
  */
  ------------------------------------------------------------------------------
  ----额度圈存冲账----BEGIN-----------------------------------------------------
  /*
    省联社对下级成员行进行授信预占用分配，只允许省联社发起圈存，
    省联社对某同业客户或发行人的剩余单项业务授信额度进行圈存，
    将圈存额度分配给一个或多个成员行使用，被分配圈存额度的成员
    行优先用信圈存额度，其他成员行对在同业客户用信检查剩余额度
    时需扣除圈存额度。
    额度圈存会产生的流水记录数为：圈存分行总数N+省联社1 N+1条。
    根据交易日期查询TB_FUND_EARMARK_MAIN额度圈存主表，如果需要冲账的数量大于1，则
    根据额度交易日期查询tb_fund_earmark_allot表、找出对应的额度切分信息。
    根据额度交易流水号、交易日期查询客户号、客户产品、省联社机构号，查询出额度编号
  */
  SELECT
   COUNT(*)
  INTO
   V_ADJUST_COUNT
  FROM
   TB_FUND_EARMARK_MAIN T1
  WHERE
   T1.ADJUST_FLAG = '1' AND
   T1.ADJUST_DIRECTION = '0' AND
   T1.TRAN_DATE = EXEC_DATE;

  IF V_ADJUST_COUNT > 0
  THEN
      /*
        是否按照流水顺序？
      */
        FOR EARMARK_MAIN_DATAS AS(  SELECT T1.TRAN_SEQ_SN, T1.TRAN_DATE FROM TB_FUND_EARMARK_MAIN T1 WHERE T1.ADJUST_FLAG = '1' AND T1.ADJUST_DIRECTION = '0' AND T1.TRAN_DATE = EXEC_DATE)
        DO
          SET V_TRAN_DATE = EARMARK_MAIN_DATAS.TRAN_DATE;
          SET V_TRAN_SEQ_SN = EARMARK_MAIN_DATAS.TRAN_SEQ_SN;
          SET N_TRAN_SEQ_SN = CLM.FNC_GET_TRAN_SEQ();--产生交易流水号
          FOR CRD_EARMARK_UION AS (SELECT T2.CUSTOMER_NUM, T2.CRD_DETAIL_PRD FROM TB_FUND_EARMARK_ALLOT T2 WHERE  T2.TRAN_SEQ_SN = V_TRAN_SEQ_SN AND T2.TRAN_DATE = V_TRAN_DATE GROUP BY T2.CUSTOMER_NUM, T2.CRD_DETAIL_PRD)
          DO
            SET  V_CUSTOMER_NUM = CRD_EARMARK_UION.CUSTOMER_NUM;
            SET  V_CRD_DETAIL_PRD = CRD_EARMARK_UION.CRD_DETAIL_PRD;
            /*
              省联社的机构号为固定的'1000' V_ORG_NUM_PRO
              根据客户号、产品编号、授信机构查询额度编号
            */
            SELECT  T1.CRD_DETAIL_NUM INTO V_CRD_DETAIL_NUM FROM TB_CRD_DETAIL T1 WHERE  T1.CRD_GRANT_ORG_NUM = V_ORG_NUM_PRO AND  T1.CUSTOMER_NUM = V_CUSTOMER_NUM AND  T1.CRD_DETAIL_PRD = V_CRD_DETAIL_PRD;
            SET V_CRD_FLOW_VALUE = (SELECT  COUNT(*) FROM  TB_CRD_GRANTING_SERIAL T1  WHERE T1.TRAN_TYPE_CD = '04' AND T1.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM);
            IF V_CRD_FLOW_VALUE > 1
            THEN
              /*
                将非本笔且最近的一笔流水(按时间排序，第二笔)重新插入流水表中、修改交易系统为CLM、操作人员为RVTELLER--冲账管理员。
              */
              INSERT INTO
          TB_CRD_GRANTING_SERIAL(
           GRANTING_SERIAL_ID --授信流水号
          ,TRAN_SEQ_SN --交易流水号
          ,TRAN_DATE --交易日期
          ,BUSI_DEAL_NUM --业务编号
          ,TRAN_TYPE_CD --交易类型
          ,CRD_GRANT_ORG_NUM --授信机构
          ,CUSTOMER_NUM --ECIF客户号
          ,CRD_DETAIL_PRD --额度产品
          ,CRD_DETAIL_NUM --额度编号
          ,CRD_CURRENCY_CD --币种
          ,CRD_DETAIL_AMT --授信额度
          ,CRD_EARK_AMT --圈存额度
          ,CRD_BEGIN_DATE --开始日期
          ,CRD_END_DATE --截至日期
          ,CRD_STATUS --额度状态
          ,CRD_ADMIT_FLAG --准入标准
          ,TRAN_SYSTEM --交易系统
          ,USER_NUM --交易柜员
          ,CREATE_TIME --创建时间
          ,UPDATE_TIME --更新时间
                        )
          SELECT
          FNC_GET_CLM_UUID(),
          N_TRAN_SEQ_SN,
          T1.TRAN_DATE,
          T1.BUSI_DEAL_NUM,
          T1.TRAN_TYPE_CD,
          T1.CRD_GRANT_ORG_NUM,
          T1.CUSTOMER_NUM,
          T1.CRD_DETAIL_PRD,
          T1.CRD_DETAIL_NUM,
          T1.CRD_CURRENCY_CD,
          T1.CRD_DETAIL_AMT,
          T1.CRD_EARK_AMT,
          T1.CRD_BEGIN_DATE,
          T1.CRD_END_DATE,
          T1.CRD_STATUS,
          T1.CRD_ADMIT_FLAG,
          T1.TRAN_SYSTEM,--
          V_USER_NUM,
          T1.CREATE_TIME,
          CURRENT_TIMESTAMP
      FROM
          (SELECT
              T2.GRANTING_SERIAL_ID --授信流水号
             ,T2.TRAN_SEQ_SN --交易流水号
             ,T2.TRAN_DATE --交易日期
             ,T2.BUSI_DEAL_NUM --业务编号
             ,T2.TRAN_TYPE_CD --交易类型
             ,T2.CRD_GRANT_ORG_NUM --授信机构
             ,T2.CUSTOMER_NUM --ECIF客户号
             ,T2.CRD_DETAIL_PRD --额度产品
             ,T2.CRD_DETAIL_NUM --额度编号
             ,T2.CRD_CURRENCY_CD --币种
             ,T2.CRD_DETAIL_AMT --授信额度
             ,T2.CRD_EARK_AMT --圈存额度
             ,T2.CRD_BEGIN_DATE --开始日期
             ,T2.CRD_END_DATE --截至日期
             ,T2.CRD_STATUS --额度状态
             ,T2.CRD_ADMIT_FLAG --准入标准
             ,T2.TRAN_SYSTEM --交易系统
             ,T2.USER_NUM --交易柜员
             ,T2.CREATE_TIME --创建时间
             ,T2.UPDATE_TIME --更新时间
          FROM
              TB_CRD_GRANTING_SERIAL T2
          WHERE
              T2.TRAN_TYPE_CD = '04'
              AND T2.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM
          ORDER BY
            BIGINT((SUBSTR(T2.TRAN_SEQ_SN,3,LENGTH(T2.TRAN_SEQ_SN) - 2))) DESC
          FETCH FIRST 2 ROWS ONLY) T1
        ORDER BY
          BIGINT((SUBSTR(T1.TRAN_SEQ_SN,3,LENGTH(T1.TRAN_SEQ_SN) - 2))) ASC
        FETCH FIRST 1 ROWS ONLY;
            UPDATE
              TB_CRD_DETAIL T3
            SET
            (
               T3.CURRENCY_CD,
               T3.LIMIT_EARMARK,
               T3.EARMARK_BEGIN_DATE,
               T3.EARMARK_END_DATE,
               T3.TRAN_SYSTEM,
               T3.USER_NUM,
               T3.UPDATE_TIME) = (
                             SELECT
                              T5.CRD_CURRENCY_CD,
                              T5.CRD_EARK_AMT,
                              T5.CRD_BEGIN_DATE,
                              T5.CRD_END_DATE,
                              T5.TRAN_SYSTEM,
                              V_USER_NUM,
                              CURRENT_TIMESTAMP
                             FROM
                              TB_CRD_GRANTING_SERIAL T5
                             WHERE
                                T5.TRAN_SEQ_SN = N_TRAN_SEQ_SN) WHERE
               T3.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM;
            ELSE
            INSERT INTO
              TB_CRD_GRANTING_SERIAL
              (
                GRANTING_SERIAL_ID --授信流水号
                ,TRAN_SEQ_SN --交易流水号
                ,TRAN_DATE --交易日期
                ,BUSI_DEAL_NUM --业务编号
                ,TRAN_TYPE_CD --交易类型
                ,CRD_GRANT_ORG_NUM --授信机构
                ,CUSTOMER_NUM --ECIF客户号
                ,CRD_DETAIL_PRD --额度产品
                ,CRD_DETAIL_NUM --额度编号
                ,CRD_CURRENCY_CD --币种
                ,CRD_DETAIL_AMT --授信额度
                ,CRD_EARK_AMT --圈存额度
                ,CRD_BEGIN_DATE --开始日期
                ,CRD_END_DATE --截至日期
                ,CRD_STATUS --额度状态
                ,CRD_ADMIT_FLAG --准入标准
                ,TRAN_SYSTEM --交易系统
                ,USER_NUM --交易柜员
                ,CREATE_TIME --创建时间
                ,UPDATE_TIME --更新时间
                      )
          SELECT
           FNC_GET_CLM_UUID(),
           N_TRAN_SEQ_SN,
           T1.TRAN_DATE,
           T1.BUSI_DEAL_NUM,
           T1.TRAN_TYPE_CD,
           T1.CRD_GRANT_ORG_NUM,
           T1.CUSTOMER_NUM,
           T1.CRD_DETAIL_PRD,
           T1.CRD_DETAIL_NUM,
           T1.CRD_CURRENCY_CD,
           T1.CRD_DETAIL_AMT,
           0.0,
           NULL,
           NULL,
           T1.CRD_STATUS,
           T1.CRD_ADMIT_FLAG,
           T1.TRAN_SYSTEM,
           V_USER_NUM,
           T1.CREATE_TIME,
           CURRENT_TIMESTAMP
          FROM
            TB_CRD_GRANTING_SERIAL T1
          WHERE
            T1.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM
            AND T1.TRAN_TYPE_CD = '04';
                      UPDATE
          TB_CRD_DETAIL T3
            SET
            (
               T3.CURRENCY_CD,
               T3.LIMIT_EARMARK,
               T3.EARMARK_BEGIN_DATE,
               T3.EARMARK_END_DATE,
               T3.TRAN_SYSTEM,
               T3.USER_NUM,
               T3.UPDATE_TIME) = (
                             SELECT
                              T5.CRD_CURRENCY_CD,
                              T5.CRD_EARK_AMT,
                              T5.CRD_BEGIN_DATE,
                              T5.CRD_END_DATE,
                              T5.TRAN_SYSTEM,
                              V_USER_NUM,
                              CURRENT_TIMESTAMP
                             FROM
                              TB_CRD_GRANTING_SERIAL T5
                             WHERE
                                T5.TRAN_SEQ_SN = N_TRAN_SEQ_SN) WHERE
               T3.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM;
            END IF;
            FOR CRD_EARMARK_DATAS AS(SELECT T2.CRD_ALLOT_ORG_NUM, T2.CUSTOMER_NUM, T2.CRD_DETAIL_PRD FROM TB_FUND_EARMARK_ALLOT T2 WHERE T2.TRAN_SEQ_SN = V_TRAN_SEQ_SN AND T2.TRAN_DATE = V_TRAN_DATE)
            DO
              SET  V_ORG_NUM_DIV = CRD_EARMARK_DATAS.CRD_ALLOT_ORG_NUM;
              SET  V_D_CUSTOMER_NUM = CRD_EARMARK_UION.CUSTOMER_NUM;
              SET  V_D_CRD_DETAIL_PRD = CRD_EARMARK_UION.CRD_DETAIL_PRD;
              SELECT T1.CRD_DETAIL_NUM INTO V_D_CRD_DETAIL_NUM FROM TB_CRD_DETAIL T1 WHERE  T1.CRD_GRANT_ORG_NUM = V_ORG_NUM_DIV AND  T1.CUSTOMER_NUM = V_D_CUSTOMER_NUM AND  T1.CRD_DETAIL_PRD = V_D_CRD_DETAIL_PRD;
              SET V_D_CRD_FLOW_VALUE    = ( SELECT COUNT(*) FROM TB_CRD_GRANTING_SERIAL T1 WHERE T1.TRAN_TYPE_CD = '04' AND T1.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM);
              IF V_D_CRD_FLOW_VALUE > 1
              THEN
                INSERT INTO
                  TB_CRD_GRANTING_SERIAL(
            GRANTING_SERIAL_ID --授信流水号
          ,TRAN_SEQ_SN --交易流水号
          ,TRAN_DATE --交易日期
          ,BUSI_DEAL_NUM --业务编号
          ,TRAN_TYPE_CD --交易类型
          ,CRD_GRANT_ORG_NUM --授信机构
          ,CUSTOMER_NUM --ECIF客户号
          ,CRD_DETAIL_PRD --额度产品
          ,CRD_DETAIL_NUM --额度编号
          ,CRD_CURRENCY_CD --币种
          ,CRD_DETAIL_AMT --授信额度
          ,CRD_EARK_AMT --圈存额度
          ,CRD_BEGIN_DATE --开始日期
          ,CRD_END_DATE --截至日期
          ,CRD_STATUS --额度状态
          ,CRD_ADMIT_FLAG --准入标准
          ,TRAN_SYSTEM --交易系统
          ,USER_NUM --交易柜员
          ,CREATE_TIME --创建时间
          ,UPDATE_TIME --更新时间
                        )
          SELECT
          FNC_GET_CLM_UUID(),
          N_TRAN_SEQ_SN,
          T1.TRAN_DATE,
          T1.BUSI_DEAL_NUM,
          T1.TRAN_TYPE_CD,
          T1.CRD_GRANT_ORG_NUM,
          T1.CUSTOMER_NUM,
          T1.CRD_DETAIL_PRD,
          T1.CRD_DETAIL_NUM,
          T1.CRD_CURRENCY_CD,
          T1.CRD_DETAIL_AMT,
          T1.CRD_EARK_AMT,
          T1.CRD_BEGIN_DATE,
          T1.CRD_END_DATE,
          T1.CRD_STATUS,
          T1.CRD_ADMIT_FLAG,
          T1.TRAN_SYSTEM,--
          V_USER_NUM,
          T1.CREATE_TIME,
          CURRENT_TIMESTAMP
      FROM
          (SELECT
              T2.GRANTING_SERIAL_ID --授信流水号
             ,T2.TRAN_SEQ_SN --交易流水号
             ,T2.TRAN_DATE --交易日期
             ,T2.BUSI_DEAL_NUM --业务编号
             ,T2.TRAN_TYPE_CD --交易类型
             ,T2.CRD_GRANT_ORG_NUM --授信机构
             ,T2.CUSTOMER_NUM --ECIF客户号
             ,T2.CRD_DETAIL_PRD --额度产品
             ,T2.CRD_DETAIL_NUM --额度编号
             ,T2.CRD_CURRENCY_CD --币种
             ,T2.CRD_DETAIL_AMT --授信额度
             ,T2.CRD_EARK_AMT --圈存额度
             ,T2.CRD_BEGIN_DATE --开始日期
             ,T2.CRD_END_DATE --截至日期
             ,T2.CRD_STATUS --额度状态
             ,T2.CRD_ADMIT_FLAG --准入标准
             ,T2.TRAN_SYSTEM --交易系统
             ,T2.USER_NUM --交易柜员
             ,T2.CREATE_TIME --创建时间
             ,T2.UPDATE_TIME --更新时间
          FROM
              TB_CRD_GRANTING_SERIAL T2
          WHERE
              T2.TRAN_TYPE_CD = '04'
              AND T2.CRD_DETAIL_NUM = V_D_CRD_DETAIL_NUM
          ORDER BY
            BIGINT((SUBSTR(T2.TRAN_SEQ_SN,3,LENGTH(T2.TRAN_SEQ_SN) - 2))) DESC
          FETCH FIRST 2 ROWS ONLY) T1
        ORDER BY
          BIGINT((SUBSTR(T1.TRAN_SEQ_SN,3,LENGTH(T1.TRAN_SEQ_SN) - 2))) ASC
        FETCH FIRST 1 ROWS ONLY;
            UPDATE
              TB_CRD_DETAIL T3
            SET
            (
               T3.CURRENCY_CD,
               T3.LIMIT_EARMARK,
               T3.EARMARK_BEGIN_DATE,
               T3.EARMARK_END_DATE,
               T3.TRAN_SYSTEM,
               T3.USER_NUM,
               T3.UPDATE_TIME) = (
                             SELECT
                              T5.CRD_CURRENCY_CD,
                              T5.CRD_EARK_AMT,
                              T5.CRD_BEGIN_DATE,
                              T5.CRD_END_DATE,
                              T5.TRAN_SYSTEM,
                              V_USER_NUM,
                              CURRENT_TIMESTAMP
                             FROM
                              TB_CRD_GRANTING_SERIAL T5
                             WHERE
                                T5.TRAN_SEQ_SN = N_TRAN_SEQ_SN) WHERE
               T3.CRD_DETAIL_NUM = V_D_CRD_DETAIL_NUM;
              ELSE
            INSERT INTO
              TB_CRD_GRANTING_SERIAL
              (
                GRANTING_SERIAL_ID --授信流水号
                ,TRAN_SEQ_SN --交易流水号
                ,TRAN_DATE --交易日期
                ,BUSI_DEAL_NUM --业务编号
                ,TRAN_TYPE_CD --交易类型
                ,CRD_GRANT_ORG_NUM --授信机构
                ,CUSTOMER_NUM --ECIF客户号
                ,CRD_DETAIL_PRD --额度产品
                ,CRD_DETAIL_NUM --额度编号
                ,CRD_CURRENCY_CD --币种
                ,CRD_DETAIL_AMT --授信额度
                ,CRD_EARK_AMT --圈存额度
                ,CRD_BEGIN_DATE --开始日期
                ,CRD_END_DATE --截至日期
                ,CRD_STATUS --额度状态
                ,CRD_ADMIT_FLAG --准入标准
                ,TRAN_SYSTEM --交易系统
                ,USER_NUM --交易柜员
                ,CREATE_TIME --创建时间
                ,UPDATE_TIME --更新时间
                      )
          SELECT
           FNC_GET_CLM_UUID(),
           N_TRAN_SEQ_SN,
           T1.TRAN_DATE,
           T1.BUSI_DEAL_NUM,
           T1.TRAN_TYPE_CD,
           T1.CRD_GRANT_ORG_NUM,
           T1.CUSTOMER_NUM,
           T1.CRD_DETAIL_PRD,
           T1.CRD_DETAIL_NUM,
           T1.CRD_CURRENCY_CD,
           T1.CRD_DETAIL_AMT,
           0.0,
           NULL,
           NULL,
           T1.CRD_STATUS,
           T1.CRD_ADMIT_FLAG,
           T1.TRAN_SYSTEM,
           V_USER_NUM,
           T1.CREATE_TIME,
           CURRENT_TIMESTAMP
          FROM
            TB_CRD_GRANTING_SERIAL T1
          WHERE
            T1.CRD_DETAIL_NUM = V_D_CRD_DETAIL_NUM
            AND T1.TRAN_TYPE_CD = '04';
            UPDATE
                TB_CRD_DETAIL T3
            SET
            (
               T3.CURRENCY_CD,
               T3.LIMIT_EARMARK,
               T3.EARMARK_BEGIN_DATE,
               T3.EARMARK_END_DATE,
               T3.TRAN_SYSTEM,
               T3.USER_NUM,
               T3.UPDATE_TIME) = (
                             SELECT
                              T5.CRD_CURRENCY_CD,
                              T5.CRD_EARK_AMT,
                              T5.CRD_BEGIN_DATE,
                              T5.CRD_END_DATE,
                              T5.TRAN_SYSTEM,
                              V_USER_NUM,
                              CURRENT_TIMESTAMP
                             FROM
                              TB_CRD_GRANTING_SERIAL T5
                             WHERE
                                T5.TRAN_SEQ_SN = N_TRAN_SEQ_SN) WHERE
               T3.CRD_DETAIL_NUM = V_D_CRD_DETAIL_NUM;
              END IF;
            END FOR;
          END FOR;
        SET STATUS = '1';
        SET SQL_CODE = SQLCODE;
        SET REMARK = '需要冲账数量为：'||V_ADJUST_COUNT||',冲账完成';
        CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,NULL,SQL_CODE,REMARK);
        SET RES_NUM = 1;
      END FOR;
      ELSE
        SET STATUS = '1';
        SET SQL_CODE = SQLCODE;
        SET REMARK = '需要冲账数量为：'||V_ADJUST_COUNT||',冲账完成';
        CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,NULL,SQL_CODE,REMARK);
        SET RES_NUM = 1;
      END IF;
END;

comment on procedure PRC_CRD_EARK_REVERSE(VARCHAR(10), INTEGER) is '授信额度圈存-冲账';

CREATE PROCEDURE CLM.PRC_CRD_SEG_CREDIT_LIMIT (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_CRD_SEG_CREDIT_LIMIT
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE V_CRD_GRANT_ORG_NUM   VARCHAR(10); --授信机构
  DECLARE V_CUSTOMER_NUM        VARCHAR(10); --ECIF客户号
  DECLARE V_CRD_MAIN_PRD        VARCHAR(10); --二级额度产品
  DECLARE V_CRD_MAIN_NUM        VARCHAR(40); --二级额度编号
  DECLARE V_CRD_DETAIL_PRD      VARCHAR(10); --三级额度产品
  DECLARE V_CRD_DETAIL_NUM      VARCHAR(10); --三级额度编号
  DECLARE V_LENGTH_VALUE        INTEGER; --字符长度
  DECLARE V_CRD_FLOW_VALUE      INTEGER; --额度流水数量
  DECLARE V_ORG_NUM_PRO         VARCHAR(10); ---省联社机构号1000
  DECLARE V_ORG_NUM_DIV         VARCHAR(10); ---分行机构号
  DECLARE V_ADJUST_COUNT        INTEGER; ---需要冲账的数量
  DECLARE V_TRAN_DATE           VARCHAR(10); --交易日期
  DECLARE V_TRAN_SEQ_SN         VARCHAR(20); --原流水号
  DECLARE N_TRAN_SEQ_SN         VARCHAR(20); --新流水号
  DECLARE V_COM_QUOTA           VARCHAR(2);--综合额度产品编号
  DECLARE V_TRAN_SYSTEM         VARCHAR(10);---定义来源系统号
  --DECLARE V_CLM_SYSTEM         VARCHAR(10);---统一授信系统的系统编号
  DECLARE V_USER_NUM         VARCHAR(10);---统一授信系统的操作员
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_CRD_SEG_CREDIT_LIMIT'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
    BEGIN
        SET SQL_CODE = SQLCODE;
        SET REMARK   = SQLSTATE;
        SET STATUS   = '0';
        CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
        SET RES_NUM  = 0;
        --ROLLBACK;
        RETURN;
    END;

  SET V_ORG_NUM_PRO = '1000';
  SET V_COM_QUOTA = '01';
  SET V_TRAN_SYSTEM = '0100';--根据需要改变
  --SET V_CLM_SYSTEM = '0087';--统一授信系统的系统编号
  SET V_USER_NUM = '0000001';--统一授信系统的操作员


  /*
  对于授信类的交易，如果对账的过程中发现额度系统中存在该笔交易、但是资金系统
  不存在该笔交易、则寻找最近的一笔流水，将额度系统的授信额度按最后一次流水进
  行授信。
  主要包括维护综合额度授信(01)、维护额度切分(02)、额度圈存维护(04)、
  额度冻结(05)、客户准入信息维护(06)
  */
  ------------------------------------------------------------------------------
  ----额度切分冲账----BEGIN-----------------------------------------------------
  /*
  根据传进的日期，查询额度授信事件主表中需要做冲账的数据数量，如果数量大于1，则
  根据该日期在授信明细表TB_FUND_GRANT_DETAIL中查询相应的授信机构、ECIF客户号、二
  级产品，以查询的 数据为条件在额度主表中查询出额度编号,根据额度编号查询流水表
  TB_CRD_GRANTING_SERIAL 中日期不等于IN_TRAN_DATE、流水不等于N_TRAN_SEQ_SN的信息按时间排序、如果存在就取
  第一条
  */
  SELECT  COUNT(*) INTO V_ADJUST_COUNT FROM TB_FUND_GRANT_MAIN T1
      WHERE T1.ADJUST_FLAG = '1' AND T1.ADJUST_DIRECTION = '0' AND T1.TRAN_DATE = EXEC_DATE AND T1.TRAN_TYPE_CD = '02';

  IF V_ADJUST_COUNT > 0
  THEN
    FOR SEG_MAIN_DATAS AS (SELECT T1.TRAN_SEQ_SN, T1.TRAN_DATE FROM TB_FUND_GRANT_MAIN T1 WHERE T1.ADJUST_FLAG = '1' AND T1.ADJUST_DIRECTION = '0' AND T1.TRAN_DATE = EXEC_DATE AND T1.TRAN_TYPE_CD = '02')
    DO
      SET V_TRAN_SEQ_SN = SEG_MAIN_DATAS.TRAN_SEQ_SN;
      SET V_TRAN_DATE = SEG_MAIN_DATAS.TRAN_DATE;
      SET N_TRAN_SEQ_SN = CLM.FNC_GET_TRAN_SEQ();
      FOR SEG_DATAS AS ( SELECT T2.CRD_GRANT_ORG_NUM, T2.CUSTOMER_NUM, T2.CRD_DETAIL_PRD FROM TB_FUND_GRANT_DETAIL T2  WHERE T2.TRAN_SEQ_SN = V_TRAN_SEQ_SN AND  T2.TRAN_DATE = V_TRAN_DATE)
      DO
        SET V_CRD_GRANT_ORG_NUM = SEG_DATAS.CRD_GRANT_ORG_NUM;
        SET V_CUSTOMER_NUM      = SEG_DATAS.CUSTOMER_NUM;
        SET V_CRD_DETAIL_PRD      = SEG_DATAS.CRD_DETAIL_PRD;
        SELECT T1.CRD_DETAIL_NUM INTO V_CRD_DETAIL_NUM FROM TB_CRD_DETAIL T1 WHERE
                                    T1.CRD_GRANT_ORG_NUM = V_CRD_GRANT_ORG_NUM
                                    AND T1.CUSTOMER_NUM = V_CUSTOMER_NUM
                                    AND T1.CRD_DETAIL_PRD = V_CRD_DETAIL_PRD;
        /*
        更新额度产品表信息
        */
        SET V_CRD_FLOW_VALUE    = (SELECT  COUNT(*) FROM TB_CRD_GRANTING_SERIAL T1  WHERE T1.TRAN_TYPE_CD = '02' AND T1.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM);
      /*
        如果V_CRD_FLOW_VALUE>1则表示该客户在该机构下该产品有过切分交易
      */
      IF V_CRD_FLOW_VALUE > 1
      THEN
          INSERT INTO
          TB_CRD_GRANTING_SERIAL(
           GRANTING_SERIAL_ID --授信流水号
          ,TRAN_SEQ_SN --交易流水号
          ,TRAN_DATE --交易日期
          ,BUSI_DEAL_NUM --业务编号
          ,TRAN_TYPE_CD --交易类型
          ,CRD_GRANT_ORG_NUM --授信机构
          ,CUSTOMER_NUM --ECIF客户号
          ,CRD_DETAIL_PRD --额度产品
          ,CRD_DETAIL_NUM --额度编号
          ,CRD_CURRENCY_CD --币种
          ,CRD_DETAIL_AMT --授信额度
          ,CRD_EARK_AMT --圈存额度
          ,CRD_BEGIN_DATE --开始日期
          ,CRD_END_DATE --截至日期
          ,CRD_STATUS --额度状态
          ,CRD_ADMIT_FLAG --准入标准
          ,TRAN_SYSTEM --交易系统
          ,USER_NUM --交易柜员
          ,CREATE_TIME --创建时间
          ,UPDATE_TIME --更新时间
                        )
          SELECT
          FNC_GET_CLM_UUID(),
          N_TRAN_SEQ_SN,
          T1.TRAN_DATE,
          T1.BUSI_DEAL_NUM,
          T1.TRAN_TYPE_CD,
          T1.CRD_GRANT_ORG_NUM,
          T1.CUSTOMER_NUM,
          T1.CRD_DETAIL_PRD,
          T1.CRD_DETAIL_NUM,
          T1.CRD_CURRENCY_CD,
          T1.CRD_DETAIL_AMT,
          T1.CRD_EARK_AMT,
          T1.CRD_BEGIN_DATE,
          T1.CRD_END_DATE,
          T1.CRD_STATUS,
          T1.CRD_ADMIT_FLAG,
          T1.TRAN_SYSTEM,--
          V_USER_NUM,
          T1.CREATE_TIME,
          CURRENT_TIMESTAMP
      FROM
          (SELECT
              T2.GRANTING_SERIAL_ID --授信流水号
             ,T2.TRAN_SEQ_SN --交易流水号
             ,T2.TRAN_DATE --交易日期
             ,T2.BUSI_DEAL_NUM --业务编号
             ,T2.TRAN_TYPE_CD --交易类型
             ,T2.CRD_GRANT_ORG_NUM --授信机构
             ,T2.CUSTOMER_NUM --ECIF客户号
             ,T2.CRD_DETAIL_PRD --额度产品
             ,T2.CRD_DETAIL_NUM --额度编号
             ,T2.CRD_CURRENCY_CD --币种
             ,T2.CRD_DETAIL_AMT --授信额度
             ,T2.CRD_EARK_AMT --圈存额度
             ,T2.CRD_BEGIN_DATE --开始日期
             ,T2.CRD_END_DATE --截至日期
             ,T2.CRD_STATUS --额度状态
             ,T2.CRD_ADMIT_FLAG --准入标准
             ,T2.TRAN_SYSTEM --交易系统
             ,T2.USER_NUM --交易柜员
             ,T2.CREATE_TIME --创建时间
             ,T2.UPDATE_TIME --更新时间
          FROM
              TB_CRD_GRANTING_SERIAL T2
          WHERE
              T2.TRAN_TYPE_CD = '02'
              AND T2.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM
          ORDER BY
            BIGINT((SUBSTR(T2.TRAN_SEQ_SN,3,LENGTH(T2.TRAN_SEQ_SN) - 2))) DESC
          FETCH FIRST 2 ROWS ONLY) T1
        ORDER BY
          BIGINT((SUBSTR(T1.TRAN_SEQ_SN,3,LENGTH(T1.TRAN_SEQ_SN) - 2))) ASC
        FETCH FIRST 1 ROWS ONLY;
        --COMMIT;
        UPDATE
         TB_CRD_DETAIL T1
        SET
         (
           T1.BEGIN_DATE,
           T1.END_DATE,
           T1.LIMIT_CREDIT,
           T1.USER_NUM,
           T1.TRAN_SYSTEM,
           T1.UPDATE_TIME) = (
                               SELECT
                                T5.CRD_BEGIN_DATE,
                                T5.CRD_END_DATE,
                                T5.CRD_DETAIL_AMT,
                                V_USER_NUM,
                                T5.TRAN_SYSTEM,
                                CURRENT_TIMESTAMP
                               FROM
                                TB_CRD_GRANTING_SERIAL T5
                               WHERE
                                T5.TRAN_SEQ_SN = N_TRAN_SEQ_SN AND T5.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM )
        WHERE
         T1.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM;

      ELSE
        /*
          如果流水表中的数据只有一条则将在流水表中插入一条流水记录，用以修改冲账信息，将授信额度修改为0.0
        */
INSERT INTO
              TB_CRD_GRANTING_SERIAL
              (
                GRANTING_SERIAL_ID --授信流水号
                ,TRAN_SEQ_SN --交易流水号
                ,TRAN_DATE --交易日期
                ,BUSI_DEAL_NUM --业务编号
                ,TRAN_TYPE_CD --交易类型
                ,CRD_GRANT_ORG_NUM --授信机构
                ,CUSTOMER_NUM --ECIF客户号
                ,CRD_DETAIL_PRD --额度产品
                ,CRD_DETAIL_NUM --额度编号
                ,CRD_CURRENCY_CD --币种
                ,CRD_DETAIL_AMT --授信额度
                ,CRD_EARK_AMT --圈存额度
                ,CRD_BEGIN_DATE --开始日期
                ,CRD_END_DATE --截至日期
                ,CRD_STATUS --额度状态
                ,CRD_ADMIT_FLAG --准入标准
                ,TRAN_SYSTEM --交易系统
                ,USER_NUM --交易柜员
                ,CREATE_TIME --创建时间
                ,UPDATE_TIME --更新时间
                      )
          SELECT
           FNC_GET_CLM_UUID(),
           N_TRAN_SEQ_SN,
           T1.TRAN_DATE,
           T1.BUSI_DEAL_NUM,
           T1.TRAN_TYPE_CD,
           T1.CRD_GRANT_ORG_NUM,
           T1.CUSTOMER_NUM,
           T1.CRD_DETAIL_PRD,
           T1.CRD_DETAIL_NUM,
           T1.CRD_CURRENCY_CD,
           0.0,
           T1.CRD_EARK_AMT,
           T1.CRD_BEGIN_DATE, --开始日期
           T1.CRD_END_DATE, --截至日期
           T1.CRD_STATUS, --额度状态
           T1.CRD_ADMIT_FLAG,
           T1.TRAN_SYSTEM,
           V_USER_NUM,
           T1.CREATE_TIME,
           CURRENT_TIMESTAMP
          FROM
            TB_CRD_GRANTING_SERIAL T1
          WHERE
            T1.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM
            AND T1.TRAN_TYPE_CD = '02';
        /*
         将额度明细表中的额度切分修改为0.0、并将操作机构、操作员工、更新时间修改
        */
       UPDATE
         TB_CRD_DETAIL T1
        SET
         (
           T1.BEGIN_DATE,
           T1.END_DATE,
           T1.LIMIT_CREDIT,
           T1.USER_NUM,
           T1.TRAN_SYSTEM,
           T1.UPDATE_TIME) = (
                               SELECT
                                T5.CRD_BEGIN_DATE,
                                T5.CRD_END_DATE,
                                T5.CRD_DETAIL_AMT,
                                V_USER_NUM,
                                T5.TRAN_SYSTEM,
                                CURRENT_TIMESTAMP
                               FROM
                                TB_CRD_GRANTING_SERIAL T5
                               WHERE
                                T5.TRAN_SEQ_SN = N_TRAN_SEQ_SN AND T5.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM )
        WHERE
         T1.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM;
         SET RES_NUM = 1;
      END IF;
      END FOR;
      SET STATUS = '1';
      SET SQL_CODE = SQLCODE;
      SET REMARK = '需要冲账数量为：'||V_ADJUST_COUNT||',冲账完成';
      CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,NULL,SQL_CODE,REMARK);
      SET RES_NUM = 1;
    END FOR;
  ELSE
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '需要冲账数量为：'||V_ADJUST_COUNT||',冲账完成';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,NULL,SQL_CODE,REMARK);
    SET RES_NUM = 1;
  END IF;
END;

comment on procedure PRC_CRD_SEG_CREDIT_LIMIT(VARCHAR(10), INTEGER) is '授信额度切分-冲账';

CREATE PROCEDURE CLM.PRC_BILL_EVENT_CHECK (
    IN EXEC_DATE	VARCHAR(8),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_BILL_EVENT_CHECK
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT   INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE       INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE    VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS         VARCHAR(20); --执行情况
  DECLARE PROC_NAME      VARCHAR(50) DEFAULT 'PRC_BILL_EVENT_CHECK'; --存储过程
  DECLARE CRD_HAVE       INTEGER DEFAULT 0; --仅统一授信系统有的数据条数
  DECLARE FUND_HAVE      INTEGER DEFAULT 0; --仅资金系统有的数据条数
  DECLARE REMARK         VARCHAR(200);
  DECLARE SQLSTATE CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE INTEGER;
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR  SQLWARNING,SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK = SQLSTATE;
    SET STATUS = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    --ROLLBACK;
    SET RES_NUM = 0;
    RETURN;
  END ;
  DELETE TB_BILL_EVENT_DETAIL T7 WHERE EXISTS (SELECT 1 FROM TB_BILL_EVENT_MAIN T8 WHERE T8.TRAN_SEQ_SN = T7.TRAN_SEQ_SN AND T8.DATA_SRC = '10' ) AND  T7.TRAN_DATE = EXEC_DATE ;

  DELETE TB_BILL_EVENT_MAIN T9 WHERE T9.TRAN_DATE = EXEC_DATE AND T9.DATA_SRC = '10';

  UPDATE TB_FUND_GRANT_MAIN SET TRAN_ACCT_STATUS='0' WHERE TRAN_DATE=EXEC_DATE;

  SET CRD_HAVE          = (
                    SELECT  COUNT(1) FROM  TB_BILL_EVENT_MAIN T1 WHERE NOT EXISTS (SELECT 1 FROM TB_BILL_EVENT_MAIN_MIDDLE T2 WHERE T2.TRAN_SEQ_SN = T1.TRAN_SEQ_SN AND T2.TRAN_DATE = T1.TRAN_DATE AND T1.TRAN_EVENT_STATUS='1'));
  SET FUND_HAVE          = (
                    SELECT COUNT(1) FROM TB_BILL_EVENT_MAIN_MIDDLE T1 WHERE NOT EXISTS (SELECT 1 FROM  TB_BILL_EVENT_MAIN T2 WHERE T2.TRAN_SEQ_SN = T1.TRAN_SEQ_SN AND T2.TRAN_DATE = T1.TRAN_DATE AND T2.TRAN_EVENT_STATUS='1'));
  --SET UUID = (SELECT FNC_GET_UUID() FROM SYSIBM.SYSDUMMY1);
------------------------------------STEP1--BIGIN-----------------------------


  IF CRD_HAVE         = 0 and
     FUND_HAVE = 0
  THEN
    UPDATE
     TB_BILL_EVENT_MAIN T3
    SET
     T3.TRAN_EVENT_INFO  = '系统之间数据无异同',
     T3.TRAN_ACCT_STATUS = '1' WHERE EXISTS (SELECT 1 FROM TB_BILL_EVENT_MAIN_MIDDLE T2 WHERE T2.TRAN_SEQ_SN = T3.TRAN_SEQ_SN AND T2.TRAN_DATE = T3.TRAN_DATE);

    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    --记录插入日志信息
    SET SQL_CODE = SQLCODE;
    SET REMARK = SQLSTATE;
    SET STATUS = '1';
    SET PROC_NAME = 'PRC_BILL_EVENT_CHECK.1';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    SET RES_NUM = 1;
  END IF;
  ------------------------------------STEP1--END-----------------------------
  ------------------------------------STEP2--BIGIN-----------------------------
  IF FUND_HAVE          > 0
  THEN
INSERT INTO
 TB_BILL_EVENT_MAIN(
   EVENT_MAIN_ID,
   TRAN_SEQ_SN,
   TRAN_DATE,
   BUSI_DEAL_NUM,
   TRAN_TYPE_CD,
   CERT_CURRENCY_CD,
   CRD_APPLY_AMT,
   TRAN_EVENT_STATUS,
   TRAN_EVENT_INFO,
   TRAN_ACCT_STATUS,
   ADJUST_FLAG,
   ADJUST_DIRECTION,
   ADJUST_DESC,
   DATA_SRC)
  SELECT
   FNC_GET_CLM_UUID(),
   T1.TRAN_SEQ_SN,
   T1.TRAN_DATE,
   T1.BUSI_DEAL_NUM,
   T1.TRAN_TYPE_CD,
   T1.CRD_CURRENCY_CD,
   T1.CRD_APPLY_AMT,
   '0',
   'CRD系统不存在',
   '1',
   '1',
   '1',
   '',
   '10'
  FROM
   TB_BILL_EVENT_MAIN_MIDDLE T1
  WHERE
   NOT EXISTS
     (SELECT
       1
      FROM
       TB_BILL_EVENT_MAIN T2
      WHERE
       T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN AND
       T1.TRAN_DATE = T2.TRAN_DATE);
    --记录插入日志信息
    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    SET SQL_CODE = SQLCODE;
    SET REMARK = SQLSTATE;
    SET STATUS = '1';
    SET PROC_NAME = 'PRC_BILL_EVENT_CHECK.2';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
       --插入授信额度明细
INSERT INTO TB_BILL_EVENT_DETAIL (
     EVENT_DETAILED_ID
	  ,EVENT_MAIN_ID
    ,TRAN_SEQ_SN
    ,TRAN_DATE
    ,BUSI_DEAL_NUM
    ,BUSI_PRD_NUM
    ,BUSI_DEAL_DESC
    ,BUSI_DEAL_ORG_NUM
    ,BUSI_DEAL_ORG_NAME
    ,BUSI_OPPT_ORG_NUM
    ,BUSI_OPPT_ORG_NAME
    ,BUSI_SUM_AMT
    ,BUSI_CERT_CNT
    ,CERT_NUM
    ,CERT_TYPE_CD
    ,CERT_PPT_CD
    ,CERT_INTEREST_PERIOD
    ,CERT_INTEREST_RATE
    ,CERT_CURRENCY_CD
    ,CERT_SEQ_AMT
    ,CERT_APPLY_AMT
    ,CERT_APPLY_BALANCE
    ,CERT_STATUS
    ,CERT_BEGIN_DATE
    ,CERT_END_DATE
    ,CERT_FINISH_DATE
    ,CERT_DRAWER_CUST_NUM
    ,CERT_DRAWER_NAME
    ,CERT_DRAWER_BANK_NUM
    ,CERT_DRAWER_BANK_NAME
    ,CERT_GUARANTY_TYPE
    ,CERT_GUARANTY_PERSON
    ,CERT_BUSI_REMARK
  )SELECT
  FNC_GET_CLM_UUID(),
 T2.EVENT_MAIN_ID,
 T1.TRAN_SEQ_SN,
 T1.TRAN_DATE,
 T1.BUSI_DEAL_NUM,
 T1.BUSI_PRD_NUM,
 T1.BUSI_DEAL_DESC,
 T1.BUSI_DEAL_ORG_NUM,
 T1.BUSI_DEAL_ORG_NAME,
 T1.BUSI_OPPT_ORG_NUM,
 T1.BUSI_OPPT_ORG_NAME,
 T1.BUSI_SUM_AMT,
 T1.BUSI_CERT_CNT,
 T1.CERT_NUM,
 T1.CERT_TYPE_CD,
 T1.CERT_PPT_CD,
 T1.CERT_INTEREST_PERIOD,
 T1.CERT_INTEREST_RATE,
 T1.CERT_CURRENCY_CD,
 T1.CERT_SEQ_AMT,
 T1.CERT_APPLY_AMT,
 T1.CERT_APPLY_BALANCE,
 T1.CERT_STATUS,
 T1.CERT_BEGIN_DATE,
 T1.CERT_END_DATE,
 T1.CERT_FINISH_DATE,
 T1.CERT_DRAWER_CUST_NUM,
 T1.CERT_DRAWER_NAME,
 T1.CERT_DRAWER_BANK_NUM,
 T1.CERT_DRAWER_BANK_NAME,
 T1.CERT_GUARANTY_TYPE,
 T1.CERT_GUARANTY_PERSON,
 T1.CERT_BUSI_REMARK
FROM
 TB_BILL_EVENT_DETAIL_MIDDLE T1 LEFT JOIN TB_BILL_EVENT_MAIN T2 ON  T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN AND
      T1.TRAN_DATE = T2.TRAN_DATE AND
      T2.TRAN_DATE = EXEC_DATE;
    --记录插入日志信息
    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    SET SQL_CODE = SQLCODE;
    SET REMARK = SQLSTATE;
    SET STATUS = '1';
    SET PROC_NAME = 'PRC_BILL_EVENT_CHECK.3';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    SET RES_NUM = 1;
  END IF;
-----------------------------STEP2--END-----------------------------------
-----------------------------STEP3--BEGIN---------------------------------
  IF CRD_HAVE > 0
  THEN
    UPDATE
     TB_BILL_EVENT_MAIN T3
    SET
     T3.TRAN_EVENT_INFO                 = '资金系统不存在该数据',
     T3.TRAN_ACCT_STATUS = '2',
     T3.ADJUST_FLAG='1',
     T3.ADJUST_DIRECTION='0'
    WHERE
     EXISTS (
     SELECT
 1
FROM
 TB_BILL_EVENT_MAIN T1
WHERE
 NOT EXISTS
   (SELECT
     1
    FROM
     TB_BILL_EVENT_MAIN_MIDDLE T2
    WHERE
     T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN AND
     T1.TRAN_DATE = T2.TRAN_DATE AND
     T1.TRAN_DATE = EXEC_DATE) and
 T1.TRAN_SEQ_SN = T3.TRAN_SEQ_SN
 );
    --记录插入日志信息
    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    SET SQL_CODE = SQLCODE;
    SET REMARK = SQLSTATE;
    SET STATUS = '1';
    SET PROC_NAME = 'PRC_BILL_EVENT_CHECK.4';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    SET RES_NUM = 1;
  END IF;
  -----------------------------STEP3--BEND---------------------------------
END;

comment on procedure PRC_BILL_EVENT_CHECK(VARCHAR(8), INTEGER) is '票据勾兑';

CREATE PROCEDURE CLM.PRC_CRD_DETAIL_INFO (
    IN EXEC_DATE	VARCHAR(8),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_CRD_DETAIL_INFO
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT   INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE       INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE    VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS         VARCHAR(20); --执行情况
  DECLARE PROC_NAME      VARCHAR(50) DEFAULT 'PRC_CRD_DETAIL_INFO'; --存储过程
  DECLARE CRD_HAVE       INTEGER DEFAULT 0; --仅统一授信系统有的数据条数
  DECLARE FUND_HAVE      INTEGER DEFAULT 0; --仅资金系统有的数据条数
  DECLARE REMARK         VARCHAR(200);
  DECLARE SQLSTATE CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE INTEGER;
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR  SQLWARNING,SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK = SQLSTATE;
    SET STATUS = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    --ROLLBACK;
    SET RES_NUM = 0;
    RETURN;
  END ;
  /*
    插入额度明细信息
  */
  INSERT INTO TB_CRD_DETAIL (
     CRD_DETAIL_NUM
    ,CRD_MAIN_NUM
    ,CRD_DETAIL_PRD
    ,CRD_PRODUCT_TYPE
    ,CRD_GRANT_ORG_NUM
    ,CUSTOMER_NUM
    ,CRD_ADMIT_FLAG
    ,CURRENCY_CD
    ,EXCHANGE_RATE
    ,BEGIN_DATE
    ,END_DATE
    ,LIMIT_CREDIT
    ,LIMIT_AVI
    ,LIMIT_USED
    ,EXP_CREDIT
    ,EXP_USED
    ,EXP_AVI
    ,LIMIT_PRE
    ,EXP_PRE
    ,LIMIT_EARMARK
    ,LIMIT_EARMARK_USED
    ,EARMARK_BEGIN_DATE
    ,EARMARK_END_DATE
    ,LIMIT_FROZEN
    ,EXP_FROZEN
    ,IS_CYCLE
    ,IS_MIX
    ,MIX_CREDIT
    ,MIX_USED
    ,MIXREMARK
    ,CLOSE_DATE
    ,CLOSE_REASON
    ,IS_CONTINUE
    ,TRAN_SYSTEM
    ,USER_NUM
    ,ORG_NUM
    ,CREATE_TIME
    ,UPDATE_TIME
    ,DATA_TRANSFER_FLAG
  ) SELECT
     T1.CRD_DETAIL_NUM
    ,T1.CRD_MAIN_NUM
    ,T1.CRD_DETAIL_PRD
    ,T1.CRD_PRODUCT_TYPE
    ,T1.CRD_GRANT_ORG_NUM
    ,T1.CUSTOMER_NUM
    ,T1.CRD_ADMIT_FLAG
    ,T1.CURRENCY_CD
    ,T1.EXCHANGE_RATE
    ,T1.BEGIN_DATE
    ,T1.END_DATE
    ,T1.LIMIT_CREDIT
    ,T1.LIMIT_AVI
    ,T1.LIMIT_USED
    ,T1.EXP_CREDIT
    ,T1.EXP_USED
    ,T1.EXP_AVI
    ,T1.LIMIT_PRE
    ,T1.EXP_PRE
    ,T1.LIMIT_EARMARK
    ,T1.LIMIT_EARMARK_USED
    ,T1.EARMARK_BEGIN_DATE
    ,T1.EARMARK_END_DATE
    ,T1.LIMIT_FROZEN
    ,T1.EXP_FROZEN
    ,T1.IS_CYCLE
    ,T1.IS_MIX
    ,T1.MIX_CREDIT
    ,T1.MIX_USED
    ,T1.MIXREMARK
    ,T1.CLOSE_DATE
    ,T1.CLOSE_REASON
    ,T1.IS_CONTINUE
    ,T1.TRAN_SYSTEM
    ,T1.USER_NUM
    ,T1.ORG_NUM
    ,CURRENT_TIMESTAMP
    ,CURRENT_TIMESTAMP
    ,'1'
  FROM TB_CRD_DETAIL T1 ;
    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    --记录插入日志信息
    SET SQL_CODE = SQLCODE;
    SET REMARK = '明细额度信息数转';
    SET STATUS = '1';
    SET PROC_NAME = 'PRC_CRD_DETAIL_INFO.1';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    SET RES_NUM = 1;
END;

comment on procedure PRC_CRD_DETAIL_INFO(VARCHAR(8), INTEGER) is '额度明细信息入模型层-数转';

CREATE PROCEDURE CLM.PRC_CRD_APPLY_SERIAL_INFO (
    IN EXEC_DATE	VARCHAR(8),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_CRD_APPLY_SERIAL_INFO
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT   INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE       INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE    VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS         VARCHAR(20); --执行情况
  DECLARE PROC_NAME      VARCHAR(50) DEFAULT 'PRC_CRD_APPLY_SERIAL_INFO'; --存储过程
  DECLARE CRD_HAVE       INTEGER DEFAULT 0; --仅统一授信系统有的数据条数
  DECLARE FUND_HAVE      INTEGER DEFAULT 0; --仅资金系统有的数据条数
  DECLARE REMARK         VARCHAR(200);
  DECLARE SQLSTATE CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE INTEGER;
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR  SQLWARNING,SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK = SQLSTATE;
    SET STATUS = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    --ROLLBACK;
    SET RES_NUM = 0;
    RETURN;
  END ;
  INSERT INTO TB_CRD_APPLY_SERIAL (
     SERIAL_ID
    ,TRAN_SEQ_SN
    ,TRAN_DATE
    ,BUSI_DEAL_NUM
    ,TRAN_TYPE_CD
    ,CRD_DETAIL_NUM
    ,CRD_GRANT_ORG_NUM
    ,CUSTOMER_NUM
    ,CRD_DETAIL_PRD
    ,LIMIT_CREDIT_AMT
    ,EXP_CREDIT_AMT
    ,CURRENCY_CD
    ,IS_MIX
    ,MIX_CREDIT
    ,TRAN_SYSTEM
    ,ORG_NUM
    ,USER_NUM
    ,CREATE_TIME
    ,UPDATE_TIME
    ,DATA_TRANSFER_FLAG
  ) SELECT
     FNC_GET_CLM_UUID()
    ,T1.TRAN_SEQ_SN
    ,T1.TRAN_DATE
    ,T1.BUSI_DEAL_NUM
    ,T1.TRAN_TYPE_CD
    ,T1.CRD_DETAIL_NUM
    ,T1.CRD_GRANT_ORG_NUM
    ,T1.CUSTOMER_NUM
    ,T1.CRD_DETAIL_PRD
    ,T1.LIMIT_CREDIT_AMT
    ,T1.EXP_CREDIT_AMT
    ,T1.CURRENCY_CD
    ,T1.IS_MIX
    ,T1.MIX_CREDIT
    ,T1.TRAN_SYSTEM
    ,T1.ORG_NUM
    ,T1.USER_NUM
    ,CURRENT_TIMESTAMP
    ,CURRENT_TIMESTAMP
    ,'1'
  FROM TB_CRD_APPLY_SERIAL_MIDDLE T1;
    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    --记录插入日志信息
    SET SQL_CODE = SQLCODE;
    SET REMARK = '用信流水信息数转';
    SET STATUS = '1';
    SET PROC_NAME = 'PRC_CRD_APPLY_SERIAL_INFO.1';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    SET RES_NUM = 1;

END;

comment on procedure PRC_CRD_APPLY_SERIAL_INFO(VARCHAR(8), INTEGER) is '用信流水信息入模型层-数转';

CREATE PROCEDURE CLM.PRC_CRD_GRANT_SERIAL_INFO (
    IN EXEC_DATE	VARCHAR(8),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_CRD_GRANT_SERIAL_INFO
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT   INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE       INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE    VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS         VARCHAR(20); --执行情况
  DECLARE PROC_NAME      VARCHAR(50) DEFAULT 'PRC_CRD_GRANT_SERIAL_INFO'; --存储过程
  DECLARE CRD_HAVE       INTEGER DEFAULT 0; --仅统一授信系统有的数据条数
  DECLARE FUND_HAVE      INTEGER DEFAULT 0; --仅资金系统有的数据条数
  DECLARE REMARK         VARCHAR(200);
  DECLARE SQLSTATE CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE INTEGER;
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR  SQLWARNING,SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK = SQLSTATE;
    SET STATUS = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    --ROLLBACK;
    SET RES_NUM = 0;
    RETURN;
  END ;
  INSERT INTO TB_CRD_GRANTING_SERIAL (
     GRANTING_SERIAL_ID
    ,TRAN_SEQ_SN
    ,TRAN_DATE
    ,BUSI_DEAL_NUM
    ,TRAN_TYPE_CD
    ,CRD_GRANT_ORG_NUM
    ,CUSTOMER_NUM
    ,CRD_DETAIL_PRD
    ,CRD_DETAIL_NUM
    ,CRD_CURRENCY_CD
    ,CRD_DETAIL_AMT
    ,CRD_EARK_AMT
    ,CRD_BEGIN_DATE
    ,CRD_END_DATE
    ,CRD_STATUS
    ,CRD_ADMIT_FLAG
    ,TRAN_SYSTEM
    ,USER_NUM
    ,CREATE_TIME
    ,UPDATE_TIME
    ,DATA_TRANSFER_FLAG
  ) SELECT
     FNC_GET_CLM_UUID()
    ,T1.TRAN_SEQ_SN
    ,T1.TRAN_DATE
    ,T1.BUSI_DEAL_NUM
    ,T1.TRAN_TYPE_CD
    ,T1.CRD_GRANT_ORG_NUM
    ,T1.CUSTOMER_NUM
    ,T1.CRD_DETAIL_PRD
    ,T1.CRD_DETAIL_NUM
    ,T1.CRD_CURRENCY_CD
    ,T1.CRD_DETAIL_AMT
    ,T1.CRD_EARK_AMT
    ,T1.CRD_BEGIN_DATE
    ,T1.CRD_END_DATE
    ,T1.CRD_STATUS
    ,T1.CRD_ADMIT_FLAG
    ,T1.TRAN_SYSTEM
    ,T1.USER_NUM
    ,CURRENT_TIMESTAMP
    ,CURRENT_TIMESTAMP
    ,'1'
  FROM TB_CRD_GRANTING_SERIAL_MIDDLE T1 ;
    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    --记录插入日志信息
    SET SQL_CODE = SQLCODE;
    SET REMARK = '授信流水信息数转';
    SET STATUS = '1';
    SET PROC_NAME = 'PRC_CRD_GRANT_SERIAL_INFO.1';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    SET RES_NUM = 1;
END;

comment on procedure PRC_CRD_GRANT_SERIAL_INFO(VARCHAR(8), INTEGER) is '授信流水信息入模型层-数转';

CREATE PROCEDURE CLM.PRC_CRD_PRODUCE_DETAIL_SN (
    IN EXEC_DATE	VARCHAR(8),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_CRD_PRODUCE_DETAIL_SN
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT   INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE       INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE    VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS         VARCHAR(20); --执行情况
  DECLARE PROC_NAME      VARCHAR(50) DEFAULT 'PRC_CRD_PRODUCE_DETAIL_SN'; --存储过程
  DECLARE REMARK         VARCHAR(200);
  DECLARE SQLSTATE CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE INTEGER;
  DECLARE V_CRD_DETAIL_NUM	VARCHAR(20);--额度明细编号
  DECLARE V_CRD_DETAIL_PRD	VARCHAR(10);--三级产品编号
  DECLARE V_CRD_GRANT_ORG_NUM	VARCHAR(5);--授信机构
  DECLARE V_CUSTOMER_NUM	VARCHAR(10);--ECIF客户号
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR  SQLWARNING,SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK = SQLSTATE;
    SET STATUS = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    --ROLLBACK;
    SET RES_NUM = 0;
    RETURN;
  END ;
  FOR PRODUCE_DETAIL_SN_DATAS AS (SELECT T1.CRD_DETAIL_PRD, T1.CRD_GRANT_ORG_NUM, T1.CUSTOMER_NUM  FROM  TT_CRD_DETAIL_SN  T1)
  DO
    SET V_CRD_DETAIL_NUM = FNC_GET_BIZ_NUM ('ED2');---产生二级额度编号
    SET V_CRD_DETAIL_PRD = PRODUCE_DETAIL_SN_DATAS.CRD_DETAIL_PRD ;
    SET V_CRD_GRANT_ORG_NUM = PRODUCE_DETAIL_SN_DATAS.CRD_GRANT_ORG_NUM ;
    SET V_CUSTOMER_NUM = PRODUCE_DETAIL_SN_DATAS.CUSTOMER_NUM ;
    UPDATE TT_CRD_DETAIL_SN SET CRD_DETAIL_NUM = V_CRD_DETAIL_NUM
        WHERE V_CRD_DETAIL_PRD = V_CRD_DETAIL_PRD
      AND CRD_GRANT_ORG_NUM = V_CRD_GRANT_ORG_NUM
      AND CUSTOMER_NUM = V_CUSTOMER_NUM;
  END FOR;
  SET EFFECT_COUNT = (SELECT COUNT(*) FROM TT_CRD_DETAIL_SN);---产生主额度编号的数量
  --记录插入日志信息
  SET SQL_CODE = SQLCODE;
  SET REMARK = '产生明细额度编号完毕！';
  SET STATUS = '1';
  SET PROC_NAME = 'PRC_CRD_PRODUCE_DETAIL_SN.1';
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
  SET RES_NUM = 1;
END;

comment on procedure PRC_CRD_PRODUCE_DETAIL_SN(VARCHAR(8), INTEGER) is '额度明细编号产生-数转';

CREATE FUNCTION FNC_GET_DICT_TRANS(IN v_value VARCHAR(10), IN v_code VARCHAR(10))
    RETURNS VARCHAR(100)
    LANGUAGE SQL
BEGIN
    --声明变量
    DECLARE key_name VARCHAR(100);

    if (v_code = '' or v_code is null) then
        return v_value;
    end if;

    if (v_value = '' or v_value is null) then
        return v_value;
    end if;

    if (v_code = 'user') then
        select t.REAL_NAME
        into key_name
        from CHAIN_USER t
        where t.ACCOUNT = v_value;
    elseif (v_code = 'crd') then
        select t.CRD_PRODUCT_NAME
        into key_name
        from TB_PAR_CRD t
        where t.CRD_PRODUCT_NUM = v_value;
    elseif (v_code = 'product') then
        select t.PRODUCT_NAME
        into key_name
        from TB_PAR_PRODUCT t
        where t.PRODUCT_NUM = v_value;
    else
        select t.DICT_VALUE
        into key_name
        from CHAIN_DICT t
        where t.CODE = v_code
          and t.DICT_KEY =v_value;
    end if;

    RETURN key_name;
END;

CREATE PROCEDURE CLM.PRC_CRD_MAIN_INFO (
    IN EXEC_DATE	VARCHAR(8),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_CRD_MAIN_INFO
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT   INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE       INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE    VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS         VARCHAR(20); --执行情况
  DECLARE PROC_NAME      VARCHAR(50) DEFAULT 'PRC_CRD_MAIN_INFO'; --存储过程
  DECLARE REMARK         VARCHAR(200);
  DECLARE SQLSTATE CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE INTEGER;
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR  SQLWARNING,SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK = SQLSTATE;
    SET STATUS = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    --ROLLBACK;
    SET RES_NUM = 0;
  END ;
    INSERT INTO TB_CRD_MAIN (CRD_MAIN_NUM,
                         CRD_MAIN_PRD,
                         CRD_PRODUCT_TYPE,
                         CRD_GRANT_ORG_NUM,
                         CUSTOMER_NUM,
                         CURRENCY_CD,
                         EXCHANGE_RATE,
                         CREDIT_STATUS,
                         LIMIT_CREDIT,
                         LIMIT_USED,
                         LIMIT_AVI,
                         EXP_CREDIT,
                         EXP_USED,
                         EXP_AVI,
                         LIMIT_PRE,
                         EXP_PRE,
                         LIMIT_FROZEN,
                         EXP_FROZEN,
                         BEGIN_DATE,
                         END_DATE,
                         FROZEN_DATE,
                         OVER_DATE,
                         LIMIT_EARMARK,
                         LIMIT_EARMARK_USED,
                         EARMARK_BEGIN_DATE,
                         EARMARK_END_DATE,
                         TRAN_SYSTEM,
                         ORG_NUM,
                         USER_NUM,
                         CREATE_TIME,
                         UPDATE_TIME,
                         DATA_TRANSFER_FLAG)
   SELECT CRD_MAIN_NUM,
          CRD_MAIN_PRD,
          CRD_PRODUCT_TYPE,
          CRD_GRANT_ORG_NUM,
          CUSTOMER_NUM,
          CURRENCY_CD,
          EXCHANGE_RATE,
          CREDIT_STATUS,
          LIMIT_CREDIT,
          LIMIT_USED,
          LIMIT_AVI,
          EXP_CREDIT,
          EXP_USED,
          EXP_AVI,
          LIMIT_PRE,
          EXP_PRE,
          LIMIT_FROZEN,
          EXP_FROZEN,
          BEGIN_DATE,
          END_DATE,
          FROZEN_DATE,
          OVER_DATE,
          LIMIT_EARMARK,
          LIMIT_EARMARK_USED,
          EARMARK_BEGIN_DATE,
          EARMARK_END_DATE,
          TRAN_SYSTEM,
          ORG_NUM,
          USER_NUM,
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP,
          '1'
   FROM TB_CRD_MAIN_MIDDLE;
    GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
    --记录插入日志信息
    SET SQL_CODE = SQLCODE;
    SET REMARK = '主额度信息数转';
    SET STATUS = '1';
    SET PROC_NAME = 'PRC_CRD_MAIN_INFO.1';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    SET RES_NUM = 1;
END;

comment on procedure PRC_CRD_MAIN_INFO(VARCHAR(8), INTEGER) is '额度主表信息-数转';

CREATE PROCEDURE CLM.PRC_CRD_CREDIT_LIMIT (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_CRD_CREDIT_LIMIT
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE V_CRD_GRANT_ORG_NUM   VARCHAR(10); --授信机构
  DECLARE V_CUSTOMER_NUM        VARCHAR(10); --ECIF客户号
  DECLARE V_CRD_MAIN_PRD        VARCHAR(10); --二级额度产品
  DECLARE V_CRD_MAIN_NUM        VARCHAR(40); --二级额度编号
  DECLARE V_CRD_DETAIL_PRD      VARCHAR(10); --三级额度产品
  DECLARE V_CRD_DETAIL_NUM      VARCHAR(10); --三级额度编号
  DECLARE V_LENGTH_VALUE        INTEGER; --字符长度
  DECLARE V_CRD_FLOW_VALUE      INTEGER; --额度流水数量
  DECLARE V_ORG_NUM_PRO         VARCHAR(10); ---省联社机构号1000
  DECLARE V_ORG_NUM_DIV         VARCHAR(10); ---分行机构号
  DECLARE V_ADJUST_COUNT        INTEGER; ---需要冲账的数量
  DECLARE V_TRAN_DATE           VARCHAR(10); --交易日期
  DECLARE V_TRAN_SEQ_SN         VARCHAR(20); --原流水号
  DECLARE N_TRAN_SEQ_SN         VARCHAR(20); --新流水号
  DECLARE V_COM_QUOTA           VARCHAR(2);--综合额度产品编号
  DECLARE V_TRAN_SYSTEM         VARCHAR(10);---定义来源系统号
  DECLARE V_USER_NUM         VARCHAR(10);---统一授信系统的操作员
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_CRD_CREDIT_LIMIT'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;

  SET V_ORG_NUM_PRO = '1000';
  SET V_COM_QUOTA = '01';
  SET V_TRAN_SYSTEM = '0100';--根据需要改变
  --SET V_CLM_SYSTEM = '0087';--统一授信系统的系统编号
  SET V_USER_NUM = '0000001';--统一授信系统的操作员

  /*
  对于授信类的交易，如果对账的过程中发现额度系统中存在该笔交易、但是资金系统
  不存在该笔交易、则寻找最近的一笔流水，将额度系统的授信额度按最后一次流水进
  行授信。
  主要包括维护综合额度授信(01)、维护额度切分(02)、额度圈存维护(04)、
  额度冻结(05)、客户准入信息维护(06)
  */
  ------------------------------------------------------------------------------
  ----维护综合额度----BEGIN-----------------------------------------------------
  /*
  根据传进的日期，查询额度授信事件主表中需要做冲账的数据数量，如果数量大于1，则
  根据该日期在授信明细表TB_FUND_GRANT_DETAIL中查询相应的授信机构、ECIF客户号、二
  级产品，以查询的 数据为条件在额度主表中查询出额度编号,根据额度编号查询流水表
  TB_CRD_GRANTING_SERIAL 中日期不等于IN_TRAN_DATE、流水不等于N_TRAN_SEQ_SN的信息按时间排序、如果存在就取
  第一条
  */
  SELECT
   COUNT(*)
  INTO
   V_ADJUST_COUNT
  FROM
   TB_FUND_GRANT_MAIN T1
  WHERE
   T1.ADJUST_FLAG = '1' AND
   T1.ADJUST_DIRECTION = '0' AND
   T1.TRAN_DATE = EXEC_DATE AND T1.TRAN_TYPE_CD = '01';

  IF V_ADJUST_COUNT > 0
  THEN
    FOR CREDIT_LIMIT_MAIN AS (SELECT T2.TRAN_SEQ_SN, T2.TRAN_DATE FROM TB_FUND_GRANT_MAIN T2 WHERE T2.ADJUST_FLAG = '1' AND T2.ADJUST_DIRECTION = '0' AND T2.TRAN_DATE = EXEC_DATE AND T2.TRAN_TYPE_CD = '01')
    DO
      SET V_TRAN_SEQ_SN = CREDIT_LIMIT_MAIN.TRAN_SEQ_SN;
      SET V_TRAN_DATE = CREDIT_LIMIT_MAIN.TRAN_DATE;
      SET N_TRAN_SEQ_SN = CLM.FNC_GET_TRAN_SEQ();
      FOR CREDIT_LIMIT_DATAS AS (
                                  SELECT
                                  T2.CRD_GRANT_ORG_NUM,
                                  T2.CUSTOMER_NUM,
                                  T2.CRD_MAIN_PRD
                                  FROM
                                  TB_FUND_GRANT_DETAIL T2
                                  WHERE T2.TRAN_SEQ_SN = V_TRAN_SEQ_SN AND T2.TRAN_DATE = V_TRAN_DATE)
      DO
      SET V_CRD_GRANT_ORG_NUM = CREDIT_LIMIT_DATAS.CRD_GRANT_ORG_NUM;
      SET V_CUSTOMER_NUM      = CREDIT_LIMIT_DATAS.CUSTOMER_NUM;
      SET V_CRD_MAIN_PRD      = CREDIT_LIMIT_DATAS.CRD_MAIN_PRD;
      SELECT T1.CRD_MAIN_NUM INTO V_CRD_MAIN_NUM FROM
             TB_CRD_MAIN T1 WHERE
             T1.CRD_GRANT_ORG_NUM = V_CRD_GRANT_ORG_NUM
             AND T1.CUSTOMER_NUM = V_CUSTOMER_NUM AND
             T1.CRD_MAIN_PRD = V_CRD_MAIN_PRD;
      /*
      更新额度主表信息
      */
      SET V_CRD_FLOW_VALUE  = (SELECT COUNT(*) FROM TB_CRD_GRANTING_SERIAL T1 WHERE T1.TRAN_TYPE_CD = '01' AND T1.CRD_DETAIL_NUM = V_CRD_MAIN_NUM);
      /*
      如果V_CRD_FLOW_VALUE>1则表示该客户在该机构下该产品有过授信交易
      */
      IF V_CRD_FLOW_VALUE > 1
      THEN
          INSERT INTO
            TB_CRD_GRANTING_SERIAL(
            GRANTING_SERIAL_ID --授信流水号
            ,TRAN_SEQ_SN --交易流水号
            ,TRAN_DATE --交易日期
            ,BUSI_DEAL_NUM --业务编号
            ,TRAN_TYPE_CD --交易类型
            ,CRD_GRANT_ORG_NUM --授信机构
            ,CUSTOMER_NUM --ECIF客户号
            ,CRD_DETAIL_PRD --额度产品
            ,CRD_DETAIL_NUM --额度编号
            ,CRD_CURRENCY_CD --币种
            ,CRD_DETAIL_AMT --授信额度
            ,CRD_EARK_AMT --圈存额度
            ,CRD_BEGIN_DATE --开始日期
            ,CRD_END_DATE --截至日期
            ,CRD_STATUS --额度状态
            ,CRD_ADMIT_FLAG --准入标准
            ,TRAN_SYSTEM --交易系统
            ,USER_NUM --交易柜员
            ,CREATE_TIME --创建时间
            ,UPDATE_TIME --更新时间
                        )
            SELECT
            FNC_GET_CLM_UUID(),
            N_TRAN_SEQ_SN,
            T1.TRAN_DATE,
            T1.BUSI_DEAL_NUM,
            T1.TRAN_TYPE_CD,
            T1.CRD_GRANT_ORG_NUM,
            T1.CUSTOMER_NUM,
            T1.CRD_DETAIL_PRD,
            T1.CRD_DETAIL_NUM,
            T1.CRD_CURRENCY_CD,
            T1.CRD_DETAIL_AMT,
            T1.CRD_EARK_AMT,
            T1.CRD_BEGIN_DATE,
            T1.CRD_END_DATE,
            T1.CRD_STATUS,
            T1.CRD_ADMIT_FLAG,
            T1.TRAN_SYSTEM,--
            V_USER_NUM,
            T1.CREATE_TIME,
            CURRENT_TIMESTAMP
        FROM
            (SELECT
              T2.GRANTING_SERIAL_ID --授信流水号
              ,T2.TRAN_SEQ_SN --交易流水号
              ,T2.TRAN_DATE --交易日期
              ,T2.BUSI_DEAL_NUM --业务编号
              ,T2.TRAN_TYPE_CD --交易类型
              ,T2.CRD_GRANT_ORG_NUM --授信机构
              ,T2.CUSTOMER_NUM --ECIF客户号
              ,T2.CRD_DETAIL_PRD --额度产品
              ,T2.CRD_DETAIL_NUM --额度编号
              ,T2.CRD_CURRENCY_CD --币种
              ,T2.CRD_DETAIL_AMT --授信额度
              ,T2.CRD_EARK_AMT --圈存额度
              ,T2.CRD_BEGIN_DATE --开始日期
              ,T2.CRD_END_DATE --截至日期
              ,T2.CRD_STATUS --额度状态
              ,T2.CRD_ADMIT_FLAG --准入标准
              ,T2.TRAN_SYSTEM --交易系统
              ,T2.USER_NUM --交易柜员
              ,T2.CREATE_TIME --创建时间
              ,T2.UPDATE_TIME --更新时间
          FROM
              TB_CRD_GRANTING_SERIAL T2
          WHERE
              T2.TRAN_TYPE_CD = '01'
              AND T2.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM
          ORDER BY
            BIGINT((SUBSTR(T2.TRAN_SEQ_SN,3,LENGTH(T2.TRAN_SEQ_SN) - 2))) DESC
          FETCH FIRST 2 ROWS ONLY) T1
        ORDER BY
          BIGINT((SUBSTR(T1.TRAN_SEQ_SN,3,LENGTH(T1.TRAN_SEQ_SN) - 2))) ASC
        FETCH FIRST 1 ROWS ONLY;
        --COMMIT;
        UPDATE
         TB_CRD_MAIN T1
        SET
         (
           T1.LIMIT_CREDIT,
           T1.CURRENCY_CD,
           T1.BEGIN_DATE,
           T1.END_DATE,
           T1.TRAN_SYSTEM,
           T1.USER_NUM,
           T1.UPDATE_TIME) = (
                               SELECT
                                T2.CRD_DETAIL_AMT,
                                T2.CRD_CURRENCY_CD,
                                T2.CRD_BEGIN_DATE,
                                T2.CRD_END_DATE,
                                V_TRAN_SYSTEM,
                                V_USER_NUM,
                                CURRENT_TIMESTAMP
                               FROM
                                TB_CRD_GRANTING_SERIAL T2
                               WHERE
                                T2.TRAN_SEQ_SN = N_TRAN_SEQ_SN)
        WHERE
         T1.CRD_MAIN_NUM = V_CRD_MAIN_NUM;
      ELSE
        /*
          如果流水表中的数据只有一条则将在流水表中插入一条流水记录，用以修改冲账信息，将授信额度修改为0.0
        */
            INSERT INTO
              TB_CRD_GRANTING_SERIAL
              (
                GRANTING_SERIAL_ID --授信流水号
                ,TRAN_SEQ_SN --交易流水号
                ,TRAN_DATE --交易日期
                ,BUSI_DEAL_NUM --业务编号
                ,TRAN_TYPE_CD --交易类型
                ,CRD_GRANT_ORG_NUM --授信机构
                ,CUSTOMER_NUM --ECIF客户号
                ,CRD_DETAIL_PRD --额度产品
                ,CRD_DETAIL_NUM --额度编号
                ,CRD_CURRENCY_CD --币种
                ,CRD_DETAIL_AMT --授信额度
                ,CRD_EARK_AMT --圈存额度
                ,CRD_BEGIN_DATE --开始日期
                ,CRD_END_DATE --截至日期
                ,CRD_STATUS --额度状态
                ,CRD_ADMIT_FLAG --准入标准
                ,TRAN_SYSTEM --交易系统
                ,USER_NUM --交易柜员
                ,CREATE_TIME --创建时间
                ,UPDATE_TIME --更新时间
                      )
          SELECT
           FNC_GET_CLM_UUID(),
           N_TRAN_SEQ_SN,
           T1.TRAN_DATE,
           T1.BUSI_DEAL_NUM,
           T1.TRAN_TYPE_CD,
           T1.CRD_GRANT_ORG_NUM,
           T1.CUSTOMER_NUM,
           T1.CRD_DETAIL_PRD,
           T1.CRD_DETAIL_NUM,
           T1.CRD_CURRENCY_CD,
           0.0,
           T1.CRD_EARK_AMT,
           T1.CRD_BEGIN_DATE, --开始日期
           T1.CRD_END_DATE, --截至日期
           T1.CRD_STATUS, --额度状态
           T1.CRD_ADMIT_FLAG,
           T1.TRAN_SYSTEM,
           V_USER_NUM,
           T1.CREATE_TIME,
           CURRENT_TIMESTAMP
          FROM
            TB_CRD_GRANTING_SERIAL T1
          WHERE
            T1.CRD_DETAIL_NUM = V_CRD_MAIN_NUM
            AND T1.TRAN_TYPE_CD = '01';
        /*
         将额度主表中的授信额度修改为0.0
        */
        UPDATE
         TB_CRD_MAIN T1
        SET
         (
           T1.LIMIT_CREDIT,
           T1.TRAN_SYSTEM,
           T1.USER_NUM,
           T1.UPDATE_TIME) = (SELECT
                                T5.CRD_DETAIL_AMT,
                                T5.TRAN_SYSTEM,
                                V_USER_NUM,
                                CURRENT_TIMESTAMP
                               FROM
                                TB_CRD_GRANTING_SERIAL T5
                               WHERE
                                T5.TRAN_SEQ_SN = N_TRAN_SEQ_SN)
        WHERE
         T1.CRD_MAIN_NUM = V_CRD_MAIN_NUM;
      END IF;
    END FOR;
      SET STATUS = '1';
      SET SQL_CODE = SQLCODE;
      SET REMARK = '需要处理数量为：'||V_ADJUST_COUNT||',冲账完成';
      CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,NULL,SQL_CODE,REMARK);
      SET RES_NUM = 1;
    END FOR;
    ELSE
      SET STATUS = '1';
      SET SQL_CODE = SQLCODE;
      SET REMARK = '需要处理数量为0';
      CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,NULL,SQL_CODE,REMARK);
      SET RES_NUM = 1;
  END IF;
---维护综合额度-----------------------------END---------------------------
END;

comment on procedure PRC_CRD_CREDIT_LIMIT(VARCHAR(10), INTEGER) is '综合授信额度-冲账';

CREATE PROCEDURE CLM.PRC_FUND_CRD_DEAL (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_FUND_CRD_DEAL
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
P1:BEGIN NOT ATOMIC
  DECLARE EFFECT_COUNT   INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE       INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE    VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS         VARCHAR(20); --执行情况
  DECLARE PROC_NAME      VARCHAR(50) DEFAULT 'PRC_FUND_CRD_DEAL'; --存储过程
  DECLARE REMARK         VARCHAR(200);--报错信息
  DECLARE SQLSTATE CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE INTEGER;
  DECLARE V_ERR_MSG VARCHAR(100);--错误信息
  DECLARE TRUNCATE_GRANT_MAIN VARCHAR(200);--清空表数据
  DECLARE TRUNCATE_GRANT_DETAIL VARCHAR(200);--清空表数据
  DECLARE EXIT HANDLER FOR  SQLWARNING,SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK = SQLSTATE;
    SET STATUS = '0';
    SET RES_NUM = 0;
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    --ROLLBACK;
    RETURN;
  END ;
  SET TRUNCATE_GRANT_MAIN= 'load from /dev/null of del replace into TB_FUND_GRANT_MAIN_MIDDLE';
  CALL SYSPROC.ADMIN_CMD(TRUNCATE_GRANT_MAIN);
  SET TRUNCATE_GRANT_DETAIL= 'load from /dev/null of del replace into TB_FUND_GRANT_DETAIL_MIDDLE';
  CALL SYSPROC.ADMIN_CMD(TRUNCATE_GRANT_DETAIL);

 INSERT INTO
   TB_FUND_GRANT_MAIN_MIDDLE(
     TRAN_SEQ_SN,
     TRAN_DATE,
     BUSI_DEAL_NUM,
     TRAN_TYPE_CD, CRD_APPLY_AMT, CRD_CURRENCY_CD, TRAN_SYSTEM, USER_NUM )
    SELECT
     TRAN_SEQ_SN,
     TRAN_DATE,
     BUSI_DEAL_NUM,
     TRAN_TYPE_CD, CRD_SUM_AMT, CRD_CURRENCY_CD, TRAN_SYSTEM, USER_NUM
    FROM
     TT_FUND_CRD_SOURCE;

  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME   = 'PROC_FUND_CRD_DEAL.1';
  SET STATUS      = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '向授信主表中间表插入信息';
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
  INSERT INTO
   TB_FUND_GRANT_DETAIL_MIDDLE(
     TRAN_SEQ_SN,
     TRAN_DATE,
     CRD_GRANT_ORG_NUM,
     CUSTOMER_NUM,
     CRD_MAIN_PRD,
     CRD_CURRENCY_CD,
     CRD_SUM_AMT,
     CRD_BEGIN_DATE,
     CRD_END_DATE,
     BUSI_SEGM_AMT,
     BUSI_SEGM_CNT,
     CRD_DETAIL_PRD,
     CRD_DETAIL_AMT)
    SELECT
     T1.TRAN_SEQ_SN,
     T1.TRAN_DATE,
     T2.CRD_GRANT_ORG_NUM,
     T2.CUSTOMER_NUM,
     T2.CRD_MAIN_PRD,
     T2.CRD_CURRENCY_CD,
     T2.CRD_SUM_AMT,
     T2.CRD_BEGIN_DATE,
     T2.CRD_END_DATE,
     T2.BUSI_SEGM_AMT,
     T2.BUSI_SEGM_CNT,
     T1.CRD_DETAIL_PRD,
     T1.CRD_DETAIL_AMT
    FROM
     TT_FUND_CRD_SEG_SOURCE T1
     LEFT JOIN TT_FUND_CRD_SOURCE T2 ON T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN;

  --记录插入日志信息
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME   = 'PROC_FUND_CRD_DEAL.2';
  SET STATUS      = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '向授信详情中间表插入信息';
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
  SET RES_NUM =1 ;
END P1;

CREATE PROCEDURE CLM.PRC_FUND_CRD_EARK (
    IN EXEC_DATE	VARCHAR(8),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_FUND_CRD_EARK
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_FUND_CRD_EARK'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  /*
  TRUNCATE TABLE TB_FUND_EARMARK_MAIN_MIDDLE IMMEDIATE;--清空表数据
  TRUNCATE TABLE TB_FUND_EARMARK_ALLOT_MIDDLE IMMEDIATE;--清空表数据
  */
  DECLARE TRUNCATE_EARMARK_MAIN VARCHAR(200);--清空表数据
  DECLARE TRUNCATE_EARMARK_ALLOT VARCHAR(200);--清空表数据
    /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;
  SET TRUNCATE_EARMARK_MAIN= 'load from /dev/null of del replace into TB_FUND_EARMARK_MAIN_MIDDLE';
  CALL SYSPROC.ADMIN_CMD(TRUNCATE_EARMARK_MAIN);
  SET TRUNCATE_EARMARK_ALLOT= 'load from /dev/null of del replace into TB_FUND_EARMARK_ALLOT_MIDDLE';
  CALL SYSPROC.ADMIN_CMD(TRUNCATE_EARMARK_ALLOT);

  INSERT INTO  TB_FUND_EARMARK_MAIN_MIDDLE (TRAN_SEQ_SN,
                                         TRAN_DATE,
                                         BUSI_DEAL_NUM,
                                         TRAN_TYPE_CD,
                                         CRD_EARK_AMT,
                                         CRD_CURRENCY_CD)
   SELECT T1.TRAN_SEQ_SN,
          T1.TRAN_DATE,
          T2.BUSI_DEAL_NUM,
          T2.TRAN_TYPE_CD,
          T2.CRD_EARK_AMT,
          T2.CRD_CURRENCY_CD
   FROM TT_FUND_LOAD_ALLOT_SOURCE T1
        LEFT JOIN TT_FUND_LOAD_SOURCE T2 ON T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN;
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME = 'PROC_FUND_CRD_DEAL.1';
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '向圈存主表中间表插入信息';
  --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
  INSERT INTO
 TB_FUND_EARMARK_ALLOT_MIDDLE(
   TRAN_SEQ_SN,
   TRAN_DATE,
   CUSTOMER_NUM,
   CRD_DETAIL_PRD,
   CRD_CURRENCY_CD,
   CRD_ALLOT_ORG_NUM,
   CRD_ALLOC_AMT)
  SELECT
   T1.TRAN_SEQ_SN,
   T1.TRAN_DATE,
   T2.CUSTOMER_NUM,
   T2.CRD_DETAIL_PRD,
   T2.CRD_CURRENCY_CD,
   T1.CRD_ALLOT_ORG_NUM,
   T1.CRD_ALLOT_AMT
  FROM
   TT_FUND_LOAD_ALLOT_SOURCE T1
   LEFT JOIN TT_FUND_LOAD_SOURCE T2 ON T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN;
           --记录插入日志信息
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME = 'PROC_FUND_CRD_DEAL.2';
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '向圈存明细表中间表插入信息';
  --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
  SET RES_NUM = 1;
END;

comment on procedure PRC_FUND_CRD_EARK(VARCHAR(8), INTEGER) is '处理资金系统额度圈存';

CREATE PROCEDURE CLM.PRC_FUND_CRD_TRANSFER (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_FUND_CRD_TRANSFER
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_FUND_CRD_TRANSFER'; --存储过程
  DECLARE REMARK              VARCHAR(200);--备注
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  /*
    清空中间表数据
  */
  DECLARE TRUNCATE_TRANSFER_MAIN VARCHAR(200);--清空表数据
  DECLARE TRUNCATE_TRANSFER_IN VARCHAR(200);--清空表数据
  DECLARE TRUNCATE_TRANSFER_OUT VARCHAR(200);--清空表数据

    /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
      SET SQL_CODE = SQLCODE;
      SET REMARK   = SQLSTATE;
      SET STATUS   = '0';
      CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
      SET RES_NUM  = 0;
      --ROLLBACK;
      RETURN;
  END;
  SET TRUNCATE_TRANSFER_MAIN= 'load from /dev/null of del replace into TB_FUND_TRANSFER_MAIN_MIDDLE';
  CALL SYSPROC.ADMIN_CMD(TRUNCATE_TRANSFER_MAIN);
  SET TRUNCATE_TRANSFER_IN= 'load from /dev/null of del replace into TB_FUND_TRANSFER_IN_MIDDLE';
  CALL SYSPROC.ADMIN_CMD(TRUNCATE_TRANSFER_IN);
  SET TRUNCATE_TRANSFER_OUT= 'load from /dev/null of del replace into TB_FUND_TRANSFER_OUT_MIDDLE';
  CALL SYSPROC.ADMIN_CMD(TRUNCATE_TRANSFER_OUT);

   --插入额度转让信息
  INSERT INTO TB_FUND_TRANSFER_MAIN_MIDDLE (
   TRAN_SEQ_SN
  ,TRAN_DATE
  ,BUSI_DEAL_NUM
  ,TRAN_TYPE_CD
  ,CRD_APPLY_AMT
) SELECT
   TRAN_SEQ_SN
  ,TRAN_DATE
  ,BUSI_DEAL_NUM
  ,TRAN_TYPE_CD
  ,BUSI_SUM_AMT
FROM TT_FUND_CRD_TRANSFER_SOURCE T1;
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '插入额度转让主表信息';
  SET PROC_NAME='PROC_FUND_CRD_DEAL.1';
  --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
   --插入额度转让--转出信息
  INSERT INTO
 TB_FUND_TRANSFER_OUT_MIDDLE(
   TRAN_SEQ_SN,
   TRAN_DATE,
   CRD_OUT_ORG_NUM,
   BUSI_SOURCE_REQ_NUM,
   CURRENCY_CD,
   CRD_APPLY_OUT_AMT)
  SELECT
   T1.TRAN_SEQ_SN,
   T1.TRAN_DATE,
   T1.CRD_OUT_ORG_NUM,
   T1.BUSI_SOURCE_REQ_NUM,
   T1.CURRENCY_CD,
   T1.CRD_APPLY_OUT_AMT
  FROM
   TT_FUND_CRD_TRNS_OUT_SOURCE T1;
--记录插入日志信息
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '插入额度转出信息';
  SET PROC_NAME='PROC_FUND_CRD_DEAL.2';
  --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
   --插入额度转让--转入信息
   INSERT INTO TB_FUND_TRANSFER_IN_MIDDLE (
   TRAN_SEQ_SN
  ,TRAN_DATE
  ,CRD_IN_ORG_NUM
  ,BUSI_PRD_NUM
  ,BUSI_NEWL_REQ_NUM
  ,CURRENCY_CD
  ,CRD_APPLY_IN_AMT
)SELECT
 T1.TRAN_SEQ_SN,
 T1.TRAN_DATE,
 T1.CRD_IN_ORG_NUM,
 T1.BUSI_PRD_NUM,
 T1.BUSI_NEWL_REQ_NUM,
 T1.CURRENCY_CD,
 T1.CRD_APPLY_IN_AMT
FROM
 TT_FUND_CRD_TRANS_IN_SOURCE T1;
--记录插入日志信息
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '插入额度转入信息';
  SET PROC_NAME='PROC_FUND_CRD_DEAL.3';
  --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
  SET RES_NUM = 1;
END;

comment on procedure PRC_FUND_CRD_TRANSFER(VARCHAR(10), INTEGER) is '资金转让交易';

CREATE PROCEDURE CLM.PRC_FUND_CUSTOMER_UPDATE (
    IN EXEC_DATE	VARCHAR(8),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_FUND_CUSTOMER_UPDATE
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_FUND_CUSTOMER_UPDATE'; --存储过程
  DECLARE REMARK              VARCHAR(200);--执行说明
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;

  DECLARE TRUNCATE_ADMIT_MAIN   VARCHAR(200); --清空表数据
  DECLARE TRUNCATE_ADMIT_DETAIL     VARCHAR(200); --清空表数据
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;
  /*
    清空中间表数据
  */
  SET TRUNCATE_ADMIT_MAIN = 'load from /dev/null of del replace into TB_FUND_ADMIT_MAIN_MIDDLE';
  CALL SYSPROC.ADMIN_CMD(TRUNCATE_ADMIT_MAIN);
  SET TRUNCATE_ADMIT_DETAIL = 'load from /dev/null of del replace into TB_FUND_ADMIT_DETAIL_MIDDLE';
  CALL SYSPROC.ADMIN_CMD(TRUNCATE_ADMIT_DETAIL);
  /*
     将TT表中客户准入状态主表信息向中间表插入
  */
  INSERT INTO TB_FUND_ADMIT_MAIN_MIDDLE(TRAN_SEQ_SN, TRAN_DATE, BUSI_DEAL_NUM, TRAN_TYPE_CD) SELECT
   TRAN_SEQ_SN,
   TRAN_DATE,
   BUSI_DEAL_NUM,
   TRAN_TYPE_CD
  FROM
   TT_FUND_CRD_ADLIMT_SOURCE;
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME='PRC_FUND_CUSTOMER_UPDATE.1';
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '向客户准入状态主表中间表插入信息';
     --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
  /*
      向客户准入状态明细中间表插入信息
  */
  INSERT INTO
 TB_FUND_ADMIT_DETAIL_MIDDLE(
   TRAN_SEQ_SN,
   TRAN_DATE,
   CUSTOMER_NUM,
   CRD_STATUS,
   FROZEN_REQ_DATE,
   FROZEN_BEGIN_DATE,
   FROZEN_OVER_DATE,
   CRD_DETAIL_PRD,
   CRD_ADMIT_FLAG)
  SELECT
   T1.TRAN_SEQ_SN,
   T1.TRAN_DATE,
   T1.CUSTOMER_NUM,
   T1.CRD_STATUS,
   T1.FROZEN_REQ_DATE,
   T1.FROZEN_BEGIN_DATE,
   T1.FROZEN_OVER_DATE,
   T2.CRD_DETAIL_PRD,
   T2.CRD_ADMIT_FLAG
  FROM
   TT_FUND_CRD_ADLIMT_SOURCE T1
   LEFT JOIN TT_FUND_STATUS_UPDATE_SOURCE T2 ON T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN;
           --记录插入日志信息
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME='PRC_FUND_CUSTOMER_UPDATE.2';
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '向客户准入状态明细中间表插入信息';
     --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
END;

comment on procedure PRC_FUND_CUSTOMER_UPDATE(VARCHAR(8), INTEGER) is '资金系统客户信息维护';

CREATE PROCEDURE CLM.PRC_CRD_BALANCE_CHECK (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_CRD_BALANCE_CHECK
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  /*
  1.1.1  批量系统自平衡检查
       自平衡检查是本系统自身业务逻辑规则处理一致性的重要保证；
       日间处理逻辑根据业务规则可能会经常发生变动，为保证日间处理逻辑正确，一旦发生错误能够及时发现，及时调整，所以需进行系统自平衡检查。
        1、业务凭证（额度编号，状态、金额）与三级额度（额度编号，已用）
            汇总业务凭证状态为有效的全部金额
                == 汇总三级额度省联社全部已用
                == 汇总三级额度成员行全部已用
        2、三级额度（额度编号，已用）与二级额度（额度编号，已用）
            汇总二级额度省联社全部已用
                == 汇总二级额度成员行全部已用
                == 汇总三级额度省联社全部已用
                == 汇总三级额度成员行全部已用
    还需要进一步确认规则进行修改
  */
  DECLARE EFFECT_COUNT   INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE       INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE    VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS         VARCHAR(20); --执行情况
  DECLARE PROC_NAME      VARCHAR(50) DEFAULT 'PRC_CRD_BALANCE_CHECK'; --存储过程
  DECLARE REMARK         VARCHAR(200);
  DECLARE SQLSTATE CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE INTEGER;
  DECLARE V_CERT_SUM_CRD DECIMAL(24,2);----凭证有效的金额汇总
  DECLARE V_P_SUM_DETAIL_CRD DECIMAL(24,2);---汇总三级额度省联社全部已用
  DECLARE V_C_SUM_DETAIL_CRD DECIMAL(24,2);---汇总三级额度分行全部已用
  DECLARE V_P_SUM_MAIN_CRD DECIMAL(24,2);---汇总二级额度省联社全部已用
  DECLARE V_C_SUM_MAIN_CRD DECIMAL(24,2);---汇总二级额度成员行全部已用
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR  SQLWARNING,SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK = SQLSTATE;
    SET STATUS = '0';
    CALL CLM.PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    --ROLLBACK;
    SET RES_NUM = 0;
    RETURN;
  END ;
  SET V_CERT_SUM_CRD = (SELECT  SUM(T1.CERT_APPLY_AMT) FROM TB_CRD_BUSI_CERT_INFO T1 WHERE T1.CERT_STATUS IN ('01','03') AND T1.TRAN_SYSTEM ='0010');----凭证有效的金额汇总
  SET V_P_SUM_DETAIL_CRD = (SELECT SUM(T2.LIMIT_USED)+SUM(T2.LIMIT_PRE) FROM TB_CRD_DETAIL T2 WHERE T2.CRD_GRANT_ORG_NUM = '01000' AND T2.TRAN_SYSTEM = '0100' AND T2.TRAN_SYSTEM = '0010');---汇总三级额度省联社全部已用
  SET V_C_SUM_DETAIL_CRD = (SELECT SUM(T2.LIMIT_USED)+SUM(T2.LIMIT_PRE) FROM TB_CRD_DETAIL T2 WHERE T2.CRD_GRANT_ORG_NUM NOT IN('01000','01122')AND T2.TRAN_SYSTEM = '0100');---汇总三级额度分行全部已用
  SET V_P_SUM_MAIN_CRD = (SELECT SUM(T3.LIMIT_USED)+SUM(T3.LIMIT_PRE) FROM TB_CRD_MAIN T3 WHERE T3.CRD_MAIN_PRD = '0301' AND T3.CRD_GRANT_ORG_NUM ='01000' AND T3.TRAN_SYSTEM = '0010');---汇总二级额度省联社全部已用
  SET V_C_SUM_MAIN_CRD = (SELECT SUM(T3.LIMIT_USED)+SUM(T3.LIMIT_PRE) FROM TB_CRD_MAIN T3 WHERE T3.CRD_MAIN_PRD = '0301' AND T3.CRD_GRANT_ORG_NUM NOT IN('01000','01122') AND T3.TRAN_SYSTEM ='0010');---汇总二级额度成员行全部已用
  IF V_CERT_SUM_CRD - V_P_SUM_DETAIL_CRD = 0.0 AND V_CERT_SUM_CRD - V_C_SUM_DETAIL_CRD = 0.0 AND V_P_SUM_MAIN_CRD - V_C_SUM_MAIN_CRD = 0.0 AND V_P_SUM_MAIN_CRD - V_CERT_SUM_CRD =0.0
  THEN
    INSERT INTO TB_CRD_BALANCE_CHECK (
       CHECK_ID
      ,CHECK_DATE
      ,CHECK_STATUS
      ,CREATE_TIME
      ,UPDATE_TIME
    ) VALUES (
       FNC_GET_CLM_UUID()
      ,EXEC_DATE
      ,'1'
      ,CURRENT_TIMESTAMP
      ,CURRENT_TIMESTAMP
    );
      SET STATUS = '1';
      SET SQL_CODE = SQLCODE;
      SET REMARK = '平衡检查完毕,结果正常';
      CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,NULL,SQL_CODE,REMARK);
      SET RES_NUM = 1 ;
  ELSE
    /*
      向平衡检查记录表插入数据
    */
    INSERT INTO TB_CRD_BALANCE_CHECK (
       CHECK_ID
      ,CHECK_DATE
      ,CHECK_STATUS
      ,CREATE_TIME
      ,UPDATE_TIME
    ) VALUES (
       FNC_GET_CLM_UUID()
      ,EXEC_DATE
      ,'1'
      ,CURRENT_TIMESTAMP
      ,CURRENT_TIMESTAMP
    );
      SET STATUS = '1';
      SET SQL_CODE = SQLCODE;
      SET REMARK = '平衡检查完毕,结果异常';
      CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,NULL,SQL_CODE,REMARK);
      SET RES_NUM =1 ;
  END IF;
END;

comment on procedure PRC_CRD_BALANCE_CHECK(VARCHAR(10), INTEGER) is '平衡检查';

CREATE PROCEDURE CLM.PRC_CRD_BILL_EXPIRE_DUE (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_CRD_BILL_EXPIRE_DUE
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE V_CRD_DETAIL_PRD VARCHAR(10);--三级额度产品号
  DECLARE V_CRD_MAIN_PRD VARCHAR(10);--二级额度产品号
  DECLARE V_D_CRD_DETAIL_NUM VARCHAR(40);--分行明细额度编号
  DECLARE V_M_CRD_DETAIL_NUM VARCHAR(40);--省联社明细额度编号
  DECLARE V_D_CRD_MAIN_NUM VARCHAR(40);--分行主额度编号
  DECLARE V_M_CRD_MAIN_NUM VARCHAR(40);--省联社主额度编号
  DECLARE V_B_CRD_GRANT_ORG_NUM VARCHAR(6);--授信机构
  DECLARE V_P_CRD_GRANT_ORG_NUM VARCHAR(6);--授信机构-省联社
  DECLARE V_CUSTOMER_NUM VARCHAR(10);--客户号
  DECLARE V_BUSI_PRD_NUM VARCHAR(40);--业务产品编号
  DECLARE V_CERT_STATUS VARCHAR(2);--凭证状态
  DECLARE V_CERT_DRAWER_CUST_NUM VARCHAR(10);--发行人客户号
  DECLARE V_CERT_DRAWER_BANK_NUM VARCHAR(10);--发行人代理/承兑行号
  DECLARE V_CERT_APPLY_AMT DECIMAL(24,2);--凭证用信金额
  DECLARE V_B_DUE_NUM INTEGER;--授信机构为分行主额度失效数量
  DECLARE V_FAILURE_STATE VARCHAR(2);--失效状态 01:生效、02：部分冻结、03：全部冻结、04、已完结
  DECLARE V_IS_C VARCHAR(2);--失效状态 01:生效、02：部分冻结、03：全部冻结、04、已完结
  DECLARE V_CRET_INFO_ID VARCHAR(32);--凭证ID
  DECLARE V_BANK_CUST_NUM VARCHAR(10);--银行客户号
  DECLARE V_B_D_CRD_MAIN_NUM VARCHAR(40);--承兑行授信法人机构的主额度编号
  DECLARE V_B_M_CRD_MAIN_NUM VARCHAR(40);--承兑行授信省联社的主额度编号
  DECLARE V_B_M_CRD_DETAIL_NUM VARCHAR(40);--承兑行授信省联社的明细额度编号
  DECLARE V_B_D_CRD_DETAIL_NUM VARCHAR(40);--承兑行授信法人机构的明细额度编号
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_CRD_BILL_EXPIRE_DUE'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
    /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;

  SET V_P_CRD_GRANT_ORG_NUM = '01000';--省联社机构号
  /*
     凭证到期
    1、首先查看凭证信息表中是否存在凭证状态为占用(占用)且票据到期的凭证。
    2、查询出符合条件1的凭证的三级额度编号、业务产品编号、凭证用信金额、凭证状态、发行人客户号、发行人代理/承兑行号、客户号、授信机构、额度明细产品。
    3、找到期凭证（条件：业务产品编号=200007-直贴入 并且 凭证状态=03 占用/10 结清）进行直贴企业的恢复用信，额度不循环，所以可用不恢复，恢复之后状态为10 结清
    4、找到期凭证（条件：凭证状态=03 占用），进行承兑行（找客户）进行恢复用信，恢复之后状态为10 结清
  */
  SELECT COUNT(*) INTO V_B_DUE_NUM FROM TB_CRD_BUSI_CERT_INFO T1 WHERE T1.CERT_END_DATE = EXEC_DATE AND T1.CERT_STATUS = '03';
  IF V_B_DUE_NUM >0
  THEN
    FOR CERT_INFO_DATAS AS (
    SELECT T1.CRET_INFO_ID, --凭证信息ID
      T1.CRD_DETAIL_NUM,--三级额度编号
      T1.BUSI_PRD_NUM,--业务产品编号
      T1.CERT_APPLY_AMT,--凭证用信金额
      T1.CERT_STATUS,--凭证状态
      T1.CERT_DRAWER_CUST_NUM,--发行人客户号
      T1.CERT_DRAWER_BANK_NUM,--发行人代理/承兑行号
      T1.CUSTOMER_NUM,--客户号
      T1.CRD_GRANT_ORG_NUM,--授信机构
      T1.CRD_DETAIL_PRD--额度明细产品
    FROM
      TB_CRD_BUSI_CERT_INFO T1 WHERE T1.CERT_END_DATE = EXEC_DATE AND T1.CERT_STATUS = '03'
      )
    DO
    SET V_CRET_INFO_ID = CERT_INFO_DATAS.CRET_INFO_ID;--ID主键用于更新票据状态
    SET V_D_CRD_DETAIL_NUM = CERT_INFO_DATAS.CRD_DETAIL_NUM;--分行明细额度编号
    SET V_BUSI_PRD_NUM = CERT_INFO_DATAS.BUSI_PRD_NUM;
    SET V_CERT_APPLY_AMT = CERT_INFO_DATAS.CERT_APPLY_AMT;
    SET V_CERT_STATUS = CERT_INFO_DATAS.CERT_STATUS;
    SET V_CERT_DRAWER_CUST_NUM = CERT_INFO_DATAS.CERT_DRAWER_CUST_NUM;
    SET V_CERT_DRAWER_BANK_NUM = CERT_INFO_DATAS.CERT_DRAWER_BANK_NUM;
    SET V_CUSTOMER_NUM = CERT_INFO_DATAS.CUSTOMER_NUM;
    SET V_B_CRD_GRANT_ORG_NUM = CERT_INFO_DATAS.CRD_GRANT_ORG_NUM;
    SET V_CRD_DETAIL_PRD = CERT_INFO_DATAS.CRD_DETAIL_PRD;
    SET V_CRD_MAIN_PRD = (SELECT SUPER_CRD_NUM FROM TB_PAR_CRD WHERE CRD_PRODUCT_NUM = V_CRD_DETAIL_PRD);--获取二级产品编号
    SET V_D_CRD_MAIN_NUM = (SELECT T1.CRD_MAIN_NUM FROM TB_CRD_DETAIL T1 WHERE T1.CRD_DETAIL_NUM = V_D_CRD_DETAIL_NUM);--获取分行主额度编号
    SET V_M_CRD_DETAIL_NUM = (SELECT T1.CRD_DETAIL_NUM  FROM TB_CRD_DETAIL T1 WHERE T1.CRD_DETAIL_PRD = V_CRD_DETAIL_PRD AND T1.CRD_GRANT_ORG_NUM = V_P_CRD_GRANT_ORG_NUM AND T1.CUSTOMER_NUM = V_CUSTOMER_NUM);--获取省联社明细额度编号
    SET V_M_CRD_MAIN_NUM = (SELECT T1.CRD_MAIN_NUM FROM TB_CRD_DETAIL T1 WHERE T1.CRD_DETAIL_NUM = V_M_CRD_DETAIL_NUM);--获取省联社主额度编号
    SET V_BANK_CUST_NUM = (SELECT  T3.CUSTOMER_NUM FROM TB_CSM_CORPORATION T3 WHERE T3.BANK_PAY_SYS_NUM = V_CERT_DRAWER_BANK_NUM);--根据行号找寻客户号
    --TB_PAR_CRD
    IF  V_BUSI_PRD_NUM = '200007'
    THEN
      UPDATE TB_CRD_DETAIL T1 SET T1.LIMIT_AVI = T1.LIMIT_AVI+V_CERT_APPLY_AMT WHERE T1.CRD_DETAIL_NUM = V_D_CRD_DETAIL_NUM AND IS_CYCLE ='1';--恢复明细额度表法人可用金额
      UPDATE TB_CRD_DETAIL T2 SET T2.LIMIT_USED =T2.LIMIT_AVI-V_CERT_APPLY_AMT WHERE CRD_DETAIL_NUM = V_D_CRD_DETAIL_NUM ;--恢复已用金额
      UPDATE TB_CRD_DETAIL T1 SET T1.LIMIT_AVI = T1.LIMIT_AVI+V_CERT_APPLY_AMT WHERE T1.CRD_DETAIL_NUM = V_M_CRD_DETAIL_NUM AND IS_CYCLE ='1';--恢复明细额度表省联社可用金额
      UPDATE TB_CRD_DETAIL T2 SET T2.LIMIT_USED =T2.LIMIT_AVI-V_CERT_APPLY_AMT WHERE CRD_DETAIL_NUM = V_M_CRD_DETAIL_NUM ;--恢复已用金额
      UPDATE TB_CRD_MAIN T2 SET T2.LIMIT_USED =T2.LIMIT_USED-V_CERT_APPLY_AMT  WHERE CRD_MAIN_NUM = V_D_CRD_MAIN_NUM;--恢复额度主表法人机构已用金额 可用金额未定
      UPDATE TB_CRD_MAIN T2 SET T2.LIMIT_USED =T2.LIMIT_USED-V_CERT_APPLY_AMT  WHERE CRD_MAIN_NUM = V_M_CRD_MAIN_NUM;--恢复额度主表省联社已用金额 可用金额未定
      UPDATE TB_CRD_BUSI_CERT_INFO SET CERT_STATUS = '10' WHERE CRET_INFO_ID = V_CRET_INFO_ID;--修改凭证状态为结清 10
    ELSE
      SELECT T2.CRD_DETAIL_NUM,T2.CRD_MAIN_NUM INTO V_B_M_CRD_DETAIL_NUM,V_B_M_CRD_MAIN_NUM  FROM TB_CRD_DETAIL T2 WHERE T2.CRD_DETAIL_PRD = V_CRD_DETAIL_PRD AND T2.CRD_GRANT_ORG_NUM = V_P_CRD_GRANT_ORG_NUM AND T2.CUSTOMER_NUM = V_BANK_CUST_NUM;--获取省联社授信的明细额度编号，主额度编号
      SELECT T2.CRD_DETAIL_NUM,T2.CRD_MAIN_NUM INTO V_B_D_CRD_DETAIL_NUM,V_B_D_CRD_MAIN_NUM FROM TB_CRD_DETAIL T2 WHERE T2.CRD_DETAIL_PRD = V_CRD_DETAIL_PRD AND T2.CRD_GRANT_ORG_NUM = V_B_CRD_GRANT_ORG_NUM AND T2.CUSTOMER_NUM = V_BANK_CUST_NUM;--获取法人机构的明细额度编号，主额度编号
      UPDATE TB_CRD_DETAIL T1 SET T1.LIMIT_AVI = T1.LIMIT_AVI+V_CERT_APPLY_AMT WHERE T1.CRD_DETAIL_NUM = V_B_D_CRD_DETAIL_NUM AND IS_CYCLE ='1';--恢复明细额度表法人可用金额
      UPDATE TB_CRD_DETAIL T2 SET T2.LIMIT_USED =T2.LIMIT_AVI-V_CERT_APPLY_AMT WHERE CRD_DETAIL_NUM = V_B_D_CRD_DETAIL_NUM ;--恢复已用金额
      UPDATE TB_CRD_DETAIL T1 SET T1.LIMIT_AVI = T1.LIMIT_AVI+V_CERT_APPLY_AMT WHERE T1.CRD_DETAIL_NUM = V_B_M_CRD_DETAIL_NUM AND IS_CYCLE ='1';--恢复明细额度表省联社可用金额
      UPDATE TB_CRD_DETAIL T2 SET T2.LIMIT_USED =T2.LIMIT_AVI-V_CERT_APPLY_AMT WHERE CRD_DETAIL_NUM = V_B_M_CRD_DETAIL_NUM ;--恢复已用金额
      UPDATE TB_CRD_MAIN T2 SET T2.LIMIT_USED =T2.LIMIT_USED-V_CERT_APPLY_AMT  WHERE CRD_MAIN_NUM = V_B_D_CRD_MAIN_NUM;--恢复额度主表法人机构已用金额 可用金额未定
      UPDATE TB_CRD_MAIN T2 SET T2.LIMIT_USED =T2.LIMIT_USED-V_CERT_APPLY_AMT  WHERE CRD_MAIN_NUM = V_B_M_CRD_MAIN_NUM;--恢复额度主表省联社已用金额 可用金额未定
      UPDATE TB_CRD_BUSI_CERT_INFO SET CERT_STATUS = '10' WHERE CRET_INFO_ID = V_CRET_INFO_ID;--修改凭证状态为结清 10
    END IF;
    END FOR;
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '凭证到期数量为：'||V_B_DUE_NUM||',处理完成';
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,NULL,SQL_CODE,REMARK);
  SET RES_NUM = 1;
  ELSE
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '凭证到期数量为0';
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,NULL,SQL_CODE,REMARK);
  SET RES_NUM = 1;
  END IF;

END;

comment on procedure PRC_CRD_BILL_EXPIRE_DUE(VARCHAR(10), INTEGER) is '票据到期处理';

CREATE PROCEDURE CLM.PRC_CRD_PRODUCE_SN (
    IN EXEC_DATE	VARCHAR(8),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_CRD_PRODUCE_SN
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT   INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE       INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE    VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS         VARCHAR(20); --执行情况
  DECLARE PROC_NAME      VARCHAR(50) DEFAULT 'PRC_CRD_PRODUCE_SN'; --存储过程
  DECLARE REMARK         VARCHAR(200);
  DECLARE SQLSTATE CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE INTEGER;
  DECLARE V_CRD_MAIN_NUM	VARCHAR(20);--主额度编号
  DECLARE V_CRD_MAIN_PRD	VARCHAR(10);--二级产品编号
  DECLARE V_CRD_GRANT_ORG_NUM	VARCHAR(5);--授信机构
  DECLARE V_CUSTOMER_NUM	VARCHAR(10);--ECIF客户号
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR  SQLWARNING,SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK = SQLSTATE;
    SET STATUS = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    --ROLLBACK;
    SET RES_NUM = 0;
    RETURN;
  END ;
  FOR PRODUCE_MAIN_SN_DATAS AS (SELECT T1.CRD_MAIN_PRD, T1.CRD_GRANT_ORG_NUM, T1.CUSTOMER_NUM FROM TT_CRD_MAIN_SERIAL T1)
  DO
    SET V_CRD_MAIN_NUM = FNC_GET_BIZ_NUM ('ED2');---产生二级额度编号
    SET V_CRD_MAIN_PRD = PRODUCE_MAIN_SN_DATAS.CRD_MAIN_PRD ;
    SET V_CRD_GRANT_ORG_NUM = PRODUCE_MAIN_SN_DATAS.CRD_GRANT_ORG_NUM ;
    SET V_CUSTOMER_NUM = PRODUCE_MAIN_SN_DATAS.CUSTOMER_NUM ;
    UPDATE TT_CRD_MAIN_SERIAL SET CRD_MAIN_NUM = V_CRD_MAIN_NUM
        WHERE CRD_MAIN_PRD = V_CRD_MAIN_PRD
      AND CRD_GRANT_ORG_NUM = V_CRD_GRANT_ORG_NUM
      AND CUSTOMER_NUM = V_CUSTOMER_NUM;
  END FOR;
  SET EFFECT_COUNT = (SELECT COUNT(*) FROM TT_CRD_MAIN_SERIAL);---产生主额度编号的数量
  --记录插入日志信息
  SET SQL_CODE = SQLCODE;
  SET REMARK = '产生主额度编号完毕！';
  SET STATUS = '1';
  SET PROC_NAME = 'PRC_CRD_PRODUCE_SN.1';
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
  SET RES_NUM = 1;
END;

comment on procedure PRC_CRD_PRODUCE_SN(VARCHAR(8), INTEGER) is '主额度编号产生-数转';

CREATE PROCEDURE CLM.PRC_FUND_CRD_OPT (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_FUND_CRD_OPT
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE EFFECT_COUNT   INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE       INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE    VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS         VARCHAR(20); --执行情况
  DECLARE PROC_NAME      VARCHAR(50) DEFAULT 'PRC_FUND_CRD_OPT'; --存储过程
  DECLARE REMARK         VARCHAR(200);--报错信息
  DECLARE SQLSTATE CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE INTEGER;
  DECLARE V_ERR_MSG VARCHAR(100);--错误信息
  DECLARE TRUNCATE_EVENT_MAIN VARCHAR(200);--清空表数据
  DECLARE TRUNCATE_EVENT_DETAIL VARCHAR(200);--清空表数据
  DECLARE EXIT HANDLER FOR  SQLWARNING,SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK = SQLSTATE;
    SET STATUS = '0';
    SET RES_NUM = 0;
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    --ROLLBACK;
    RETURN;
  END ;
  SET TRUNCATE_EVENT_MAIN= 'load from /dev/null of del replace into TB_FUND_EVENT_MAIN_MIDDLE';
  CALL SYSPROC.ADMIN_CMD(TRUNCATE_EVENT_MAIN);
  SET TRUNCATE_EVENT_DETAIL= 'load from /dev/null of del replace into TB_FUND_EVENT_DETAIL_MIDDLE';
  CALL SYSPROC.ADMIN_CMD(TRUNCATE_EVENT_DETAIL);
  INSERT INTO
 TB_FUND_EVENT_MAIN_MIDDLE(
     TRAN_SEQ_SN,
     TRAN_DATE,
     BUSI_DEAL_NUM,
     TRAN_TYPE_CD,
     CRD_APPLY_AMT,
     CRD_CURRENCY_CD)
  SELECT
     TRAN_SEQ_SN,
    TRAN_DATE,
    BUSI_DEAL_NUM,
    TRAN_TYPE_CD,
    CRD_APPLY_AMT,
    CRD_CURRENCY_CD
  FROM
     TT_FUND_CRD_OPT_SOURCE;
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME = 'PROC_FUND_CRD_OPT.1';
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '向用信主表中间层插入信息';
  --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);

  INSERT INTO
 TB_FUND_EVENT_DETAIL_MIDDLE(
   TRAN_SEQ_SN,
   TRAN_DATE,
   CRD_GRANT_ORG_NUM,
   CUSTOMER_NUM,
   CRD_DETAIL_PRD,
   BUSI_DEAL_NUM,
   BUSI_PRD_NUM,
   BUSI_DEAL_DESC,
   BUSI_DEAL_ORG_NUM,
   BUSI_DEAL_ORG_NAME,
   BUSI_OPPT_ORG_NUM,
   BUSI_OPPT_ORG_NAME,
   BUSI_SUM_AMT,
   BUSI_CERT_CNT,
   CERT_NUM,
   CERT_TYPE_CD,
   CERT_PPT_CD,
   CERT_INTEREST_PERIOD,
   CERT_INTEREST_RATE,
   CERT_CURRENCY_CD,
   CERT_SEQ_AMT,
   CERT_APPLY_AMT,
   CERT_APPLY_BALANCE,
   CERT_STATUS,
   CERT_BEGIN_DATE,
   CERT_END_DATE,
   CERT_FINISH_DATE,
   CERT_DRAWER_CUST_NUM,
   CERT_DRAWER_NAME,
   CERT_DRAWER_BANK_NUM,
   CERT_DRAWER_BANK_NAME,
   CERT_GUARANTY_TYPE,
   CERT_GUARANTY_PERSON,
   CERT_BUSI_REMARK)
  SELECT
   T3.TRAN_SEQ_SN,
   T3.TRAN_DATE,
   T3.CRD_GRANT_ORG_NUM,
   T3.CUSTOMER_NUM,
   T3.CRD_DETAIL_PRD,
   T1.BUSI_DEAL_NUM,
   T2.BUSI_PRD_NUM,
   T2.BUSI_DEAL_DESC,
   T2.BUSI_DEAL_ORG_NUM,
   T2.BUSI_DEAL_ORG_NAME,
   T2.BUSI_OPPT_ORG_NUM,
   T2.BUSI_OPPT_ORG_NAME,
   T2.BUSI_SUM_AMT,
   T2.BUSI_CERT_CNT,
   T3.CERT_NUM,
   T3.CERT_TYPE_CD,
   T3.CERT_PPT_CD,
   T3.CERT_INTEREST_PERIOD,
   T3.CERT_INTEREST_RATE,
   T3.CERT_CURRENCY_CD,
   T3.CERT_SEQ_AMT,
   T3.CERT_APPLY_AMT,
   T3.CERT_APPLY_BALANCE,
   T3.CERT_STATUS,
   T3.CERT_BEGIN_DATE,
   T3.CERT_END_DATE,
   T3.CERT_FINISH_DATE,
   T3.CERT_DRAWER_CUST_NUM,
   T3.CERT_DRAWER_NAME,
   T3.CERT_DRAWER_BANK_NUM,
   T3.CERT_DRAWER_BANK_NAME,
   T3.CERT_GUARANTY_TYPE,
   T3.CERT_GUARANTY_PERSON,
   T3.CERT_BUSI_REMARK
  FROM
   TT_FUND_CRD_OPT_SOURCE T1
   LEFT JOIN TT_FUND_CRD_OPT_TRAN_SOURCE T2
     ON T1.TRAN_SEQ_SN = T2.TRAN_SEQ_SN AND
        T1.TRAN_DATE = T2.TRAN_DATE
   LEFT JOIN TT_FUND_CRD_OPT_CERT_SOURCE T3
     ON T1.TRAN_SEQ_SN = T3.TRAN_SEQ_SN AND
        T1.TRAN_DATE = T3.TRAN_DATE AND
        T3.TRAN_SEQ_SN = T2.TRAN_SEQ_SN AND
        T3.TRAN_DATE = T2.TRAN_DATE;
           --记录插入日志信息
  GET DIAGNOSTICS EFFECT_COUNT = ROW_COUNT;
  SET PROC_NAME = 'PROC_FUND_CRD_DEAL.2';
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '向用信主表中间层插入信息';
  --记录插入日志信息
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
  SET RES_NUM = 1;
END;

comment on procedure PRC_FUND_CRD_OPT(VARCHAR(10), INTEGER) is '资金系统用信事件处理';

CREATE PROCEDURE PRC_GUARANTEE_RECOUNT(IN V_CUSTOMER_NUM VARCHAR(40))
    SPECIFIC SQL191115091457404
    LANGUAGE SQL
    NOT DETERMINISTIC
    EXTERNAL ACTION
    MODIFIES SQL DATA
    CALLED ON NULL INPUT
    INHERIT SPECIAL REGISTERS
    OLD SAVEPOINT LEVEL
BEGIN
    /*
    担保额度重算
    */
    DECLARE V_GRT_USED DECIMAL(24, 6) DEFAULT 0; --担保额度已用

    DECLARE V_CRD_DETAIL_NUM VARCHAR(40); --三级额度编号
    DECLARE V_CRD_MAIN_NUM VARCHAR(40); --二级额度编号

    DECLARE V_WORK_TIME TIMESTAMP; --营业时间
    SET V_WORK_TIME = CLM.FNC_GET_BUSI_TIME();


    /*担保额度--额度重算*/
    FOR GRT_LIST AS (SELECT T.SURETY_NUM, NVL(SUM(S.SURETY_AMT), 0) SURETY_AMT
                     FROM TB_CRD_SURETY T
                              LEFT JOIN (SELECT A.SURETY_NUM, NVL(A.SURETY_AMT, 0) SURETY_AMT
                                         FROM TB_CRD_SUBCONTRACT_SURETY A,
                                              TB_CRD_SUBCONTRACT B,
                                              TB_CRD_SUBCONTRACT_CON C,
                                              TB_CRD_CONTRACT D
                                         WHERE A.SUBCONTRACT_NUM = B.SUBCONTRACT_NUM
                                           AND B.SUBCONTRACT_NUM = C.SUBCONTRACT_NUM
                                           AND C.CONTRACT_NUM = D.CONTRACT_NUM
                                           AND D.CONTRACT_STATUS in ('0', '1', '2')
                     ) S ON S.SURETY_NUM = T.SURETY_NUM
                     WHERE T.CUSTOMER_NUM = V_CUSTOMER_NUM
                     GROUP BY T.SURETY_NUM)
        DO
            --更新担保额度可用，已用
            UPDATE TB_CRD_SURETY T
            SET T.AMT_USED=GRT_LIST.SURETY_AMT,
                T.AMT_AVI=T.AMT_ACTUAL - GRT_LIST.SURETY_AMT,
                T.UPDATE_TIME=V_WORK_TIME
            WHERE T.SURETY_NUM = GRT_LIST.SURETY_NUM
              and t.GUARANTEE_TYPE in ('2', '3');

            UPDATE TB_CRD_SURETY T
            SET t.AMT_ACTUAL=GRT_LIST.SURETY_AMT,
                t.AMT_ASSES=GRT_LIST.SURETY_AMT,
                T.AMT_USED=GRT_LIST.SURETY_AMT,
                T.AMT_AVI=0,
                T.UPDATE_TIME=V_WORK_TIME
            WHERE T.SURETY_NUM = GRT_LIST.SURETY_NUM
              and t.GUARANTEE_TYPE in ('1', '4');
        END FOR;


    --更新额度三级编号
    for crd_detail_list as (
        SELECT T.CRD_DETAIL_PRD,
               T.CUSTOMER_NUM,
               A.CORP_ORG_CODE
        FROM TB_CRD_SURETY T,
             CHAIN_DEPT A
        WHERE T.ORG_NUM = A.ID
          AND T.CUSTOMER_NUM = V_CUSTOMER_NUM
        GROUP BY T.CUSTOMER_NUM,
                 A.CORP_ORG_CODE,
                 T.CRD_DETAIL_PRD
    )
        do
            set V_CRD_DETAIL_NUM = null;
            select t.CRD_DETAIL_NUM
            into V_CRD_DETAIL_NUM
            from TB_CRD_DETAIL t
            where t.CUSTOMER_NUM = crd_detail_list.CUSTOMER_NUM
              and t.CRD_DETAIL_PRD = crd_detail_list.CRD_DETAIL_PRD
              and t.ORG_NUM = crd_detail_list.CORP_ORG_CODE;

            if (V_CRD_DETAIL_NUM is null or V_CRD_DETAIL_NUM = '') then
                set V_CRD_DETAIL_NUM = CLM.FNC_GET_BIZ_NUM('ED3');
            end if;

            update TB_CRD_SURETY t
            set t.CRD_DETAIL_NUM=V_CRD_DETAIL_NUM,
                t.UPDATE_TIME=V_WORK_TIME
            where t.CUSTOMER_NUM = crd_detail_list.CUSTOMER_NUM
              and t.CRD_DETAIL_PRD = crd_detail_list.CRD_DETAIL_PRD
              and exists(select 1
                         from CHAIN_DEPT a
                         where a.ID = t.ORG_NUM
                           and a.CORP_ORG_CODE = crd_detail_list.CORP_ORG_CODE);
        end for;
    --更新额度三级编号
    for crd_main_list as (
        SELECT t.CRD_MAIN_PRD,
               T.CUSTOMER_NUM,
               A.CORP_ORG_CODE
        FROM TB_CRD_SURETY T,
             CHAIN_DEPT A
        WHERE T.ORG_NUM = A.ID
          AND T.CUSTOMER_NUM = V_CUSTOMER_NUM
        GROUP BY T.CUSTOMER_NUM,
                 A.CORP_ORG_CODE,
                 t.CRD_MAIN_PRD
    )
        do
            set V_CRD_MAIN_NUM = null;

            select t.CRD_MAIN_NUM
            into V_CRD_MAIN_NUM
            from TB_CRD_MAIN t
            where t.CUSTOMER_NUM = crd_main_list.CUSTOMER_NUM
              and t.CRD_MAIN_PRD = crd_main_list.CRD_MAIN_PRD
              and t.ORG_NUM = crd_main_list.CORP_ORG_CODE;

            if (V_CRD_MAIN_NUM is null or V_CRD_MAIN_NUM = '') then
                set V_CRD_MAIN_NUM = CLM.FNC_GET_BIZ_NUM('ED2');
            end if;

            update TB_CRD_SURETY t
            set t.CRD_MAIN_NUM=V_CRD_MAIN_NUM,
                t.UPDATE_TIME=V_WORK_TIME
            where t.CUSTOMER_NUM = crd_main_list.CUSTOMER_NUM
              and t.CRD_MAIN_PRD = crd_main_list.CRD_MAIN_PRD
              and exists(select 1
                         from CHAIN_DEPT a
                         where a.ID = t.ORG_NUM
                           and a.CORP_ORG_CODE = crd_main_list.CORP_ORG_CODE);
        end for;

    --删除没有关联到担保物的的担保额度额度信息
    DELETE
    FROM TB_CRD_DETAIL T
    WHERE T.TRAN_SYSTEM = '0007'
      AND T.CUSTOMER_NUM = V_CUSTOMER_NUM
      and t.CRD_PRODUCT_TYPE = '2'--担保额度
      AND NOT EXISTS(
            SELECT 1 FROM TB_CRD_SURETY A WHERE A.CRD_DETAIL_NUM = T.CRD_DETAIL_NUM
        );

    /* 更新额度明细表额度（TB_CRD_DETAIL）*/
    MERGE INTO TB_CRD_DETAIL TA
    USING (SELECT T.CRD_MAIN_NUM,
                  T.CRD_DETAIL_NUM,
                  T.CRD_DETAIL_PRD,
                  T.CUSTOMER_NUM,
                  A.CORP_ORG_CODE,
                  SUM(T.AMT_ACTUAL) LIMIT_CREDIT,
                  SUM(T.AMT_USED)   LIMIT_USED,
                  SUM(T.AMT_AVI)    LIMIT_AVI
           FROM TB_CRD_SURETY T,
                CHAIN_DEPT A
           WHERE T.ORG_NUM = A.ID
             AND T.CUSTOMER_NUM = V_CUSTOMER_NUM
           GROUP BY T.CUSTOMER_NUM,
                    A.CORP_ORG_CODE,
                    T.CRD_DETAIL_PRD,
                    T.CRD_DETAIL_NUM,
                    T.CRD_MAIN_NUM) S
    ON (
        S.CRD_DETAIL_NUM = TA.CRD_DETAIL_NUM
        )
    WHEN NOT MATCHED
        THEN
        INSERT (CRD_MAIN_NUM,--二级额度编号
                CRD_DETAIL_NUM, --三级额度编号
                CRD_DETAIL_PRD, --三级额度品种
                CUSTOMER_NUM, --客户编号
                CURRENCY_CD, --币种
                EXCHANGE_RATE, --汇率
                LIMIT_CREDIT, --授信额度
                LIMIT_USED, --授信额度已用
                LIMIT_AVI, --授信额度可用
                EXP_CREDIT,--敞口额度
                EXP_USED,--敞口已用
                EXP_AVI,--敞口可用
                CREATE_TIME, --创建日期
                UPDATE_TIME, --更新日期
                ORG_NUM, --经办机构
                CRD_GRANT_ORG_NUM,--授信机构
                TRAN_SYSTEM,--交易系统
                CRD_PRODUCT_TYPE--额度类型
        )
        VALUES (S.CRD_MAIN_NUM,
                S.CRD_DETAIL_NUM,
                S.CRD_DETAIL_PRD,
                S.CUSTOMER_NUM,
                '156',
                '1',
                S.LIMIT_CREDIT,
                S.LIMIT_USED,
                S.LIMIT_AVI, 0, 0, 0,
                V_WORK_TIME,
                V_WORK_TIME,
                S.CORP_ORG_CODE, s.CORP_ORG_CODE, '0007', '2')
    WHEN MATCHED
        THEN
        UPDATE
        SET ta.CRD_MAIN_NUM=s.CRD_MAIN_NUM,--额度二级编号
            ta.LIMIT_CREDIT = S.LIMIT_CREDIT, --授信额度
            ta.LIMIT_USED = S.LIMIT_USED, --授信额度已用
            ta.LIMIT_AVI = S.LIMIT_AVI, --授信额度可用
            ta.EXP_CREDIT=0,--敞口额度
            ta.EXP_USED=0,--敞口已用
            ta.EXP_AVI=0,--敞口可用
            ta.CRD_GRANT_ORG_NUM=S.CORP_ORG_CODE, --经办机构
            ta.ORG_NUM = S.CORP_ORG_CODE, --经办机构
            ta.TRAN_SYSTEM='0007',
            ta.UPDATE_TIME = V_WORK_TIME;


END;

CREATE PROCEDURE PRC_THIRD_RECOUNT(IN V_CUSTOMER_NUM VARCHAR(40))
    SPECIFIC SQL191114170624602
    LANGUAGE SQL
    NOT DETERMINISTIC
    EXTERNAL ACTION
    MODIFIES SQL DATA
    CALLED ON NULL INPUT
    INHERIT SPECIAL REGISTERS
    OLD SAVEPOINT LEVEL
BEGIN
    /*
    第三方额度重算
    */
    DECLARE v_prj_used DECIMAL(24, 6) DEFAULT 0; --第三方额度已用

    DECLARE v_summary_bal DECIMAL(24, 6) DEFAULT 0; --借据余额
    DECLARE v_summary_amt DECIMAL(24, 6) DEFAULT 0; --借据金额

    DECLARE V_CRD_DETAIL_NUM VARCHAR(40); --三级额度编号
    DECLARE V_CRD_MAIN_NUM VARCHAR(40); --二级额度编号

    DECLARE v_work_time TIMESTAMP; --营业时间
    SET v_work_time = CLM.FNC_GET_busi_time();

    --第三方额度--额度重算
    FOR prj_list AS (SELECT t.PROJECT_NUM, t.LIMIT_CONTROL_TYPE, t.TOTAL_AMT
                     FROM TB_CRD_PROJECT t
                     WHERE t.customer_num = V_CUSTOMER_NUM
                       AND t.PROJECT_STATUS = '1')
        DO

            SELECT nvl(sum(b.APPROVE_AMT), 0)
            INTO v_prj_used
            FROM tb_crd_approve b
            where b.APPROVE_STATUS = '2' -- 已生效
              and b.PROJECT_NUM = prj_list.PROJECT_NUM;

            /*
                SELECT COALESCE(sum(s.summary_bal), 0),
                       COALESCE(sum(s.summary_amt), 0)
                INTO v_summary_bal, v_summary_amt
                FROM tb_crd_summary s,
                     tb_crd_approve b
                WHERE s.APPROVE_ID = b.APPROVE_ID
                  AND b.PROJECT_NUM = prj_list.PROJECT_num;
                IF prj_list.LIMIT_CONTROL_TYPE = '0' --按贷款累计发生额控制
                THEN
                    --循环额度
                    SET v_prj_used = v_summary_amt;
                ELSEIF prj_list.LIMIT_CONTROL_TYPE = '1'
                THEN --按贷款余额控制
                    SET v_prj_used = v_summary_bal;
                ELSE --不控制
                    SET v_prj_used = 0;
                END IF;
            */
            --更新项目额度可用
            UPDATE TB_CRD_PROJECT t
            SET t.USED_AMT    = v_prj_used,
                t.AVI_AMT     = t.TOTAL_AMT - v_prj_used,
                t.update_time = v_work_time
            WHERE t.PROJECT_NUM = prj_list.PROJECT_NUM;
        END FOR;


    --更新额度三级编号
    for crd_detail_list as (
        SELECT T.CRD_DETAIL_PRD,
               T.CUSTOMER_NUM,
               A.CORP_ORG_CODE
        FROM TB_CRD_PROJECT T,
             CHAIN_DEPT A
        WHERE T.ORG_NUM = A.ID
          AND T.CUSTOMER_NUM = V_CUSTOMER_NUM
        GROUP BY T.CUSTOMER_NUM,
                 A.CORP_ORG_CODE,
                 T.CRD_DETAIL_PRD
    )
        do
            set V_CRD_DETAIL_NUM = null;
            select t.CRD_DETAIL_NUM
            into V_CRD_DETAIL_NUM
            from TB_CRD_DETAIL t
            where t.CUSTOMER_NUM = crd_detail_list.CUSTOMER_NUM
              and t.CRD_DETAIL_PRD = crd_detail_list.CRD_DETAIL_PRD
              and t.ORG_NUM = crd_detail_list.CORP_ORG_CODE;

            if (V_CRD_DETAIL_NUM is null or V_CRD_DETAIL_NUM = '') then
                set V_CRD_DETAIL_NUM = CLM.FNC_GET_BIZ_NUM('ED3');
            end if;

            update TB_CRD_PROJECT t
            set t.CRD_DETAIL_NUM=V_CRD_DETAIL_NUM,
                t.UPDATE_TIME=V_WORK_TIME
            where t.CUSTOMER_NUM = crd_detail_list.CUSTOMER_NUM
              and t.CRD_DETAIL_PRD = crd_detail_list.CRD_DETAIL_PRD
              and exists(select 1
                         from CHAIN_DEPT a
                         where a.ID = t.ORG_NUM
                           and a.CORP_ORG_CODE = crd_detail_list.CORP_ORG_CODE);
        end for;

    --更新额度二级编号
    for crd_main_list as (
        SELECT t.CRD_MAIN_PRD,
               T.CUSTOMER_NUM,
               A.CORP_ORG_CODE
        FROM TB_CRD_PROJECT T,
             CHAIN_DEPT A
        WHERE T.ORG_NUM = A.ID
          AND T.CUSTOMER_NUM = V_CUSTOMER_NUM
        GROUP BY T.CUSTOMER_NUM,
                 A.CORP_ORG_CODE,
                 t.CRD_MAIN_PRD
    )
        do
            set V_CRD_MAIN_NUM = null;

            select t.CRD_MAIN_NUM
            into V_CRD_MAIN_NUM
            from TB_CRD_MAIN t
            where t.CUSTOMER_NUM = crd_main_list.CUSTOMER_NUM
              and t.CRD_MAIN_PRD = crd_main_list.CRD_MAIN_PRD
              and t.ORG_NUM = crd_main_list.CORP_ORG_CODE;

            if (V_CRD_MAIN_NUM is null or V_CRD_MAIN_NUM = '') then
                set V_CRD_MAIN_NUM = CLM.FNC_GET_BIZ_NUM('ED3');
            end if;

            update TB_CRD_PROJECT t
            set t.CRD_MAIN_NUM=V_CRD_MAIN_NUM,
                t.UPDATE_TIME=V_WORK_TIME
            where t.CUSTOMER_NUM = crd_main_list.CUSTOMER_NUM
              and t.CRD_MAIN_PRD = crd_main_list.CRD_MAIN_PRD
              and exists(select 1
                         from CHAIN_DEPT a
                         where a.ID = t.ORG_NUM
                           and a.CORP_ORG_CODE = crd_main_list.CORP_ORG_CODE);
        end for;

    --删除没有关联到合作项目的授信额度信息
    delete
    from tb_crd_detail t
    where t.CUSTOMER_NUM = V_CUSTOMER_NUM
      and t.TRAN_SYSTEM = '0007'
      and t.CRD_PRODUCT_TYPE = '3'
      and not exists(
            select 1 from TB_CRD_PROJECT a where a.CRD_DETAIL_NUM = t.CRD_DETAIL_NUM and a.PROJECT_STATUS in ('1', '2')--生效批复
        );

    --更新额度明细表额度（tb_crd_detail）
    MERGE INTO tb_crd_detail ta
    USING (SELECT t.CRD_MAIN_NUM,
                  t.crd_detail_num,
                  t.crd_detail_prd,
                  t.CUSTOMER_NUM,
                  b.CORP_ORG_CODE,
                  sum(t.total_amt) limit_credit,
                  sum(t.used_amt)  limit_used,
                  sum(t.avi_amt)   limit_avi
           FROM tb_crd_project t,
                CHAIN_DEPT b
           WHERE t.ORG_NUM = b.ID
             and t.PROJECT_STATUS = '1'
             AND t.customer_num = V_CUSTOMER_NUM
           GROUP BY t.CUSTOMER_NUM,
                    b.CORP_ORG_CODE,
                    t.CRD_MAIN_NUM,
                    t.crd_detail_prd,
                    t.crd_detail_num) s
    ON (s.CRD_DETAIL_NUM = ta.CRD_DETAIL_NUM)
    WHEN NOT MATCHED
        THEN
        INSERT (crd_main_num,--额度编号
                crd_detail_num, --额度明细编号
                crd_detail_prd, --额度产品编号
                CUSTOMER_NUM, --客户编号
                currency_cd, --币种
                exchange_rate, --汇率
                limit_credit, --授信额度
                limit_used, --授信额度已用
                limit_avi, --授信额度可用
                EXP_CREDIT,--敞口额度
                EXP_USED,--敞口已用
                EXP_AVI,--敞口可用
                create_time, --创建日期
                update_time, --更新日期
                org_num,--经办机构
                CRD_GRANT_ORG_NUM,--授信机构
                TRAN_SYSTEM,--经办系统
                CRD_PRODUCT_TYPE--额度类型
        )
        VALUES (s.crd_main_num,
                s.crd_detail_num,
                s.crd_detail_prd,
                s.customer_num,
                'CNY',
                '1',
                s.limit_credit,
                s.limit_used,
                s.limit_avi,0,0,0,
                v_work_time,
                v_work_time,
                s.CORP_ORG_CODE, s.CORP_ORG_CODE, '0007', '3')
    WHEN MATCHED
        THEN
        UPDATE
        SET ta.CRD_MAIN_NUM=s.CRD_MAIN_NUM,--二级额度编号
            ta.CRD_DETAIL_PRD=s.CRD_DETAIL_PRD,--三级额度品种
            ta.limit_credit = s.limit_credit, --授信额度
            ta.limit_used = s.limit_used, --授信额度已用
            ta.limit_avi = s.limit_avi, --授信额度可用
            ta.EXP_CREDIT=0,--敞口额度
            ta.EXP_USED=0,--敞口已用
            ta.EXP_AVI=0,--敞口可用
            ta.org_num = s.CORP_ORG_CODE, --经办机构
            ta.update_time = v_work_time;
    --更新日期

END;

CREATE PROCEDURE PRC_CC_statis()
BEGIN
    DECLARE V_CRD_DETAIL_NUM VARCHAR(40); --三级额度编号
    DECLARE V_CRD_MAIN_NUM VARCHAR(40); --二级额度编号

    DECLARE V_WORK_TIME TIMESTAMP; --营业时间
    SET V_WORK_TIME = CLM.FNC_GET_BUSI_TIME();

    --初始化三级额度品种、二级额度品种
    update TB_CRD_CC_INFO t
    set t.CRD_DETAIL_PRD='01040001',
        t.CRD_MAIN_PRD='0104 '
    where t.CRD_DETAIL_PRD is null
       or t.CRD_DETAIL_PRD = '';

    --根据信用卡3要素匹配客户号
    merge into TB_CRD_CC_INFO t
    using (
        select a.CUSTOMER_NUM, a.CUSTOMER_NAME, b.CERT_TYPE, b.CERT_NUM
        from TB_CSM_PARTY a,
             TB_CSM_CERT_INFO b
        where a.CUSTOMER_NUM = b.CUSTOMER_NUM
    ) s
    on (
            t.CUSTOMER_NAME = s.CUSTOMER_NAME
            and t.CERT_TYPE = s.CERT_TYPE
            and t.CERT_NUM = s.CERT_NUM
        )
    when matched then
        update set t.CUSTOMER_NUM=s.CUSTOMER_NUM;


    --更新额度三级编号
    for crd_detail_list as (
        SELECT T.CRD_DETAIL_PRD,
               T.CUSTOMER_NUM,
               A.CORP_ORG_CODE
        FROM TB_CRD_CC_INFO T,
             CHAIN_DEPT A
        WHERE T.ORG_NUM = A.ID
        GROUP BY T.CUSTOMER_NUM,
                 A.CORP_ORG_CODE,
                 T.CRD_DETAIL_PRD
    )
        do
            set V_CRD_DETAIL_NUM = null;

            select t.CRD_DETAIL_NUM
            into V_CRD_DETAIL_NUM
            from TB_CRD_DETAIL t
            where t.CUSTOMER_NUM = crd_detail_list.CUSTOMER_NUM
              and t.CRD_DETAIL_PRD = crd_detail_list.CRD_DETAIL_PRD
              and t.ORG_NUM = crd_detail_list.CORP_ORG_CODE;

            if (V_CRD_DETAIL_NUM is null or V_CRD_DETAIL_NUM = '') then
                set V_CRD_DETAIL_NUM = CLM.FNC_GET_BIZ_NUM('ED3');
            end if;

            update TB_CRD_CC_INFO t
            set t.CRD_DETAIL_NUM=V_CRD_DETAIL_NUM,
                t.UPDATE_TIME=V_WORK_TIME
            where t.CUSTOMER_NUM = crd_detail_list.CUSTOMER_NUM
              and t.CRD_DETAIL_PRD = crd_detail_list.CRD_DETAIL_PRD
              and exists(select 1
                         from CHAIN_DEPT a
                         where a.ID = t.ORG_NUM
                           and a.CORP_ORG_CODE = crd_detail_list.CORP_ORG_CODE);
        end for;

    --更新额度二级编号
    for crd_main_list as (
        SELECT t.CRD_MAIN_PRD,
               T.CUSTOMER_NUM,
               A.CORP_ORG_CODE
        FROM TB_CRD_CC_INFO T,
             CHAIN_DEPT A
        WHERE T.ORG_NUM = A.ID
        GROUP BY T.CUSTOMER_NUM,
                 A.CORP_ORG_CODE,
                 t.CRD_MAIN_PRD
    )
        do
            set V_CRD_MAIN_NUM = null;

            select t.CRD_MAIN_NUM
            into V_CRD_MAIN_NUM
            from TB_CRD_MAIN t
            where t.CUSTOMER_NUM = crd_main_list.CUSTOMER_NUM
              and t.CRD_MAIN_PRD = crd_main_list.CRD_MAIN_PRD
              and t.ORG_NUM = crd_main_list.CORP_ORG_CODE;

            if (V_CRD_MAIN_NUM is null or V_CRD_MAIN_NUM = '') then
                set V_CRD_MAIN_NUM = CLM.FNC_GET_BIZ_NUM('ED3');
            end if;

            update TB_CRD_CC_INFO t
            set t.CRD_MAIN_NUM=V_CRD_MAIN_NUM,
                t.UPDATE_TIME=V_WORK_TIME
            where t.CUSTOMER_NUM = crd_main_list.CUSTOMER_NUM
              and t.CRD_MAIN_PRD = crd_main_list.CRD_MAIN_PRD
              and exists(select 1
                         from CHAIN_DEPT a
                         where a.ID = t.ORG_NUM
                           and a.CORP_ORG_CODE = crd_main_list.CORP_ORG_CODE);
        end for;

    --删除没有关联到批复和信用卡的授信额度信息
    DELETE
    FROM TB_CRD_DETAIL T
    WHERE T.TRAN_SYSTEM = '1106'
      AND EXISTS(
            SELECT 1
            FROM TB_PAR_CRD A
            WHERE A.CRD_PRODUCT_NUM = T.CRD_DETAIL_PRD
              AND SUBSTR(A.CRD_PRODUCT_NUM, 1, 2) IN ('01', '02')--信贷个人、公司的额度
              AND A.CRD_PRODUCT_TYPE = '1'--授信额度
        )
      AND NOT EXISTS(
            SELECT 1
            FROM TB_CRD_CC_INFO A
            WHERE A.CRD_DETAIL_NUM = T.CRD_DETAIL_NUM
        );

    --更新额度明细表额度（TB_CRD_DETAIL）
    MERGE INTO TB_CRD_DETAIL TA
    USING (SELECT T.CRD_MAIN_NUM,
                  T.CRD_DETAIL_NUM,
                  T.CRD_DETAIL_PRD,
                  T.CUSTOMER_NUM,
                  SUM(T.CREDIT_LIMIT)              LIMIT_CREDIT,
                  SUM(T.CREDIT_LIMIT - t.CURR_BAL) LIMIT_USED,
                  SUM(T.CURR_BAL)                  LIMIT_AVI,
                  SUM(T.CREDIT_LIMIT)              EXP_CREDIT,
                  SUM(T.CREDIT_LIMIT - t.CURR_BAL) EXP_USED,
                  SUM(T.CURR_BAL)                  EXP_AVI,
                  0                                LIMIT_PRE,
                  0                                EXP_PRE,
                  A.CORP_ORG_CODE
           FROM TB_CRD_CC_INFO T,
                CHAIN_DEPT A
           WHERE T.ORG_NUM = A.ID
           GROUP BY T.CUSTOMER_NUM,
                    A.CORP_ORG_CODE,
                    T.CRD_DETAIL_PRD,
                    T.CRD_DETAIL_NUM, T.CRD_MAIN_NUM) S
    ON (
        S.CRD_DETAIL_NUM = ta.CRD_DETAIL_NUM
        )
    WHEN NOT MATCHED
        THEN
        INSERT (CRD_MAIN_NUM,--二级额度编号
                CRD_DETAIL_NUM, --三级额度编号
                CRD_DETAIL_PRD, --三级额度品种
                CUSTOMER_NUM, --ECIF客户编号
                CURRENCY_CD, --币种
                EXCHANGE_RATE, --汇率
                LIMIT_CREDIT, --授信额度
                LIMIT_USED, --授信额度已用
                LIMIT_AVI, --授信额度可用
                EXP_CREDIT, --授信敞口
                EXP_USED, --敞口已用
                EXP_AVI, --敞口可用
                LIMIT_PRE, ---预占用额度
                EXP_PRE, --预占用敞口
                CREATE_TIME, --创建日期
                UPDATE_TIME, --更新日期
                CRD_GRANT_ORG_NUM,--授信机构
                ORG_NUM, --经办机构
                TRAN_SYSTEM,--交易系统
                CRD_PRODUCT_TYPE--额度类型
        )
        VALUES (S.CRD_MAIN_NUM,
                S.CRD_DETAIL_NUM,
                S.CRD_DETAIL_PRD,
                S.CUSTOMER_NUM,
                'CNY',
                '1',
                S.LIMIT_CREDIT,
                S.LIMIT_USED,
                S.LIMIT_AVI,
                S.EXP_CREDIT,
                S.EXP_AVI,
                S.EXP_USED,
                S.LIMIT_PRE,
                S.EXP_PRE,
                V_WORK_TIME,
                V_WORK_TIME,
                S.CORP_ORG_CODE,
                S.CORP_ORG_CODE,
                '1106', '1'--额度类型(授信额度)
               )
    WHEN MATCHED
        THEN
        UPDATE
        SET TA.CRD_MAIN_NUM=S.CRD_MAIN_NUM,--二级额度编号
            TA.CRD_DETAIL_PRD=S.CRD_DETAIL_PRD,--三级额度品种
            TA.LIMIT_CREDIT = S.LIMIT_CREDIT, --授信额度
            TA.LIMIT_USED = S.LIMIT_USED, --授信额度已用
            TA.LIMIT_AVI = S.LIMIT_AVI, --授信额度可用
            TA.EXP_CREDIT = S.EXP_CREDIT, --授信敞口
            TA.EXP_USED = S.EXP_USED, --敞口已用
            TA.EXP_AVI = S.EXP_AVI, --敞口可用
            TA.LIMIT_PRE = S.LIMIT_PRE, ---预占用额度
            TA.EXP_PRE = S.LIMIT_PRE, --预占用敞口
            TA.CRD_GRANT_ORG_NUM=S.CORP_ORG_CODE,--经办机构
            TA.ORG_NUM = S.CORP_ORG_CODE, --经办机构
            TA.UPDATE_TIME = V_WORK_TIME;
    --更新日期

    --删除无效的二级额度
    DELETE
    FROM TB_CRD_MAIN T
    WHERE T.TRAN_SYSTEM = '1106'
      AND EXISTS(
            SELECT 1
            FROM TB_PAR_CRD A
            WHERE A.CRD_PRODUCT_NUM = T.CRD_MAIN_PRD
              AND SUBSTR(A.CRD_PRODUCT_NUM, 1, 2) IN ('01', '02') --信贷个人、公司的额度
        )
      AND NOT EXISTS(
            SELECT 1 FROM TB_CRD_CC_INFO A WHERE A.CRD_MAIN_NUM = T.CRD_MAIN_NUM --信用卡
        );

    --更新额度主表
    MERGE INTO TB_CRD_MAIN TA
    USING (SELECT T.CRD_MAIN_NUM,
                  T.CRD_MAIN_PRD,
                  T.CUSTOMER_NUM,
                  SUM(T.CREDIT_LIMIT)              LIMIT_CREDIT,
                  SUM(T.CREDIT_LIMIT - t.CURR_BAL) LIMIT_USED,
                  SUM(T.CURR_BAL)                  LIMIT_AVI,
                  SUM(T.CREDIT_LIMIT)              EXP_CREDIT,
                  SUM(T.CREDIT_LIMIT - t.CURR_BAL) EXP_USED,
                  SUM(T.CURR_BAL)                  EXP_AVI,
                  0                                LIMIT_PRE,
                  0                                EXP_PRE,
                  A.CORP_ORG_CODE
           FROM TB_CRD_CC_INFO T,
                CHAIN_DEPT A
           WHERE T.ORG_NUM = A.ID
           GROUP BY T.CUSTOMER_NUM,
                    A.CORP_ORG_CODE,
                    T.CRD_MAIN_NUM,
                    T.CRD_MAIN_PRD) S
    ON (
        S.CRD_MAIN_NUM = TA.CRD_MAIN_NUM
        )
    WHEN NOT MATCHED
        THEN
        INSERT (CRD_MAIN_NUM,--二级额度编号
                CRD_MAIN_PRD, --三级额度品种
                CUSTOMER_NUM, --ECIF客户编号
                CURRENCY_CD, --币种
                EXCHANGE_RATE, --汇率
                LIMIT_CREDIT, --授信额度
                LIMIT_USED, --授信额度已用
                LIMIT_AVI, --授信额度可用
                EXP_CREDIT, --授信敞口
                EXP_USED, --敞口已用
                EXP_AVI, --敞口可用
                LIMIT_PRE, ---预占用额度
                EXP_PRE, --预占用敞口
                CREATE_TIME, --创建日期
                UPDATE_TIME, --更新日期
                CRD_GRANT_ORG_NUM,--授信机构
                ORG_NUM,--经办机构
                TRAN_SYSTEM,--交易系统
                CRD_PRODUCT_TYPE--额度类型
        )
        VALUES (S.CRD_MAIN_NUM,
                S.CRD_MAIN_PRD,
                S.CUSTOMER_NUM,
                'CNY',
                '1',
                S.LIMIT_CREDIT,
                S.LIMIT_USED,
                S.LIMIT_AVI,
                S.EXP_CREDIT,
                S.EXP_AVI,
                S.EXP_USED,
                S.LIMIT_PRE,
                S.EXP_PRE,
                V_WORK_TIME,
                V_WORK_TIME,
                S.CORP_ORG_CODE,
                S.CORP_ORG_CODE, '1106', '1')
    WHEN MATCHED
        THEN
        UPDATE
        SET TA.CRD_MAIN_PRD=S.CRD_MAIN_PRD,
            TA.LIMIT_CREDIT = S.LIMIT_CREDIT,    --授信额度
            TA.LIMIT_USED = S.LIMIT_USED,        --授信额度已用
            TA.LIMIT_AVI = S.LIMIT_AVI,          --授信额度可用
            TA.EXP_CREDIT = S.EXP_CREDIT,        --授信敞口
            TA.EXP_USED = S.EXP_USED,            --敞口已用
            TA.EXP_AVI = S.EXP_AVI,              --敞口可用
            TA.LIMIT_PRE = S.LIMIT_PRE,          ---预占用额度
            TA.EXP_PRE = S.LIMIT_PRE,            --预占用敞口
            TA.CRD_GRANT_ORG_NUM=S.CORP_ORG_CODE,--经办机构
            TA.ORG_NUM = S.CORP_ORG_CODE,        --经办机构
            TA.UPDATE_TIME = V_WORK_TIME; --更新日期
END;

comment on procedure PRC_CC_STATIS() is '信用卡额度统计';

CREATE PROCEDURE PRC_CREDIT_RECOUNT(IN V_CUSTOMER_NUM VARCHAR(40))
BEGIN
    DECLARE V_APPROVE_USED DECIMAL(18, 2) DEFAULT 0; --批复已用
    DECLARE V_APPROVE_EXP_USED DECIMAL(18, 2) DEFAULT 0; --批复敞口已用


    DECLARE V_APPROVE_PRE_AMT DECIMAL(18, 2) DEFAULT 0; --批复预占用金额
    DECLARE V_APPROVE_PRE_EXP DECIMAL(18, 2) DEFAULT 0; --批复预占用敞口金额

    DECLARE V_CON_BAL DECIMAL(18, 2) DEFAULT 0; --合同余额
    DECLARE V_CON_AMT DECIMAL(18, 2) DEFAULT 0; --合同下借据金额之和
    DECLARE V_CON_USED DECIMAL(18, 2) DEFAULT 0; --合同已用

    DECLARE V_CRD_DETAIL_NUM VARCHAR(40); --三级额度编号
    DECLARE V_CRD_MAIN_NUM VARCHAR(40); --二级额度编号

    DECLARE V_WORK_TIME TIMESTAMP; --营业时间
    SET V_WORK_TIME = CLM.FNC_GET_BUSI_TIME();

    --合同额度重算
    FOR CON_LIST AS (SELECT CONTRACT_NUM, IS_CYCLE, CONTRACT_AMT
                     FROM TB_CRD_CONTRACT
                     WHERE CUSTOMER_NUM = V_CUSTOMER_NUM
                       AND CONTRACT_STATUS IN ('0', '1', '2'))
        DO
        --1）合同余额(合同已用) = 合同下未结清的借据余额之和（借据状态：02,03）
        --2）循环合同可用=合同金额-合同下未结清借据余额之和
        --3）非循环合同可用=合同金额-合同下未结清借据金额之和
            SELECT NVL(SUM(S.SUMMARY_BAL), 0),
                   NVL(SUM(S.SUMMARY_AMT), 0)
            INTO V_CON_BAL, V_CON_AMT
            FROM TB_CRD_SUMMARY S
            WHERE S.CONTRACT_NUM = CON_LIST.CONTRACT_NUM
              AND S.SUMMARY_STATUS IN ('00', '156', '02', '03');
            /*  00 未发放  01正常  02逾期  03部分逾期  07核销  08销户  09票据置换  10资产置换  11股权置换*/

            IF CON_LIST.IS_CYCLE = '1'
            THEN
                --循环额度
                SET V_CON_USED = V_CON_BAL;
            ELSE
                SET V_CON_USED = V_CON_AMT;
            END IF;

            --更新合同可用/已用额度
            UPDATE TB_CRD_CONTRACT T
            SET T.CONTRACT_USED=V_CON_USED,
                T.CONTRACT_AVI = T.CONTRACT_AMT - V_CON_USED,
                T.CONTRACT_BAL = V_CON_BAL,
                T.UPDATE_TIME  = V_WORK_TIME
            WHERE T.CONTRACT_NUM = CON_LIST.CONTRACT_NUM;
        END FOR;

    --批复可已用计算
    FOR APPROVE_LIST AS SELECT nvl(t.DEPOSIT_RATIO, 0) DEPOSIT_RATIO,
                               T.APPROVE_ID,
                               T.IS_CYCLE,
                               A.LIMIT_USED_TYPE, --产品额度占用方式（CD000179）1合同占用 2放款占用
                               t.IS_LOW_RISK--低风险标识
                        FROM TB_CRD_APPROVE T,
                             TB_PAR_PRODUCT A,
                             CHAIN_DEPT b
                        WHERE T.PRODUCT_NUM = A.PRODUCT_NUM
                          and b.ID = t.ORG_NUM
                          and a.PRODUCT_TARGER = '1'--用信系统为信贷类
                          AND T.CUSTOMER_NUM = V_CUSTOMER_NUM
        DO
            IF APPROVE_LIST.LIMIT_USED_TYPE = '1' THEN--合同占用额度
                IF APPROVE_LIST.IS_CYCLE = '1' THEN--额度循环
                    SELECT SUM(NVL(CONTRACT_AMT, 0)), CAST(SUM(NVL(CONTRACT_EXP, 0)) AS DECIMAL(18, 2))
                    INTO V_APPROVE_USED,V_APPROVE_EXP_USED
                    FROM (
                             SELECT SUM(T.CONTRACT_AMT)                                 CONTRACT_AMT,
                                    SUM(T.CONTRACT_AMT * (100 - T.DEPOSIT_RATIO) / 100) CONTRACT_EXP
                             FROM TB_CRD_CONTRACT T
                             WHERE T.APPROVE_ID = APPROVE_LIST.APPROVE_ID
                               AND T.IS_CYCLE = '1'--合同循环
                               AND T.CONTRACT_STATUS IN ('0', '1', '2')
                             UNION ALL
                             SELECT SUM(T.CONTRACT_AVI + T.CONTRACT_BAL)                                   CONTRACT_AMT,
                                    SUM((T.CONTRACT_AVI + T.CONTRACT_BAL) * (100 - T.DEPOSIT_RATIO) / 100) CONTRACT_EXP
                             FROM TB_CRD_CONTRACT T
                             WHERE T.APPROVE_ID = APPROVE_LIST.APPROVE_ID
                               AND T.IS_CYCLE = '0'--合同不循环
                               AND T.CONTRACT_STATUS IN ('0', '1', '2')
                         ) S;
                ELSEIF APPROVE_LIST.IS_CYCLE = '0' THEN--额度不循环
                    SELECT SUM(T.CONTRACT_AMT)                                 CONTRACT_AMT,
                           SUM(T.CONTRACT_AMT * (100 - T.DEPOSIT_RATIO) / 100) CONTRACT_EXP
                    INTO V_APPROVE_USED,V_APPROVE_EXP_USED
                    FROM TB_CRD_CONTRACT T
                    WHERE T.APPROVE_ID = APPROVE_LIST.APPROVE_ID
                      --AND T.IS_CYCLE IN('1','0')--合同循环
                      AND T.CONTRACT_STATUS IN ('0', '1', '2', '4');
                END IF;
            ELSEIF APPROVE_LIST.LIMIT_USED_TYPE = '2' THEN--放款占用
                IF APPROVE_LIST.IS_CYCLE = '1' THEN--额度循环
                    SELECT SUM(SUMMARY_AMT), SUM(SUMMARY_EXP)
                    INTO V_APPROVE_USED,V_APPROVE_EXP_USED
                    FROM (
                             SELECT SUM(A.SUMMARY_AMT)                                 SUMMARY_AMT,
                                    SUM(A.SUMMARY_AMT * (100 - T.DEPOSIT_RATIO) / 100) SUMMARY_EXP
                             FROM TB_CRD_CONTRACT T,
                                  TB_CRD_SUMMARY A
                             WHERE T.CONTRACT_NUM = A.CUSTOMER_NUM
                               AND T.APPROVE_ID = APPROVE_LIST.APPROVE_ID
                               AND T.IS_CYCLE = '1'--合同循环
                               AND A.SUMMARY_STATUS IN ('02', '03', '04')
                             UNION ALL
                             SELECT SUM(A.SUMMARY_BAL)                                 SUMMARY_AMT,
                                    SUM(A.SUMMARY_BAL * (100 - T.DEPOSIT_RATIO) / 100) SUMMARY_EXP
                             FROM TB_CRD_CONTRACT T,
                                  TB_CRD_SUMMARY A
                             WHERE T.CONTRACT_NUM = A.CUSTOMER_NUM
                               AND T.APPROVE_ID = APPROVE_LIST.APPROVE_ID
                               AND T.IS_CYCLE = '0'--合同不循环
                               AND A.SUMMARY_STATUS IN ('02', '03')
                         ) S;
                ELSEIF APPROVE_LIST.IS_CYCLE = '0' THEN--额度不循环
                    SELECT SUM(A.SUMMARY_AMT)                                 CONTRACT_AMT,
                           SUM(A.SUMMARY_AMT * (100 - T.DEPOSIT_RATIO) / 100) CONTRACT_EXP
                    INTO V_APPROVE_USED,V_APPROVE_EXP_USED
                    FROM TB_CRD_CONTRACT T,
                         TB_CRD_SUMMARY A
                    WHERE T.CONTRACT_NUM = A.CUSTOMER_NUM
                      AND T.APPROVE_ID = APPROVE_LIST.APPROVE_ID
                      AND T.IS_CYCLE IN ('1', '0')--合同循环、不循环
                      AND T.CONTRACT_STATUS IN ('0', '1', '2', '4');
                END IF;
            END IF;

            --查询预占用的额度
            SELECT SUM(NVL(T.CONTRACT_AMT, 0)),
                   SUM(NVL(T.CONTRACT_AMT * (100 - T.DEPOSIT_RATIO) / 100, 0))
            INTO V_APPROVE_PRE_AMT,V_APPROVE_PRE_EXP
            FROM TB_CRD_CONTRACT T
            WHERE T.APPROVE_ID = APPROVE_LIST.APPROVE_ID
              AND T.CONTRACT_STATUS IN ('0');--流程中的合同

            SET V_APPROVE_USED = NVL(V_APPROVE_USED, 0);
            SET V_APPROVE_EXP_USED = NVL(V_APPROVE_EXP_USED, 0);
            SET V_APPROVE_PRE_AMT = NVL(V_APPROVE_PRE_AMT, 0);
            SET V_APPROVE_PRE_EXP = NVL(V_APPROVE_PRE_EXP, 0);

            --低风险业务敞口额度为0
            if (APPROVE_LIST.IS_LOW_RISK = '1') then
                SET V_APPROVE_PRE_EXP = 0;
                SET V_APPROVE_EXP_USED = 0;
            end if;

            UPDATE TB_CRD_APPROVE T
            SET T.APPROVE_USED     = V_APPROVE_USED,                         --授信额度已用
                T.APPROVE_AVI      = T.APPROVE_AMT - V_APPROVE_USED,         --授信额度可用
                T.APPROVE_EXP_USED = V_APPROVE_EXP_USED,                     --敞口已用
                T.APPROVE_EXP_AVI  = T.APPROVE_EXP_AMT - V_APPROVE_EXP_USED, --敞口可用
                T.APPROVE_PRE_AMT  = V_APPROVE_PRE_AMT,                      --预占用额度
                T.APPROVE_PRE_EXP  = V_APPROVE_PRE_EXP,                      --预占用敞口
                t.DEPOSIT_RATIO=APPROVE_LIST.DEPOSIT_RATIO,--保证金比例
                T.UPDATE_TIME      = V_WORK_TIME
            WHERE T.APPROVE_ID = APPROVE_LIST.APPROVE_ID;
        END FOR;


    --更新额度三级编号
    for crd_detail_list as (
        SELECT T.CRD_DETAIL_PRD,
               T.CUSTOMER_NUM,
               A.CORP_ORG_CODE
        FROM TB_CRD_APPROVE T,
             CHAIN_DEPT A
        WHERE T.ORG_NUM = A.ID
          AND T.CUSTOMER_NUM = V_CUSTOMER_NUM
        GROUP BY T.CUSTOMER_NUM,
                 A.CORP_ORG_CODE,
                 T.CRD_DETAIL_PRD
    )
        do
            set V_CRD_DETAIL_NUM = null;

            --查询已有的额度编号
            select distinct t.CRD_DETAIL_NUM
            into V_CRD_DETAIL_NUM
            from TB_CRD_DETAIL t
            where t.CUSTOMER_NUM = crd_detail_list.CUSTOMER_NUM
              and t.CRD_DETAIL_PRD = crd_detail_list.CRD_DETAIL_PRD
              and t.ORG_NUM = crd_detail_list.CORP_ORG_CODE;

            --如果还没有生成3级额度编号，重新生成
            if (V_CRD_DETAIL_NUM is null or V_CRD_DETAIL_NUM = '') then
                set V_CRD_DETAIL_NUM = CLM.FNC_GET_BIZ_NUM('ED3');
            end if;

            update TB_CRD_APPROVE t
            set t.CRD_DETAIL_NUM=V_CRD_DETAIL_NUM,
                t.UPDATE_TIME=V_WORK_TIME
            where t.CUSTOMER_NUM = crd_detail_list.CUSTOMER_NUM
              and t.CRD_DETAIL_PRD = crd_detail_list.CRD_DETAIL_PRD
              and exists(select 1
                         from CHAIN_DEPT a
                         where a.ID = t.ORG_NUM
                           and a.CORP_ORG_CODE = crd_detail_list.CORP_ORG_CODE);
        end for;

    --更新额度二级编号
    for crd_main_list as (
        SELECT t.CRD_MAIN_PRD,
               T.CUSTOMER_NUM,
               A.CORP_ORG_CODE
        FROM TB_CRD_APPROVE T,
             CHAIN_DEPT A
        WHERE T.ORG_NUM = A.ID
          AND T.CUSTOMER_NUM = V_CUSTOMER_NUM
        GROUP BY T.CUSTOMER_NUM,
                 A.CORP_ORG_CODE,
                 t.CRD_MAIN_PRD
    )
        do
            set V_CRD_MAIN_NUM = null;

            select t.CRD_MAIN_NUM
            into V_CRD_MAIN_NUM
            from TB_CRD_MAIN t
            where t.CUSTOMER_NUM = crd_main_list.CUSTOMER_NUM
              and t.CRD_MAIN_PRD = crd_main_list.CRD_MAIN_PRD
              and t.ORG_NUM = crd_main_list.CORP_ORG_CODE;

            if (V_CRD_MAIN_NUM is null or V_CRD_MAIN_NUM = '') then
                set V_CRD_MAIN_NUM = CLM.FNC_GET_BIZ_NUM('EDM3');
            end if;

            update TB_CRD_APPROVE t
            set t.CRD_MAIN_NUM=V_CRD_MAIN_NUM,
                t.UPDATE_TIME=V_WORK_TIME
            where t.CUSTOMER_NUM = crd_main_list.CUSTOMER_NUM
              and t.CRD_MAIN_PRD = crd_main_list.CRD_MAIN_PRD
              and exists(select 1
                         from CHAIN_DEPT a
                         where a.ID = t.ORG_NUM
                           and a.CORP_ORG_CODE = crd_main_list.CORP_ORG_CODE);
        end for;

    --删除没有关联到批复的授信额度信息
    DELETE
    FROM TB_CRD_DETAIL T
    WHERE T.TRAN_SYSTEM in ('0007', '0007,0010')
      AND T.CUSTOMER_NUM = V_CUSTOMER_NUM
      AND t.CRD_PRODUCT_TYPE = '1'--授信额度
      AND NOT EXISTS(
            SELECT 1 FROM TB_CRD_APPROVE A WHERE A.CRD_DETAIL_NUM = T.CRD_DETAIL_NUM AND A.APPROVE_STATUS = '02'--生效批复
        );

    --更新额度明细表额度（TB_CRD_DETAIL）
    MERGE INTO TB_CRD_DETAIL TA
    USING (SELECT T.CRD_MAIN_NUM,
                  T.CRD_DETAIL_NUM,
                  T.CRD_DETAIL_PRD,
                  T.CUSTOMER_NUM,
                  SUM(T.APPROVE_AMT)      LIMIT_CREDIT,
                  SUM(T.APPROVE_USED)     LIMIT_USED,
                  SUM(T.APPROVE_AVI)      LIMIT_AVI,
                  SUM(T.APPROVE_EXP_AMT)  EXP_CREDIT,
                  SUM(T.APPROVE_EXP_USED) EXP_USED,
                  SUM(T.APPROVE_EXP_AVI)  EXP_AVI,
                  SUM(T.APPROVE_PRE_AMT)  LIMIT_PRE,
                  SUM(T.APPROVE_PRE_EXP)  EXP_PRE,
                  A.CORP_ORG_CODE
           FROM TB_CRD_APPROVE T,
                CHAIN_DEPT A,
                TB_PAR_PRODUCT b
           WHERE T.ORG_NUM = A.ID
             and t.PRODUCT_NUM = b.PRODUCT_NUM
             and b.PRODUCT_TARGER = '1'--用信系统为信贷类
             AND T.APPROVE_STATUS = '02'--生效
             AND T.CUSTOMER_NUM = V_CUSTOMER_NUM
           GROUP BY T.CUSTOMER_NUM, A.CORP_ORG_CODE, T.CRD_DETAIL_PRD, T.CRD_DETAIL_NUM, T.CRD_MAIN_NUM) S
    ON (
        S.CRD_DETAIL_NUM = ta.CRD_DETAIL_NUM
        )
    WHEN NOT MATCHED
        THEN
        INSERT (CRD_MAIN_NUM, CRD_DETAIL_NUM, CRD_DETAIL_PRD, CUSTOMER_NUM, CURRENCY_CD,
                EXCHANGE_RATE, LIMIT_CREDIT, LIMIT_USED, LIMIT_AVI, EXP_CREDIT, EXP_USED,
                EXP_AVI, LIMIT_PRE, EXP_PRE, CREATE_TIME, UPDATE_TIME,
                CRD_GRANT_ORG_NUM, ORG_NUM, TRAN_SYSTEM, CRD_PRODUCT_TYPE)
        VALUES (S.CRD_MAIN_NUM, S.CRD_DETAIL_NUM, S.CRD_DETAIL_PRD, S.CUSTOMER_NUM, '156', 1,
                S.LIMIT_CREDIT, S.LIMIT_USED, S.LIMIT_AVI, S.EXP_CREDIT, S.EXP_AVI, S.EXP_USED,
                S.LIMIT_PRE, S.EXP_PRE, V_WORK_TIME, V_WORK_TIME, S.CORP_ORG_CODE, S.CORP_ORG_CODE, '0007', '1')
    WHEN MATCHED
        THEN
        UPDATE
        SET TA.CRD_MAIN_NUM=S.CRD_MAIN_NUM,--二级额度编号
            TA.CRD_DETAIL_PRD=S.CRD_DETAIL_PRD,--三级额度品种
            TA.LIMIT_CREDIT = S.LIMIT_CREDIT, --授信额度
            TA.LIMIT_USED = S.LIMIT_USED, --授信额度已用
            TA.LIMIT_AVI = S.LIMIT_AVI, --授信额度可用
            TA.EXP_CREDIT = S.EXP_CREDIT, --授信敞口
            TA.EXP_USED = S.EXP_USED, --敞口已用
            TA.EXP_AVI = S.EXP_AVI, --敞口可用
            TA.LIMIT_PRE = S.LIMIT_PRE, ---预占用额度
            TA.EXP_PRE = S.LIMIT_PRE, --预占用敞口
            TA.CRD_GRANT_ORG_NUM=S.CORP_ORG_CODE,--经办机构
            TA.ORG_NUM = S.CORP_ORG_CODE, --经办机构
            TA.UPDATE_TIME = V_WORK_TIME;
    --更新日期

    --用信系统为资金的，只需要插入额度明细，不能更新
    insert into TB_CRD_DETAIL(CRD_MAIN_NUM, CRD_DETAIL_NUM, CRD_DETAIL_PRD, CUSTOMER_NUM,
                              CURRENCY_CD, EXCHANGE_RATE, LIMIT_CREDIT, LIMIT_USED, LIMIT_AVI,
                              EXP_CREDIT, EXP_USED, EXP_AVI, LIMIT_PRE, EXP_PRE, CREATE_TIME,
                              UPDATE_TIME, CRD_GRANT_ORG_NUM, ORG_NUM, TRAN_SYSTEM, CRD_PRODUCT_TYPE)
    SELECT T.CRD_MAIN_NUM,
           T.CRD_DETAIL_NUM,
           T.CRD_DETAIL_PRD,
           T.CUSTOMER_NUM,
           '156',
           1,
           SUM(T.APPROVE_AMT)      LIMIT_CREDIT,
           SUM(T.APPROVE_USED)     LIMIT_USED,
           SUM(T.APPROVE_AVI)      LIMIT_AVI,
           SUM(T.APPROVE_EXP_AMT)  EXP_CREDIT,
           SUM(T.APPROVE_EXP_USED) EXP_USED,
           SUM(T.APPROVE_EXP_AVI)  EXP_AVI,
           SUM(T.APPROVE_PRE_AMT)  LIMIT_PRE,
           SUM(T.APPROVE_PRE_EXP)  EXP_PRE,
           V_WORK_TIME,
           V_WORK_TIME,
           A.CORP_ORG_CODE,
           A.CORP_ORG_CODE,
           '0007,0010',
           '1'
    FROM TB_CRD_APPROVE T,
         CHAIN_DEPT A,
         TB_PAR_PRODUCT b
    WHERE T.ORG_NUM = A.ID
      and t.PRODUCT_NUM = b.PRODUCT_NUM
      and b.PRODUCT_TARGER = '2'--用信系统为资金类
      AND T.APPROVE_STATUS = '02'--生效
      AND T.CUSTOMER_NUM = V_CUSTOMER_NUM
      and not exists(select 1 from TB_CRD_DETAIL c where c.CRD_DETAIL_NUM = t.CRD_DETAIL_NUM)
    GROUP BY T.CUSTOMER_NUM, A.CORP_ORG_CODE, T.CRD_DETAIL_PRD, T.CRD_DETAIL_NUM, T.CRD_MAIN_NUM;

    --删除无效的额度主信息
    DELETE
    FROM TB_CRD_MAIN T
    WHERE T.TRAN_SYSTEM in('0007','0007,0010')--信贷类授信的品种
      AND T.CUSTOMER_NUM = V_CUSTOMER_NUM
      AND NOT EXISTS(
            SELECT 1 FROM TB_CRD_APPROVE A WHERE a.CRD_MAIN_NUM = T.CRD_MAIN_NUM AND A.APPROVE_STATUS = '02'--生效批复
        );

    --用信系统为资金的，只需要插入额度主，不能更新
    insert into TB_CRD_MAIN(CRD_MAIN_NUM, CRD_MAIN_PRD, CUSTOMER_NUM, CURRENCY_CD, EXCHANGE_RATE, LIMIT_CREDIT,
                            LIMIT_USED, LIMIT_AVI, EXP_CREDIT, EXP_USED, EXP_AVI, LIMIT_PRE, EXP_PRE,
                            CREATE_TIME, UPDATE_TIME, CRD_GRANT_ORG_NUM, ORG_NUM, TRAN_SYSTEM)
    SELECT T.CRD_MAIN_NUM,
           T.CRD_MAIN_PRD,
           T.CUSTOMER_NUM,
           '156',
           1,
           SUM(T.APPROVE_AMT)      LIMIT_CREDIT,
           SUM(T.APPROVE_USED)     LIMIT_USED,
           SUM(T.APPROVE_AVI)      LIMIT_AVI,
           SUM(T.APPROVE_EXP_AMT)  EXP_CREDIT,
           SUM(T.APPROVE_EXP_USED) EXP_USED,
           SUM(T.APPROVE_EXP_AVI)  EXP_AVI,
           SUM(T.APPROVE_PRE_AMT)  LIMIT_PRE,
           SUM(T.APPROVE_PRE_EXP)  EXP_PRE,
           V_WORK_TIME,
           V_WORK_TIME,
           A.CORP_ORG_CODE,
           A.CORP_ORG_CODE,
           '0007,0010'
    FROM TB_CRD_APPROVE T,
         CHAIN_DEPT A,
         TB_PAR_PRODUCT b
    WHERE T.ORG_NUM = A.ID
      and t.PRODUCT_NUM = b.PRODUCT_NUM
      and b.PRODUCT_TARGER = '2'--用信系统为资金类
      AND T.APPROVE_STATUS = '02'--生效
      AND T.CUSTOMER_NUM = V_CUSTOMER_NUM
      and not exists(select 1 from TB_CRD_MAIN c where c.CRD_MAIN_NUM = t.CRD_MAIN_NUM)
    GROUP BY T.CUSTOMER_NUM, A.CORP_ORG_CODE, T.CRD_MAIN_PRD, T.CRD_MAIN_NUM;

END;

CREATE PROCEDURE CLM.PRC_CRD_FREEZE_REVERSE (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_CRD_FREEZE_REVERSE
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE V_CRD_GRANT_ORG_NUM   VARCHAR(10); --授信机构
  DECLARE V_CUSTOMER_NUM        VARCHAR(10); --ECIF客户号
  DECLARE V_CRD_MAIN_PRD        VARCHAR(10); --二级额度产品
  DECLARE V_CRD_MAIN_NUM        VARCHAR(40); --二级额度编号
  DECLARE V_CRD_DETAIL_PRD      VARCHAR(10); --三级额度产品
  DECLARE V_CRD_DETAIL_NUM      VARCHAR(10); --三级额度编号
  DECLARE V_LENGTH_VALUE        INTEGER; --字符长度
  DECLARE V_CRD_FLOW_VALUE      INTEGER; --额度流水数量
  DECLARE V_ORG_NUM_PRO         VARCHAR(10); ---省联社机构号1000
  DECLARE V_ORG_NUM_DIV         VARCHAR(10); ---分行机构号
  DECLARE V_ADJUST_COUNT        INTEGER; ---需要冲账的数量
  DECLARE V_TRAN_DATE           VARCHAR(10); --交易日期
  DECLARE V_TRAN_SEQ_SN         VARCHAR(20); --原流水号
  DECLARE N_TRAN_SEQ_SN         VARCHAR(20); --新流水号
  DECLARE V_COM_QUOTA           VARCHAR(2);--综合额度产品编号
  DECLARE V_TRAN_SYSTEM         VARCHAR(10);---定义来源系统号
  --DECLARE V_CLM_SYSTEM         VARCHAR(10);---统一授信系统的系统编号
  DECLARE V_USER_NUM         VARCHAR(10);---统一授信系统的操作员
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_CRD_FREEZE_REVERSE'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
    BEGIN
        SET SQL_CODE = SQLCODE;
        SET REMARK   = SQLSTATE;
        SET STATUS   = '0';
        CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
        SET RES_NUM  = 0;
        --ROLLBACK;
        RETURN;
    END;

  SET V_ORG_NUM_PRO = '1000';
  SET V_COM_QUOTA = '01';
  SET V_TRAN_SYSTEM = '0100';--根据需要改变
  --SET V_CLM_SYSTEM = '0087';--统一授信系统的系统编号
  SET V_USER_NUM = '0000001';--统一授信系统的操作员

  /*
  对于授信类的交易，如果对账的过程中发现额度系统中存在该笔交易、但是资金系统
  不存在该笔交易、则寻找最近的一笔流水，将额度系统的授信额度按最后一次流水进
  行授信。
  主要包括维护综合额度授信(01)、维护额度切分(02)、额度圈存维护(04)、
  额度冻结(05)、客户准入信息维护(06)
  */
  ------------------------------------------------------------------------------
  ----维护额度冻结解冻----BEGIN-----------------------------------------------------
  /*
  根据传进的日期，查询客户维护事件主表TB_FUND_ADMIT_MAIN中需要做冲账的数据数量，
  如果数量大于1，由于冻结、解冻交易只是针对省联社、二级产品(综合授信)，所以根据
  该日期在授信明细表TB_FUND_ADMIT_DETAIL中查询相应的ECIF客户号，以查询的数据为条件
  在额度主表中查询出额度编号,根据额度编号，按流水号排序查询出授信流水表TB_CRD_GRANTING_SERIAL
  中的第二条数据，将第二条数据复制重新生成一条流水信息，生成的流水号在当天最大流水号的基础上+1
  */
  SELECT COUNT(*) INTO V_ADJUST_COUNT FROM TB_FUND_ADMIT_MAIN T1
        WHERE
            T1.ADJUST_FLAG = '1' AND
            T1.ADJUST_DIRECTION = '0' AND
            T1.TRAN_DATE = EXEC_DATE AND T1.TRAN_TYPE_CD = '05';

   IF V_ADJUST_COUNT >0 --综合额度01  冻结只是在省联社一级进行冻结。省联社1000
    THEN
    FOR ADMIT_MAIN_DATAS AS (SELECT  T1.TRAN_SEQ_SN, T1.TRAN_DATE FROM TB_FUND_ADMIT_MAIN T1 WHERE   T1.ADJUST_FLAG = '1' AND T1.ADJUST_DIRECTION = '0' AND T1.TRAN_DATE = EXEC_DATE AND T1.TRAN_TYPE_CD = '05')
    DO
      SET V_TRAN_DATE = ADMIT_MAIN_DATAS.TRAN_DATE;
      SET V_TRAN_SEQ_SN = ADMIT_MAIN_DATAS.TRAN_SEQ_SN;
      SET N_TRAN_SEQ_SN = CLM.FNC_GET_TRAN_SEQ();
      FOR CRD_ADMIT_DATAS AS (SELECT T2.CUSTOMER_NUM FROM TB_FUND_ADMIT_DETAIL T2 WHERE T2.TRAN_SEQ_SN = V_TRAN_SEQ_SN AND T2.TRAN_DATE = V_TRAN_DATE)
      DO
        SET V_CRD_GRANT_ORG_NUM = '1000';
        SET V_CUSTOMER_NUM      = CRD_ADMIT_DATAS.CUSTOMER_NUM;
        SET V_CRD_MAIN_PRD      = '01';
        /*
         根据客户号、二级额度产品号、授信机构在额度主表中获取额度编号。
        */
        SELECT T1.CRD_MAIN_NUM INTO V_CRD_MAIN_NUM FROM
            TB_CRD_MAIN T1
        WHERE
            T1.CRD_GRANT_ORG_NUM = V_CRD_GRANT_ORG_NUM AND
            T1.CUSTOMER_NUM = V_CUSTOMER_NUM AND
            T1.CRD_MAIN_PRD = V_CRD_MAIN_PRD;
        /*
         在额度授信流水表中查询
        */
         SET V_CRD_FLOW_VALUE = (SELECT COUNT(*) FROM TB_CRD_GRANTING_SERIAL T1 WHERE T1.TRAN_TYPE_CD = '05' AND T1.CRD_DETAIL_NUM = V_CRD_MAIN_NUM);
         IF V_CRD_FLOW_VALUE > 1
         THEN
          INSERT INTO
          TB_CRD_GRANTING_SERIAL(
           GRANTING_SERIAL_ID --授信流水号
          ,TRAN_SEQ_SN --交易流水号
          ,TRAN_DATE --交易日期
          ,BUSI_DEAL_NUM --业务编号
          ,TRAN_TYPE_CD --交易类型
          ,CRD_GRANT_ORG_NUM --授信机构
          ,CUSTOMER_NUM --ECIF客户号
          ,CRD_DETAIL_PRD --额度产品
          ,CRD_DETAIL_NUM --额度编号
          ,CRD_CURRENCY_CD --币种
          ,CRD_DETAIL_AMT --授信额度
          ,CRD_EARK_AMT --圈存额度
          ,CRD_BEGIN_DATE --开始日期
          ,CRD_END_DATE --截至日期
          ,CRD_STATUS --额度状态
          ,CRD_ADMIT_FLAG --准入标准
          ,TRAN_SYSTEM --交易系统
          ,USER_NUM --交易柜员
          ,CREATE_TIME --创建时间
          ,UPDATE_TIME --更新时间
                        )
          SELECT
          FNC_GET_CLM_UUID(),
          N_TRAN_SEQ_SN,
          T1.TRAN_DATE,
          T1.BUSI_DEAL_NUM,
          T1.TRAN_TYPE_CD,
          T1.CRD_GRANT_ORG_NUM,
          T1.CUSTOMER_NUM,
          T1.CRD_DETAIL_PRD,
          T1.CRD_DETAIL_NUM,
          T1.CRD_CURRENCY_CD,
          T1.CRD_DETAIL_AMT,
          T1.CRD_EARK_AMT,
          T1.CRD_BEGIN_DATE,
          T1.CRD_END_DATE,
          T1.CRD_STATUS,
          T1.CRD_ADMIT_FLAG,
          T1.TRAN_SYSTEM,--
          V_USER_NUM,
          T1.CREATE_TIME,
          CURRENT_TIMESTAMP
      FROM
          (SELECT
              T2.GRANTING_SERIAL_ID --授信流水号
             ,T2.TRAN_SEQ_SN --交易流水号
             ,T2.TRAN_DATE --交易日期
             ,T2.BUSI_DEAL_NUM --业务编号
             ,T2.TRAN_TYPE_CD --交易类型
             ,T2.CRD_GRANT_ORG_NUM --授信机构
             ,T2.CUSTOMER_NUM --ECIF客户号
             ,T2.CRD_DETAIL_PRD --额度产品
             ,T2.CRD_DETAIL_NUM --额度编号
             ,T2.CRD_CURRENCY_CD --币种
             ,T2.CRD_DETAIL_AMT --授信额度
             ,T2.CRD_EARK_AMT --圈存额度
             ,T2.CRD_BEGIN_DATE --开始日期
             ,T2.CRD_END_DATE --截至日期
             ,T2.CRD_STATUS --额度状态
             ,T2.CRD_ADMIT_FLAG --准入标准
             ,T2.TRAN_SYSTEM --交易系统
             ,T2.USER_NUM --交易柜员
             ,T2.CREATE_TIME --创建时间
             ,T2.UPDATE_TIME --更新时间
          FROM
              TB_CRD_GRANTING_SERIAL T2
          WHERE
              T2.TRAN_TYPE_CD = '05'
              AND T2.CRD_DETAIL_NUM = V_CRD_MAIN_NUM
          ORDER BY
            BIGINT((SUBSTR(T2.TRAN_SEQ_SN,3,LENGTH(T2.TRAN_SEQ_SN) - 2))) DESC
          FETCH FIRST 2 ROWS ONLY) T1
        ORDER BY
          BIGINT((SUBSTR(T1.TRAN_SEQ_SN,3,LENGTH(T1.TRAN_SEQ_SN) - 2))) ASC
        FETCH FIRST 1 ROWS ONLY;
        /*
          更具最新的流水表信息修改额度主表的额度状态、冻结日期，维护时间等字段。
        */
          UPDATE TB_CRD_MAIN T1 SET
                  (
                    T1.CREDIT_STATUS,
                    T1.FROZEN_DATE,
                    T1.OVER_DATE,
                    T1.TRAN_SYSTEM,
                    T1.USER_NUM,
                    T1.UPDATE_TIME
                    ) = (
                               SELECT
                                T5.CRD_STATUS,
                                T5.CRD_BEGIN_DATE,
                                T5.CRD_END_DATE,
                                T5.TRAN_SYSTEM,
                                V_USER_NUM,
                                CURRENT_TIMESTAMP
                               FROM
                                TB_CRD_GRANTING_SERIAL T5
                               WHERE
                                T5.TRAN_SEQ_SN = N_TRAN_SEQ_SN) WHERE
         T1.CRD_MAIN_NUM = V_CRD_MAIN_NUM;
        ELSE
            INSERT INTO
              TB_CRD_GRANTING_SERIAL
              (
                GRANTING_SERIAL_ID --授信流水号
                ,TRAN_SEQ_SN --交易流水号
                ,TRAN_DATE --交易日期
                ,BUSI_DEAL_NUM --业务编号
                ,TRAN_TYPE_CD --交易类型
                ,CRD_GRANT_ORG_NUM --授信机构
                ,CUSTOMER_NUM --ECIF客户号
                ,CRD_DETAIL_PRD --额度产品
                ,CRD_DETAIL_NUM --额度编号
                ,CRD_CURRENCY_CD --币种
                ,CRD_DETAIL_AMT --授信额度
                ,CRD_EARK_AMT --圈存额度
                ,CRD_BEGIN_DATE --开始日期
                ,CRD_END_DATE --截至日期
                ,CRD_STATUS --额度状态
                ,CRD_ADMIT_FLAG --准入标准
                ,TRAN_SYSTEM --交易系统
                ,USER_NUM --交易柜员
                ,CREATE_TIME --创建时间
                ,UPDATE_TIME --更新时间
                      )
          SELECT
           FNC_GET_CLM_UUID(),
           N_TRAN_SEQ_SN,
           T1.TRAN_DATE,
           T1.BUSI_DEAL_NUM,
           T1.TRAN_TYPE_CD,
           T1.CRD_GRANT_ORG_NUM,
           T1.CUSTOMER_NUM,
           T1.CRD_DETAIL_PRD,
           T1.CRD_DETAIL_NUM,
           T1.CRD_CURRENCY_CD,
           T1.CRD_DETAIL_AMT,
           T1.CRD_EARK_AMT,
           NULL,
           NULL,
           NULL,
           T1.CRD_ADMIT_FLAG,
           T1.TRAN_SYSTEM,
           V_USER_NUM,
           T1.CREATE_TIME,
           CURRENT_TIMESTAMP
          FROM
            TB_CRD_GRANTING_SERIAL T1
          WHERE
            T1.CRD_DETAIL_NUM = V_CRD_MAIN_NUM
            AND T1.TRAN_TYPE_CD = '05';
        /*
          更具最新的流水表信息修改额度主表的额度状态、冻结日期，维护时间等字段。
        */
        UPDATE TB_CRD_MAIN T1 SET
                  (
                    T1.CREDIT_STATUS,
                    T1.FROZEN_DATE,
                    T1.OVER_DATE,
                    T1.TRAN_SYSTEM,
                    T1.USER_NUM,
                    T1.UPDATE_TIME
                    ) = (SELECT
                                T5.CRD_STATUS,
                                T5.CRD_BEGIN_DATE,
                                T5.CRD_END_DATE,
                                T5.TRAN_SYSTEM,
                                V_USER_NUM,
                                CURRENT_TIMESTAMP
                               FROM
                                TB_CRD_GRANTING_SERIAL T5
                               WHERE
                                T5.TRAN_SEQ_SN = N_TRAN_SEQ_SN)
                          WHERE
                            T1.CRD_MAIN_NUM = V_CRD_MAIN_NUM;


        END IF;
      END FOR;
      SET STATUS = '1';
      SET SQL_CODE = SQLCODE;
      SET REMARK = '需要冲账数量为：'||V_ADJUST_COUNT||',冲账完成';
      CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,NULL,SQL_CODE,REMARK);
      SET RES_NUM = 1;
    END FOR;
  ELSE
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '需要冲账数量为：'||V_ADJUST_COUNT||',冲账完成';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,NULL,SQL_CODE,REMARK);
    SET RES_NUM = 1;
  END IF;
END;

comment on procedure PRC_CRD_FREEZE_REVERSE(VARCHAR(10), INTEGER) is '冻结解冻-冲账';

CREATE PROCEDURE CLM.PRC_CRD_EXPIRE_DUE (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_CRD_EXPIRE_DUE
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN
  DECLARE V_CRD_MAIN_PRD VARCHAR(10);--二级产品号
  DECLARE V_CRD_MAIN_NUM VARCHAR(40);--主额度编号
  DECLARE V_M_CRD_GRANT_ORG_NUM VARCHAR(6);--授信机构
  DECLARE V_CUSTOMER_NUM VARCHAR(10);--客户号
  DECLARE V_M_DUE_NUM INTEGER;--授信机构为分行主额度失效数量
  DECLARE V_FAILURE_STATE VARCHAR(2);--失效状态 01:生效、02：部分冻结、03：全部冻结、04、已完结
  DECLARE V_P_CRD_GRANT_ORG_NUM VARCHAR(6);--授信机构
  DECLARE EFFECT_COUNT        INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE            INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE         VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS              VARCHAR(1); --执行情况
  DECLARE PROC_NAME           VARCHAR(50) DEFAULT 'PRC_CRD_EXPIRE_DUE'; --存储过程
  DECLARE REMARK              VARCHAR(200);
  DECLARE SQLSTATE            CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE             INTEGER;
    /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR SQLWARNING, SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK   = SQLSTATE;
    SET STATUS   = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE, PROC_NAME, STATUS, EFFECT_COUNT, SQL_CODE, REMARK);
    SET RES_NUM  = 0;
    --ROLLBACK;
    RETURN;
  END;
  SET V_P_CRD_GRANT_ORG_NUM = '01122';--省联社机构号
  SET V_FAILURE_STATE = '04';--额度失效状态
  /*
                  额度到期处理
    额度的到期截至日大于或等于批量日期将额度状态置为生效的置为失效状态(已完结)。
    1、在额度主表中，当授信机构为省联社时，则根据对应的客户号将该客户名下的所有联社下属于该二级产品下的主额度表的额度状态置为失效
    2、在额度主表中，当授信机构为法人机构时，根据对应的客户号、授信机构，将所有属于该机构下的该二级产品下的额度置失效状态
    *****由于控制规则只是涉及额度主表的状态，因此只需要修改额度主表的额度状态，明细额度表中的额度状态不涉及。
  */
  SELECT COUNT(*) INTO V_M_DUE_NUM FROM TB_CRD_MAIN T1 WHERE T1.END_DATE = EXEC_DATE;
  IF V_M_DUE_NUM >0
  THEN
    FOR CRD_MAIN_DATAS AS (SELECT T1.CRD_MAIN_PRD, T1.CRD_MAIN_NUM, T1.CRD_GRANT_ORG_NUM, T1.CUSTOMER_NUM   FROM TB_CRD_MAIN T1 WHERE T1.END_DATE = EXEC_DATE)
    DO
      SET V_CRD_MAIN_PRD = CRD_MAIN_DATAS.CRD_MAIN_PRD;
      SET V_CRD_MAIN_NUM = CRD_MAIN_DATAS.CRD_MAIN_NUM;
      SET V_M_CRD_GRANT_ORG_NUM = CRD_MAIN_DATAS.CRD_GRANT_ORG_NUM;
      SET V_CUSTOMER_NUM = CRD_MAIN_DATAS.CUSTOMER_NUM;
      /*
        授信机构为省联社时
      */
      IF V_M_CRD_GRANT_ORG_NUM = V_P_CRD_GRANT_ORG_NUM
      THEN
        UPDATE TB_CRD_MAIN T2 SET T2.CREDIT_STATUS = V_FAILURE_STATE WHERE T2.CUSTOMER_NUM = V_CRD_MAIN_NUM AND T2.CRD_MAIN_PRD = V_CRD_MAIN_PRD;
      ELSE
        UPDATE TB_CRD_MAIN T2 SET T2.CREDIT_STATUS = V_FAILURE_STATE WHERE T2.CRD_MAIN_NUM = V_CRD_MAIN_NUM;
      END IF;
    END FOR;
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '需要处理数量为：'||V_M_DUE_NUM||',处理完成';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,NULL,SQL_CODE,REMARK);
    SET RES_NUM = 1;
  ELSE
    SET STATUS = '1';
    SET SQL_CODE = SQLCODE;
    SET REMARK = '需要处理数量为0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,NULL,SQL_CODE,REMARK);
    SET RES_NUM = 1;
  END IF;

END;

comment on procedure PRC_CRD_EXPIRE_DUE(VARCHAR(10), INTEGER) is '同业额度到期处理';

CREATE PROCEDURE CLM.PRC_CRD_ADMIT_REVERSE (
    IN EXEC_DATE	VARCHAR(10),
    OUT RES_NUM	INTEGER )
  SPECIFIC PRC_CRD_ADMIT_REVERSE
  LANGUAGE SQL
  NOT DETERMINISTIC
  EXTERNAL ACTION
  MODIFIES SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
  OLD SAVEPOINT LEVEL
BEGIN

  DECLARE V_CRD_GRANT_ORG_NUM   VARCHAR(10); --授信机构
  DECLARE V_CUSTOMER_NUM        VARCHAR(10); --ECIF客户号
  DECLARE V_CRD_MAIN_PRD        VARCHAR(10); --二级额度产品
  DECLARE V_CRD_MAIN_NUM        VARCHAR(40); --二级额度编号
  DECLARE V_CRD_DETAIL_PRD      VARCHAR(10); --三级额度产品
  DECLARE V_CRD_DETAIL_NUM      VARCHAR(10); --三级额度编号
  DECLARE V_LENGTH_VALUE        INTEGER; --字符长度
  DECLARE V_CRD_FLOW_VALUE      INTEGER; --额度流水数量
  DECLARE V_ORG_NUM_PRO         VARCHAR(10); ---省联社机构号1000
  DECLARE V_ORG_NUM_DIV         VARCHAR(10); ---分行机构号
  DECLARE V_ADJUST_COUNT        INTEGER; ---需要冲账的数量
  DECLARE V_TRAN_DATE           VARCHAR(10); --交易日期
  DECLARE V_TRAN_SEQ_SN         VARCHAR(20); --原流水号
  DECLARE V_COM_QUOTA           VARCHAR(2);--综合额度产品编号
  DECLARE V_TRAN_SYSTEM         VARCHAR(10);---定义来源系统号
  DECLARE V_USER_NUM         VARCHAR(10);---统一授信系统的操作员
  DECLARE N_TRAN_SEQ_SN         VARCHAR(20); --新流水号
  DECLARE EFFECT_COUNT          INTEGER DEFAULT 0; ---影响条数
  DECLARE SQL_CODE              INTEGER DEFAULT 0; --错误码
  DECLARE SCHEMA_TYPE           VARCHAR(10) DEFAULT 'CLM'; --SCHEMA类型
  DECLARE STATUS                VARCHAR(1); --执行情况
  DECLARE PROC_NAME             VARCHAR(50) DEFAULT 'PRC_CRD_ADMIT_REVERSE'; --存储过程
  DECLARE REMARK         VARCHAR(200);
  DECLARE SQLSTATE CHAR(5) DEFAULT '00000';
  DECLARE SQLCODE INTEGER;
  /*
    异常捕捉
  */
  DECLARE EXIT HANDLER FOR  SQLWARNING,SQLEXCEPTION
  BEGIN
    SET SQL_CODE = SQLCODE;
    SET REMARK = SQLSTATE;
    SET STATUS = '0';
    CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,EFFECT_COUNT,SQL_CODE,REMARK);
    SET RES_NUM = 0;
    --ROLLBACK;
    RETURN;
  END ;
  SET V_ORG_NUM_PRO = '1000';
  SET V_COM_QUOTA = '01';
  SET V_TRAN_SYSTEM = '0100';--根据需要改变
  SET V_USER_NUM = '0000001';--统一授信系统的操作员
  /*
  对于授信类的交易，如果对账的过程中发现额度系统中存在该笔交易、但是资金系统
  不存在该笔交易、则寻找最近的一笔流水，将额度系统的授信额度按最后一次流水进
  行授信。
  主要包括维护综合额度授信(01)、维护额度切分(02)、额度圈存维护(04)、
  额度冻结(05)、客户准入信息维护(06)
  */
  ------------------------------------------------------------------------------
  ----------------------维护客户准入----BEGIN-----------------------------------
  /*
  根据传进的日期，查询客户维护事件主表TB_FUND_ADMIT_MAIN中需要做冲账的数据数量，
  如果数量大于1，由于客户准入交易只是针对省联社、三级产品(存放同业额度，同业拆借额度，
  同业借款额度，债券回购额度，同业存单额度，债券投资额度，票据贴现额度)，所以根据
  该日期在授信明细表TB_FUND_ADMIT_DETAIL中查询相应的ECIF客户号，以查询的数据为条件
  在额度主表中查询出额度编号,根据额度编号，按流水号排序查询出授信流水表TB_CRD_GRANTING_SERIAL
  中的第二条数据，将第二条数据复制重新生成一条流水信息，生成的流水号在当天最大流水号的基础上+1
  */
  SELECT COUNT(*) INTO V_ADJUST_COUNT FROM TB_FUND_ADMIT_MAIN T1
                  WHERE
                      T1.ADJUST_FLAG            = '1' AND
                      T1.ADJUST_DIRECTION = '0' AND
                      T1.TRAN_DATE = EXEC_DATE AND
                      T1.TRAN_TYPE_CD = '06';

  IF V_ADJUST_COUNT >0 --综合额度01  冻结只是在省联社一级进行冻结。省联社1000
    THEN
    FOR ADMIT_MAIN_DATAS AS(SELECT T2.TRAN_SEQ_SN           ,T2.TRAN_DATE FROM TB_FUND_ADMIT_MAIN T2)
    DO
      SET V_TRAN_SEQ_SN = ADMIT_MAIN_DATAS.TRAN_SEQ_SN;
      SET V_TRAN_DATE = ADMIT_MAIN_DATAS.TRAN_DATE;
      SET N_TRAN_SEQ_SN = CLM.FNC_GET_TRAN_SEQ();
      FOR ADMIT_DATAS            AS (SELECT T1.CRD_DETAIL_PRD,T1.CUSTOMER_NUM FROM TB_FUND_ADMIT_DETAIL T1 WHERE T1.TRAN_SEQ_SN = V_TRAN_SEQ_SN AND T1.TRAN_DATE = V_TRAN_DATE)
      DO
        SET V_CRD_GRANT_ORG_NUM = '1000';
        SET V_CUSTOMER_NUM      = ADMIT_DATAS.CUSTOMER_NUM;
        SET V_CRD_DETAIL_PRD      = ADMIT_DATAS.CRD_DETAIL_PRD;
        /*
          根据客户号、三级额度产品号、授信机构在额度主表中获取额度编号。
        */
        SELECT T2.CRD_DETAIL_NUM               INTO     V_CRD_DETAIL_NUM
              FROM TB_CRD_DETAIL T2
              WHERE T2.CRD_GRANT_ORG_NUM = V_CRD_GRANT_ORG_NUM
              AND T2.CRD_DETAIL_PRD = V_CRD_DETAIL_PRD
              AND T2.CUSTOMER_NUM = V_CUSTOMER_NUM;
        SET V_CRD_FLOW_VALUE                 = (SELECT COUNT(*) FROM TB_CRD_GRANTING_SERIAL T1 WHERE T1.TRAN_TYPE_CD = '06' AND T1.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM);
        /*
          在额度授信流水表中查询
        */
       IF V_CRD_FLOW_VALUE                 > 1
       THEN
          INSERT INTO TB_CRD_GRANTING_SERIAL
          (
           GRANTING_SERIAL_ID --授信流水号
          ,TRAN_SEQ_SN --交易流水号
          ,TRAN_DATE --交易日期
          ,BUSI_DEAL_NUM --业务编号
          ,TRAN_TYPE_CD --交易类型
          ,CRD_GRANT_ORG_NUM --授信机构
          ,CUSTOMER_NUM --ECIF客户号
          ,CRD_DETAIL_PRD --额度产品
          ,CRD_DETAIL_NUM --额度编号
          ,CRD_CURRENCY_CD --币种
          ,CRD_DETAIL_AMT --授信额度
          ,CRD_EARK_AMT --圈存额度
          ,CRD_BEGIN_DATE --开始日期
          ,CRD_END_DATE --截至日期
          ,CRD_STATUS --额度状态
          ,CRD_ADMIT_FLAG --准入标准
          ,TRAN_SYSTEM --交易系统
          ,USER_NUM --交易柜员
          ,CREATE_TIME --创建时间
          ,UPDATE_TIME --更新时间
          )SELECT
          FNC_GET_CLM_UUID(),
          N_TRAN_SEQ_SN,
          T1.TRAN_DATE,
          T1.BUSI_DEAL_NUM,
          T1.TRAN_TYPE_CD,
          T1.CRD_GRANT_ORG_NUM,
          T1.CUSTOMER_NUM,
          T1.CRD_DETAIL_PRD,
          T1.CRD_DETAIL_NUM,
          T1.CRD_CURRENCY_CD,
          T1.CRD_DETAIL_AMT,
          T1.CRD_EARK_AMT,
          T1.CRD_BEGIN_DATE,
          T1.CRD_END_DATE,
          T1.CRD_STATUS,
          T1.CRD_ADMIT_FLAG,
          T1.TRAN_SYSTEM,--
          V_USER_NUM,
          T1.CREATE_TIME,
          CURRENT_TIMESTAMP
          FROM (
          SELECT
              T2.GRANTING_SERIAL_ID --授信流水号
             ,T2.TRAN_SEQ_SN --交易流水号
             ,T2.TRAN_DATE --交易日期
             ,T2.BUSI_DEAL_NUM --业务编号
             ,T2.TRAN_TYPE_CD --交易类型
             ,T2.CRD_GRANT_ORG_NUM --授信机构
             ,T2.CUSTOMER_NUM --ECIF客户号
             ,T2.CRD_DETAIL_PRD --额度产品
             ,T2.CRD_DETAIL_NUM --额度编号
             ,T2.CRD_CURRENCY_CD --币种
             ,T2.CRD_DETAIL_AMT --授信额度
             ,T2.CRD_EARK_AMT --圈存额度
             ,T2.CRD_BEGIN_DATE --开始日期
             ,T2.CRD_END_DATE --截至日期
             ,T2.CRD_STATUS --额度状态
             ,T2.CRD_ADMIT_FLAG --准入标准
             ,T2.TRAN_SYSTEM --交易系统
             ,T2.USER_NUM --交易柜员
             ,T2.CREATE_TIME --创建时间
             ,T2.UPDATE_TIME --更新时间
          FROM
              TB_CRD_GRANTING_SERIAL T2
          WHERE
              T2.TRAN_TYPE_CD = '06'
              AND T2.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM
          ORDER BY
            BIGINT((SUBSTR(T2.TRAN_SEQ_SN,3,LENGTH(T2.TRAN_SEQ_SN) - 2))) DESC
          FETCH FIRST 2 ROWS ONLY) T1
        ORDER BY
          BIGINT((SUBSTR(T1.TRAN_SEQ_SN,3,LENGTH(T1.TRAN_SEQ_SN) - 2))) ASC
        FETCH FIRST 1 ROWS ONLY;
        /*
          根据最新的流水表信息修改额度明细表的准入状态，维护时间等字段。
        */
          UPDATE TB_CRD_DETAIL T1
              SET(
                    T1.CRD_ADMIT_FLAG              ,
                    T1.TRAN_SYSTEM,
                    T1.USER_NUM,
                    T1.UPDATE_TIME
                  ) = ( SELECT T5.CRD_ADMIT_FLAG,
                                 T5.TRAN_SYSTEM,
                                 V_USER_NUM,
                                 CURRENT_TIMESTAMP
                               FROM
                                 TB_CRD_GRANTING_SERIAL T5
                               WHERE
                                 T5.TRAN_SEQ_SN = N_TRAN_SEQ_SN
                                 AND T5.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM)
                                 WHERE
                                 T1.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM;
        ELSE
              INSERT       INTO
              TB_CRD_GRANTING_SERIAL
              (
                GRANTING_SERIAL_ID --授信流水号
                ,TRAN_SEQ_SN --交易流水号
                ,TRAN_DATE --交易日期
                ,BUSI_DEAL_NUM --业务编号
                ,TRAN_TYPE_CD --交易类型
                ,CRD_GRANT_ORG_NUM --授信机构
                ,CUSTOMER_NUM --ECIF客户号
                ,CRD_DETAIL_PRD --额度产品
                ,CRD_DETAIL_NUM --额度编号
                ,CRD_CURRENCY_CD --币种
                ,CRD_DETAIL_AMT --授信额度
                ,CRD_EARK_AMT --圈存额度
                ,CRD_BEGIN_DATE --开始日期
                ,CRD_END_DATE --截至日期
                ,CRD_STATUS --额度状态
                ,CRD_ADMIT_FLAG --准入标准
                ,TRAN_SYSTEM --交易系统
                ,USER_NUM --交易柜员
                ,CREATE_TIME --创建时间
                ,UPDATE_TIME --更新时间
                      ) SELECT
                FNC_GET_CLM_UUID(),
                N_TRAN_SEQ_SN,
                T1.TRAN_DATE,
                T1.BUSI_DEAL_NUM,
                T1.TRAN_TYPE_CD,
                T1.CRD_GRANT_ORG_NUM,
                T1.CUSTOMER_NUM,
                T1.CRD_DETAIL_PRD,
                T1.CRD_DETAIL_NUM,
                T1.CRD_CURRENCY_CD,
                T1.CRD_DETAIL_AMT,
                T1.CRD_EARK_AMT,
                T1.CRD_BEGIN_DATE,
                T1.CRD_END_DATE,
                T1.CRD_STATUS,
                '0',
                T1.TRAN_SYSTEM,
                V_USER_NUM,
                T1.CREATE_TIME,
                CURRENT_TIMESTAMP
              FROM
                TB_CRD_GRANTING_SERIAL T1
              WHERE
                T1.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM
                AND T1.TRAN_TYPE_CD = '06';
        /*
          更具最新的流水表信息修改额度明细表的准入状态、维护时间等字段。
        */
               UPDATE TB_CRD_DETAIL T1 SET
                  (
                    T1.CRD_ADMIT_FLAG              ,
                    T1.TRAN_SYSTEM,
                    T1.USER_NUM,
                    T1.UPDATE_TIME
                  )=
                  ( SELECT T5.CRD_ADMIT_FLAG,
                           T5.TRAN_SYSTEM,
                           V_USER_NUM,
                           CURRENT_TIMESTAMP
                            FROM
                              TB_CRD_GRANTING_SERIAL T5
                            WHERE
                              T5.TRAN_SEQ_SN = N_TRAN_SEQ_SN)
                            WHERE T1.CRD_DETAIL_NUM = V_CRD_DETAIL_NUM;
       END IF;
      END FOR;
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '需要冲账数量为：'||V_ADJUST_COUNT||',冲账完成';
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,NULL,SQL_CODE,REMARK);
  SET RES_NUM = 1;
    END FOR;
  ELSE
  SET STATUS = '1';
  SET SQL_CODE = SQLCODE;
  SET REMARK = '不需要冲账';
  CALL PRC_CRD_BATCH_LOG(SCHEMA_TYPE,PROC_NAME,STATUS,NULL,SQL_CODE,REMARK);
  SET RES_NUM = 1;
  END IF;
END;

comment on procedure PRC_CRD_ADMIT_REVERSE(VARCHAR(10), INTEGER) is '客户准入-冲账';

CREATE PROCEDURE PRC_CREDIT_STATIS_CSM(IN V_CUSTOMER_NUM VARCHAR(40))
BEGIN
    /**
        统计某一客户的实时额度信息
    */
    DECLARE v_approve_count DECIMAL(18, 2) DEFAULT 0; --批复数量
    DECLARE v_approve_exp_amount DECIMAL(18, 2) DEFAULT 0; --批复敞口金额
    DECLARE v_credit_exp_balance DECIMAL(18, 2) DEFAULT 0;--授信敞口余额
    DECLARE v_loan_exp_balance DECIMAL(18, 2) DEFAULT 0;--贷款敞口余额
    DECLARE limit_credit DECIMAL(18, 2) DEFAULT 0;--授信额度
    DECLARE limit_avi DECIMAL(18, 2) DEFAULT 0;--可用额度
    DECLARE limit_used DECIMAL(18, 2) DEFAULT 0;--已用额度
    DECLARE exp_used DECIMAL(18, 2) DEFAULT 0;--已用敞口
    DECLARE exp_avi DECIMAL(18, 2) DEFAULT 0;--可用敞口

    DECLARE V_WORK_TIME TIMESTAMP; --营业时间
    SET V_WORK_TIME = CLM.FNC_GET_BUSI_TIME();

    --删除无效的二级额度品种
    DELETE
    FROM TB_CRD_MAIN T
    WHERE T.TRAN_SYSTEM in ('0007', '1106', '0007,0010')--信贷类授信的品种
      AND T.CUSTOMER_NUM = V_CUSTOMER_NUM
      AND NOT EXISTS(
            SELECT 1 FROM TB_CRD_DETAIL A WHERE A.CRD_MAIN_NUM = T.CRD_MAIN_NUM
        );

    --更新额度主表
    MERGE INTO TB_CRD_MAIN TA
    USING (SELECT T.CRD_MAIN_NUM,
                  a.SUPER_CRD_NUM           crd_main_prd,
                  T.CUSTOMER_NUM,
                  t.ORG_NUM,
                  SUM(T.LIMIT_CREDIT)       LIMIT_CREDIT,
                  SUM(T.LIMIT_USED)         LIMIT_USED,
                  SUM(T.LIMIT_AVI)          LIMIT_AVI,
                  SUM(T.EXP_CREDIT)         EXP_CREDIT,
                  SUM(T.EXP_USED)           EXP_USED,
                  SUM(T.EXP_AVI)            EXP_AVI,
                  SUM(T.LIMIT_PRE)          LIMIT_PRE,
                  SUM(T.EXP_PRE)            EXP_PRE,
                  SUM(T.LIMIT_FROZEN)       LIMIT_FROZEN,
                  sum(t.exp_frozen)         exp_frozen,
                  sum(t.limit_earmark)      limit_earmark,
                  sum(t.limit_earmark_used) limit_earmark_used
           FROM TB_CRD_DETAIL T,
                TB_PAR_CRD a
           WHERE t.CRD_DETAIL_PRD = a.CRD_PRODUCT_NUM
             and t.TRAN_SYSTEM in ('0007', '1106')--信贷类产品
             and T.CUSTOMER_NUM = V_CUSTOMER_NUM
           GROUP BY T.CUSTOMER_NUM,
                    t.ORG_NUM,
                    T.CRD_MAIN_NUM,
                    a.SUPER_CRD_NUM) S
    ON (
        S.CRD_MAIN_NUM = TA.CRD_MAIN_NUM
        )
    WHEN NOT MATCHED
        THEN
        INSERT (CRD_MAIN_NUM,--二级额度编号
                CRD_MAIN_PRD, --三级额度品种
                CUSTOMER_NUM, --ECIF客户编号
                CURRENCY_CD, --币种
                EXCHANGE_RATE, --汇率
                LIMIT_CREDIT, --授信额度
                LIMIT_USED, --授信额度已用
                LIMIT_AVI, --授信额度可用
                EXP_CREDIT,--敞口额度
                EXP_USED,--敞口已用
                EXP_AVI,--敞口可用
                LIMIT_PRE,--预占用额度
                EXP_PRE,--预占用敞口
                LIMIT_FROZEN,--冻结额度
                exp_frozen,--冻结敞口
                limit_earmark,--圈存额度
                LIMIT_EARMARK_USED,--圈存已用额度
                CREATE_TIME, --创建日期
                UPDATE_TIME, --更新日期
                CRD_GRANT_ORG_NUM,--授信机构
                ORG_NUM, --经办机构
                TRAN_SYSTEM)
        VALUES (S.CRD_MAIN_NUM,
                S.CRD_MAIN_PRD,
                S.CUSTOMER_NUM,
                '156',
                '1',
                S.LIMIT_CREDIT,
                S.LIMIT_USED,
                S.LIMIT_AVI,
                s.EXP_CREDIT,--敞口额度
                s.EXP_USED,--敞口已用
                s.EXP_AVI,--敞口可用
                s.LIMIT_PRE,--预占用额度
                s.EXP_PRE,--预占用敞口
                s.LIMIT_FROZEN,--冻结额度
                s.exp_frozen,--冻结敞口
                s.limit_earmark,--圈存额度
                s.limit_earmark_used,--圈存已用额度
                V_WORK_TIME, --创建日期
                V_WORK_TIME, --更新日期
                s.ORG_NUM,--授信机构
                s.ORG_NUM, --经办机构
                '0007')
    when matched then
        UPDATE
        SET TA.CRD_MAIN_PRD=S.CRD_MAIN_PRD,--二级额度品种
            TA.LIMIT_CREDIT = S.LIMIT_CREDIT, --授信额度
            TA.LIMIT_USED = S.LIMIT_USED, --授信额度已用
            TA.LIMIT_AVI = S.LIMIT_AVI, --授信额度可用
            TA.EXP_CREDIT = S.EXP_CREDIT, --授信敞口
            TA.EXP_USED = S.EXP_USED, --敞口已用
            TA.EXP_AVI = S.EXP_AVI, --敞口可用
            TA.LIMIT_PRE = S.LIMIT_PRE, ---预占用额度
            TA.EXP_PRE = S.LIMIT_PRE, --预占用敞口
            ta.LIMIT_FROZEN=s.LIMIT_FROZEN,--冻结额度
            ta.exp_frozen=s.exp_frozen,--冻结敞口
            ta.limit_earmark=s.limit_earmark,--圈存额度
            ta.limit_earmark_used=s.limit_earmark_used,--圈存已用额度
            TA.CRD_GRANT_ORG_NUM=S.ORG_NUM,--经办机构
            TA.ORG_NUM = S.ORG_NUM, --经办机构
            TA.UPDATE_TIME = V_WORK_TIME;
    --更新日期;


    --统计额度汇总表（资金业务从额度主表汇总）
    delete from TB_CRD_SUM t where t.CUSTOMER_NUM = V_CUSTOMER_NUM;

    merge into TB_CRD_SUM ta
    using (
        select t.CUSTOMER_NUM,
               t.ORG_NUM,
               sum(t.limit_credit)       limit_credit,--授信额度
               sum(t.limit_used)         limit_used,--已用额度
               sum(t.limit_avi)          limit_avi,--可用额度
               sum(t.exp_credit)         exp_credit,--授信敞口
               sum(t.exp_used)           exp_used,--已用敞口
               sum(t.exp_avi)            exp_avi,--可用敞口
               sum(t.limit_pre)          limit_pre,--预占用额度
               sum(t.exp_pre)            exp_pre,--预占用敞口
               sum(t.limit_frozen)       limit_frozen,--冻结额度
               sum(t.EXP_FROZEN)         exp_frozen,--冻结敞口
               sum(t.limit_earmark)      limit_earmark,--圈存额度
               sum(t.limit_earmark_used) limit_earmark_used--圈存已用
        from TB_CRD_MAIN t
        where t.TRAN_SYSTEM = '0010'--资金系统
          and t.CUSTOMER_NUM = V_CUSTOMER_NUM
        group by t.CUSTOMER_NUM, t.ORG_NUM
    ) s
    on (s.ORG_NUM = ta.ORG_NUM
        and s.CUSTOMER_NUM = ta.CUSTOMER_NUM
        and ta.CRD_PRODUCT_TYPE = '1'
        )
    when not matched then
        insert
        (STATIS_ID, CUSTOMER_NUM, CRD_PRODUCT_TYPE, CURRENCY_CD,
         LIMIT_CREDIT, LIMIT_USED, LIMIT_AVI,
         EXP_CREDIT, EXP_USED, EXP_AVI,
         LIMIT_PRE, EXP_PRE, LIMIT_FROZEN, exp_frozen, limit_earmark, limit_earmark_used,
         ORG_NUM, CREATE_TIME, UPDATE_TIME)
        values (CLM.FNC_GET_UUID('TJ'), s.CUSTOMER_NUM, '1', '156',
                s.LIMIT_CREDIT, s.LIMIT_USED, s.LIMIT_AVI, s.EXP_CREDIT, s.EXP_USED, s.EXP_AVI,
                s.LIMIT_PRE, s.EXP_PRE, s.LIMIT_FROZEN, s.exp_frozen, s.limit_earmark, s.limit_earmark_used,
                s.ORG_NUM, V_WORK_TIME, V_WORK_TIME)
    when matched then
        update
        set ta.CURRENCY_CD='156',
            ta.LIMIT_CREDIT=s.limit_credit,
            ta.LIMIT_USED=s.LIMIT_USED,
            ta.LIMIT_AVI=s.LIMIT_AVI,
            ta.EXP_CREDIT=s.exp_credit,
            ta.EXP_USED=s.EXP_USED,
            ta.EXP_AVI=s.exp_avi,
            ta.LIMIT_PRE=s.limit_pre,
            ta.EXP_PRE=s.exp_pre,
            ta.LIMIT_FROZEN=s.limit_frozen,
            ta.exp_frozen=s.exp_frozen,
            ta.limit_earmark=s.limit_earmark,
            ta.limit_earmark_used=s.limit_earmark_used,
            ta.ORG_NUM=s.ORG_NUM,
            ta.UPDATE_TIME=V_WORK_TIME;

    --统计额度汇总表（信贷业务从额度明细表汇总）
    merge into TB_CRD_SUM ta
    using (
        select t.CUSTOMER_NUM,
               t.ORG_NUM,
               t.CRD_PRODUCT_TYPE,
               sum(t.limit_credit)       limit_credit,--授信额度
               sum(t.limit_used)         limit_used,--已用额度
               sum(t.limit_avi)          limit_avi,--可用额度
               sum(t.exp_credit)         exp_credit,--授信敞口
               sum(t.exp_used)           exp_used,--已用敞口
               sum(t.exp_avi)            exp_avi,--可用敞口
               sum(t.limit_pre)          limit_pre,--预占用额度
               sum(t.exp_pre)            exp_pre,--预占用敞口
               sum(t.limit_frozen)       limit_frozen,--冻结额度
               sum(t.exp_frozen)         exp_frozen,--冻结敞口
               sum(t.limit_earmark)      limit_earmark,--圈存额度
               sum(t.limit_earmark_used) limit_earmark_used--圈存已用
        from TB_CRD_DETAIL t
        where t.TRAN_SYSTEM in ('0010', '1106')--信贷业务
          and t.CUSTOMER_NUM = V_CUSTOMER_NUM
        group by t.CUSTOMER_NUM, t.ORG_NUM, t.CRD_PRODUCT_TYPE
    ) s
    on (s.ORG_NUM = ta.ORG_NUM
        and s.CUSTOMER_NUM = ta.CUSTOMER_NUM
        )
    when not matched then
        insert
        (STATIS_ID, CUSTOMER_NUM, CRD_PRODUCT_TYPE, CURRENCY_CD,
         LIMIT_CREDIT, LIMIT_USED, LIMIT_AVI,
         EXP_CREDIT, EXP_USED, EXP_AVI,
         LIMIT_PRE, EXP_PRE, LIMIT_FROZEN, exp_frozen, limit_earmark, limit_earmark_used,
         ORG_NUM, CREATE_TIME, UPDATE_TIME)
        values (CLM.FNC_GET_UUID('TJ'), s.CUSTOMER_NUM, s.CRD_PRODUCT_TYPE, '156',
                s.LIMIT_CREDIT, s.LIMIT_USED, s.LIMIT_AVI,
                s.EXP_CREDIT, s.EXP_USED, s.EXP_AVI,
                s.LIMIT_PRE, s.EXP_PRE, s.LIMIT_FROZEN, s.exp_frozen, s.limit_earmark, s.limit_earmark_used,
                s.ORG_NUM, V_WORK_TIME, V_WORK_TIME)
    when matched then
        update
        set ta.CURRENCY_CD='156',
            ta.LIMIT_CREDIT=s.limit_credit,
            ta.LIMIT_USED=s.LIMIT_USED,
            ta.LIMIT_AVI=s.LIMIT_AVI,
            ta.EXP_CREDIT=s.exp_credit,
            ta.EXP_USED=s.EXP_USED,
            ta.EXP_AVI=s.exp_avi,
            ta.LIMIT_PRE=s.limit_pre,
            ta.EXP_PRE=s.exp_pre,
            ta.LIMIT_FROZEN=s.limit_frozen,
            ta.exp_frozen=s.exp_frozen,
            ta.limit_earmark=s.limit_earmark,
            ta.limit_earmark_used=s.limit_earmark_used,
            ta.ORG_NUM=s.ORG_NUM,
            ta.UPDATE_TIME=V_WORK_TIME;

    --统计额度数据到额度统计表，根据额度品种、业务品种、机构、行业、客户类型、担保方式、企业规模分组
    --先删除已有的统计信息
    delete from tb_crd_statis t where t.CUSTOMER_NUM = V_CUSTOMER_NUM;

    --统计信贷的客户额度
    insert into TB_CRD_STATIS(STATIS_ID, CUSTOMER_NUM, CURRENCY_CD,
                              APPROVE_COUNT, APPROVE_EXP_AMOUNT, CREDIT_EXP_BALANCE, LOAN_EXP_BALANCE, LIMIT_CREDIT,
                              LIMIT_AVI, LIMIT_USED, EXP_USED, EXP_AVI,
                              CRD_DETAIL_PRD, PRODUCT_NUM, ORG_NUM, INDUSTRY, CUSTOMER_TYPE, GUARANTEE_TYPE,
                              UNIT_SCALE,
                              CREATE_TIME, UPDATE_TIME)
    select CLM.FNC_GET_UUID('TJ')    STATIS_ID,
           t.CUSTOMER_NUM,
           '156'                     CURRENCY_CD,
           count(1)                  APPROVE_COUNT,--批复数量
           sum(t.APPROVE_EXP_AMT)    APPROVE_EXP_AMOUNT,--批复敞口金额
           sum(s.CREDIT_EXP_BALANCE) CREDIT_EXP_BALANCE,--授信敞口余额
           sum(s2.LOAN_EXP_BALANCE)  LOAN_EXP_BALANCE,--贷款敞口余额
           sum(t.APPROVE_AMT)        LIMIT_CREDIT,--授信额度
           sum(t.APPROVE_AVI)        LIMIT_AVI,--可用额度
           sum(t.APPROVE_USED)       LIMIT_USED,--已用额度
           sum(t.APPROVE_EXP_USED)   EXP_USED,--已用敞口
           sum(t.APPROVE_EXP_AVI)    EXP_AVI,--可用敞口
           t.CRD_DETAIL_PRD,--额度品种
           t.PRODUCT_NUM,--业务品种
           a.CORP_ORG_CODE           ORG_NUM,--机构号
           substr(t.INDUSTRY, 1, 1),--行业
           b.CUSTOMER_TYPE,--客户类型
           t.MAIN_GUARANTEE_TYPE     GUARANTEE_TYPE,--担保方式
           c.UNIT_SCALE,--企业规模
           V_WORK_TIME               CREATE_TIME,
           V_WORK_TIME               UPDATE_TIME
    from CHAIN_DEPT a,
         TB_CSM_PARTY b
             left join TB_CSM_CORPORATION c on b.CUSTOMER_Num = c.CUSTOMER_NUM,
         TB_CRD_APPROVE t
             left join (
             select ts.APPROVE_ID, sum(ts.SUMMARY_BAL * (100 - ts.DEPOSIT_RATIO) / 100) CREDIT_EXP_BALANCE
             from TB_CRD_SUMMARY ts
             group by ts.APPROVE_ID
         ) s on s.APPROVE_ID = t.APPROVE_ID
             left join (
             select ts.APPROVE_ID, sum(ts.SUMMARY_BAL) LOAN_EXP_BALANCE
             from TB_CRD_SUMMARY ts,
                  TB_PAR_PRODUCT p
             where ts.PRODUCT_NUM = p.PRODUCT_NUM
               and p.INOUT_TABLE = '1'
             group by ts.APPROVE_ID
         ) s2 on s2.APPROVE_ID = t.APPROVE_ID
    where t.ORG_NUM = a.ID
      and t.CUSTOMER_NUM = b.CUSTOMER_NUM
      and t.CUSTOMER_NUM = V_CUSTOMER_NUM
    group by t.CUSTOMER_NUM, t.CRD_DETAIL_PRD, t.PRODUCT_NUM, a.CORP_ORG_CODE, substr(t.INDUSTRY, 1, 1),
             b.CUSTOMER_TYPE,
             t.MAIN_GUARANTEE_TYPE, c.UNIT_SCALE;

    --统计同业客户额、信用卡额度
    insert into TB_CRD_STATIS(STATIS_ID, CUSTOMER_NUM, CURRENCY_CD,
                              APPROVE_COUNT, APPROVE_EXP_AMOUNT, CREDIT_EXP_BALANCE, LOAN_EXP_BALANCE, LIMIT_CREDIT,
                              LIMIT_AVI, LIMIT_USED, EXP_USED, EXP_AVI,
                              CRD_DETAIL_PRD, PRODUCT_NUM, ORG_NUM, INDUSTRY, CUSTOMER_TYPE, GUARANTEE_TYPE,
                              UNIT_SCALE,
                              CREATE_TIME, UPDATE_TIME)
    select CLM.FNC_GET_UUID('TJ') STATIS_ID,
           t.CUSTOMER_NUM,
           '156'                  CURRENCY_CD,
           count(1)               APPROVE_COUNT,--批复数量
           sum(t.EXP_CREDIT)      APPROVE_EXP_AMOUNT,--批复敞口金额
           sum(t.EXP_USED)        CREDIT_EXP_BALANCE,--授信敞口余额
           sum(t.EXP_AVI)         LOAN_EXP_BALANCE,--贷款敞口余额
           sum(t.LIMIT_CREDIT)    LIMIT_CREDIT,--授信额度
           sum(t.LIMIT_AVI)       LIMIT_AVI,--可用额度
           sum(t.LIMIT_USED)      LIMIT_USED,--已用额度
           sum(t.EXP_USED)        EXP_USED,--已用敞口
           sum(t.EXP_AVI)         EXP_AVI,--可用敞口
           t.CRD_DETAIL_PRD,--额度品种
           ''                     product_num,--业务品种
           t.ORG_NUM,--机构号
           ''                     INDUSTRY,--行业
           b.CUSTOMER_TYPE,--客户类型
           ''                     GUARANTEE_TYPE,--担保方式
           '',--企业规模
           V_WORK_TIME            CREATE_TIME,
           V_WORK_TIME            UPDATE_TIME
    from TB_CRD_DETAIL t,
         TB_CSM_PARTY b
    where t.CUSTOMER_NUM = b.CUSTOMER_NUM
      and t.TRAN_SYSTEM in ('0010', '1106')--资金业务系统,信用卡系统
      and t.CUSTOMER_NUM = V_CUSTOMER_NUM
    group by t.CUSTOMER_NUM, t.CRD_DETAIL_PRD, t.ORG_NUM, b.CUSTOMER_TYPE;


    --额度统计表-客户（实时）
    delete from TB_CRD_STATIS_CSM t where t.CUSTOMER_NUM = V_CUSTOMER_NUM;

    insert into TB_CRD_STATIS_CSM(STATIS_ID, CUSTOMER_NUM,
                                  APPROVE_COUNT, APPROVE_EXP_AMOUNT, CREDIT_EXP_BALANCE, LOAN_EXP_BALANCE,
                                  LIMIT_CREDIT, LIMIT_AVI, LIMIT_USED, EXP_USED, EXP_AVI,
                                  ORG_NUM, CREATE_TIME, UPDATE_TIME)
    select CLM.FNC_GET_UUID('TJ')    STATIS_ID,
           t.CUSTOMER_NUM,
           sum(t.APPROVE_COUNT)      APPROVE_COUNT,--批复数量
           sum(t.APPROVE_EXP_AMOUNT) APPROVE_EXP_AMOUNT,--批复敞口金额
           sum(t.APPROVE_EXP_AMOUNT) CREDIT_EXP_BALANCE,--授信敞口余额
           sum(t.LOAN_EXP_BALANCE)   LOAN_EXP_BALANCE,--贷款敞口余额
           sum(t.LIMIT_CREDIT)       LIMIT_CREDIT,--授信额度
           sum(t.LIMIT_AVI)          LIMIT_AVI,--授信额度可用
           sum(t.LIMIT_USED)         LIMIT_USED,--授信额度已用
           sum(t.EXP_USED)           EXP_USED,--敞口已用
           sum(t.EXP_AVI)            EXP_AVI,--敞口可用
           t.ORG_NUM,
           V_WORK_TIME               CREATE_TIME,
           V_WORK_TIME               UPDATE_TIME
    from TB_CRD_STATIS t
    where t.CUSTOMER_NUM = V_CUSTOMER_NUM
    group by t.CUSTOMER_NUM, t.ORG_NUM;


END;

