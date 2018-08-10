package cache;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Context {
    public static final ClassPathXmlApplicationContext context ;
    static{
        context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
    }
    public static ClassPathXmlApplicationContext getContext() {
        return context;
    }
}