package com.song;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
@SpringBootApplication
@Slf4j
public class SeckillApplication {
    public static void main(String[] args) {
        log.info("seckillApplication start");
       SpringApplication.run(SeckillApplication.class, args);
    }
}