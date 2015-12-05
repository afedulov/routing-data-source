package website.fedulov.aspect;

import website.fedulov.routing.DbContextHolder;
import website.fedulov.routing.DbType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ReadOnlyConnectionInterceptor implements Ordered {
    private static final Logger log = LoggerFactory.getLogger(ReadOnlyConnectionInterceptor.class);

	private int order;

	@Value("20")
	public void setOrder(int order) {
        System.out.println("ReadOnlyConnectionInterceptor >>> order = 20");
        this.order = order;
	}

	@Override
	public int getOrder() {
		return order;
	}

    @Pointcut(value="execution(public * *(..))")
    public void anyPublicMethod() { }

	@Around("@annotation(readOnlyConnection)")
	public Object proceed(ProceedingJoinPoint pjp,
			ReadOnlyConnection readOnlyConnection) throws Throwable {

		try {
            DbContextHolder.setDbType(DbType.REPLICA1);
            Object result = pjp.proceed();
            DbContextHolder.clearDbType();
			return result;
		} finally {
            DbContextHolder.clearDbType();
		}
	}
}
