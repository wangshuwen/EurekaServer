package com.cst.xinhe.kafka.consumer.service;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.cst.xinhe.kafka.consumer.service.consumer.GasKafka;
import com.cst.xinhe.kafka.consumer.service.context.SpringContextUtil;
import com.cst.xinhe.kafka.consumer.service.service.KafkaConsumerService;
import com.cst.xinhe.kafka.consumer.service.service.impl.KafkaConsumerServiceImpl;
import com.cst.xinhe.kafka.consumer.service.util.CheckPointWithPolygon;
import com.cst.xinhe.kafka.consumer.service.util.ICheckPointWithPolygon;
import com.cst.xinhe.kafka.consumer.service.util.ObserverableOfPoint;
import com.cst.xinhe.persistence.dao.warning_area.WarningAreaMapper;
import com.cst.xinhe.persistence.dao.warning_area.WarningAreaRecordMapper;
import com.cst.xinhe.persistence.model.warning_area.WarningArea;
import com.cst.xinhe.persistence.model.warning_area.WarningAreaRecord;
import com.cst.xinhe.persistence.model.warning_area.WarningAreaRecordExample;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@EnableTransactionManagement
@MapperScan("com.cst.xinhe.persistence.dao")
@EnableFeignClients
@EnableHystrix
@EnableEurekaClient
@SpringBootApplication
public class KafkaConsumerServiceApplication  implements ApplicationRunner  {


    @Resource
    private WarningAreaRecordMapper warningAreaRecordMapper;

    @Resource
    private WarningAreaMapper warningAreaMapper;

    @Resource
    private KafkaConsumerService kafkaConsumerService;

    @Resource
    ObserverableOfPoint observerableOfPoint;

    public static void main(String[] args) {

        SpringApplication.run(KafkaConsumerServiceApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        observerableOfPoint.register(SpringContextUtil.getBean(CheckPointWithPolygon.class));
        kafkaConsumerService.updateWarningAreaInfo();

        //项目启动后执行，解决项目重启后，重点限制区域的set为空
        WarningAreaRecordExample example = new WarningAreaRecordExample();
        example.createCriteria().andOutTimeIsNull();
        List<WarningAreaRecord> records = warningAreaRecordMapper.selectByExample(example);
        if(records!=null&&records.size()>0){
            for (WarningAreaRecord record : records) {
                Integer staffId = record.getStaffId();
                GasKafka.areaSet.add(staffId);
                WarningArea warningArea = warningAreaMapper.selectByPrimaryKey(record.getWarningAreaId());
                if(warningArea.getWarningAreaType()==1){
                    GasKafka.importantArea.add(staffId);
                }else{
                    GasKafka.limitArea.add(staffId);
                }
            }
        }

    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        System.out.println("fastjson配置读取");
//        super.configureMessageConverters(converters);
//        //调用父类的配置
//        //创建fastJson消息转换器
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        //创建配置类
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        //修改配置返回内容的过滤
//        fastJsonConfig.setSerializerFeatures(
////                SerializerFeature.DisableCircularReferenceDetect,
//                SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteNullStringAsEmpty
//        );
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        //处理中文乱码问题
//        List<MediaType> fastMediaTypes = new ArrayList<>();
//        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        fastConverter.setSupportedMediaTypes(fastMediaTypes);
//        converters.add(fastConverter);
//        //将FastJson添加到视图消息转换器列表内
//        converters.add(fastConverter);
//    }
}
