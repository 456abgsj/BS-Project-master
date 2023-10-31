package example.controller;
import example.dao.DailyJDBCTemplate;
import example.dao.PrivateBooksJDBCTemplate;
import example.dao.UserJDBCTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/Hello")
public class DeleteUserController {
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/delete_user/{user}", method = RequestMethod.GET)
    public Map<String, String> DeleteUser(@PathVariable String user) {
        Map<String, String> resultMap = new HashMap<String, String>();
        System.out.println("DeleteUser, user: " + user);
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("file:D://BS-Project-master/web/applicationContext.xml");
            UserJDBCTemplate temp = (UserJDBCTemplate) context.getBean("userJDBCTemplate");
            temp.delete(user);
            PrivateBooksJDBCTemplate pTemp = (PrivateBooksJDBCTemplate) context.getBean("privateBooksJDBCTemplate");
            pTemp.deleteAllOfUser(user);
            DailyJDBCTemplate dailyTemp = (DailyJDBCTemplate) context.getBean("dailyJDBCTemplate");
            dailyTemp.batchDelete(user);
            resultMap.put("info", "success");
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("info", "error");
            return resultMap;
        }
    }
}
