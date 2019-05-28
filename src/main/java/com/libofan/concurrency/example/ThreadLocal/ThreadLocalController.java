package com.libofan.concurrency.example.ThreadLocal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/threadLocal") // 映射到这个接口
public class ThreadLocalController {
    @RequestMapping("/test")
    @ResponseBody // 返回对象就是响应的body
    public Long test() {
        return RequestHolder.get();
    }
}
