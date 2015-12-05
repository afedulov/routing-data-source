package website.fedulov.routing;

public class DbContextHolder {

   private static final ThreadLocal<DbType> contextHolder =
            new ThreadLocal<DbType>();

   public static void setDbType(DbType dbType) {
       if(dbType == null){
           throw new NullPointerException();
       }
      contextHolder.set(dbType);
   }

   public static DbType getDbType() {
      return (DbType) contextHolder.get();
   }

   public static void clearDbType() {
      contextHolder.remove();
   }
}