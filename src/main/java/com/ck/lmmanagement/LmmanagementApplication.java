package com.ck.lmmanagement;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.MultipartConfigElement;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * springboot的启动类
 */
@SpringBootApplication
@EnableTransactionManagement
@PropertySource("classpath:config/sys.properties")
public class LmmanagementApplication {
	/**
	 * 这个并不会覆盖掉Jackson，只不过添加了一个比默认的更优先的HttpMessageConverter
	 * @return
	 */
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		FastJsonConfig config = new FastJsonConfig();
		config.setSerializerFeatures(
				// 保留map空的字段
				SerializerFeature.WriteMapNullValue,
				// 将String类型的null转成""
				SerializerFeature.WriteNullStringAsEmpty,
				// 将Number类型的null转成0
				SerializerFeature.WriteNullNumberAsZero,
				// 将List类型的null转成[]
				SerializerFeature.WriteNullListAsEmpty,
				// 将Boolean类型的null转成false
				SerializerFeature.WriteNullBooleanAsFalse,
				// 避免循环引用
				SerializerFeature.DisableCircularReferenceDetect);
		converter.setFastJsonConfig(config);
		converter.setDefaultCharset(Charset.forName("UTF-8"));
		List<MediaType> mediaTypeList = new ArrayList<>();
		// 解决中文乱码问题，相当于在Controller上的@RequestMapping中加了个属性produces = "application/json"
		mediaTypeList.add(MediaType.APPLICATION_JSON);
		converter.setSupportedMediaTypes(mediaTypeList);
		HttpMessageConverter<?> mesConverter = converter;
		return new HttpMessageConverters(mesConverter);
	}

	public static void main(String[] args) {
		SpringApplication.run(LmmanagementApplication.class, args);
	}
}
