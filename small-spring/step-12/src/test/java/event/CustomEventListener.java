package event;

import com.imwj.springframework.context.ApplicationListener;

import java.util.Date;

/**
 * @author wj
 * @create 2022-11-24 17:35
 */
public class CustomEventListener implements ApplicationListener<CustomEvent> {

    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("收到：" + event.getSource() + "消息;时间：" + new Date());
        System.out.println("消息：" + event.getId() + ":" + event.getMessage());
    }

}