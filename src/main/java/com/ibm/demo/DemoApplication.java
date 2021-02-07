package com.ibm.demo;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.MongoSimpleTypes;

@SpringBootApplication
//@EnableMongoRepositories(basePackages = { "com.ibm.demo" })
public class DemoApplication {

	public static void main(String[] args) {
		//SpringApplication.run(DemoApplication.class, args);
		
		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
		
		System.out.println("Let's inspect the beans provided by Spring Boot:");

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}

	@Bean
	public MongoMappingContext mongoMappingContext() {
	    MongoMappingContext context = new MongoMappingContext();
	    context.setSimpleTypeHolder(new SimpleTypeHolder(new HashSet<>(Arrays.asList(
	    		ZonedDateTime.class,
	            LocalDateTime.class
	    )), MongoSimpleTypes.HOLDER));
	    return context;
	}

	@Bean
	public MongoCustomConversions customConversions() {
	    List<Converter<?, ?>> converterList = new ArrayList<Converter<?, ?>>();
	    converterList.add(new MongoLocalDateTimeFromStringConverter());
	    converterList.add(new MongoZonedDateTimeFromStringConverter());
	    return new MongoCustomConversions(converterList);
	}

	private static final class MongoLocalDateTimeFromStringConverter implements Converter<String, LocalDateTime> {
	    @Override
	    public LocalDateTime convert(String source) {
	        return source == null ? null : LocalDateTime.parse(source);
	    }
	}

	private static final class MongoZonedDateTimeFromStringConverter implements Converter<String, ZonedDateTime> {
	    @Override
	    public ZonedDateTime convert(String source) {
	        return source == null ? null : ZonedDateTime.parse(source);
	    }
	}

}
