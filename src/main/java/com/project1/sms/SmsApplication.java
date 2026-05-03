package com.project1.sms;

import org.joda.time.DateTime;
import org.joda.time.chrono.EthiopicChronology;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmsApplication {

	public static void main(String[] args) {
		DateTime dateTime = DateTime.now();
		DateTime ethDatTime =dateTime.withChronology(EthiopicChronology.getInstance());

		System.out.println(ethDatTime.getYear());
		SpringApplication.run(SmsApplication.class, args);
	}

}
