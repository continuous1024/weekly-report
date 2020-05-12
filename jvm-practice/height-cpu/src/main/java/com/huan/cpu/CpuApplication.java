package com.huan.cpu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@RestController
@Slf4j
public class CpuApplication {

	@GetMapping(path = "/")
	public String home() {
		List<String> list = new ArrayList<>();
		int i = 0;
		while(i < 1024 * 1024 * 1024) {
			log.info("一刻不停的处理任务");
			list.add(new String(new byte[1024 * 1024]) + "处理任务分配一个1M的对象，序号为 " + i);
			i++;
			try {
				// 每次任务处理完成之后，休息100毫秒。
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				log.info(e.getMessage());
				Thread.currentThread().interrupt();
			}
		}
		return "Hello, CPU";
	}

	public static void main(String[] args) {
		SpringApplication.run(CpuApplication.class, args);
	}
}
